package ar.edu.unsam.pds.dto.response

data class EventResponseDto(
    val id: String,
    val name: String,
    val courseName: String,
    val programNames: List<String>,
    val schedules: List<ScheduleResponseDto>,
)