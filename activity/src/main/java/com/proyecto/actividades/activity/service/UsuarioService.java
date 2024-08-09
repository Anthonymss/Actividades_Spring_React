package com.proyecto.actividades.activity.service;

import com.proyecto.actividades.activity.model.entity.Usuario;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UsuarioService {
    public List<Usuario> listarUsuarios();
    public Usuario obtenerUsuarioPorId(Long id);
    public Usuario guardarUsuario(Usuario usuario);
    public Usuario actualizarUsuario(Usuario usuario);
    public void eliminarUsuario(Long id);
    public Usuario login(String username, String password);
    public String actualizarPuntuacion(Usuario usuario);
}
