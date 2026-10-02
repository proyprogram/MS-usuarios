package com.proyectointegrador.msusuarios.controller;

import com.proyectointegrador.msusuarios.dto.request.CrearClienteDTO;
import com.proyectointegrador.msusuarios.dto.request.CrearEmpleadoDTO;
import com.proyectointegrador.msusuarios.dto.request.LoginRequestDTO;
import com.proyectointegrador.msusuarios.dto.response.LoginResponseDTO;
import com.proyectointegrador.msusuarios.entity.Usuario;
import com.proyectointegrador.msusuarios.service.AuthServicio;
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
    private final AuthServicio authServicio;

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

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @Valid @RequestBody LoginRequestDTO dto) {

        LoginResponseDTO respuesta = authServicio.login(dto);

        return ResponseEntity.ok(respuesta);
    }
}
