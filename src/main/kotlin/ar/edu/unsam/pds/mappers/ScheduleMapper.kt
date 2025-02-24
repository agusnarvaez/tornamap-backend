package ar.edu.unsam.pds.mappers

import ar.edu.unsam.pds.dto.response.ScheduleResponseDto
import ar.edu.unsam.pds.models.Schedule

object ScheduleMapper {

    fun buildScheduleDto(schedule: Schedule): ScheduleResponseDto {
        return ScheduleResponseDto(
            startTime = schedule.startTime,
            endTime = schedule.endTime,
            date = schedule.date,
            weekDay = schedule.weekDay,
            isVirtual = schedule.isVirtual,
        )
    }
}