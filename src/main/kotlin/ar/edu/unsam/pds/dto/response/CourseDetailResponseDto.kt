package ar.edu.unsam.pds.dto.response

data class CourseDetailResponseDto(
    val id: String,
    val name: String,
    val description: String,
    val events: MutableSet<EventResponseDto>,
)