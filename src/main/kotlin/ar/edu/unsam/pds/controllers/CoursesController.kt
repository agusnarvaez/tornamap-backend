package ar.edu.unsam.pds.controllers

import ar.edu.unsam.pds.dto.request.CourseRequestDto
import ar.edu.unsam.pds.dto.response.CourseDetailResponseDto
import ar.edu.unsam.pds.dto.response.CourseResponseDto
import ar.edu.unsam.pds.dto.response.CourseStatsResponseDto
import ar.edu.unsam.pds.security.models.Principal
import ar.edu.unsam.pds.services.CoursesService
import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/courses")
@CrossOrigin("*")
class CoursesController : UUIDValid() {
    @Autowired lateinit var courseServices: CoursesService

    @GetMapping("")
    @Operation(summary = "Get all courses")
    fun getAll(
        @RequestParam(required = false) query: String?
    ): ResponseEntity<List<CourseResponseDto>> {
        return ResponseEntity.ok(courseServices.getAll(query ?: ""))
    }

    @DeleteMapping("")
    @Operation(summary = "Delete n courses by n IDs")
    fun deleteMultipleCourses() {}

    @DeleteMapping("{courseId}")
    @Operation(summary = "Delete a course by ID")
    fun deleteCourse() {}

    @PostMapping(value = [""], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    @Operation(summary = "Create a course")
    fun createCourse() {}

    @GetMapping("{courseId}")
    @Operation(summary = "Get a course by ID")
    fun getCourse(
        @PathVariable idCourse: String
    ): ResponseEntity<CourseResponseDto> {
        this.validatedUUID(idCourse)
        return ResponseEntity.ok(courseServices.findCourseById(idCourse))
    }
}
