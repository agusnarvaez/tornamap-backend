package ar.edu.unsam.pds.dto.response

import ar.edu.unsam.pds.models.Classroom
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime

data class ScheduleResponseDto (
    val id: String,
    val startTime: String,
    val endTime: String,
    val weekDay:DayOfWeek?,
    val date: LocalDate?,
    val isVirtual:Boolean,
    val professors: List<String>,
    val classroom: ClassroomResponseDto?
)

