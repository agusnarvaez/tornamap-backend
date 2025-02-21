package ar.edu.unsam.pds.services

import ar.edu.unsam.pds.dto.request.CourseRequestDto
import ar.edu.unsam.pds.dto.response.CourseDetailResponseDto
import ar.edu.unsam.pds.dto.response.CourseResponseDto
import ar.edu.unsam.pds.dto.response.CourseStatsResponseDto
import ar.edu.unsam.pds.exceptions.NotFoundException
import ar.edu.unsam.pds.exceptions.PermissionDeniedException
import ar.edu.unsam.pds.exceptions.ValidationException
import ar.edu.unsam.pds.mappers.CourseMapper
import ar.edu.unsam.pds.models.Course
import ar.edu.unsam.pds.repository.CourseRepository
import ar.edu.unsam.pds.repository.ProgramRepository
import ar.edu.unsam.pds.security.models.Principal
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.util.*

@Service
class CoursesService(
    private val courseRepository: CourseRepository,
    private val programRepository: ProgramRepository,
    private val imageService: StorageService
) {

    fun getAll(query: String): List<CourseResponseDto> {
        val courses = courseRepository.getAllBy(query)
        return courses.map { CourseMapper.buildCourseDto(it) }
    }

    fun getAllByPrincipal(query: String, principal: Principal): List<CourseResponseDto> {
        val courses = courseRepository.getAllByPrincipal(query, principal)
        return courses.map { CourseMapper.buildCourseDto(it) }
    }

    fun getCourse(idCourse: String): CourseDetailResponseDto {
        val course = findCourseById(idCourse)
        return CourseMapper.buildCourseDetailDto(course)
    }

    @Transactional
    fun deleteCourse(idCourse: String, principal: Principal) {
        val course = findCourseById(idCourse)
        val uuid = UUID.fromString(idCourse)

        if (!courseRepository.isOwner(uuid, principal)) {
            throw PermissionDeniedException("No se puede borrar un curso del cual no es propietario")
        }

        if (course.assignments.any { it.hasAnySubscribedUser() }) {
            throw ValidationException("No se puede eliminar un curso con usuarios inscriptos")
        }
        val imageName = course.image
        courseRepository.delete(course)
        imageService.deletePublic(imageName)
    }

    @Transactional
    fun deleteAllById(courseIds: List<String>, principal: Principal) {
        courseIds.forEach { id ->
            deleteCourse(id, principal)
        }
    }

    private fun findCourseById(idCourse: String): Course {
        val uuid = UUID.fromString(idCourse)
        return courseRepository.findById(uuid).orElseThrow {
            NotFoundException("Curso no encontrado para el uuid suministrado")
        }
    }

    @Transactional
    fun createCourse(course: CourseRequestDto): CourseResponseDto? {
        val institutionId = UUID.fromString(course.programId)
        val institution = institutionRepository.findById(institutionId).orElseThrow {
            NotFoundException("Institución no encontrada para el uuid suministrado")
        }

        val imageName = imageService.savePublic(course.file)

        val newCourse = Course(
            course.title,
            course.description,
            course.category,
            imageName,
        )
        courseRepository.save(newCourse)

        institution.addCourse(newCourse)
        institutionRepository.save(institution)

        return CourseMapper.buildCourseDto(newCourse)
    }

    fun getCourseStats(idCourse: String): CourseStatsResponseDto? {
        val course = findCourseById(idCourse)
        return CourseMapper.buildCourseStatsDto(course)
    }
}