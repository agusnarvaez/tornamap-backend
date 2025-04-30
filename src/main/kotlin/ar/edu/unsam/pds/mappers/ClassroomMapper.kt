package ar.edu.unsam.pds.mappers

import ar.edu.unsam.pds.dto.response.ClassroomResponseDto
import ar.edu.unsam.pds.models.Classroom

object ClassroomMapper {

    fun buildClassroomDto(classroom: Classroom): ClassroomResponseDto{
        return ClassroomResponseDto(
            code = classroom.code,
            id = classroom.id.toString(),
            name = classroom.name,
            capacity = classroom.capacity,
            type = classroom.type.toString(),
            floor = classroom.floor,
            building = BuildingMapper.buildBuildingDto(classroom.building)
        )
    }
}