package ar.edu.unsam.pds.mappers

import ar.edu.unsam.pds.dto.request.ScheduleRequestDto
import ar.edu.unsam.pds.dto.response.ScheduleResponseDto
import ar.edu.unsam.pds.models.Schedule

object ScheduleMapper {

    fun buildScheduleDto(schedule: Schedule): ScheduleResponseDto {
        return ScheduleResponseDto(
            id = schedule.id.toString(),
            startTime = schedule.startTime,
            endTime = schedule.endTime,
            date = schedule.date,
            weekDay = schedule.weekDay,
            isVirtual = schedule.isVirtual,
            classroom = schedule.classroom?.let { ClassroomMapper.buildClasroomDto(it) }
        )
    }

    fun buildSchedule(schedule: ScheduleRequestDto): Schedule {

        return Schedule(
            startTime = schedule.startTime,
            endTime = schedule.endTime,
            weekDay = schedule.weekDay,
            date = schedule.date,
            isVirtual = schedule.isVirtual
        )
    }
}