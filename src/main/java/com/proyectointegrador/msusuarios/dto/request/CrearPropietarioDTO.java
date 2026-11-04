package com.proyectointegrador.msusuarios.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CrearPropietarioDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;

    @NotBlank(message = "El documento de identidad es obligatorio")
    @Pattern(regexp = "^[0-9]+$", message = "El documento de identidad debe ser únicamente numérico")
    private String documentoDeIdentidad;

    @NotBlank(message = "El celular es obligatorio")
    @Pattern(
            regexp = "^(\\+[0-9]{1,12}|[0-9]{1,13})$",
            message = "El celular debe tener máximo 13 caracteres, solo números y puede iniciar con +"
    )
    private String celular;

    @NotBlank(message = "El correo es obligatorio")
    @Email(
            regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = "El correo no tiene una estructura válida"
    )
    private String correo;

    @NotBlank(message = "La clave es obligatoria")
    private String clave;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha de nacimiento debe ser una fecha pasada")
    private LocalDate fechaDeNacimiento;
}
