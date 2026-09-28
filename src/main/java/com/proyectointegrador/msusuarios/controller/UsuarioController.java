package com.proyectointegrador.msusuarios.controller;

import com.proyectointegrador.msusuarios.dto.CrearClienteDTO;
import com.proyectointegrador.msusuarios.dto.CrearEmpleadoDTO;
import com.proyectointegrador.msusuarios.entity.Usuario;
import com.proyectointegrador.msusuarios.service.UsuarioServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioServicio usuarioServicio;

    @PostMapping("/cliente")
    public ResponseEntity<Usuario> crearCliente(
            @Valid @RequestBody CrearClienteDTO dto) {

        Usuario usuario = usuarioServicio.crearCliente(dto);

        return ResponseEntity.ok(usuario);
    }

    @PostMapping("/empleado")
    public ResponseEntity<Usuario> crearEmpleado(
            @Valid @RequestBody CrearEmpleadoDTO dto) {

        Usuario usuario = usuarioServicio.crearEmpleado(dto);

        return ResponseEntity.ok(usuario);
    }
}
