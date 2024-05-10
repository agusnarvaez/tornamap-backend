package ar.edu.unsam.pds.services

import ar.edu.unsam.pds.dto.response.CourseAssignmentsResponseDto
import ar.edu.unsam.pds.models.Assignment
import ar.edu.unsam.pds.dto.response.CourseResponseDto
import ar.edu.unsam.pds.models.Course
import ar.edu.unsam.pds.repository.CourseRepository
import ar.edu.unsam.pds.utils.Mapper
import org.springframework.stereotype.Service

@Service
class CoursesService(
    private val courseRepository: CourseRepository
) {

    fun getAll(): List<CourseResponseDto> {
        val courses = courseRepository.getAll()
        return courses.map { Mapper.buildCourseDto(it) }
    }

    fun getCourse(idCourse: String): CourseResponseDto {
        val course = courseRepository.findById(idCourse) as Course
        return Mapper.buildCourseDto(course)
    }

    fun getAssignmentOfCourse(idCourse: String): CourseAssignmentsResponseDto {
        val course = courseRepository.findById(idCourse) as Course
        return Mapper.courseInstitutionDto(course)
    }
}