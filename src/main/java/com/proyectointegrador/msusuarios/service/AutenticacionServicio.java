package com.proyectointegrador.msusuarios;

import java.util.List;
import org.mindrot.BCrypt;

public class AutenticacionServicio {

    private List<Usuario> listaUsuarios;

    public AutenticacionServicio(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public Usuario iniciarSesion(String correo, String clave) {
        Usuario encontrado = buscarPorCorreo(correo);

        if (encontrado == null) {
            throw new SecurityException("Credenciales inválidas.");
        }

        boolean claveCorrecta = BCrypt.checkpw(clave, encontrado.getClave());

        if (!claveCorrecta) {
            throw new SecurityException("Credenciales inválidas.");
        }

        return encontrado;
    }

    private Usuario buscarPorCorreo(String correo) {
        for (Usuario usuario : listaUsuarios) {
            if (usuario.getCorreo().equals(correo)) {
                return usuario;
            }
        }
        return null;
    }
}