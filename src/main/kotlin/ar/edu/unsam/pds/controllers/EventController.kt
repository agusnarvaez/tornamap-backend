package ar.edu.unsam.pds.controllers

import ar.edu.unsam.pds.dto.request.EventRequestDto
import ar.edu.unsam.pds.services.EventService
import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("api/events")
@CrossOrigin("*")
class EventController : UUIDValid() {
    @Autowired lateinit var eventService: EventService

    @GetMapping("")
    @Operation(summary = "Get all events")
    fun getAll(
        @PathVariable(value = "search", required = false) search: String?,
        @PathVariable(value = "eventDate", required = false) eventDate: LocalDate?,
        @PathVariable(value = "classroomName", required = false) classroomName: String?
    ){}

    @GetMapping("{eventId}")
    @Operation(summary = "Get an event by ID")
    fun getEvent(){}


    @PostMapping("")
    @Operation(summary = "Create an event")
    fun createEvent(
        @RequestBody @Valid event: EventRequestDto
    ){ eventService.createEvent(event) }


    @DeleteMapping("{eventId}")
    @Operation(summary = "Delete an event by ID")
    fun deleteEvent(
        @PathVariable eventID: String
    ){ eventService.deleteEvent(eventID) }

}