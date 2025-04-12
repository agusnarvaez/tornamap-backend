package ar.edu.unsam.pds.dto.request

data class ClassroomRequestDto(
    val id: String,
    val name: String,
    val capacity: Int,
    val type: String,
    val floor: Int,
    val building: BuildingRequestDto
)