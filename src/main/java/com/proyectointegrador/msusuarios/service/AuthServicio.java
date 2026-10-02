package com.proyectointegrador.msusuarios.service;

import com.proyectointegrador.msusuarios.dto.request.LoginRequestDTO;
import com.proyectointegrador.msusuarios.dto.response.LoginResponseDTO;
import com.proyectointegrador.msusuarios.entity.Usuario;
import com.proyectointegrador.msusuarios.repository.UsuarioRepository;
import com.proyectointegrador.msusuarios.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServicio {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public LoginResponseDTO login(LoginRequestDTO dto) {

        Usuario usuario = usuarioRepository.findByCorreo(dto.getCorreo())
                .orElseThrow(() -> new IllegalArgumentException("Correo o clave incorrectos"));

        boolean claveCorrecta = passwordEncoder.matches(dto.getClave(), usuario.getClave());
        if (!claveCorrecta) {
            throw new IllegalArgumentException("Correo o clave incorrectos");
        }

        String token = jwtService.generateToken(usuario.getCorreo(), usuario.getRol());

        return LoginResponseDTO.builder().token(token).build();
    }
}
