package com.upb.pigmentos_api.service.impl;

import com.upb.pigmentos_api.exception.DuplicateResourceException;
import com.upb.pigmentos_api.model.FamiliaQuimica;
import com.upb.pigmentos_api.repository.FamiliaRepository;
import com.upb.pigmentos_api.service.FamiliaService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.CallableStatement;
import java.sql.Types;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FamiliaServiceImpl implements FamiliaService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private FamiliaRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<FamiliaQuimica> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FamiliaQuimica> findById(UUID id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public FamiliaQuimica create(FamiliaQuimica familia) {
        if (repository.findByNombre(familia.getNombre()).isPresent()) {
            throw new DuplicateResourceException("Ya existe una familia con ese nombre.");
        }

        Session session = entityManager.unwrap(Session.class);

        session.doWork(connection -> {
            try (CallableStatement call = connection.prepareCall("CALL pigmentos.sp_crear_familia_quimica(?, ?, ?)")) {
                call.setString(1, familia.getNombre());
                call.setString(2, familia.getDescripcion());
                call.registerOutParameter(3, Types.OTHER);

                call.execute();

                UUID newId = (UUID) call.getObject(3);
                familia.setId(newId);
            }
        });

        return familia;
    }

    @Override
    @Transactional
    public FamiliaQuimica update(UUID id, FamiliaQuimica familia) {
        FamiliaQuimica existing = repository.findById(id)
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Familia no encontrada"));

        Optional<FamiliaQuimica> byName = repository.findByNombre(familia.getNombre());
        if (byName.isPresent() && !byName.get().getId().equals(id)) {
            throw new DuplicateResourceException("Ya existe otra familia con ese nombre.");
        }

        existing.setNombre(familia.getNombre());
        existing.setDescripcion(familia.getDescripcion());
        return repository.save(existing);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new jakarta.persistence.EntityNotFoundException("Familia no encontrada");
        }
        repository.deleteById(id);
    }
}
