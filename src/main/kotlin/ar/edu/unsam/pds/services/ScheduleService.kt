package ar.edu.unsam.pds.services

import ar.edu.unsam.pds.dto.request.ScheduleRequestDto
import ar.edu.unsam.pds.dto.response.CourseDetailResponseDto
import ar.edu.unsam.pds.dto.response.CourseResponseDto
import ar.edu.unsam.pds.dto.response.ScheduleResponseDto
import ar.edu.unsam.pds.exceptions.NotFoundException
import ar.edu.unsam.pds.exceptions.PermissionDeniedException
import ar.edu.unsam.pds.mappers.CourseMapper
import ar.edu.unsam.pds.mappers.ScheduleMapper
import ar.edu.unsam.pds.models.Classroom
import ar.edu.unsam.pds.models.Course
import ar.edu.unsam.pds.models.Schedule
import ar.edu.unsam.pds.repository.ClassroomRepository
import ar.edu.unsam.pds.repository.EventRepository
import ar.edu.unsam.pds.repository.ScheduleRepository
import ar.edu.unsam.pds.security.models.Principal
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.DayOfWeek
import java.time.LocalDate
import java.util.*

@Service
class ScheduleService(
    val scheduleRepository: ScheduleRepository,
    val classroomRepository: ClassroomRepository,
    val eventRepository : EventRepository
) {

    private fun findScheduleById(idSchedule: String): Schedule {
        val uuid = UUID.fromString(idSchedule)
        return scheduleRepository.findById(uuid).orElseThrow {
            NotFoundException("Schedule no encontrado para el uuid suministrado")
        }
    }

    @Transactional
    fun editSchedule(scheduleID: String,schedule: ScheduleRequestDto) {
        this.scheduleRepository.save(schedule)
        val updatedSchedule = buildSchedule(schedule, scheduleID)
        scheduleRepository.save(updatedSchedule)
    }

    @Transactional
    fun createSchedule(schedule: ScheduleRequestDto) {
        val newSchedule = buildSchedule(schedule)
        scheduleRepository.save(newSchedule)
    }


    private fun buildSchedule(schedule: ScheduleRequestDto, existingID:String? = null): Schedule {
        if (isValidDateOrWeekDay(schedule.date, schedule.weekDay)) {
            val classroom = findClassroom(schedule)


            val newSchedule = Schedule(
                schedule.startTime,
                schedule.endTime,
                schedule.weekDay,
                schedule.date,
                schedule.isVirtual,
                classroom,
            )


            newSchedule.id=UUID.fromString(existingID) ?: UUID.randomUUID()

            return newSchedule
        } else {
            throw IllegalArgumentException("Debe ingresar un día de la semana o una fecha válida")
        }
    }


    fun findClassroom(schedule: ScheduleRequestDto): Classroom? {
        if (schedule.isVirtual){
            return null
        }
        val classroomID = UUID.fromString(schedule.classroomID)
        return classroomRepository.findById(classroomID).orElseThrow{
            NotFoundException("Aula no encontrada para el id suministrado")
        }
    }


    private fun isValidDateOrWeekDay(date: LocalDate?, weekDay:DayOfWeek?):Boolean{
        return (date == null) != (weekDay == null)
    }


}
