package ar.edu.unsam.pds.dto.response

data class ProgramDetailResponseDto(
    val id: String,
    val name: String,
    val description: String,
    val courses: MutableSet<CourseResponseDto>
)