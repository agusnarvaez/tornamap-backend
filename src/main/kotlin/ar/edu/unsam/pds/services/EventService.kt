package ar.edu.unsam.pds.services

import ar.edu.unsam.pds.dto.request.EventRequestDto
import ar.edu.unsam.pds.dto.response.EventDetailResponseDto
import ar.edu.unsam.pds.dto.response.EventResponseDto
import ar.edu.unsam.pds.exceptions.NotFoundException
import ar.edu.unsam.pds.mappers.EventMapper
import ar.edu.unsam.pds.mappers.ScheduleMapper
import ar.edu.unsam.pds.models.Event
import ar.edu.unsam.pds.models.Schedule
import ar.edu.unsam.pds.repository.ClassroomRepository
import ar.edu.unsam.pds.repository.EventRepository
import ar.edu.unsam.pds.repository.ScheduleRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.util.*

@Service
class EventService(
    private val eventRepository: EventRepository,
    private val courseService:CourseService,
    private val periodService: PeriodService,
    private val classroomRepository: ClassroomRepository,
    private val scheduleRepository: ScheduleRepository
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

    fun getEventDetail(eventId: String): EventDetailResponseDto? {
        val eventUUID = UUID.fromString(eventId)
        val matchingEvent = eventRepository.findById(eventUUID)
            .orElseThrow { NotFoundException("Evento no encontrado para el uuid suministrado") }

        return EventMapper.buildEventDetailDto(matchingEvent)
    }

    fun addSchedules(event: Event, schedules: List<Schedule>){
        event.addSchedules(schedules)
    }

    fun addPeriod(event:Event, periodID:String?){
        val period = periodService.getById(periodID)
        event.addPeriod(period)
    }

    @Transactional
    fun update(eventDto: EventRequestDto): Event {

        val existingEvent = findByID(eventDto.id)

        /* 1️⃣ datos simples */
        existingEvent.apply {
            name        = eventDto.name
            isApproved  = eventDto.isApproved
            isCancelled = eventDto.isCancelled
            course      = courseService.findByID(eventDto.courseID)
            period      = periodService.getById(eventDto.periodID)
        }
        /* ─── horarios ────────────────────────────────── */
        // índice de los actuales por id
        val currentById = existingEvent.schedules.associateBy { it.id }.toMutableMap()
        // recorremos los que llegan del front
        eventDto.schedules.forEach { schDto ->

            val schId = schDto.id?.let(UUID::fromString)

            // a) existe → sólo se actualiza
            if (schId != null && currentById.containsKey(schId)) {

                val cur = currentById.remove(schId)!!

                cur.startTime = schDto.startTime
                cur.endTime   = schDto.endTime
                cur.weekDay   = ScheduleMapper.toDayOfWeek(schDto.weekDay)
                cur.date      = schDto.date
                cur.isVirtual = schDto.isVirtual
                cur.classroom = if (cur.isVirtual) null
                else classroomRepository.findById(
                    UUID.fromString(schDto.classroomId)
                ).orElseThrow()
            }
            // b) no existe → es nuevo
            else {
                val newSch = ScheduleMapper.buildSchedule(schDto).apply {
                    event = existingEvent
                    if (!isVirtual) {
                        classroom = classroomRepository.findById(
                            UUID.fromString(schDto.classroomId)
                        ).orElseThrow()
                    }
                }
                existingEvent.schedules.add(newSch)
            }
        }
        // c) lo que sobró en currentById se eliminó en el front → borramos
        currentById.values.forEach { orphan ->
            /* 🔸 romper la relación en las DOS puntas */
            orphan.assignedUsers.forEach { user ->
                user.scheduleList.remove(orphan)      // lado User
            }
            orphan.assignedUsers.clear()          // rompe FK user-schedule
            existingEvent.schedules.remove(orphan)        // Hibernate hace el delete
            scheduleRepository.delete(orphan)
        }

        return existingEvent
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