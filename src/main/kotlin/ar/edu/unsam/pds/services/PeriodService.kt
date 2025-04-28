package ar.edu.unsam.pds.services


import ar.edu.unsam.pds.dto.request.PeriodRequestDto
import ar.edu.unsam.pds.dto.response.PeriodResponseDto
import ar.edu.unsam.pds.exceptions.BadRequestException
import ar.edu.unsam.pds.exceptions.NotFoundException
import ar.edu.unsam.pds.exceptions.PermissionDeniedException
import ar.edu.unsam.pds.mappers.PeriodMapper
import ar.edu.unsam.pds.models.Period
import ar.edu.unsam.pds.repository.EventRepository
import ar.edu.unsam.pds.repository.PeriodRepository
import ar.edu.unsam.pds.security.models.Principal
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.*


@Service
class PeriodService(
    private val periodRepository : PeriodRepository,
) {

    fun getAllPeriods(): List<Period> {
        return periodRepository.findAllByOrderByStartDateDesc()
    }

    fun findPeriodById(idPeriod: String?): Period {
        val uuid = UUID.fromString(idPeriod)
        return periodRepository.findById(uuid).orElseThrow {
            NotFoundException("Periodo no encontrado para el uuid suministrado")
        }
    }

    @Transactional
    fun createPeriod(period: PeriodRequestDto): UUID {
        if(isValidEndDate(period.startDate, period.endDate)){
            val newPeriod = Period(
                period.title,
                period.startDate,
                period.endDate,
            )
            periodRepository.save(newPeriod)
            return newPeriod.id
        } else{
            throw BadRequestException("La fecha de finalizacion no puede ser previa a la de inicio")
        }
    }

    fun deletePeriod(idPeriod: String) {
        val periodDeleted = findPeriodById(idPeriod)
        periodRepository.delete(periodDeleted)
    }

    fun updatePeriod(
        idPeriod: String,
        period: PeriodRequestDto,
    ): PeriodResponseDto {
        val periodToUpdate = findPeriodById(idPeriod)
        if (isValidEndDate(period.startDate, period.endDate)) {
            periodToUpdate.title = period.title
            periodToUpdate.startDate = period.startDate
            periodToUpdate.endDate = period.endDate
            return PeriodMapper.buildPeriodDto(periodRepository.save(periodToUpdate))
        } else {
            throw BadRequestException("La fecha de finalizacion no puede ser previa a la de inicio")
        }
    }

    private fun isValidEndDate(startDate: LocalDate, endDate: LocalDate): Boolean { return endDate.isAfter(startDate) }

}