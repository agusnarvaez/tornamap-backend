package ar.edu.unsam.pds.controllers

import ar.edu.unsam.pds.dto.request.EventRequestDto
import ar.edu.unsam.pds.dto.request.SearchEventRequestDto
import ar.edu.unsam.pds.dto.response.EventResponseDto
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
    fun getAll(@ModelAttribute searchEventRequestDto: SearchEventRequestDto): List<EventResponseDto> {
        println(searchEventRequestDto.searchQuery)
        if (searchEventRequestDto.notBlankID()){
            validatedUUID(searchEventRequestDto.classroomID)
        }
        return eventService.searchBy(searchEventRequestDto)
    }


    @GetMapping("{eventID}")
    @Operation(summary = "Get an event by ID")
    fun getEvent (@PathVariable (value="eventID", required= true) eventID: String): EventResponseDto? {
        validatedUUID(eventID)
        return eventService.getEvent(eventID)
    }


    @PostMapping("")
    @Operation(summary = "Create an event")
    fun createEvent(
        @RequestBody @Valid event: EventRequestDto
    ){}


    @DeleteMapping("{eventId}")
    @Operation(summary = "Delete an event by ID")
    fun deleteEvent(
        @PathVariable eventId: String
    ){}

}