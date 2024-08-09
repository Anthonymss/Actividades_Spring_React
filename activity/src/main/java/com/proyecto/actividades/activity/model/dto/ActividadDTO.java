package com.proyecto.actividades.activity.model.dto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ActividadDTO {
    private Long id;
    private String nombre;
    private String categoria;
}
