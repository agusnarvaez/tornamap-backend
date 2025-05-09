package ar.edu.unsam.pds.dto.response

data class BuildingResponseDto(
    val id: String,
    val name: String
)

data class BuildingResponseDtoWithClassrooms(
    val id: String,
    val name: String,
    val classrooms: List<ClassroomResponseDto>
)
