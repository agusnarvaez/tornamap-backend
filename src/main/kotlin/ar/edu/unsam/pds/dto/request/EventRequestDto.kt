package ar.edu.unsam.pds.dto.request

import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern

data class EventRequestDto(

    val id: String?,

    val isApproved: Boolean,

    val isCancelled: Boolean,

    @field: NotNull(message = "El ID no debe ser nulo")
    @field: Pattern(
        regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
        message = "UUID debe ser valido"
    )
    val courseID: String?,

    @field: NotNull(message = "El ID no debe ser nulo")
    @field: Pattern(
        regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
        message = "UUID debe ser valido"
    )
    val periodID: String?,

    @field:NotNull(message = "El nombre no debe ser nulo")
    @field:NotBlank(message = "El nombre no puede estar vacío")
    val name: String,

    @field:Valid
    val schedules: MutableList<ScheduleRequestDto>
)