package com.proyecto.actividades.activity.service;

import com.proyecto.actividades.activity.model.entity.Asignacion;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AsignacionService {
    public List<Asignacion> listarAsignaciones();
    public Asignacion obtenerAsignacionPorId(Long id);
    public Asignacion guardarAsignacion(Asignacion asignacion);
    public void eliminarAsignacion(Long id);
    public Asignacion actualizarAsignacion(Long id, Asignacion nuevaAsignacion);
    public List<Asignacion> listarAsignacionesPorUsuario(Long idUsuario);
    public Asignacion completarAsignacion(Asignacion asignacion);
}
