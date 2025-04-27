package ar.edu.unsam.pds.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.springframework.web.multipart.MultipartFile

data class ProgramRequestDto(
    @field: NotBlank(message = "El nombre no puede estar vacío")
    @field: NotNull(message = "El nombre no puede ser nulo")
    @field: Size(max = 50, message = "El nombre debe tener como máximo 50 caracteres")
    val name: String = "",

    @field: NotBlank(message = "La descripción no puede estar vacía")
    @field: NotNull(message = "La descripción no puede ser nula")
    @field: Size(max = 250, message = "La descripción debe tener como máximo 250 caracteres")
    val description: String = "",
)