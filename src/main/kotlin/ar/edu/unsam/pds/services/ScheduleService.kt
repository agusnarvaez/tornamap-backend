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
    val classroomRepository: ClassroomRepository
    // val eventRepository : EventRepository
) {
    fun getAll(query: String): List<ScheduleResponseDto> {
        val schedules = scheduleRepository.getAllBy(query)
        return schedules.map { ScheduleMapper.buildScheduleDto(this) }
    }

    fun getAllByPrincipal(query: String, principal: Principal): List<CourseResponseDto> {
        val schedules = scheduleRepository.getAllByPrincipal(query, principal)
        return schedules.map { ScheduleMapper.buildScheduleDto(this) }
    }

    fun getSchedule(idSchedule: String): ScheduleResponseDto {
        val schedule = findScheduleById(idSchedule)
        return ScheduleMapper.buildScheduleDto(schedule)
    }

    @Transactional
    fun deleteSchedule(idSchedule: String, principal: Principal) {
        val schedule = findScheduleById(idSchedule)
        val uuid = UUID.fromString(idSchedule)

        if (!scheduleRepository.isOwner(uuid, principal)) {
            throw PermissionDeniedException("No se puede borrar un curso del cual no es propietario")
        }

    }

    @Transactional
    fun deleteAllById(scheduleIds: List<String>, principal: Principal) {
        scheduleIds.forEach { id ->
            deleteSchedule(id, principal)
        }
    }

    private fun findScheduleById(idSchedule: String): Schedule {
        val uuid = UUID.fromString(idSchedule)
        return scheduleRepository.findById(uuid).orElseThrow {
            NotFoundException("Curso no encontrado para el uuid suministrado")
        }
    }


    fun createSchedule(schedule: ScheduleRequestDto){
        if (isValidDateOrWeekDay(schedule.date,schedule.weekDay)){
            val idEvent = UUID.fromString(schedule.idEvent)
            /*val event=eventRepository.findById(idEvent).orElseThrow {
                NotFoundException("Evento no encontrado para el uuid suministrado")
            }*/

            val classroom=createClassroom(schedule)

            val newSchedule=Schedule(
                schedule.startTime,
                schedule.endTime,
                schedule.weekDay,
                schedule.date,
                schedule.isVirtual,
                classroom,
                //event
            )

            scheduleRepository.save(newSchedule)
        }
    }

    private fun createClassroom(schedule: ScheduleRequestDto): Classroom? {
        if (schedule.isVirtual){
            return null
        } else{
            val idClassroom = UUID.fromString(schedule.idClassroom)
            return classroomRepository.findById(idClassroom).orElseThrow{
                NotFoundException("Aula no encontrada para el id suministrado")
            }
        }
    }


    private fun isValidDateOrWeekDay(date: LocalDate?, weekDay:DayOfWeek?):Boolean{
        return (date == null) != (weekDay == null)
    }


}