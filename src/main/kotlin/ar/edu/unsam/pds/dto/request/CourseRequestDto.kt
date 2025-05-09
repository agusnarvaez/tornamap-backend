package ar.edu.unsam.pds.dto.request

import ar.edu.unsam.pds.tools.EachUuid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class CourseRequestDto(

    val id: String?,

    @field: NotBlank(message = "El campo titulo no puede estar vacío")
    @field: NotNull(message = "El campo titulo no puede ser nulo")
    @field: Size(max = 250, message = "El campo titulo debe tener como máximo 250 caracteres")
    val name: String = "",

    @field: NotBlank(message = "El campo descripcion no puede estar vacío")
    @field: NotNull(message = "El campo descripcion no puede ser nulo")
    @field: Size(max = 250, message = "El campo descripcion debe tener como máximo 250 caracteres")
    val description: String = "",

    @field:EachUuid(message = "Cada elemento en programas debe ser un UUID válido")
    val programs: List<String> = listOf()
)