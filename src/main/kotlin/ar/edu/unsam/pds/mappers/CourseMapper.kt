package ar.edu.unsam.pds.mappers

import ar.edu.unsam.pds.dto.response.*
import ar.edu.unsam.pds.models.Course

object CourseMapper {

    fun buildCourseDto(course: Course): CourseResponseDto {
        return CourseResponseDto(
            id = course.id.toString(),
            title = course.title,
        )
    }

    fun buildCourseDetailDto(course: Course): CourseDetailResponseDto {

        return CourseDetailResponseDto(
            id = course.id.toString(),
            title = course.title,
            description = course.description
        )
    }
}