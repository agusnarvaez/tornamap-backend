package ar.edu.unsam.pds.controllers

import ar.edu.unsam.pds.dto.request.EventRequestDto
import ar.edu.unsam.pds.dto.request.ScheduleRequestDto
import ar.edu.unsam.pds.mappers.ScheduleMapper
import ar.edu.unsam.pds.services.ScheduleService
import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/schedules")
@CrossOrigin("*")
class ScheduleController : UUIDValid() {
    @Autowired lateinit var scheduleService: ScheduleService
    @Autowired lateinit var scheduleMapper: ScheduleMapper

    @PutMapping("{scheduleId}")
    @Operation(summary = "Edit an schedule by ID")
    fun editEvent(@PathVariable eventID: String,
                  @RequestBody @Valid schedule: ScheduleRequestDto
    ){
        val schedule = scheduleMapper.buildSchedule(schedule)
        scheduleService.editSchedule(eventID, schedule)
    }

    @PostMapping("")
    @Operation(summary = "Create an schedule")
    fun createEvent(
        @RequestBody @Valid schedule: ScheduleRequestDto
    ){ scheduleService.createSchedule(schedule)}
    }