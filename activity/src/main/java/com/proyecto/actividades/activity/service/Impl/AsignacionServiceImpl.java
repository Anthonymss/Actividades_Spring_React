package com.proyecto.actividades.activity.service.Impl;

import com.proyecto.actividades.activity.model.entity.Asignacion;
import com.proyecto.actividades.activity.repository.AsignacionRepository;
import com.proyecto.actividades.activity.service.AsignacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AsignacionServiceImpl implements AsignacionService {
    @Autowired
    private AsignacionRepository asignacionRepository;

    @Override
    public List<Asignacion> listarAsignaciones() {
        return asignacionRepository.findAll();
    }

    @Override
    public Asignacion obtenerAsignacionPorId(Long id) {
        return asignacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asignacion no encontrada con id: " + id));
    }

    @Override
    public Asignacion guardarAsignacion(Asignacion asignacion) {
        return asignacionRepository.save(asignacion);
    }

    @Override
    public void eliminarAsignacion(Long id) {
        if (!asignacionRepository.existsById(id)) {
            throw new RuntimeException("Asignacion no encontrada con id: " + id);
        }
        asignacionRepository.deleteById(id);
    }

    @Override
    public Asignacion actualizarAsignacion(Long id, Asignacion nuevaAsignacion) {
        Optional<Asignacion> optionalAsignacion = asignacionRepository.findById(id);
        if (optionalAsignacion.isPresent()) {
            Asignacion asignacion = optionalAsignacion.get();
            asignacion.setActividad(nuevaAsignacion.getActividad());
            asignacion.setUsuario(nuevaAsignacion.getUsuario());
            asignacion.setTiempoAsignado(nuevaAsignacion.getTiempoAsignado());
            asignacion.setEstado(nuevaAsignacion.getEstado());
            return asignacionRepository.save(asignacion);
        } else {
            throw new RuntimeException("Asignacion no encontrada con id: " + id);
        }
    }

    @Override
    public List<Asignacion> listarAsignacionesPorUsuario(Long idUsuario) {
        return asignacionRepository.findByUsuarioId(idUsuario);
    }

    @Override
    public Asignacion completarAsignacion(Asignacion asignacion) {
        return asignacionRepository.save(asignacion);
    }
}
