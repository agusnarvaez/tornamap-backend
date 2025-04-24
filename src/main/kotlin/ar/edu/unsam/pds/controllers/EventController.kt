package ar.edu.unsam.pds.controllers

import ar.edu.unsam.pds.dto.request.EventRequestDto
import ar.edu.unsam.pds.dto.response.CustomResponse
import ar.edu.unsam.pds.mappers.EventMapper
import ar.edu.unsam.pds.mappers.ScheduleMapper
import ar.edu.unsam.pds.services.CourseService
import ar.edu.unsam.pds.services.EventService
import ar.edu.unsam.pds.services.PeriodService
import ar.edu.unsam.pds.services.UserService
import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("api/events")
@CrossOrigin("*")
class EventController : UUIDValid() {
    @Autowired lateinit var eventService: EventService
    @Autowired lateinit var courseService: CourseService
    @Autowired lateinit var periodService: PeriodService

    @GetMapping("/{classroomID}/{date}")
    @Operation(summary = "Get all events in a given classroom")
    fun getByClassroom(
        @PathVariable classroomID: String,
        @PathVariable date: String
    ): ResponseEntity<CustomResponse> {
        val parsedDate = LocalDate.parse(date)
        return ResponseEntity.status(200).body (
            CustomResponse(
                message = "Eventos obtenidos con exito",
                data = eventService.searchBy(classroomID, parsedDate).flatMap { event ->
                    event.schedules.map { schedule -> EventMapper.buildEventCardDto(event, schedule) }
                }.sortedBy { event -> event.schedules[0].startTime }
            )
        )
    }

    @GetMapping("{eventID}")
    @Operation(summary = "Get an event by ID")
    fun getEvent (@PathVariable (value="eventID", required= true) eventID: String): ResponseEntity<CustomResponse> {
        validatedUUID(eventID)
        return ResponseEntity.status(200).body (
            CustomResponse(
                message = "Evento obtenido con exito",
                data = eventService.getEvent(eventID)
            )
        )
    }

/*
    @PostMapping("")
    @Operation(summary = "Create an event")
    fun createEvent(
        @RequestBody @Valid event: EventRequestDto
    ){ eventService.createEvent(event) }

    @DeleteMapping("{eventId}")
    @Operation(summary = "Delete an event by ID")
    fun deleteEvent(
        @PathVariable eventID: String
    ){
        eventService.deleteEvent(eventID)
    }
*/

    @PutMapping("{eventId}")
    @Operation(summary = "Edit an event by ID")
    fun editEvent(
                  @RequestBody @Valid eventDTO: EventRequestDto
    ): ResponseEntity<CustomResponse> {
        val eventID=eventDTO.id
            ?: throw IllegalArgumentException("Debe proveer un ID de Event para editarlo")

        val existingEvent = eventService.findEventByID(eventID)

        val builtSchedules = eventDTO.schedules.map { schedule ->
            ScheduleMapper.buildSchedule(schedule)
        }.toMutableSet()

        existingEvent.apply{
            name = eventDTO.name
            isApproved = eventDTO.isApproved
            isCancelled = eventDTO.isCancelled
            course = courseService.findCourseByID(eventDTO.courseID)
            period = periodService.findPeriodByID(eventDTO.periodID)
            schedules = builtSchedules
        }

        eventService.editEvent(existingEvent)

        return ResponseEntity.status(200).body(
            CustomResponse(
                message = "Event editado con éxito",
                data = EventMapper.buildEventDto(existingEvent)
            )
        )
    }
}