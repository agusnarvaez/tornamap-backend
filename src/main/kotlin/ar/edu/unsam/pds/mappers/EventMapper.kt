package ar.edu.unsam.pds.mappers

import ar.edu.unsam.pds.dto.response.EventResponseDto
import ar.edu.unsam.pds.dto.response.EventStatsResponseDto
import ar.edu.unsam.pds.models.Event

object EventMapper {

    fun buildEventDto(event: Event): EventResponseDto {
        return EventResponseDto(
            id = event.id.toString(),
            name = event.name,
            isActive = event.isApproved,
            schedule = ScheduleMapper.buildScheduleDto(event.schedule),
            isCancelled = event.isCancelled
        )
    }


}