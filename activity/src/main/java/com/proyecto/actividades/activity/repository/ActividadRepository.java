package com.proyecto.actividades.activity.repository;

import com.proyecto.actividades.activity.model.entity.Actividad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActividadRepository extends JpaRepository<Actividad,Long> {
}
