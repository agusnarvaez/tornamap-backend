package ar.edu.unsam.pds.dto.request

import ar.edu.unsam.pds.models.enums.RecurrenceWeeks
import jakarta.validation.constraints.*
import org.springframework.cglib.core.Local
import java.time.DayOfWeek
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