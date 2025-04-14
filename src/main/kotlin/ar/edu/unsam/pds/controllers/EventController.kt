package ar.edu.unsam.pds.controllers

import ar.edu.unsam.pds.dto.request.EventRequestDto
import ar.edu.unsam.pds.dto.response.CustomResponse
import ar.edu.unsam.pds.mappers.EventMapper
import ar.edu.unsam.pds.services.EventService
import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/events")
@CrossOrigin("*")
class EventController : UUIDValid() {
    @Autowired lateinit var eventService: EventService

    @GetMapping("/{classroomID}/{date}")
    @Operation(summary = "Get all events in a given classroom")
    fun getByClassroom(@PathVariable (value="classroomID", required= true) classroomID : String,
                       @PathVariable (value="date", required= true) date: String): ResponseEntity<CustomResponse> {
        return ResponseEntity.status(200).body (
            CustomResponse(
                message = "Eventos obtenidos con exito",
                data = eventService.searchBy(classroomID, date).map { EventMapper.buildEventDto(it) }
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
    fun editEvent(@PathVariable eventId: String,
                  @RequestBody @Valid event: EventRequestDto
    ){
        eventService.editEvent(eventId,event)
    }
}