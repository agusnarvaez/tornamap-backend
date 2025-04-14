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
import ar.edu.unsam.pds.models.Course
import ar.edu.unsam.pds.models.Event
import ar.edu.unsam.pds.models.Schedule
import ar.edu.unsam.pds.repository.EventRepository
import ar.edu.unsam.pds.repository.CourseRepository
import ar.edu.unsam.pds.repository.ScheduleRepository
import ar.edu.unsam.pds.security.models.Principal
import com.sun.java.accessibility.util.EventID
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.util.*

@Service
class EventService(
    private val eventRepository: EventRepository,
    private val scheduleService: ScheduleService,
    private val courseService:CourseService,
    private val periodService: PeriodService,
    private val scheduleService:ScheduleService,
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

    private fun findEventByID(id:String):Event{
        val eventID= UUID.fromString(id)
        return eventRepository.findById(eventID).orElseThrow {
            NotFoundException("Evento no encontrado para el uuid suministrado")
        }
    }
    fun getEvent(eventId: String): EventResponseDto? {
        val eventUUID = UUID.fromString(eventId)
        val matchingEvent = eventRepository.findById(eventUUID)
            .orElseThrow { NotFoundException("Evento no encontrado para el uuid suministrado") }

        return EventMapper.buildEventDto(matchingEvent)
    }

    @Transactional
    fun editEvent(eventID:String, event: EventRequestDto){
        val updatedEvent=buildEvent(event,eventID)
        eventRepository.save(updatedEvent)
    }

    @Transactional
    fun createEvent(event: EventRequestDto){
        val newEvent= buildEvent(event)
        eventRepository.save(newEvent)
    }

    private fun buildEvent(event: EventRequestDto, existingID: String? = null):Event{

        val course= event.courseID?.let { courseService.findCourseById(it) }
        val period= event.periodID?.let{ periodService.findPeriodById(it) }
        val schedules: List<Schedule> = event.schedules.map {scheduleService.createSchedule(it) }

        val newEvent = Event(
            event.name,
            isApproved = true,
            isCancelled = false,
        )

        newEvent.id = UUID.fromString(existingID) ?: UUID.randomUUID()
        course?.let { newEvent.attachCourse(it) }
        period?.let { newEvent.addPeriod(it) }
        schedules.forEach { newEvent.addSchedule(it) }

        return newEvent
    }

    @Transactional
    fun deleteEvent(id: String) {
        val event=findEventByID(id)
        eventRepository.delete(event)
    }

}