package ar.edu.unsam.pds.dto.request

import jakarta.validation.constraints.*
import java.time.LocalDate
import java.time.LocalTime

data class ScheduleRequestDto (

    val id: String?,

    @field: NotNull(message = "La hora de inicio no puede ser nula")
    val startTime: LocalTime,

    @field: NotNull(message = "La hora de finalizacion no puede ser nula")
    val endTime: LocalTime,

    val weekDay:String?,
    val date:LocalDate?,

    @field: NotNull(message = "isVirtual no puede ser nulo")
    val isVirtual: Boolean,

    val classroomId: String? = null,
)