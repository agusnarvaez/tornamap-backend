package ar.edu.unsam.pds.mappers

import ar.edu.unsam.pds.dto.request.ScheduleRequestDto
import ar.edu.unsam.pds.dto.response.ScheduleResponseDto
import ar.edu.unsam.pds.models.Schedule
import java.time.DayOfWeek
import java.util.Locale
import java.time.format.DateTimeFormatter

private val TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm")

val spanish2DayOfWeek = mapOf(
    "LUNES"    to DayOfWeek.MONDAY,
    "MARTES"   to DayOfWeek.TUESDAY,
    "MIÉRCOLES" to DayOfWeek.WEDNESDAY,
    "JUEVES"   to DayOfWeek.THURSDAY,
    "VIERNES"  to DayOfWeek.FRIDAY,
    "SÁBADO"   to DayOfWeek.SATURDAY,
    "DOMINGO"  to DayOfWeek.SUNDAY
)

object ScheduleMapper {

    fun buildScheduleDto(schedule: Schedule): ScheduleResponseDto {
        return ScheduleResponseDto(
            id = schedule.id.toString(),
            startTime = schedule.startTime.format(TIME_FORMATTER),
            endTime = schedule.endTime.format(TIME_FORMATTER),
            date = schedule.date,
            weekDay = schedule.translateAndFormatWeekDay(),
            isVirtual = schedule.isVirtual,
            classroom = schedule.classroom?.let { ClassroomMapper.buildClassroomDto(it) },
            professors = schedule.assignedUsers.map { it.lastName+", "+it.name },
        )
    }

    /** usado en la actualización */
    fun toDayOfWeek(label: String?): DayOfWeek? =
        label?.trim()?.uppercase(Locale.ROOT)?.let { spanish2DayOfWeek[it] }

    fun buildSchedule(schedule: ScheduleRequestDto): Schedule {
        val weekDayEnum = schedule.weekDay               // "martes", "MARTES", …
            ?.trim()                                     // quita espacios
            ?.uppercase(Locale.ROOT)                     // <-- aquí el cambio ✅
            ?.let { spanish2DayOfWeek[it] }              // busca en el mapa
        return Schedule(
            startTime = schedule.startTime,
            endTime   = schedule.endTime,
            weekDay   = weekDayEnum,
            date      = schedule.date,
            isVirtual = schedule.isVirtual
        )
    }


}

