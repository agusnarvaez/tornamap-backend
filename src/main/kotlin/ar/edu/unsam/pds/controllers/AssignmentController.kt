package ar.edu.unsam.pds.controllers

import ar.edu.unsam.pds.models.Assignment
import ar.edu.unsam.pds.models.Course
import ar.edu.unsam.pds.services.AssignmentService
import io.swagger.v3.oas.annotations.Operation
import org.hibernate.validator.constraints.UUID
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RequestMethod.GET

@RestController
@RequestMapping("api/")
@Validated
@CrossOrigin(origins = ["*"], methods = [GET])
class AssignmentController {
    @Autowired
    private lateinit var assignmentService: AssignmentService

    @GetMapping(value = ["assignments"])
    @Operation(summary = "Get all assignments")
    fun assignmentAll(): ResponseEntity<List<Assignment>> {
        return ResponseEntity.ok(assignmentService.getAssignmentAll())
    }

    @GetMapping(value = ["assignments/course/{idCourse}"])
    @Operation(summary = "Get all assignments by course")
    fun assignmentList(
        @PathVariable @UUID idCourse: String
    ): ResponseEntity<List<Assignment>> {
        return ResponseEntity.ok(assignmentService.getAssignmentList(idCourse))
    }

    @GetMapping(value = ["assignment/{idAssignment}"])
    @Operation(summary = "Get assignment by id")
    fun assignmentItem(
        @PathVariable @UUID idAssignment: String
    ): ResponseEntity<Assignment> {
        return ResponseEntity.ok(assignmentService.getAssignmentItem(idAssignment))
    }
}