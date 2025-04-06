package ar.edu.unsam.pds.mappers

import ar.edu.unsam.pds.models.Classroom
import ar.edu.unsam.pds.dto.response.ClassroomResponseDto

object ClassroomMapper {
    fun buildClassroomDto(classroom: Classroom) : ClassroomResponseDto {
        return ClassroomResponseDto(
            code = classroom.code,
            name = classroom.getFullName(),
        )
    }
}