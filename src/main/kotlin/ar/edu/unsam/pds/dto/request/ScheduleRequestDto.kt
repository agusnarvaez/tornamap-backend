package ar.edu.unsam.pds.dto.request

import ar.edu.unsam.pds.models.enums.RecurrenceWeeks
import jakarta.validation.constraints.*
import org.springframework.cglib.core.Local
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime

data class ScheduleRequestDto (

    @field: NotNull(message = "La hora de inicio no puede ser nula")
    val startTime: LocalTime,

    @field: NotNull(message = "La hora de finalizacion no puede ser nula")
    val endTime: LocalTime,

    val weekDay:DayOfWeek?,

    val date:LocalDate?,

    /*por ahora dejo que weekDay y date puedan ser nulos, lei algo de una anotacion
    personalizada para esto pero no sabía si usarlo. Valida en el service por ahora
     */

    @field: NotNull(message = "isVirtual no puede ser nulo")
    val isVirtual: Boolean,

    @field: NotNull(message = "El ID no debe ser nulo")
    @field: NotBlank(message = "El ID no debe estar en blanco")
    @field: Pattern(
        regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
        message = "UUID debe ser valido"
    )
    val idClassroom: String?,

    /*lo mismo de la anotacion personalizada en isVirtual y idClassroom*/

    @field: NotNull(message = "El ID no debe ser nulo")
    @field: NotBlank(message = "El ID no debe estar en blanco")
    @field: Pattern(
        regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
        message = "UUID debe ser valido"
    )
    val idEvent: String,
    )