package com.proyectointegrador.msusuarios;

import java.time.LocalDate;
import org.mindrot.BCrypt;

public class Usuario {
    private int id;
    private String nombre;
    private String apellido;
    private String documentoDeIdentidad;
    private String celular;
    private LocalDate fechaNacimiento;
    private String correo;
    private String clave;
    private Rol rol;

    public Usuario(int id,String nombre, String apellido, String documentoDeIdentidad,
                   String celular, LocalDate fechaNacimiento, String correo, String clave) {

        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.documentoDeIdentidad = documentoDeIdentidad;
        this.celular = celular;
        this.fechaNacimiento = fechaNacimiento;
        this.correo = correo;
        this.clave = clave;
        this.rol = Rol.PROPIETARIO;
    }

    public boolean documentoValido() {
        return documentoDeIdentidad.matches("[0-9]+");
    }

    public boolean celularValido() {
        return celular.length() <= 13 && celular.matches("\\+?[0-9]+");
    }

    public boolean correoValido() {
        int posicionArroba = correo.indexOf("@");
        int posicionPunto = correo.lastIndexOf(".");

        return posicionArroba > 0
                && posicionPunto > posicionArroba + 1
                && posicionPunto < correo.length() - 1;
    }

    public boolean esMayorDeEdad() {
        LocalDate hoy = LocalDate.now();
        LocalDate fechaLimite = fechaNacimiento.plusYears(18);

        return !fechaLimite.isAfter(hoy);
    }

    public boolean camposObligatoriosValidos() {
        return !nombre.isEmpty()
                && !apellido.isEmpty()
                && !documentoDeIdentidad.isEmpty()
                && !celular.isEmpty()
                && fechaNacimiento != null
                && !correo.isEmpty()
                && !clave.isEmpty();
    }

    public void encriptarClave() {
        clave = BCrypt.hashpw(clave, BCrypt.gensalt());
    }

    public String getClave() {
        return clave;
    }

    public Rol getRol() {
        return rol;
    }
    public int getId() {
        return id;
    }
}




