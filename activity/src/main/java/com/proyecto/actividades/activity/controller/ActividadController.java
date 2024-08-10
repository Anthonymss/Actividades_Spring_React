package com.proyecto.actividades.activity.controller;

import com.proyecto.actividades.activity.model.dto.ActividadDTO;
import com.proyecto.actividades.activity.model.dto.UsuarioDTO;
import com.proyecto.actividades.activity.model.entity.Actividad;
import com.proyecto.actividades.activity.model.entity.Asignacion;
import com.proyecto.actividades.activity.model.entity.Usuario;
import com.proyecto.actividades.activity.service.ActividadService;
import com.proyecto.actividades.activity.service.AsignacionService;
import com.proyecto.actividades.activity.service.UsuarioService;
import com.proyecto.actividades.activity.util.Estado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/actividades")
public class ActividadController {

    @Autowired
    private ActividadService actividadService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private AsignacionService asignacionService;
    @GetMapping
    public ResponseEntity<List<ActividadDTO>> listarActividades() {
        List<Actividad> actividades = actividadService.listarActividades();
        List<ActividadDTO> usuActividadDTOS=new ArrayList<>();
        usuActividadDTOS=actividades.stream().map(
                actividad -> convertir(actividad)
        ).collect(Collectors.toList());
        return new ResponseEntity<>(usuActividadDTOS, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActividadDTO> obtenerActividadPorId(@PathVariable Long id) {
        Actividad actividad = actividadService.obtenerActividadPorId(id).get();
        return new ResponseEntity<>(convertir(actividad), HttpStatus.OK);
    }

    @GetMapping("/generar/{id}")
    public ResponseEntity<?> generarActividades(@PathVariable Long id) {
        List<Asignacion> listarActividades = asignacionService.listarAsignacionesPorUsuario(id);
        int con = 0;
        LocalDateTime now = LocalDateTime.now();

        for (Asignacion asignacion : listarActividades) {
            if (asignacion.getEstado() == Estado.PENDIENTE && !asignacion.getTiempoAsignado().isBefore(now)) {
                con++;
            }
        }

        if (con >= 3) {
            return new ResponseEntity<>("Completar sus Pendientes", HttpStatus.NOT_FOUND);
        }

        List<Actividad> actividades = actividadService.listarActividades();
        Random random = new Random();
        if (actividades.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        int index = random.nextInt(actividades.size());
        Actividad actividadGenerada = actividades.get(index);

        return new ResponseEntity<>(convertir(actividadGenerada), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ActividadDTO> guardarActividad(@RequestBody ActividadDTO  actividadDTO) {
        try {
            Actividad actividad = Actividad.builder()
                    .nombre(actividadDTO.getNombre())
                    .categoria(actividadDTO.getCategoria())
                    .build();
            Actividad nuevaActividad= actividadService.guardarActividad(actividad);
            actividadDTO.setId(nuevaActividad.getId());
        }catch (Exception e) {
            throw new IllegalStateException(e.getMessage());
        }
        return new ResponseEntity<>(actividadDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Actividad> actualizarActividad(@PathVariable Long id, @RequestBody Actividad actividad) {
        actividad.setId(id);
        Actividad actividadActualizada = actividadService.actualizarActividad(id, actividad);
        return new ResponseEntity<>(actividadActualizada, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarActividad(@PathVariable Long id) {
        actividadService.eliminarActividad(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    private ActividadDTO convertir(Actividad actividad){
        return ActividadDTO.builder()
                .id(actividad.getId())
                .nombre(actividad.getNombre())
                .categoria(actividad.getCategoria())
                .build();
    }
}