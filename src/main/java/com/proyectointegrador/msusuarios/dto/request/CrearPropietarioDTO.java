package com.proyectointegrador.msusuarios.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CrearPropietarioDTO {

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