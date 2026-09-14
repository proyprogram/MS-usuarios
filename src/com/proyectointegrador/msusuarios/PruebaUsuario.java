package com.proyectointegrador.msusuarios;

import java.time.LocalDate;

public class PruebaUsuario {

    public static void main(String[] args) {

        Usuario usuario = new Usuario(
                1,
                "Carlos",
                "Rodriguez",
                "123456789",
                "+573015641927",
                LocalDate.of(2000, 5, 10),
                "carlos@gmail.com",
                "Clave654"
        );

        System.out.println("ID: " + usuario.getId());
        System.out.println("Documento válido: " + usuario.documentoValido());
        System.out.println("Celular válido: " + usuario.celularValido());
        System.out.println("Correo válido: " + usuario.correoValido());
        System.out.println("Es mayor de edad: " + usuario.esMayorDeEdad());
        System.out.println("Campos obligatorios: " + usuario.camposObligatoriosValidos());
        System.out.println("Rol: " + usuario.getRol());

        usuario.encriptarClave();

        System.out.println("Clave encriptada: " + usuario.getClave());
    }
}
