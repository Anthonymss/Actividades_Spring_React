package com.proyecto.actividades.activity.repository;

import com.proyecto.actividades.activity.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
    Optional<Usuario> findByUsernameAndPassword(String nombre, String password);
}
