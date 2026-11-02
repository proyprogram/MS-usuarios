package com.proyectointegrador.msusuarios.service;

import com.proyectointegrador.msusuarios.dto.request.CrearAdministradorDTO;
import com.proyectointegrador.msusuarios.dto.request.CrearClienteDTO;
import com.proyectointegrador.msusuarios.dto.request.CrearEmpleadoDTO;
import com.proyectointegrador.msusuarios.dto.request.CrearPropietarioDTO;
import com.proyectointegrador.msusuarios.dto.response.ClienteCelularDTO;
import com.proyectointegrador.msusuarios.entity.Rol;
import com.proyectointegrador.msusuarios.entity.Usuario;
import com.proyectointegrador.msusuarios.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioServicioTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioServicio usuarioServicio;

    @Test
    void crearCliente_deberiaGuardarUsuarioConRolClienteYClaveEncriptada() {
        CrearClienteDTO dto = CrearClienteDTO.builder()
                .nombre("Juan")
                .apellido("Perez")
                .documentoDeIdentidad("123456")
                .celular("3001234567")
                .correo("juan@test.com")
                .clave("clave123")
                .build();

        when(passwordEncoder.encode("clave123")).thenReturn("claveEncriptada");
        when(usuarioRepository.save(any(Usuario.class)))
                .thenAnswer(invocacion -> invocacion.getArgument(0));

        Usuario resultado = usuarioServicio.crearCliente(dto);

        assertEquals("Juan", resultado.getNombre());
        assertEquals(Rol.CLIENTE, resultado.getRol());
        assertEquals("claveEncriptada", resultado.getClave());
        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    void crearEmpleado_deberiaGuardarUsuarioConRolEmpleadoYRestauranteAsociado() {
        CrearEmpleadoDTO dto = CrearEmpleadoDTO.builder()
                .nombre("Pedro")
                .apellido("Gomez")
                .documentoDeIdentidad("654321")
                .celular("3009876543")
                .correo("pedro@test.com")
                .clave("clave456")
                .idRestaurante(1L)
                .build();

        when(passwordEncoder.encode("clave456")).thenReturn("claveEncriptada2");
        when(usuarioRepository.save(any(Usuario.class)))
                .thenAnswer(invocacion -> invocacion.getArgument(0));

        Usuario resultado = usuarioServicio.crearEmpleado(dto);

        assertEquals(Rol.EMPLEADO, resultado.getRol());
        assertEquals(1L, resultado.getIdRestaurante());
        assertEquals("claveEncriptada2", resultado.getClave());
        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    void crearPropietario_deberiaGuardarUsuarioConRolPropietario() {
        CrearPropietarioDTO dto = CrearPropietarioDTO.builder()
                .nombre("Laura")
                .apellido("Martinez")
                .documentoDeIdentidad("789012")
                .celular("3012345678")
                .correo("laura@test.com")
                .clave("clave789")
                .build();

        when(passwordEncoder.encode("clave789")).thenReturn("claveEncriptada3");
        when(usuarioRepository.save(any(Usuario.class)))
                .thenAnswer(invocacion -> invocacion.getArgument(0));

        Usuario resultado = usuarioServicio.crearPropietario(dto);

        assertEquals(Rol.PROPIETARIO, resultado.getRol());
        assertEquals("laura@test.com", resultado.getCorreo());
        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    void crearAdministrador_deberiaGuardarUsuarioConRolAdministrador_siCorreoNoExiste() {
        CrearAdministradorDTO dto = CrearAdministradorDTO.builder()
                .nombre("Admin")
                .apellido("Test")
                .documentoDeIdentidad("000111")
                .celular("3000000000")
                .correo("admin2@test.com")
                .clave("adminClave")
                .build();

        when(usuarioRepository.findByCorreo("admin2@test.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("adminClave")).thenReturn("claveEncriptadaAdmin");
        when(usuarioRepository.save(any(Usuario.class)))
                .thenAnswer(invocacion -> invocacion.getArgument(0));

        Usuario resultado = usuarioServicio.crearAdministrador(dto);

        assertEquals(Rol.ADMINISTRADOR, resultado.getRol());
        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    void crearAdministrador_deberiaLanzarExcepcion_siElCorreoYaExiste() {
        CrearAdministradorDTO dto = CrearAdministradorDTO.builder()
                .nombre("Admin")
                .apellido("Test")
                .documentoDeIdentidad("000111")
                .celular("3000000000")
                .correo("admin@test.com")
                .clave("adminClave")
                .build();

        when(usuarioRepository.findByCorreo("admin@test.com"))
                .thenReturn(Optional.of(new Usuario()));

        IllegalArgumentException excepcion = assertThrows(
                IllegalArgumentException.class,
                () -> usuarioServicio.crearAdministrador(dto)
        );

        assertEquals(
                "Ya existe un usuario registrado con el correo admin@test.com.",
                excepcion.getMessage()
        );
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    void consultarCelularCliente_deberiaRetornarCelular_siElUsuarioExisteYEsCliente() {
        Usuario cliente = Usuario.builder()
                .id(3)
                .rol(Rol.CLIENTE)
                .celular("3007654321")
                .build();

        when(usuarioRepository.findById(3)).thenReturn(Optional.of(cliente));

        ClienteCelularDTO resultado = usuarioServicio.consultarCelularCliente(3);

        assertEquals("3007654321", resultado.getCelular());
    }

    @Test
    void consultarCelularCliente_deberiaLanzarExcepcion_siElUsuarioNoExiste() {
        when(usuarioRepository.findById(999)).thenReturn(Optional.empty());

        IllegalArgumentException excepcion = assertThrows(
                IllegalArgumentException.class,
                () -> usuarioServicio.consultarCelularCliente(999)
        );

        assertEquals("No existe el usuario con ID: 999", excepcion.getMessage());
    }

    @Test
    void consultarCelularCliente_deberiaLanzarExcepcion_siElUsuarioNoEsCliente() {
        Usuario empleado = Usuario.builder()
                .id(4)
                .rol(Rol.EMPLEADO)
                .celular("3001112222")
                .build();

        when(usuarioRepository.findById(4)).thenReturn(Optional.of(empleado));

        IllegalArgumentException excepcion = assertThrows(
                IllegalArgumentException.class,
                () -> usuarioServicio.consultarCelularCliente(4)
        );

        assertEquals("El usuario indicado no es un cliente.", excepcion.getMessage());
    }
}

