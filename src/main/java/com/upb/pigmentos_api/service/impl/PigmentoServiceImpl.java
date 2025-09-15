package com.upb.pigmentos_api.service.impl;

import com.upb.pigmentos_api.model.Pigmento;
import com.upb.pigmentos_api.repository.PigmentoRepository;
import com.upb.pigmentos_api.service.PigmentoService;
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
public class PigmentoServiceImpl implements PigmentoService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PigmentoRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Pigmento> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Pigmento> findById(UUID id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Pigmento create(Pigmento pigmento) {
        Session session = entityManager.unwrap(Session.class);

        session.doWork(connection -> {
            try (CallableStatement call = connection.prepareCall("CALL pigmentos.sp_crear_pigmento(?, ?, ?, ?, ?, ?)")) {
                call.setString(1, pigmento.getNombreComercial());
                call.setString(2, pigmento.getFormulaQuimica());
                call.setString(3, pigmento.getNumeroCi());
                call.setObject(4, pigmento.getFamiliaQuimica().getId());
                call.setObject(5, pigmento.getColorPrincipal().getId());
                call.registerOutParameter(6, Types.OTHER);

                call.execute();

                UUID newId = (UUID) call.getObject(6);
                pigmento.setId(newId);
            }
        });

        return pigmento;
    }

    @Override
    @Transactional
    public Pigmento update(UUID id, Pigmento pigmento) {
        Pigmento existing = repository.findById(id)
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Pigmento no encontrado"));

        entityManager.createStoredProcedureQuery("pigmentos.sp_actualizar_pigmento")
                .registerStoredProcedureParameter("p_id", UUID.class, jakarta.persistence.ParameterMode.IN)
                .registerStoredProcedureParameter("p_nombre_comercial", String.class, jakarta.persistence.ParameterMode.IN)
                .registerStoredProcedureParameter("p_formula_quimica", String.class, jakarta.persistence.ParameterMode.IN)
                .registerStoredProcedureParameter("p_numero_ci", String.class, jakarta.persistence.ParameterMode.IN)
                .registerStoredProcedureParameter("p_familia_quimica", UUID.class, jakarta.persistence.ParameterMode.IN)
                .registerStoredProcedureParameter("p_color_principal", UUID.class, jakarta.persistence.ParameterMode.IN)
                .setParameter("p_id", id)
                .setParameter("p_nombre_comercial", pigmento.getNombreComercial())
                .setParameter("p_formula_quimica", pigmento.getFormulaQuimica())
                .setParameter("p_numero_ci", pigmento.getNumeroCi())
                .setParameter("p_familia_quimica", pigmento.getFamiliaQuimica().getId())
                .setParameter("p_color_principal", pigmento.getColorPrincipal().getId())
                .execute();

        existing.setNombreComercial(pigmento.getNombreComercial());
        existing.setFormulaQuimica(pigmento.getFormulaQuimica());
        existing.setNumeroCi(pigmento.getNumeroCi());
        existing.setFamiliaQuimica(pigmento.getFamiliaQuimica());
        existing.setColorPrincipal(pigmento.getColorPrincipal());
        return existing;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new jakarta.persistence.EntityNotFoundException("Pigmento no encontrado");
        }

        entityManager.createStoredProcedureQuery("pigmentos.sp_eliminar_pigmento")
                .registerStoredProcedureParameter("p_id", UUID.class, jakarta.persistence.ParameterMode.IN)
                .setParameter("p_id", id)
                .execute();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pigmento> findByFamiliaQuimicaId(UUID familiaId) {
        return repository.findByFamiliaQuimicaId(familiaId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pigmento> findByColorPrincipalId(UUID colorId) {
        return repository.findByColorPrincipalId(colorId);
    }
}
