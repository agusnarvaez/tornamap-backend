package ar.edu.unsam.pds.dto.response

data class EventResponseDto(
    val id: String,
    val name: String,
    var isActive: Boolean,
    var isCancelled: Boolean,
    val schedule: ScheduleResponseDto
)