package com.proyectointegrador.msusuarios.service;

import com.proyectointegrador.msusuarios.dto.request.CrearAdministradorDTO;
import com.proyectointegrador.msusuarios.dto.request.CrearClienteDTO;
import com.proyectointegrador.msusuarios.dto.request.CrearEmpleadoDTO;
import com.proyectointegrador.msusuarios.dto.request.CrearPropietarioDTO;
import com.proyectointegrador.msusuarios.dto.response.ClienteCelularDTO;
import com.proyectointegrador.msusuarios.entity.Rol;
import com.proyectointegrador.msusuarios.entity.Usuario;
import com.proyectointegrador.msusuarios.repository.UsuarioRepository;
import com.proyectointegrador.msusuarios.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.Period;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UsuarioServicio {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RestTemplate restTemplate;

    @Value("${ms.plazoleta.url:http://localhost:8080}")
    private String msPlazoletaUrl;

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

    public Usuario crearEmpleado(CrearEmpleadoDTO dto, String authorization) {

        String token = authorization.replace("Bearer ", "");
        Long idPropietarioToken = jwtService.extractIdUsuario(token);

        Long idPropietarioRestaurante = consultarPropietarioDelRestaurante(
                dto.getIdRestaurante(), authorization);

        if (idPropietarioToken == null
                || !idPropietarioToken.equals(idPropietarioRestaurante)) {
            throw new SecurityException(
                    "Solo el propietario del restaurante puede crear empleados para él.");
        }

        Usuario usuario = Usuario.builder()
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .documentoDeIdentidad(dto.getDocumentoDeIdentidad())
                .celular(dto.getCelular())
                .correo(dto.getCorreo())
                .clave(passwordEncoder.encode(dto.getClave()))
                .rol(Rol.EMPLEADO)
                .idRestaurante(dto.getIdRestaurante())
                .build();

        return usuarioRepository.save(usuario);
    }

    private Long consultarPropietarioDelRestaurante(Long idRestaurante, String authorization) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.AUTHORIZATION, authorization);
            HttpEntity<Void> entity = new HttpEntity<>(headers);

            String url = msPlazoletaUrl + "/api/restaurantes/" + idRestaurante + "/propietario";

            Map respuesta = restTemplate.exchange(
                    url, HttpMethod.GET, entity, Map.class).getBody();

            if (respuesta == null || respuesta.get("idPropietario") == null) {
                throw new IllegalArgumentException(
                        "El restaurante con id " + idRestaurante + " no existe.");
            }

            return ((Number) respuesta.get("idPropietario")).longValue();
        } catch (HttpClientErrorException e) {
            throw new IllegalArgumentException(
                    "El restaurante con id " + idRestaurante + " no existe.");
        }
    }

    public Usuario crearPropietario(CrearPropietarioDTO dto) {

        if (usuarioRepository.existsByCorreo(dto.getCorreo())) {
            throw new IllegalArgumentException(
                    "Ya existe un usuario registrado con el correo " + dto.getCorreo() + ".");
        }

        if (usuarioRepository.existsByDocumentoDeIdentidad(dto.getDocumentoDeIdentidad())) {
            throw new IllegalArgumentException(
                    "Ya existe un usuario registrado con el documento " + dto.getDocumentoDeIdentidad() + ".");
        }

        int edad = Period.between(dto.getFechaDeNacimiento(), LocalDate.now()).getYears();
        if (edad < 18) {
            throw new IllegalArgumentException(
                    "El propietario debe ser mayor de edad (18 años o más).");
        }

        Usuario usuario = Usuario.builder()
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .documentoDeIdentidad(dto.getDocumentoDeIdentidad())
                .celular(dto.getCelular())
                .correo(dto.getCorreo())
                .clave(passwordEncoder.encode(dto.getClave()))
                .rol(Rol.PROPIETARIO)
                .fechaDeNacimiento(dto.getFechaDeNacimiento())
                .build();

        return usuarioRepository.save(usuario);
    }

    public Usuario crearAdministrador(CrearAdministradorDTO dto) {

        if (usuarioRepository.findByCorreo(dto.getCorreo()).isPresent()) {
            throw new IllegalArgumentException(
                    "Ya existe un usuario registrado con el correo " + dto.getCorreo() + "."
            );
        }

        Usuario usuario = Usuario.builder()
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .documentoDeIdentidad(dto.getDocumentoDeIdentidad())
                .celular(dto.getCelular())
                .correo(dto.getCorreo())
                .clave(passwordEncoder.encode(dto.getClave()))
                .rol(Rol.ADMINISTRADOR)
                .build();

        return usuarioRepository.save(usuario);
    }

    public ClienteCelularDTO consultarCelularCliente(Integer idCliente) {

        Usuario usuario = usuarioRepository.findById(idCliente)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe el usuario con ID: " + idCliente));

        if (usuario.getRol() != Rol.CLIENTE) {
            throw new IllegalArgumentException(
                    "El usuario indicado no es un cliente.");
        }

        return new ClienteCelularDTO(usuario.getCelular());
    }

    public Map<String, Object> consultarRol(Integer id) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe el usuario con ID: " + id));

        return Map.of(
                "id", usuario.getId(),
                "rol", usuario.getRol().name()
        );
    }
}
