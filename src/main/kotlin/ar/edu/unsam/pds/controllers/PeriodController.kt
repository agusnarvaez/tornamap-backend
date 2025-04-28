package ar.edu.unsam.pds.controllers

import ar.edu.unsam.pds.dto.request.PeriodRequestDto
import ar.edu.unsam.pds.dto.response.CustomResponse
import ar.edu.unsam.pds.mappers.PeriodMapper
import ar.edu.unsam.pds.services.PeriodService
import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/periods")
@CrossOrigin("*")
class PeriodController : UUIDValid () {
    @Autowired lateinit var periodService: PeriodService

    @GetMapping()
    @Operation(summary = "Get all periods")
    fun getAllPeriods(): ResponseEntity<CustomResponse> {
        return ResponseEntity.status(200).body(
            CustomResponse(
                message = "Periodos obtenidos con exito",
                data = periodService.getAllPeriods().map { PeriodMapper.buildPeriodDto(it)}
            )
        )
    }

    @GetMapping("/{periodID}")
    @Operation(summary = "Get a period by ID")
    fun getPeriod(@PathVariable(value="periodID", required= true) periodID: String): ResponseEntity<CustomResponse> {
        validatedUUID(periodID)
        return ResponseEntity.status(200).body(
            CustomResponse(
                message = "Periodo obtenido con exito",
                data = PeriodMapper.buildPeriodDto(periodService.findById(periodID))
            )
        )
    }

    @PostMapping("")
    @Operation(summary = "Create a period")
    fun createPeriod(
        @RequestBody @Valid period: PeriodRequestDto
    ): ResponseEntity<CustomResponse> {
        return ResponseEntity.status(201).body(
            CustomResponse(
                message = "Periodo creado con exito",
                data = periodService.createPeriod(period)
            )
        )
    }

    @DeleteMapping("{periodId}")
    @Operation(summary = "Delete a period by ID")
    fun deletePeriod(
        @PathVariable periodId: String
    ): ResponseEntity<CustomResponse> {
        validatedUUID(periodId)
        return ResponseEntity.status(200).body(
            CustomResponse(
                message = "Periodo eliminado con exito",
                data = periodService.deletePeriod(periodId)
            )
        )
    }

    @PutMapping("{periodId}")
    @Operation(summary = "Edit a period by ID")
    fun editPeriod(
        @PathVariable periodId: String,
        @RequestBody @Valid period: PeriodRequestDto
    ): ResponseEntity<CustomResponse> {
        validatedUUID(periodId)
        return ResponseEntity.status(200).body(
            CustomResponse(
                message = "Periodo editado con exito",
                data = periodService.updatePeriod(periodId, period)
            )
        )
    }
}
