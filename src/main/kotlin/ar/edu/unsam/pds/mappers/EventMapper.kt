package ar.edu.unsam.pds.mappers

import ar.edu.unsam.pds.dto.response.EventResponseDto
import ar.edu.unsam.pds.dto.response.EventStatsResponseDto
import ar.edu.unsam.pds.models.Event

object EventMapper {

    fun buildEventDto(event: Event): EventResponseDto {
        return EventResponseDto(
            id = event.id.toString(),
            courseName = event.getCourseName(),
            programNames= event.getProgramNames(),
            professorsName= event.getProfessorNames(),
            schedule = ScheduleMapper.buildScheduleDto(event.schedule),
            isCancelled = event.isCancelled
        )
    }


}