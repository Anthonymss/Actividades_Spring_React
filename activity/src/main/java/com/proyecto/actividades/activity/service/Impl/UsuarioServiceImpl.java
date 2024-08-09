package com.proyecto.actividades.activity.service.Impl;

import com.proyecto.actividades.activity.model.entity.Usuario;
import com.proyecto.actividades.activity.repository.UsuarioRepository;
import com.proyecto.actividades.activity.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
    }

    @Override
    public Usuario guardarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario actualizarUsuario(Usuario usuario) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findById(usuario.getId());
        if (usuarioExistente.isPresent()) {
            Usuario usuarioActualizado = usuarioExistente.get();
            usuarioActualizado.setNombre(usuario.getNombre());
            usuarioActualizado.setApellido(usuario.getApellido());
            usuarioActualizado.setPuntos(usuario.getPuntos());
            // Aquí podrías actualizar más campos si es necesario
            return usuarioRepository.save(usuarioActualizado);
        } else {
            throw new RuntimeException("Usuario no encontrado con id: " + usuario.getId());
        }
    }

    @Override
    public void eliminarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado con id: " + id);
        }
        usuarioRepository.deleteById(id);
    }

    @Override
    public Usuario login(String username, String password) {
        return usuarioRepository.findByUsernameAndPassword(username, password)
                .orElseThrow(() -> new RuntimeException("Credenciales inválidas"));
    }

    @Override
    public String actualizarPuntuacion(Usuario usuario) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findById(usuario.getId());
        if (usuarioExistente.isPresent()) {
            Usuario usuarioActualizado = usuarioExistente.get();
            usuarioActualizado.setPuntos(usuario.getPuntos());
            usuarioRepository.save(usuarioActualizado);
            return "Puntuación actualizada con éxito";
        } else {
            return "Usuario no encontrado";
        }
    }
}
