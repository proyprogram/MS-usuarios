package com.proyectointegrador.msusuarios.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CrearClienteDTO {

    @NotBlank
    private String nombre;

    @NotBlank
    private String apellido;

    @NotBlank
    private String documentoDeIdentidad;

    @NotBlank
    private String celular;

    @NotBlank
    private String correo;

    @NotBlank
    private String clave;
}