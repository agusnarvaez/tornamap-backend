package ar.edu.unsam.pds.services


import ar.edu.unsam.pds.dto.response.PeriodResponseDto
import ar.edu.unsam.pds.exceptions.NotFoundException
import ar.edu.unsam.pds.exceptions.PermissionDeniedException
import ar.edu.unsam.pds.mappers.PeriodMapper
import ar.edu.unsam.pds.models.Period
import ar.edu.unsam.pds.repository.PeriodRepository
import ar.edu.unsam.pds.security.models.Principal
import jakarta.transaction.Transactional
import java.util.*

class PeriodService(
    private val periodRepository : PeriodRepository
) {
    fun getAll(query: String): List<PeriodResponseDto> {
        val periods = periodRepository.getAllBy(query)
        return periods.map { PeriodMapper.buildPeriodDto(it) }
    }

    fun getAllByPrincipal(query: String, principal: Principal): List<PeriodResponseDto> {
        val periods = periodRepository.getAllByPrincipal(query, principal)
        return periods.map { PeriodMapper.buildPeriodDto(it) }
    }

    fun getPeriod(idPeriod: String): PeriodResponseDto {
        val period = findPeriodById(idPeriod)
        return PeriodMapper.buildPeriodDto(period)
    }

    @Transactional
    fun deletePeriod(idPeriod: String, principal: Principal) {
        val period = findPeriodById(idPeriod)
        val uuid = UUID.fromString(idPeriod)

        if (!periodRepository.isOwner(uuid, principal)) {
            throw PermissionDeniedException("No se puede borrar un periodo del cual no es propietario")
        }
        //TODO: agregar delete
    }

    @Transactional
    fun deleteAllById(periodIds: List<String>, principal: Principal) {
        periodIds.forEach { id ->
            deletePeriod(id, principal)
        }
    }

    private fun findPeriodById(idPeriod: String): Period {
        val uuid = UUID.fromString(idPeriod)
        return periodRepository.findById(uuid).orElseThrow {
            NotFoundException("Periodo no encontrado para el uuid suministrado")
        }
    }

   /* @Transactional
    fun createPeriod(course: CourseRequestDto): CourseResponseDto? {
        val programId = UUID.fromString(course.programId)
        val program = programRepository.findById(programId).orElseThrow {
            NotFoundException("Institución no encontrada para el uuid suministrado")
        }



        val newCourse = Course(
            course.title,
            course.description,
        )
        courseRepository.save(newCourse)

        program.addCourse(newCourse)
        programRepository.save(program)

        return CourseMapper.buildCourseDto(newCourse)
    }*/

}