package com.proyecto.actividades.activity.service;

import com.proyecto.actividades.activity.model.entity.Actividad;
import com.proyecto.actividades.activity.repository.ActividadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ActividadService {
    public List<Actividad> listarActividades();
    public Optional<Actividad> obtenerActividadPorId(Long id);
    public Actividad guardarActividad(Actividad actividad);
    public Actividad actualizarActividad(Long id, Actividad actividad);
    public String eliminarActividad(Long id);
    public List<Actividad> buscarActividadesPorCategoria(String categoria);
    public String guardarListarActividades(List<Actividad> actividadList);

}
