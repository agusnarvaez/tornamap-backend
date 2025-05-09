package ar.edu.unsam.pds.services


import ar.edu.unsam.pds.dto.request.PeriodRequestDto
import ar.edu.unsam.pds.dto.response.PeriodResponseDto
import ar.edu.unsam.pds.exceptions.BadRequestException
import ar.edu.unsam.pds.exceptions.NotFoundException
import ar.edu.unsam.pds.mappers.PeriodMapper
import ar.edu.unsam.pds.models.Building
import ar.edu.unsam.pds.models.Period
import ar.edu.unsam.pds.repository.BuildingRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.*


@Service
class BuildingService(
    private val buildingRepository: BuildingRepository,
) {

    fun getAll(): List<Building> {
        return buildingRepository.findAll()
    }

    fun getById(idBuilding: String?): Building {
        val uuid = UUID.fromString(idBuilding)
        return buildingRepository.findById(uuid).orElseThrow {
            NotFoundException("Edificio no encontrado para el uuid suministrado")
        }
    }

//    @Transactional
//    fun create(period: PeriodRequestDto): UUID {
//        if (isValidEndDate(period.startDate, period.endDate)) {
//            val newPeriod = Period(
//                period.title,
//                period.startDate,
//                period.endDate,
//            )
//            buildingRepository.save(newPeriod)
//            return newPeriod.id
//        } else {
//            throw BadRequestException("La fecha de finalizacion no puede ser previa a la de inicio")
//        }
//    }
//
//    fun delete(idPeriod: String) {
//        val periodDeleted = getById(idPeriod)
//        buildingRepository.delete(periodDeleted)
//    }
//
//    fun update(
//        idPeriod: String,
//        period: PeriodRequestDto,
//    ): PeriodResponseDto {
//        val periodToUpdate = getById(idPeriod)
//        if (isValidEndDate(period.startDate, period.endDate)) {
//            periodToUpdate.title = period.title
//            periodToUpdate.startDate = period.startDate
//            periodToUpdate.endDate = period.endDate
//            return PeriodMapper.buildPeriodDto(buildingRepository.save(periodToUpdate))
//        } else {
//            throw BadRequestException("La fecha de finalizacion no puede ser previa a la de inicio")
//        }
//    }
//
//    private fun isValidEndDate(startDate: LocalDate, endDate: LocalDate): Boolean {
//        return endDate.isAfter(startDate)
//    }

}