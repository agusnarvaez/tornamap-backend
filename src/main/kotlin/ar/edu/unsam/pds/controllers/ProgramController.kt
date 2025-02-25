package ar.edu.unsam.pds.controllers

import ar.edu.unsam.pds.dto.request.ProgramRequestDto
import ar.edu.unsam.pds.dto.response.ProgramDetailResponseDto
import ar.edu.unsam.pds.dto.response.ProgramResponseDto
import ar.edu.unsam.pds.security.models.Principal
import ar.edu.unsam.pds.services.ProgramService
import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/programs")
@CrossOrigin("*")
class ProgramController : UUIDValid() {
    @Autowired lateinit var programService: ProgramService

    @GetMapping("")
    @Operation(summary = "Get all programs")
    fun getAll(
        @RequestParam(required = false) query: String?
    ): ResponseEntity<List<ProgramResponseDto>> {
        return ResponseEntity.ok(programService.getAll(query))
    }

    @GetMapping("admin")
    @Operation(summary = "Get all programs")
    fun getAllByPrincipal(
        @RequestParam(required = false) query: String?,
        @AuthenticationPrincipal principal: Principal
    ): ResponseEntity<List<ProgramResponseDto>> {
        return ResponseEntity.ok(programService.getAllByPrincipal(query, principal))
    }

    @GetMapping("{idProgram}")
    @Operation(summary = "Get program by id")
    fun getProgram(
        @PathVariable idProgram: String
    ): ResponseEntity<ProgramDetailResponseDto> {
        this.validatedUUID(idProgram)
        return ResponseEntity.ok(programService.getProgram(idProgram))
    }

    @PostMapping(value = [""], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    @Operation(summary = "Create a program")
    fun createProgram(
        @ModelAttribute @Valid program: ProgramRequestDto,
        @AuthenticationPrincipal principal: Principal
    ): ResponseEntity<ProgramResponseDto> {
        return ResponseEntity.ok(programService.createProgram(program, principal))
    }

    @DeleteMapping("{idProgram}")
    @Operation(summary = "Delete program by id")
    fun deleteProgram(
        @PathVariable idProgram: String,
        @AuthenticationPrincipal principal: Principal
    ): ResponseEntity<SuccessfulRequest> {
        this.validatedUUID(idProgram)
        programService.deleteProgram(idProgram, principal)
        return ResponseEntity.ok(SuccessfulRequest(message = "Programa eliminado correctamente."))
        
    }
}

data class SuccessfulRequest(val message: String)
