package ar.edu.unsam.pds.services

import ar.edu.unsam.pds.dto.request.EventRequestDto
import ar.edu.unsam.pds.dto.response.EventResponseDto
import ar.edu.unsam.pds.exceptions.NotFoundException
import ar.edu.unsam.pds.exceptions.ValidationException
import ar.edu.unsam.pds.mappers.EventMapper
import ar.edu.unsam.pds.models.Event
import ar.edu.unsam.pds.repository.EventRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.util.*

@Service
class EventService(
    private val eventRepository: EventRepository,
    private val courseService:CourseService,
    private val periodService: PeriodService,
) {

    fun getAll():List<Event>{
        return eventRepository.findAll()
    }

    @Transactional(readOnly = true)
    fun searchBy(classroomID: String, date: LocalDate): List<Event> = eventRepository.findEventsByClassroomAndDate(classroomID, date, date.dayOfWeek)

    fun findEventByID(id:String):Event{
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
    fun editEvent(event: Event){
        eventRepository.save(event)
    }

    @Transactional
    fun createEvent(newEvent: Event){
        eventRepository.save(newEvent)
    }


    @Transactional
    fun deleteEvent(id: String) {
        val event=findEventByID(id)
        eventRepository.delete(event)
    }

}