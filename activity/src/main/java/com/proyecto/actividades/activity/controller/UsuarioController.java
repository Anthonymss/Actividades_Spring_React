package com.proyecto.actividades.activity.controller;

import com.proyecto.actividades.activity.model.dto.UsuarioDTO;
import com.proyecto.actividades.activity.model.entity.Usuario;
import com.proyecto.actividades.activity.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping()
    public ResponseEntity<?> getUsuarios() {
        try {
            List<Usuario>listaUsuarios = usuarioService.listarUsuarios();
            List<UsuarioDTO> usuarioDTOList= new ArrayList<UsuarioDTO>();
            usuarioDTOList=listaUsuarios.stream().map(
                    usuario -> convertir(usuario)
            ).collect(Collectors.toList());
            return ResponseEntity.ok(usuarioDTOList);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    private UsuarioDTO convertir(Usuario usuario){
        return UsuarioDTO.builder()
                .nombre(usuario.getNombre())
                .apellido(usuario.getApellido())
                .puntos(usuario.getPuntos())
                .username(usuario.getUsername())
                .image(usuario.getImage())
                .id(usuario.getId())
                .build();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable Long id) {
        Usuario usuario = usuarioService.obtenerUsuarioPorId(id);
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Usuario> guardarUsuario(@RequestBody Usuario usuario) {
        Usuario nuevoUsuario = usuarioService.guardarUsuario(usuario);
        nuevoUsuario.setPuntos(0L);
        return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        usuario.setId(id);
        Usuario usuarioActualizado = usuarioService.actualizarUsuario(usuario);
        return new ResponseEntity<>(usuarioActualizado, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        try {
            Usuario usuario = usuarioService.login(username, password);
            if (usuario != null) {
                return new ResponseEntity<>(convertir(usuario), HttpStatus.OK);
            }
            return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred during login", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/{id}/puntuacion")
    public ResponseEntity<String> actualizarPuntuacion(@PathVariable Long id, @RequestBody Usuario usuario) {
        usuario.setId(id);
        String mensaje = usuarioService.actualizarPuntuacion(usuario);
        return new ResponseEntity<>(mensaje, HttpStatus.OK);
    }

    @GetMapping("/search/{idUser}")
    public ResponseEntity<?>InfoByIdUser( @PathVariable Long idUser) {
        try {
            Usuario usuario=usuarioService.obtenerUsuarioPorId(idUser);
            return new ResponseEntity<>(convertir(usuario), HttpStatus.OK);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
