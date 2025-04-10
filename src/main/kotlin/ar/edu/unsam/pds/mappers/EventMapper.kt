package ar.edu.unsam.pds.mappers

import ar.edu.unsam.pds.dto.response.EventResponseDto
import ar.edu.unsam.pds.dto.response.EventStatsResponseDto
import ar.edu.unsam.pds.models.Event

object EventMapper {

    fun buildEventDto(event: Event): EventResponseDto {
        return EventResponseDto(
            id = event.id.toString(),
            name = event.name,
            courseName = event.getCourseName(),
            programNames= event.getProgramNames(),
            professorsName= event.getProfessorNames(),
            schedules = event.schedules.map { ScheduleMapper.buildScheduleDto(it) },
        )
    }

    /*{
        "className": "nombreClase",                         OK
        "programs": ["Carrera 1", "Carrera 2"],
        "schedules":[
        {
            "startTime": "18:00",
            "endTime": "22:00",
            "professors": ["Profesor 1", "Profesor 2"],
            "weekDay": "Martes",
            "date": "null",
            "classRoom": [
            {
                id: "A28",
                name: "A28 - Edificio Tornavías"
                ],
                "isVirtual": "false"
            },
            {
                "startTime": "19:00",
                "endTime": "21:00",
                "professors": ["Profesor 1"],
                "weekDay": "Jueves",
                "date": "null"
                "classRoom": [],
                "isVirtual": "true"
            }
            ]
        }*/

}