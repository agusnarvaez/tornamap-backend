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
    fun getIDByClassroomIDAndDate(classroomID: String, date: LocalDate): List<UUID> {
        val classroomUUID = UUID.fromString(classroomID)
        val matchingSchedules = scheduleRepository.findByClassroomIdAndDate(classroomUUID,date)
        return schedulesToUUIDs(matchingSchedules)
    }

    fun schedulesToUUIDs(schedules: List<Schedule>): List<UUID> {
        if (schedules.isEmpty()) {
            throw NotFoundException("No se encontraron horarios para el aula y la fecha especificadas")
        }
        return schedules.map { it.id }
    }
}