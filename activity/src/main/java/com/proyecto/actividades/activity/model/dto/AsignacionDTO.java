package com.proyecto.actividades.activity.model.dto;

import com.proyecto.actividades.activity.util.Estado;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
@Data
@Builder
public class AsignacionDTO {
    private Long id;
    private String actividadNombre;
    private String usuarioNombre;
    private LocalDateTime tiempoAsignado;
    private Estado estado;
    private String categoria;
    private Long puntos;
}
