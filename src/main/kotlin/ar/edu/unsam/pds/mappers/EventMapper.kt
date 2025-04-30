package ar.edu.unsam.pds.mappers

import ar.edu.unsam.pds.dto.request.EventRequestDto
import ar.edu.unsam.pds.dto.response.EventResponseDto
import ar.edu.unsam.pds.models.Course
import ar.edu.unsam.pds.models.Event
import ar.edu.unsam.pds.models.Schedule

object EventMapper {


    fun buildEventDto(event: Event): EventResponseDto {
        return EventResponseDto(
            id = event.id.toString(),
            name = event.name,
            isApproved = event.isApproved,
            schedules = event.schedules.map { ScheduleMapper.buildScheduleDto(it) },
            isCancelled = event.isCancelled,
            courseName = event.getCourseName(),
            programNames= event.getProgramNames(),
        )
    }

    fun buildEventCardDto(event: Event, schedule: Schedule): EventResponseDto {
        return EventResponseDto(
            id = event.id.toString(),
            name = event.name,
            isApproved = event.isApproved,
            schedules = listOf(ScheduleMapper.buildScheduleDto(schedule)),
            isCancelled = event.isCancelled,
            courseName = event.getCourseName(),
            programNames= event.getProgramNames(),
        )
    }

    fun buildEvent(eventDTO: EventRequestDto, course: Course): Event {
        return Event(
            name = eventDTO.name,
            isApproved = eventDTO.isApproved,
            isCancelled = eventDTO.isCancelled,
            course = course,
        )
    }
}