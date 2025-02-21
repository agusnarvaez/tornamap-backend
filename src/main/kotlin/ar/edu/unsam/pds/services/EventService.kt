package ar.edu.unsam.pds.services

import ar.edu.unsam.pds.dto.request.EventRequestDto
import ar.edu.unsam.pds.dto.response.EventResponseDto
import ar.edu.unsam.pds.dto.response.UserResponseDto
import ar.edu.unsam.pds.exceptions.NotFoundException
import ar.edu.unsam.pds.exceptions.PermissionDeniedException
import ar.edu.unsam.pds.exceptions.ValidationException
import ar.edu.unsam.pds.mappers.EventMapper
import ar.edu.unsam.pds.mappers.UserMapper
import ar.edu.unsam.pds.models.Event
import ar.edu.unsam.pds.models.Schedule
import ar.edu.unsam.pds.repository.EventRepository
import ar.edu.unsam.pds.repository.CourseRepository
import ar.edu.unsam.pds.repository.ScheduleRepository
import ar.edu.unsam.pds.security.models.Principal
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.util.*

@Service
class EventService(
    private val eventRepository: EventRepository,
    private val scheduleRepository: ScheduleRepository,
    private val courseRepository: CourseRepository,
) {

    fun getAll(): List<EventResponseDto> {
        val events = eventRepository.findAll()
        return events.map { EventMapper.buildEventDto(it) }
    }

    fun getEvent(eventId: String): EventResponseDto {
        val assignments = findEventById(eventId)
        return EventMapper.buildEventDto(assignments)
    }

    private fun findEventById(eventId: String): Event {
        val uuid = UUID.fromString(eventId)
        return eventRepository.findById(uuid).orElseThrow {
            NotFoundException("Clase no encontrada, para el uuid suministrado")
        }
    }

    fun createEvent(event: EventRequestDto): EventResponseDto {
        val courseId = UUID.fromString(event.idCourse)
        val course = courseRepository.findById(courseId).orElseThrow {
            NotFoundException("Curso no encontrado, para el uuid suministrado")
        }

        if(event.schedule.startTime.isAfter(event.schedule.endTime)) {
            throw ValidationException("La hora de inicio no puede ser posterior a la hora de fin")
        }

        if(event.schedule.startDate.isBefore(LocalDate.now())) {
            throw ValidationException("La fecha de inicio no puede ser anterior a la fecha actual")
        }

        if (event.schedule.startDate.isAfter(event.schedule.endDate)) {
            throw ValidationException("La fecha de inicio no puede ser posterior a la fecha de fin")
        }

        val newSchedule = Schedule(
            days = event.schedule.days,
            startTime = event.schedule.startTime,
            endTime = event.schedule.endTime,
            startDate = event.schedule.startDate,
            endDate = event.schedule.endDate,
            recurrenceWeeks = event.schedule.recurrenceWeeks,
        )

        scheduleRepository.save(newSchedule)

        val newEvent = Event(
            name = event.name,
            isApproved = true,
            schedule = newSchedule
        )

        eventRepository.save(newEvent)
        course.addEvent(newEvent)
        courseRepository.save(course)

        return EventMapper.buildEventDto(newEvent)
    }

    @Transactional
    fun deleteEvent(eventId: String, principal: Principal) {
        val uuid = UUID.fromString(eventId)
        if (!eventRepository.isOwner(uuid, principal)) {
            throw PermissionDeniedException("Acceso denegado")
        }

        val event = findEventById(eventId)

        if (event.hasAnyUser()) {
            throw ValidationException("No se puede eliminar un curso con usuarios inscriptos")
        }

        val course = courseRepository.findByAssignmentId(event.id).orElseThrow {
            NotFoundException("curso no encontrado")
        }

        course.removeEvent(event)
        courseRepository.save(course)
    }

    fun getUsersInEvent(eventId: String): MutableList<UserResponseDto> {
        val event = findEventById(eventId)
        val users = event.users.map { user ->
            UserMapper.buildUserDto(user)
        }.toMutableList()
        return users
    }

}