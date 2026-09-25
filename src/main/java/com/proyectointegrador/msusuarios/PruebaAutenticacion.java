package com.proyectointegrador.msusuarios;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PruebaAutenticacion {

    public static void main(String[] args) {

        Usuario usuario = new Usuario(
                1,
                "Carlos",
                "Rodriguez",
                "123456789",
                "+573015641927",
                LocalDate.of(2000, 5, 20),
                "carlos@gmail.com",
                "Clave654"
        );

        usuario.encriptarClave();

        List<Usuario> listaUsuarios = new ArrayList<>();
        listaUsuarios.add(usuario);

        AutenticacionServicio autenticacionServicio = new AutenticacionServicio(listaUsuarios);

        try {
            Usuario autenticado = autenticacionServicio.iniciarSesion("carlos@gmail.com", "Clave654");
            System.out.println("Login exitoso. Rol: " + autenticado.getRol());
        } catch (SecurityException e) {
            System.out.println("Error inesperado: " + e.getMessage());
        }

        try {
            autenticacionServicio.iniciarSesion("carlos@gmail.com", "ClaveMala");
            System.out.println("Esto no debería pasar: se permitió login con clave incorrecta");
        } catch (SecurityException e) {
            System.out.println("Login rechazado correctamente (clave mala): " + e.getMessage());
        }

        try {
            autenticacionServicio.iniciarSesion("noexiste@gmail.com", "Clave654");
            System.out.println("Esto no debería pasar: se permitió login con correo inexistente");
        } catch (SecurityException e) {
            System.out.println("Login rechazado correctamente (correo no existe): " + e.getMessage());
        }
    }
}