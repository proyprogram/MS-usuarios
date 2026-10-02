package com.proyectointegrador.msusuarios.service;

import com.proyectointegrador.msusuarios.dto.request.CrearClienteDTO;
import com.proyectointegrador.msusuarios.dto.request.CrearEmpleadoDTO;
import com.proyectointegrador.msusuarios.dto.request.CrearPropietarioDTO;
import com.proyectointegrador.msusuarios.entity.Rol;
import com.proyectointegrador.msusuarios.entity.Usuario;
import com.proyectointegrador.msusuarios.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioServicio {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public Usuario crearCliente(CrearClienteDTO dto) {

        Usuario usuario = Usuario.builder()
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .documentoDeIdentidad(dto.getDocumentoDeIdentidad())
                .celular(dto.getCelular())
                .correo(dto.getCorreo())
                .clave(passwordEncoder.encode(dto.getClave()))
                .rol(Rol.CLIENTE)
                .build();

        return usuarioRepository.save(usuario);
    }

    public Usuario crearEmpleado(CrearEmpleadoDTO dto) {

        Usuario usuario = Usuario.builder()
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .documentoDeIdentidad(dto.getDocumentoDeIdentidad())
                .celular(dto.getCelular())
                .correo(dto.getCorreo())
                .clave(passwordEncoder.encode(dto.getClave()))
                .rol(Rol.EMPLEADO)
                .build();

        return usuarioRepository.save(usuario);
    }

    public Usuario crearPropietario(CrearPropietarioDTO dto) {

        Usuario usuario = Usuario.builder()
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .documentoDeIdentidad(dto.getDocumentoDeIdentidad())
                .celular(dto.getCelular())
                .correo(dto.getCorreo())
                .clave(passwordEncoder.encode(dto.getClave()))
                .rol(Rol.PROPIETARIO)
                .build();

        return usuarioRepository.save(usuario);
    }
}