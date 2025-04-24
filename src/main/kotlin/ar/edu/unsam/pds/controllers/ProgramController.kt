package ar.edu.unsam.pds.controllers

import ar.edu.unsam.pds.dto.request.ProgramRequestDto
import ar.edu.unsam.pds.dto.response.CustomResponse
import ar.edu.unsam.pds.mappers.ProgramMapper
import ar.edu.unsam.pds.services.ProgramService
import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/programs")
@CrossOrigin("*")
class ProgramController : UUIDValid() {
    @Autowired lateinit var programService: ProgramService

    @GetMapping("")
    @Operation(summary = "Get all programs sorted by name")
    fun getAll(): ResponseEntity<CustomResponse> {
        return ResponseEntity.status(200).body(
            CustomResponse(
                message = "Programs obtenidos con exito",
                data = programService.getAll().map { ProgramMapper.buildProgramDto(it) }
            )
        )
    }

    @GetMapping("{idProgram}")
    @Operation(summary = "Get a program by ID")
    fun getProgram (@PathVariable idProgram: String): ResponseEntity<CustomResponse> {
        validatedUUID(idProgram)
        return ResponseEntity.status(200).body(
            CustomResponse(
                message = "Program obtenido con exito",
                data = ProgramMapper.buildProgramDto( programService.getProgram(idProgram) )
            )
        )
    }

    @PostMapping(value = [""])
    @Operation(summary = "Create a program")
    fun createProgram(
        @RequestBody @Valid program: ProgramRequestDto
    ) : ResponseEntity<CustomResponse> {
        return ResponseEntity.status(201).body(
            CustomResponse(
                message = "Program creado con exito",
                data = programService.createProgram(program)
            )
        )
    }

    @PutMapping("{idProgram}")
    @Operation(summary = "Update a program")
    fun updateProgram(
        @PathVariable idProgram: String,
        @RequestBody @Valid program: ProgramRequestDto) : ResponseEntity<CustomResponse> {
        validatedUUID(idProgram)
        return ResponseEntity.status(201).body(
            CustomResponse(
                message = "Program actualizado con exito",
                data = ProgramMapper.buildProgramDto( programService.updateProgram(idProgram, program))
            )
        )
    }

    @DeleteMapping("{idProgram}")
    @Operation(summary = "Delete a program by ID")
    fun deleteProgram(@PathVariable idProgram : String) : ResponseEntity<CustomResponse> {
        validatedUUID(idProgram)
        return ResponseEntity.status(200).body(
            CustomResponse(
                message = "Program eliminado con exito",
                data = programService.deleteProgram(idProgram)
            )
        )
    }


}

