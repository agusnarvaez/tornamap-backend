package ar.edu.unsam.pds.services

import ar.edu.unsam.pds.controllers.UUIDValid
import ar.edu.unsam.pds.dto.request.EventRequestDto
import ar.edu.unsam.pds.dto.request.SearchEventRequestDto
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

    //si no hay busqueda devuelve toda la lista ordenada por horario OK

    //si viene el searchQuery devuelve solo los eventos que contengan el nombre y desestima eventDate y classroomID
    //si viene el eventDate devuelve solo los eventos que contengan la fecha y desestima el searchQuery y classroomID
    //si viene el classroomID devuelve solo los eventos que contengan el id del aula y desestima el searchQuery y eventDate


    fun searchBy(searchEventRequestDto: SearchEventRequestDto): List<EventResponseDto> {
        if (searchEventRequestDto.notBlankQuery()) {
            val searchEvent = eventRepository.findByQuery(
                searchEventRequestDto.searchQuery!!
            )
            return searchEvent.map { EventMapper.buildEventDto(it) }
        }
//        if (searchEventRequestDto.hasDateAndClassroomID()) {
//            val searchEvent = eventRepository.findByClassroomIDAndDate(
//                searchEventRequestDto.classroomID!!,
//                searchEventRequestDto.eventDate!!
//            )
//            return searchEvent.map { EventMapper.buildEventDto(it) }
//        }
        return getAll(searchEventRequestDto)
    }


    fun getAll(searchEventRequestDto: SearchEventRequestDto): List<EventResponseDto> {
        val sortedEvents = eventRepository.findAllByOrderByNameAsc()
        return sortedEvents.map { EventMapper.buildEventDto(it) }
    }

//    fun orderByAscDate(): List<Event> {
//        scheduleService.sortedDates(this.findAll())
//    }


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