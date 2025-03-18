package ar.edu.unsam.pds.mappers

import ar.edu.unsam.pds.dto.response.PeriodResponseDto
import ar.edu.unsam.pds.models.Period

object PeriodMapper {

    fun buildPeriodDto(period: Period):PeriodResponseDto{
        return PeriodResponseDto(
            startDate = period.startDate.toString(),
            endDate= period.endDate.toString(),
            id=period.id.toString(),
            title=period.title,
        )
    }
}