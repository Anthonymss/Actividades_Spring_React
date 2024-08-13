package com.proyecto.actividades.activity.service.Impl;

import com.proyecto.actividades.activity.model.entity.Actividad;
import com.proyecto.actividades.activity.repository.ActividadRepository;
import com.proyecto.actividades.activity.repository.AsignacionRepository;
import com.proyecto.actividades.activity.service.ActividadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ActividadServiceImpl implements ActividadService {
    @Autowired
    private ActividadRepository actividadRepository;

    @Override
    public List<Actividad> listarActividades() {
        return actividadRepository.findAll();
    }

    @Override
    public Optional<Actividad> obtenerActividadPorId(Long id) {
        return actividadRepository.findById(id);
    }

    @Override
    public Actividad guardarActividad(Actividad actividad) {
        return actividadRepository.save(actividad);
    }

    @Override
    public Actividad actualizarActividad(Long id, Actividad actividad) {
        Optional<Actividad> actividadOptional = actividadRepository.findById(id);
            Actividad actividadUpdate = actividadOptional.get();
            actividadUpdate.setId(id);
            actividadUpdate.setNombre(actividad.getNombre());
            actividadUpdate.setCategoria(actividad.getCategoria());
            return actividadRepository.save(actividadUpdate);
    }

    @Override
    public String eliminarActividad(Long id) {
        Optional<Actividad> actividadOptional = actividadRepository.findById(id);
        if(actividadOptional.isPresent()){
            actividadRepository.deleteById(id);
            return "La actividad con id: " + id + " ha sido eliminada";
        } else {
            return "No se encontró la actividad con el id: " + id;
        }
    }

    @Override
    public List<Actividad> buscarActividadesPorCategoria(String categoria) {
        return List.of();
    }

    @Override
    public String guardarListarActividades(List<Actividad> actividadList) {
        if (actividadList == null || actividadList.isEmpty()) {
            return "No hay actividades para guardar";
        }

        try {
            actividadRepository.saveAll(actividadList);
        } catch (Exception e) {
            e.printStackTrace(); // Para obtener más información sobre la excepción
            return "Error al guardar las actividades: " + e.getMessage();
        }

        return "Se han guardado y listado las actividades";
    }

}
