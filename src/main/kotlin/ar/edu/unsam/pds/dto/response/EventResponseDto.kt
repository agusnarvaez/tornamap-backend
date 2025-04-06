package ar.edu.unsam.pds.dto.response

data class EventResponseDto(
    val id: String,
    val courseName: String,
    val programNames: List<String>,
    val professorsName: List<String>,
    val schedule: ScheduleResponseDto,
)