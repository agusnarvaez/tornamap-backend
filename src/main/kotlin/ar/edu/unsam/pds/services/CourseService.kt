package ar.edu.unsam.pds.services

import ar.edu.unsam.pds.dto.request.CourseRequestDto
import ar.edu.unsam.pds.dto.response.CourseResponseDto
import ar.edu.unsam.pds.exceptions.NotFoundException
import ar.edu.unsam.pds.mappers.CourseMapper
import ar.edu.unsam.pds.models.Course
import ar.edu.unsam.pds.repository.CourseRepository
import ar.edu.unsam.pds.repository.ProgramRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.util.*

@Service
class CourseService (
    private val courseRepository: CourseRepository,
    private val programRepository: ProgramRepository,
) {

    fun searchBy(query: String): List<Course> {
        if (query.isNotBlank()) {
//            val searchCourse = allCourses.apply {
//                filterByName(this, query)
//                filterByProgram(this, query)
//                filterByUserName(this, query)
//            }
            return (courseRepository.findByProgramsNameContainingIgnoreCase(query).toSet() + courseRepository.findByNameContainingIgnoreCase(query).toSet()).toList()
        }
        return getAll()
    }

    fun filterByName(courseList: List<Course>, query: String) = courseList.filter { it.name.contains(query)}

    fun filterByProgram(courseList: List<Course>, query: String) = courseList.filter { it.programNames().contains(query)}

    fun filterByUserName(courseList: List<Course>, query: String) = courseList.filter { it.userNames().contains(query)}

    fun getAll(): List<Course> {
        return courseRepository.findAllByOrderByNameAsc()
    }

    fun findCourseById(courseId: String): CourseResponseDto {
        val uuid = UUID.fromString(courseId)
        val matchingCourse = courseRepository.findById(uuid).orElseThrow({
            NotFoundException("Asignatura no encontrada para el uuid suministrado")
        })
        return CourseMapper.buildCourseDto(matchingCourse)
    }

/*
    @Transactional
    fun deleteCourse() {}
*/

/*
    @Transactional
    fun deleteAllById() {}
*/


    @Transactional
    fun createCourse(course: CourseRequestDto): CourseResponseDto? {
        val programId = UUID.fromString(course.programId)
        val program = programRepository.findById(programId).orElseThrow {
            NotFoundException("Asignatura no encontrada para el uuid suministrado")
        }

        val newCourse = Course(
            course.title,
            course.description,
        )
        courseRepository.save(newCourse)

        program.addCourse(listOf(newCourse))
        programRepository.save(program)

        return CourseMapper.buildCourseDto(newCourse)
    }

}