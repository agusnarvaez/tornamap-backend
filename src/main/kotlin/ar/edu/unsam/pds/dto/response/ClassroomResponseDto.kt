package ar.edu.unsam.pds.dto.response

data class ClassroomResponseDto(
    val id: String,
    val name: String,
    val capacity: Int,
    val type: String,
    val floor: Int,
    val building: BuildingResponseDto
    val code: String,
)
