package ar.edu.unsam.pds.controllers

import ar.edu.unsam.pds.dto.request.EventRequestDto
import ar.edu.unsam.pds.dto.response.CustomResponse
import ar.edu.unsam.pds.mappers.EventMapper
import ar.edu.unsam.pds.mappers.ScheduleMapper
import ar.edu.unsam.pds.services.CourseService
import ar.edu.unsam.pds.services.EventService
import ar.edu.unsam.pds.services.PeriodService
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

    @GetMapping
    @Operation(summary = "Get all events")
    fun getAllEvents(
    ): ResponseEntity<CustomResponse> {
        val events = eventService.getAll()
        return ResponseEntity.status(200).body(
            CustomResponse(
                message = "Events encontrados",
                data = events.map { EventMapper.buildEventDto(it) }
            )
        )
    }


    @PostMapping("")
    @Operation(summary = "Create an event")
    fun createEvent(
        @RequestBody @Valid eventDTO: EventRequestDto
    ): ResponseEntity<CustomResponse> {

        val course = courseService.findByID(eventDTO.courseID)
        val event = EventMapper.buildEvent(eventDTO,course)

        val builtSchedules = eventDTO.schedules.map { schedule ->
            ScheduleMapper.buildSchedule(schedule)
        }

        eventService.addSchedules(event,builtSchedules)
        eventService.addPeriod(event, eventDTO.periodID)

        val newEvent = eventService.create(event)

        return ResponseEntity.status(200).body(
            CustomResponse(
                message = "Event creado con éxito",
                data = EventMapper.buildEventDto(newEvent)
            )
        )
    }

        @DeleteMapping("{id}")
        @Operation(summary = "Delete an event by ID")
        fun deleteEvent(
            @PathVariable id: String
        ): ResponseEntity<CustomResponse> {
            validatedUUID(id)
            eventService.delete(id)
            return ResponseEntity.status(200).body(
                CustomResponse(
                    message = "Event eliminado con exito",
                    data = null
                )
            )
        }


    @PutMapping
    @Operation(summary = "Edit an event by ID")
    fun editEvent(
                  @RequestBody @Valid eventDTO: EventRequestDto
    ): ResponseEntity<CustomResponse> {
        val updatedEvent = eventService.update(eventDTO)
        return ResponseEntity.status(200).body(
            CustomResponse(
                message = "Event editado con éxito",
                data = EventMapper.buildEventDto(updatedEvent)
            )
        )
    }
}