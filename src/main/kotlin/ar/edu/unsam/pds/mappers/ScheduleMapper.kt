package ar.edu.unsam.pds.mappers

import ar.edu.unsam.pds.dto.request.ScheduleRequestDto
import ar.edu.unsam.pds.dto.response.ClassroomResponseDto
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
            professors = schedule.assignedUsers.map { it.fullName() },
            classroom = getClassroomDto(schedule)
        )
    }

    fun getClassroomDto(schedule: Schedule): ClassroomResponseDto? {
        return if (schedule.classroom != null) {
            ClassroomMapper.buildClassroomDto(schedule.classroom!!)
        } else {
            null
        }
    }
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
        "classroom": [
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
            "classroom": [],
            "isVirtual": "true"
        }
        ]
    }*/