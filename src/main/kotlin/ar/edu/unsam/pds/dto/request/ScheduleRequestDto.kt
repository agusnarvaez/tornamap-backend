package ar.edu.unsam.pds.dto.request

import ar.edu.unsam.pds.models.enums.RecurrenceWeeks
import jakarta.validation.constraints.FutureOrPresent
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.springframework.cglib.core.Local
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime

data class ScheduleRequestDto (

    @field: NotNull(message = "La hora de inicio no puede ser nula")
    val startTime: LocalTime,

    @field: NotNull(message = "La hora de finalizaciin no puede ser nula")
    val endTime: LocalTime,

    val weekDay:DayOfWeek?,

    val date:LocalDate?,

    /*por ahora dejo que weekDay y date puedan ser nulos, lei algo de una anotacion
    personalizada para esto pero no sabía si usarlo. Valida en el service por ahora
     */

    @field: NotNull(message = "isVirtual no puede ser nulo")
    val isVirtual: Boolean,

    )