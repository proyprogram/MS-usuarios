package com.proyectointegrador.msusuarios.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CrearClienteDTO {

    private String nombre;

    private String apellido;

    private String documentoDeIdentidad;

    private String celular;

    private String correo;

    private String clave;
}