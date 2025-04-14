package ar.edu.unsam.pds.dto.response

data class CourseResponseDto(
    val id: String,
    val name: String,
    val programs: List<String>,
    val events: List<EventResponseDto>
)