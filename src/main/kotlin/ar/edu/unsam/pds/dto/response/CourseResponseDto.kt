package ar.edu.unsam.pds.dto.response

data class CourseResponseDto(
    val id: String,
    val name: String,
    val programNames: List<String>,
    val schedules: List<ScheduleResponseDto>
)