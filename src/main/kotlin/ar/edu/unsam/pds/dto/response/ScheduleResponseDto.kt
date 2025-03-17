package ar.edu.unsam.pds.dto.response

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime

data class ScheduleResponseDto (
    val startTime: LocalTime,
    val endTime: LocalTime,
    val weekDay:DayOfWeek?,
    val date: LocalDate?,
    val isVirtual:Boolean
)

