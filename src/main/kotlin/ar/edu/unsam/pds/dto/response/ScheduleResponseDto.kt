package ar.edu.unsam.pds.dto.response

import java.time.DayOfWeek
import java.time.LocalDate

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

