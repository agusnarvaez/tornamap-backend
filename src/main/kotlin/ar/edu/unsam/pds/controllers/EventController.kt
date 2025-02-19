package ar.edu.unsam.pds.controllers

import ar.edu.unsam.pds.dto.request.EventRequestDto
import ar.edu.unsam.pds.dto.response.EventResponseDto
import ar.edu.unsam.pds.dto.response.UserResponseDto
import ar.edu.unsam.pds.security.models.Principal
import ar.edu.unsam.pds.services.EventService
import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/events")
@CrossOrigin("*")
class EventController : UUIDValid() {
    @Autowired lateinit var eventService: EventService

    @GetMapping("")
    @Operation(summary = "Get all events")
    fun getAll(): ResponseEntity<List<EventResponseDto>> {
        return ResponseEntity.ok(eventService.getAll())
    }

    @GetMapping("{idEvent}")
    @Operation(summary = "Get event by id")
    fun getEvent(
        @PathVariable idEvent: String
    ): ResponseEntity<EventResponseDto> {
        this.validatedUUID(idEvent)
        return ResponseEntity.ok(eventService.getEvent(idEvent))
    }

    @PostMapping("")
    @Operation(summary = "Create a event")
    fun createEvent(
        @RequestBody @Valid event: EventRequestDto
    ): ResponseEntity<EventResponseDto> {
        return ResponseEntity.ok(eventService.createEvent(event))
    }

    @DeleteMapping("{idEvent}")
    @Operation(summary = "Delete event by id")
    fun deleteEvent(
        @PathVariable idEvent: String,
        @AuthenticationPrincipal principal: Principal
    ): ResponseEntity<Map<String, String>> {
        this.validatedUUID(idEvent)
        eventService.deleteEvent(idEvent, principal)
        return ResponseEntity.ok(mapOf("message" to "Event eliminado correctamente."))
    }

    @GetMapping("{idEvent}/admin")
    @Operation(summary = "get users who participate in this event for administrator")
    fun getEventUsers(
        @PathVariable idEvent: String
    ): ResponseEntity<List<UserResponseDto>> {
        this.validatedUUID(idEvent)
        return ResponseEntity.ok(eventService.getUsersInEvent(idEvent))
    }

}