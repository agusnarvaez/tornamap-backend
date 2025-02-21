package ar.edu.unsam.pds.mappers

import ar.edu.unsam.pds.dto.response.ProgramDetailResponseDto
import ar.edu.unsam.pds.dto.response.ProgramResponseDto
import ar.edu.unsam.pds.models.Program

object ProgramMapper {

    fun buildProgramDto(program: Program): ProgramResponseDto {
        return ProgramResponseDto(
            id = program.id.toString(),
            name = program.name,
            description = program.description
        )
    }

    fun buildProgramDetailDto(program: Program): ProgramDetailResponseDto {
        return ProgramDetailResponseDto(
            id = program.id.toString(),
            name = program.name,
            description = program.description,
            courses = program.courses.map { CourseMapper.buildCourseDto(it) }.toMutableSet()
        )
    }
}