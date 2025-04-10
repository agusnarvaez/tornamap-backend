package ar.edu.unsam.pds.dto.request

import jakarta.validation.constraints.FutureOrPresent
import java.time.LocalDate

class SearchEventRequestDto(
    val searchQuery: String?,
    @field: FutureOrPresent(message = "La fecha de inicio no puede ser en el pasado")
    val eventDate: LocalDate?,
    val classroomID: String?
) {

    fun hasDateAndClassroomID() = notBlankDate() && notBlankID()

    fun notBlankDate() = eventDate != null

    fun notBlankQuery() = !searchQuery.isNullOrBlank()

    fun notBlankID() = !classroomID.isNullOrBlank()
}