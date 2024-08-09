package com.proyecto.actividades.activity.controller;

import com.proyecto.actividades.activity.model.dto.ActividadDTO;
import com.proyecto.actividades.activity.model.dto.AsignacionDTO;
import com.proyecto.actividades.activity.model.dto.UsuarioDTO;
import com.proyecto.actividades.activity.model.entity.Actividad;
import com.proyecto.actividades.activity.model.entity.Asignacion;
import com.proyecto.actividades.activity.model.entity.Usuario;
import com.proyecto.actividades.activity.service.ActividadService;
import com.proyecto.actividades.activity.service.AsignacionService;
import com.proyecto.actividades.activity.service.UsuarioService;
import com.proyecto.actividades.activity.util.Estado;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/asignacion")
public class AsignacionController {

    @Autowired
    private AsignacionService asignacionService;

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private ActividadService actividadService;
    @GetMapping
    public ResponseEntity<List<Asignacion>> listarAsignaciones() {
        List<Asignacion> asignaciones = asignacionService.listarAsignaciones();
        return new ResponseEntity<>(asignaciones, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Asignacion> obtenerAsignacionPorId(@PathVariable Long id) {
        Asignacion asignacion = asignacionService.obtenerAsignacionPorId(id);
        return new ResponseEntity<>(asignacion, HttpStatus.OK);
    }

    @PostMapping("/{idUser}/{idActivity}")
    public ResponseEntity<String> guardarAsignacion(@PathVariable Long idUser, @PathVariable Long idActivity) {
        try {
            Usuario usuario = usuarioService.obtenerUsuarioPorId(idUser);
            Actividad actividad = actividadService.obtenerActividadPorId(idActivity).get();
            Asignacion asignacion = new Asignacion();
            asignacion.setUsuario(usuario);
            asignacion.setActividad(actividad);
            asignacion.setTiempoAsignado(LocalDateTime.now().plus((int)(Math.random() * 10) + 5, ChronoUnit.HOURS));
            asignacion.setEstado(Estado.PENDIENTE);
            asignacion.setPuntos((long)(Math.random() * (16)) + 20);
            asignacionService.guardarAsignacion(asignacion);
            return new ResponseEntity<>("Asignación creada exitosamente.", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Asignacion> actualizarAsignacion(@PathVariable Long id, @RequestBody Asignacion asignacion) {
        asignacion.setId(id);
        Asignacion asignacionActualizada = asignacionService.actualizarAsignacion(id, asignacion);
        return new ResponseEntity<>(asignacionActualizada, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAsignacion(@PathVariable Long id) {
        asignacionService.eliminarAsignacion(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<AsignacionDTO>> listarAsignacionesPorUsuario(@PathVariable Long idUsuario) {
        List<Asignacion> asignaciones = asignacionService.listarAsignacionesPorUsuario(idUsuario);
        List<AsignacionDTO> asignacionDTOList = asignaciones.stream()
                .map(this::convertir)
                .collect(Collectors.toList());
        return new ResponseEntity<>(asignacionDTOList, HttpStatus.OK);
    }

    private AsignacionDTO convertir(Asignacion asignacion) {
        return AsignacionDTO.builder()
                .id(asignacion.getId())
                .actividadNombre(asignacion.getActividad().getNombre())
                .usuarioNombre(asignacion.getUsuario().getNombre())
                .tiempoAsignado(asignacion.getTiempoAsignado())
                .estado(asignacion.getEstado())
                .categoria(asignacion.getActividad().getCategoria())
                .puntos(asignacion.getPuntos())
                .build();
    }
    @PutMapping("/completar/{id}")
    public ResponseEntity<AsignacionDTO> finalizarAsignacion(@PathVariable Long id) {
        try {
            Asignacion asignacion1=asignacionService.obtenerAsignacionPorId(id);
            Long puntos=asignacion1.getPuntos();
            Usuario usuario=usuarioService.obtenerUsuarioPorId(asignacion1.getUsuario().getId());
            usuario.setPuntos(usuario.getPuntos()+puntos);
            asignacion1.setEstado(Estado.COMPLETADA);
            return new ResponseEntity<>(convertir(asignacionService.completarAsignacion(asignacion1)),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }
    //extras
}