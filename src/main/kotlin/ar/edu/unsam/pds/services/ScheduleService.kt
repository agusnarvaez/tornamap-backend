package ar.edu.unsam.pds.services

import ar.edu.unsam.pds.dto.response.ScheduleResponseDto
import ar.edu.unsam.pds.models.Schedule
import org.springframework.stereotype.Service
import java.time.DayOfWeek
import java.time.LocalDate

@Service
class ScheduleService {

    fun createSchedule(schedule: ScheduleResponseDto){
        if (isValidDateOrWeekDay(schedule.date,schedule.weekDay)){
            //aca crea el schedule
        }
    }

    fun isValidDateOrWeekDay(date: LocalDate?, weekDay:DayOfWeek?):Boolean{
        return (date == null) != (weekDay == null)
    }
}