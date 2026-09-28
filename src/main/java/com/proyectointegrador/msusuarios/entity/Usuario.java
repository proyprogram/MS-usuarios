package com.proyectointegrador.msusuarios.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "usuario")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nombre;

    private String apellido;

    @Column(name = "documento", unique = true)
    private String documentoDeIdentidad;

    private String celular;

    @Column(name = "fechadenacimiento")
    private LocalDate fechaDeNacimiento;

    @Column(unique = true)
    private String correo;

    private String clave;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rol rol;
}
