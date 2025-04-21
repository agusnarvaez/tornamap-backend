package ar.edu.unsam.pds.controllers

import ar.edu.unsam.pds.services.ProgramService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/programs")
@CrossOrigin("*")
class ProgramController : UUIDValid() {
    @Autowired lateinit var programService: ProgramService

    @GetMapping("")
    @Operation(summary = "Get all programs")
    fun getAll(){}


    @GetMapping("{idProgram}")
    @Operation(summary = "Get a program by ID")
    fun getProgram(){}

    @PostMapping(value = [""])
    @Operation(summary = "Create a program")
    fun createProgram(){}

    @DeleteMapping("{programId}")
    @Operation(summary = "Delete a program by ID")
    fun deleteProgram(programId: String) {}
}

