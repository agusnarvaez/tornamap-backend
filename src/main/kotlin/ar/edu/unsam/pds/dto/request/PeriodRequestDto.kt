package ar.edu.unsam.pds.dto.request

import jakarta.validation.constraints.FutureOrPresent
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.LocalDate
import jakarta.validation.constraints.Size

class PeriodRequestDto (
    @field: NotBlank(message = "El campo titulo no puede estar vacío")
    @field: NotNull(message = "El campo titulo no puede ser nulo")
    @field: Size(max = 250, message = "El campo titulo debe tener como máximo 250 caracteres")
    val title: String = "",

    @field: NotNull(message = "La fecha de inicio no puede ser nula")
    val startDate: LocalDate,

    @field: NotNull(message = "La fecha de finalización no puede ser nula")
    val endDate: LocalDate,

)