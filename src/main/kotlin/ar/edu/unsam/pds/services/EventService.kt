package ar.edu.unsam.pds.services

import ar.edu.unsam.pds.dto.request.EventRequestDto
import ar.edu.unsam.pds.dto.response.EventResponseDto
import ar.edu.unsam.pds.exceptions.NotFoundException
import ar.edu.unsam.pds.mappers.EventMapper
import ar.edu.unsam.pds.mappers.ScheduleMapper
import ar.edu.unsam.pds.models.Event
import ar.edu.unsam.pds.models.Schedule
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

    fun findByID(id:String?):Event{
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

    fun addSchedules(event: Event, schedules: MutableSet<Schedule>){
        schedules.forEach{ schedule -> event.addSchedule(schedule) }
    }

    fun addPeriod(event:Event, periodID:String?){
        val period = periodService.findById(periodID)
        event.addPeriod(period)
    }



    @Transactional
    fun update(eventDTO: EventRequestDto):Event{
        val existingEvent=findByID(eventDTO.id)
        val builtSchedules = eventDTO.schedules.map { schedule ->
            ScheduleMapper.buildSchedule(schedule)
        }.toMutableList()
        existingEvent.apply{
            name = eventDTO.name
            isApproved = eventDTO.isApproved
            isCancelled = eventDTO.isCancelled
            course = courseService.findByID(eventDTO.courseID)
            period = periodService.findById(eventDTO.periodID)
            schedules = builtSchedules
        }
        return eventRepository.save(existingEvent)
    }

    @Transactional
    fun create(newEvent: Event):Event{
        eventRepository.save(newEvent)
        return newEvent
    }


    @Transactional
    fun delete(id: String) {
        val eventToDelete = findByID(id)

        eventToDelete.schedules.forEach { schedule ->
            schedule.assignedUsers.forEach { user ->
                user.scheduleList.remove(schedule)
            }
            schedule.assignedUsers.clear()

            eventRepository.delete(eventToDelete)
        }
    }
}