package ar.edu.unsam.pds.mappers

import ar.edu.unsam.pds.dto.response.InstitutionDetailResponseDto
import ar.edu.unsam.pds.dto.response.InstitutionResponseDto
import ar.edu.unsam.pds.models.Program

object InstitutionMapper {

    fun buildInstitutionDto(program: Program): InstitutionResponseDto {
        return InstitutionResponseDto(
            id = program.id.toString(),
            name = program.name,
            description = program.description,
            category = program.category,
            image = program.image
        )
    }

    fun buildInstitutionDetailDto(program: Program): InstitutionDetailResponseDto {
        return InstitutionDetailResponseDto(
            id = program.id.toString(),
            name = program.name,
            description = program.description,
            category = program.category,
            image = program.image,
            courses = program.courses.map { CourseMapper.buildCourseDto(it) }.toMutableSet()
        )
    }
}