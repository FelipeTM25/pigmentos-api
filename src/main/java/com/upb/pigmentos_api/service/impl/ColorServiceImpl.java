package com.upb.pigmentos_api.service.impl;

import com.upb.pigmentos_api.model.Color;
import com.upb.pigmentos_api.repository.ColorRepository;
import com.upb.pigmentos_api.service.ColorService;
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
public class ColorServiceImpl implements ColorService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ColorRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Color> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Color> findById(UUID id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Color create(Color color) {
        Session session = entityManager.unwrap(Session.class);

        session.doWork(connection -> {
            try (CallableStatement call = connection.prepareCall("CALL pigmentos.sp_crear_color(?, ?, ?)")) {
                call.setString(1, color.getNombre());
                call.setString(2, color.getCodigoHexadecimal());
                call.registerOutParameter(3, Types.OTHER);

                call.execute();

                UUID newId = (UUID) call.getObject(3);
                color.setId(newId);
            }
        });

        return color;
    }

    @Override
    @Transactional
    public Color update(UUID id, Color color) {
        Color existing = repository.findById(id)
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Color no encontrado"));

        entityManager.createStoredProcedureQuery("pigmentos.sp_actualizar_color")
                .registerStoredProcedureParameter("p_id", UUID.class, jakarta.persistence.ParameterMode.IN)
                .registerStoredProcedureParameter("p_nombre", String.class, jakarta.persistence.ParameterMode.IN)
                .registerStoredProcedureParameter("p_codigo_hexadecimal", String.class, jakarta.persistence.ParameterMode.IN)
                .setParameter("p_id", id)
                .setParameter("p_nombre", color.getNombre())
                .setParameter("p_codigo_hexadecimal", color.getCodigoHexadecimal())
                .execute();

        existing.setNombre(color.getNombre());
        existing.setCodigoHexadecimal(color.getCodigoHexadecimal());
        return existing;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new jakarta.persistence.EntityNotFoundException("Color no encontrado");
        }

        entityManager.createStoredProcedureQuery("pigmentos.sp_eliminar_color")
                .registerStoredProcedureParameter("p_id", UUID.class, jakarta.persistence.ParameterMode.IN)
                .setParameter("p_id", id)
                .execute();
    }
}
