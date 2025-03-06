package ar.edu.unsam.pds.dto.request

import jakarta.validation.constraints.FutureOrPresent
import jakarta.validation.constraints.NotNull
import java.time.LocalDate
import java.time.LocalTime

class PeriodRequestDto (
    @field: NotNull(message = "La fecha de inicio no puede ser nula")
    @field: FutureOrPresent(message = "La fecha de inicio no puede ser en el pasado")
    val startDate: LocalDate,

    @field: NotNull(message = "La fecha de finalización no puede ser nula")
    @field: FutureOrPresent(message = "La fecha de inicio no puede ser en el pasado")
    val endDate: LocalDate,
)