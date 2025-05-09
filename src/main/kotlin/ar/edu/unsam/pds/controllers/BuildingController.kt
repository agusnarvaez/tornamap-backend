package ar.edu.unsam.pds.controllers

import ar.edu.unsam.pds.dto.response.CustomResponse
import ar.edu.unsam.pds.mappers.BuildingMapper
import ar.edu.unsam.pds.services.BuildingService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/buildings")
@CrossOrigin("*")
class BuildingController : UUIDValid () {
    @Autowired lateinit var buildingService: BuildingService

    @GetMapping()
    @Operation(summary = "Get all buildings")
    fun getAll(): ResponseEntity<CustomResponse> {
        return ResponseEntity.status(200).body(
            CustomResponse(
                message = "Edificios obtenidos con exito",
                data = buildingService.getAll().map { BuildingMapper.buildBuildingDtoWithClassrooms(it)}
            )
        )
    }

    @GetMapping("/{buildingId}")
    @Operation(summary = "Get a building by ID")
    fun getById(@PathVariable(value="buildingId", required= true) buildingId: String): ResponseEntity<CustomResponse> {
        validatedUUID(buildingId)
        return ResponseEntity.status(200).body(
            CustomResponse(
                message = "Edificio obtenido con exito",
                data = BuildingMapper.buildBuildingDtoWithClassrooms(buildingService.getById(buildingId))
            )
        )
    }

//    @PostMapping("")
//    @Operation(summary = "Create a building")
//    fun create(
//        @RequestBody @Valid building: BuildingRequestDto
//    ): ResponseEntity<CustomResponse> {
//        return ResponseEntity.status(201).body(
//            CustomResponse(
//                message = "Edificio creado con exito",
//                data = buildingService.create(building)
//            )
//        )
//    }
//
//    @DeleteMapping("{buildingId}")
//    @Operation(summary = "Delete a building by ID")
//    fun delete(
//        @PathVariable buildingId: String
//    ): ResponseEntity<CustomResponse> {
//        validatedUUID(buildingId)
//        return ResponseEntity.status(200).body(
//            CustomResponse(
//                message = "Edificio eliminado con exito",
//                data = buildingService.delete(buildingId)
//            )
//        )
//    }
//
//    @PutMapping("{buildingId}")
//    @Operation(summary = "Edit a building by ID")
//    fun edit(
//        @PathVariable buildingId: String,
//        @RequestBody @Valid building: BuildingRequestDto
//    ): ResponseEntity<CustomResponse> {
//        validatedUUID(buildingId)
//        return ResponseEntity.status(200).body(
//            CustomResponse(
//                message = "Edificio editado con exito",
//                data = buildingService.update(buildingId, building)
//            )
//        )
//    }
}
