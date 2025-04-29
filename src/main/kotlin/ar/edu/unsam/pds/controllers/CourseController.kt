package ar.edu.unsam.pds.controllers

import ar.edu.unsam.pds.dto.request.CourseRequestDto
import ar.edu.unsam.pds.dto.response.CustomResponse
import ar.edu.unsam.pds.mappers.CourseMapper
import ar.edu.unsam.pds.services.CourseService
import ar.edu.unsam.pds.services.ProgramService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/courses")
@CrossOrigin("*")
class CourseController : UUIDValid() {
    @Autowired
    private lateinit var programService: ProgramService
    @Autowired lateinit var courseService: CourseService

    @GetMapping("")
    @Operation(summary = "Get all courses")
    fun getAll(@RequestParam(value = "query", required = false) query: String?): ResponseEntity<CustomResponse> {
        return ResponseEntity.status(200).body(
            CustomResponse (
                message = "Cursos obtenidos con exito",
                data = courseService.searchBy(query ?: "").map { CourseMapper.buildCourseListDto(it) }
            )
        )
    }

    @GetMapping("{idCourse}")
    @Operation(summary = "Get a course by ID")
    fun getCourse(
        @PathVariable idCourse: String
    ): ResponseEntity<CustomResponse> {

        return ResponseEntity.status(200).body(
            CustomResponse (
                message = "Courso obtenido con exito",
                data = CourseMapper.buildCourseDetailDto(courseService.findByID(idCourse))
            )
        )
    }

    @PostMapping
    @Operation(summary = "Create a course")
    fun createCourse(
        @RequestBody courseDto: CourseRequestDto
    ): ResponseEntity<CustomResponse> {
        val course = CourseMapper.buildCourse(courseDto)
        val programs = programService.getAllById(courseDto.programs)

        return ResponseEntity.status(201).body(
            CustomResponse (
                message = "Curso creado con exito",
                data = CourseMapper.buildCourseDetailDto(courseService.create(course,programs))
            )
        )
    }
    /*
    @DeleteMapping("")
    @Operation(summary = "Delete n courses by n IDs")
    fun deleteMultipleCourses() {}

    @DeleteMapping("{courseId}")
    @Operation(summary = "Delete a course by ID")
    fun deleteCourse() {}


    */

}