package com.proyecto.actividades.activity.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String username;
    private String image;
    private Long puntos;
}