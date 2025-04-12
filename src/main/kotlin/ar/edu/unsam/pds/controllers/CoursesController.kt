package ar.edu.unsam.pds.controllers

import ar.edu.unsam.pds.dto.response.CourseResponseDto
import ar.edu.unsam.pds.mappers.CourseMapper
import ar.edu.unsam.pds.services.CourseService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/courses")
@CrossOrigin("*")
class CoursesController : UUIDValid() {
    @Autowired lateinit var courseService: CourseService

    @GetMapping("")
    @Operation(summary = "Get all courses")
    fun getAll(
        @RequestParam(required = false) query: String?
    ): ResponseEntity<List<CourseResponseDto>> {
        return ResponseEntity.ok(courseService.getAll(query ?: ""))
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
        @PathVariable courseId: String
    ): ResponseEntity<CourseResponseDto> {
        this.validatedUUID(courseId)
        return ResponseEntity.ok(CourseMapper.buildCourseDto(courseService.findCourseById(courseId)))
    }
}
