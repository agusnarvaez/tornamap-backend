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

    fun buildEventStatsDto(event: Event): EventStatsResponseDto {
        return EventStatsResponseDto(
            id = event.id.toString(),
            isActive = event.isApproved,
            schedule = ScheduleMapper.buildScheduleDto(event.schedule),
            name = event.activeDays(),
            users = event.users.map { UserMapper.buildUserDto(it) }.toMutableSet(),
            status = event.status()
        )
    }

}