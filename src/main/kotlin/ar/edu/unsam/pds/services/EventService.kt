package ar.edu.unsam.pds.services

import ar.edu.unsam.pds.controllers.UUIDValid
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

    fun searchBy(classroomID: String, date: String): List<Event> {
        val formattedDate = parseDate(date)
        val scheduleUUIDs = scheduleService.getIDByClassroomIDAndDate(classroomID, formattedDate)
        return eventRepository.findBySchedules_IdIn(scheduleUUIDs)
    }

    fun parseDate(date: String): LocalDate {
        return try {
            LocalDate.parse(date)
        } catch (e: Exception) {
            throw ValidationException("Formato de fecha inválido")
        }
    }

    fun getEvent(eventId: String): EventResponseDto? {
        val eventUUID = UUID.fromString(eventId)
        val matchingEvent = eventRepository.findById(eventUUID)
            .orElseThrow { NotFoundException("Evento no encontrado para el uuid suministrado") }

        return EventMapper.buildEventDto(matchingEvent)
    }

    private fun findEventById(){}

    fun createEvent(){}

    @Transactional
    fun deleteEvent() {}

}