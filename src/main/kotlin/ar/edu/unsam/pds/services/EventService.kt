package ar.edu.unsam.pds.services

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
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.util.*

@Service
class EventService(
    private val eventRepository: EventRepository,
    private val scheduleRepository: ScheduleRepository,
    private val courseRepository: CourseRepository,
    private val courseService:CourseService,
    private val periodService: PeriodService
) {

    fun getAll(){}

    fun getEvent(eventId: String){}

    private fun findEventById(){}

    fun createEvent(event: EventRequestDto){

        val course= event.courseID?.let { courseService.findCourseById(it) }
        val period= event.periodID?.let{ periodService.findPeriodById(it) }
        //val schedules=

        val newEvent = Event(event.name,
                            isApproved = true,
                            isCancelled = false,
        )

        course?.let { newEvent.attachCourse(it) }
        period?.let { newEvent.addPeriod(it) }
        event.schedules.forEach { newEvent.addSchedule(it) }

        eventRepository.save(newEvent)
    }

    @Transactional
    fun deleteEvent() {}

}