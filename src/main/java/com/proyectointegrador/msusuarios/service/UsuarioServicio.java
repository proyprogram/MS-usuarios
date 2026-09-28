package com.proyectointegrador.msusuarios.service;

import com.proyectointegrador.msusuarios.dto.CrearClienteDTO;
import com.proyectointegrador.msusuarios.entity.Usuario;
import com.proyectointegrador.msusuarios.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.mindrot.BCrypt;

@Service
@RequiredArgsConstructor
public class UsuarioServicio {

    private final UsuarioRepository usuarioRepository;

    public Usuario crearCliente(CrearClienteDTO dto) {

        Usuario usuario = Usuario.builder()
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .documentoDeIdentidad(dto.getDocumentoDeIdentidad())
                .celular(dto.getCelular())
                .correo(dto.getCorreo())
                .clave(BCrypt.hashpw(dto.getClave(), BCrypt.gensalt()))
                .idRol(2)
                .build();

        return usuarioRepository.save(usuario);
    }
}