package ar.edu.unsam.pds.dto.request

import jakarta.validation.constraints.*

data class UserRequestDto(
    @field: NotEmpty(message = "El campo nombre es requerido")
    @field: NotBlank(message = "El campo nombre no debe estar en blanco")
    @field: Size(max = 128, message = "El nombre no debe superar los 128 caracteres")
    val name: String = "",

    @field: NotEmpty(message = "El campo apellido es requerido")
    @field: NotBlank(message = "El campo apellido no debe estar en blanco")
    @field: Size(max = 128, message = "El apellido no debe superar los 128 caracteres")
    val lastName: String = "",


    @field: NotEmpty(message = "El campo email es requerido")
    @field: NotBlank(message = "El campo email no debe estar en blanco")
    @field: Email(message = "Debe ser una dirección de correo electrónico con formato correcto")
    val email: String = "",

    // máximo de internet explorer, por compatibilidad, el más largo del más corto
    @field: Size(max = 2083, message = "El image no debe superar los 2083 caracteres")
    val image: String = "",

    @field:NotNull
    val isAdmin: Boolean,

    @field: NotNull(message = "El ID no debe ser nulo")
    @field: NotBlank(message = "El ID no debe estar en blanco")
    @field: Pattern(
        regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
        message = "UUID debe ser valido"
    )
    val id: String?,
)