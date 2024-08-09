package com.proyecto.actividades.activity.repository;

import com.proyecto.actividades.activity.model.entity.Asignacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AsignacionRepository extends JpaRepository<Asignacion,Long> {
    List<Asignacion> findByUsuarioId(Long usuarioId);
}
