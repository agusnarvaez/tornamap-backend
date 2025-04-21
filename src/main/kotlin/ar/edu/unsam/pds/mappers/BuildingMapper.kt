package ar.edu.unsam.pds.mappers

object BuildingMapper {
    fun buildBuildingDto(building: ar.edu.unsam.pds.models.Building): ar.edu.unsam.pds.dto.response.BuildingResponseDto {
        return ar.edu.unsam.pds.dto.response.BuildingResponseDto(
            id = building.id.toString(),
            name = building.name
        )
    }
}