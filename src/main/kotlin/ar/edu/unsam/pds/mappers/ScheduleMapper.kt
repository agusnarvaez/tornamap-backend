package ar.edu.unsam.pds.mappers

import ar.edu.unsam.pds.dto.request.ScheduleRequestDto
import ar.edu.unsam.pds.dto.response.ScheduleResponseDto
import ar.edu.unsam.pds.models.Schedule
import java.time.format.DateTimeFormatter

private val TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm")

object ScheduleMapper {

    fun buildScheduleDto(schedule: Schedule): ScheduleResponseDto {
        return ScheduleResponseDto(
            id = schedule.id.toString(),
            startTime = schedule.startTime.format(TIME_FORMATTER),
            endTime = schedule.endTime.format(TIME_FORMATTER),
            date = schedule.date,
            weekDay = schedule.weekDay,
            isVirtual = schedule.isVirtual,
            classroom = schedule.classroom?.let { ClassroomMapper.buildClasroomDto(it) },
            professors = schedule.assignedUsers.map { it.lastName+", "+it.name },
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