package ar.edu.unsam.pds.controllers

import ar.edu.unsam.pds.dto.request.ScheduleRequestDto
import ar.edu.unsam.pds.dto.response.CustomResponse
import ar.edu.unsam.pds.exceptions.NotFoundException
import ar.edu.unsam.pds.mappers.ScheduleMapper
import ar.edu.unsam.pds.repository.ClassroomRepository
import ar.edu.unsam.pds.services.ScheduleService
import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("api/schedules")
@CrossOrigin("*")
class ScheduleController : UUIDValid() {
    @Autowired private lateinit var classroomRepository: ClassroomRepository
    @Autowired private lateinit var scheduleService: ScheduleService

    @GetMapping
    @Operation(summary = "Get all schedules")
    fun getAllSchedules(
    ): ResponseEntity<CustomResponse> {
        val schedules = scheduleService.getAll()
        return ResponseEntity.status(200).body(
            CustomResponse(
                message = "Schedules encontrados",
                data = schedules.map { ScheduleMapper.buildScheduleDto(it) }
            )
        )
    }


    @PutMapping
    @Operation(summary = "Edit an schedule by ID")
    fun editSchedule(
        @RequestBody @Valid scheduleDTO: ScheduleRequestDto
    ):ResponseEntity<CustomResponse> {
        // 1) Verificar que tengamos un ID
        val scheduleId = scheduleDTO.id
            ?: throw IllegalArgumentException("Debe proveer un ID de Schedule para editarlo")

        // 2) Buscar el Schedule en la BD
        val existingSchedule = scheduleService.findById(scheduleId)

        // 3) Actualizarle los campos
        existingSchedule.apply {
            startTime = scheduleDTO.startTime
            endTime   = scheduleDTO.endTime
            weekDay   = scheduleDTO.weekDay
            date      = scheduleDTO.date
            isVirtual = scheduleDTO.isVirtual
        }

        // 4) Manejar el classroom, si no es virtual
        if(!scheduleDTO.isVirtual && scheduleDTO.classroomId == null) {
            throw IllegalArgumentException("El Schedule no es virtual, pero no se ha proporcionado un classroomId")
        } else if (!scheduleDTO.isVirtual) {
            val classroomEntity = classroomRepository
                .findById(UUID.fromString(scheduleDTO.classroomId))
                .orElseThrow { NotFoundException("No se encontró Classroom con id=${scheduleDTO.classroomId}") }
            existingSchedule.classroom = classroomEntity
        } else {
            // Si es virtual, podrías poner classroom en null
            existingSchedule.classroom = null
        }

        // 5) Guardar y devolver el Schedule con los cambios aplicados
        scheduleService.editSchedule(existingSchedule)

        return ResponseEntity.status(200).body(
            CustomResponse(
                message = "Schedule editado con éxito",
                data = ScheduleMapper.buildScheduleDto(existingSchedule)
            )
        )
    }

    @PostMapping
    @Operation(summary = "Create an schedule")
    fun createSchedule(
        @RequestBody @Valid scheduleDTO: ScheduleRequestDto
    ):ResponseEntity<CustomResponse> {
        val schedule = ScheduleMapper.buildSchedule(scheduleDTO)
        if (!scheduleDTO.isVirtual) {
            val classroomEntity = classroomRepository
                .findById(UUID.fromString(scheduleDTO.classroomId))
                .orElseThrow { NotFoundException("No se encontró Classroom con id=${scheduleDTO.classroomId}") }
            schedule.classroom = classroomEntity
        }

        val scheduleSaved = scheduleService.createSchedule(schedule)

        return ResponseEntity.status(201).body(
            CustomResponse(
                message = "Schedule creado con exito",
                data = ScheduleMapper.buildScheduleDto(scheduleSaved)
            )
        )
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an schedule by ID")
    fun deleteSchedule(
        @PathVariable id: String
    ): ResponseEntity<CustomResponse> {
        // 1) Verificar que tengamos un ID
        val scheduleId = try {
            UUID.fromString(id)
        } catch (ex: IllegalArgumentException) {
            throw IllegalArgumentException("El parámetro 'id' no es un UUID válido: $id")
        }

        scheduleService.deleteSchedule(scheduleId)
        return ResponseEntity.status(200).body(
            CustomResponse(
                message = "Schedule eliminado con exito",
                data = null
            )
        )
    }
}