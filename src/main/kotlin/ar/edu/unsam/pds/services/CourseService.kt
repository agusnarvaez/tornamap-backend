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

    fun getAll(): List<Course> = courseRepository.findAllByOrderByEventsPresenceAndName()

    fun searchBy(query: String): List<Course> {
        if (query.isNotBlank()) {
            return courseRepository.searchByNameOrProgramOrProfessor(query)
        }
        return getAll()
    }

    fun findByID(courseID: String?): Course {
        val uuid = UUID.fromString(courseID)
        return courseRepository.findById(uuid).orElseThrow {
            NotFoundException("Curso no encontrado para el uuid suministrado")
        }
    }

    @Transactional
    fun create(course: Course, programs: List<Program>): Course {
        course.addPrograms(programs)
        return courseRepository.save(course)
    }

}