package ar.edu.unsam.pds.services

import ar.edu.unsam.pds.BootstrapNBTest
import ar.edu.unsam.pds.dto.request.CourseRequestDto
import ar.edu.unsam.pds.dto.response.CourseResponseDto
import ar.edu.unsam.pds.dto.response.CourseStatsResponseDto
import ar.edu.unsam.pds.exceptions.NotFoundException
import ar.edu.unsam.pds.exceptions.PermissionDeniedException
import ar.edu.unsam.pds.exceptions.ValidationException
import ar.edu.unsam.pds.mappers.EventMapper
import ar.edu.unsam.pds.mappers.CourseMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.`when`
import java.util.*

class CourseServiceTest : BootstrapNBTest() {
    private lateinit var courseServices: CourseService
    private lateinit var assignmentService: EventService

    @BeforeEach
    fun setUpCoursesServiceTest() {
        courseServices = CourseService(
            courseRepository = courseRepository,
            institutionRepository = institutionRepository,
            imageService = imageService,
        )

        assignmentService = EventService(
            eventRepository = assignmentRepository,
            userRepository = userRepository,
            scheduleRepository = scheduleRepository,
            courseRepository = courseRepository,
            paymentRepository = paymentRepository,
            emailService = emailService
        )
    }

    @Test
    fun `test get all courses`() {
        val obtainedValue = courseServices.getAll("").toList()
        val expectedValue = courses.map {
            CourseMapper.buildCourseDto(it)
        }

        assertEquals(obtainedValue, expectedValue)
    }

    @Test
    fun `test get classic dance course`() {
        val obtainedValue = courseServices.getAll("classic dance").toList()

        val expectedValue = listOf(courses[0]).map {
            CourseMapper.buildCourseDto(it)
        }

        assertEquals(obtainedValue, expectedValue)
    }

    @Test
    fun `test get modern dance course`() {
        val obtainedValue = courseServices.getAll("modern").toList()

        val expectedValue = listOf(courses[1]).map {
            CourseMapper.buildCourseDto(it)
        }

        assertEquals(obtainedValue, expectedValue)
    }

    @Test
    fun `test get yoga course`() {
        val obtainedValue = courseServices.getAll("yoga_category").toList()

        val expectedValue = listOf(courses[2]).map {
            CourseMapper.buildCourseDto(it)
        }

        assertEquals(obtainedValue, expectedValue)
    }

    @Test
    fun `test get all courses - adam_email_com`() {
        val obtainedValue = courseServices.getAllByPrincipal("", principals[0]).toList()
        val expectedValue = listOf(courses[0], courses[1]).map {
            CourseMapper.buildCourseDto(it)
        }

        assertEquals(obtainedValue, expectedValue)
    }

    @Test
    fun `test get classic dance course - adam_email_com`() {
        val obtainedValue = courseServices.getAllByPrincipal("classic dance", principals[0]).toList()
        val expectedValue = listOf(courses[0]).map {
            CourseMapper.buildCourseDto(it)
        }

        assertEquals(obtainedValue, expectedValue)
    }

    @Test
    fun `test get modern dance course - adam_email_com`() {
        val obtainedValue = courseServices.getAllByPrincipal("modern", principals[0]).toList()

        val expectedValue = listOf(courses[1]).map {
            CourseMapper.buildCourseDto(it)
        }

        assertEquals(obtainedValue, expectedValue)
    }

    @Test
    fun `test get yoga course - adam_email_com`() {
        val obtainedValue = courseServices.getAllByPrincipal("yoga_category", principals[0]).toList()
        val expectedValue = listOf<CourseResponseDto>()

        assertEquals(obtainedValue, expectedValue)
    }

    @Test
    fun `test get a particular course`() {
        val uuid = courses[0].id.toString()
        val obtainedValue = courseServices.getCourse(uuid)
        val expectedValue = CourseMapper.buildCourseDetailDto(courses[0])

        assertEquals(obtainedValue, expectedValue)
    }

    @Test
    fun `test throw get a particular course`() {
        val uuid = "029ce681-9f90-45e7-af7f-e74a8cfb4b57"
        assertThrows<NotFoundException> {
            courseServices.getCourse(uuid)
        }
    }

    @Test
    fun `test delete a particular course`() {
        val uuid = courses[1].id.toString()

        val obtainedValuePre = courseServices.getAll("").toList()
        val expectedValuePre = courses.map {
            CourseMapper.buildCourseDto(it)
        }

        assertEquals(obtainedValuePre, expectedValuePre)

        courseServices.deleteCourse(uuid, principals[0])

        val obtainedValuePos = courseServices.getAll("").toList()
        val expectedValuePos = listOf(courses[0],courses[2]).map {
            CourseMapper.buildCourseDto(it)
        }

        assertEquals(obtainedValuePos, expectedValuePos)
    }

    @Test
    fun `test throw delete a particular course`() {
        val userId = users[0].id.toString()
        val courseId = courses[0].id.toString()
        val assignmentId = events[0].id.toString()

        assignmentService.subscribe(userId, assignmentId)

        assertThrows<ValidationException> {
            courseServices.deleteCourse(courseId, principals[0])
        }
    }

    @Test
    fun `test throw delete a particular course - is not owner`() {
        val uuid = courses[2].id.toString()

        assertThrows<PermissionDeniedException> {
            courseServices.deleteCourse(uuid, principals[0])
        }
    }

    @Test
    fun `test delete a list course`() {
        val uuid = courses[1].id.toString()

        val obtainedValuePre = courseServices.getAll("").toList()
        val expectedValuePre = courses.map {
            CourseMapper.buildCourseDto(it)
        }

        assertEquals(obtainedValuePre, expectedValuePre)

        courseServices.deleteAllById(listOf(uuid), principals[0])

        val obtainedValuePos = courseServices.getAll("").toList()
        val expectedValuePos =  listOf(courses[0], courses[2]) .map {
            CourseMapper.buildCourseDto(it)
        }

        assertEquals(obtainedValuePos, expectedValuePos)
    }

    @Test
    fun `test create a course`() {
        val courseRequest = CourseRequestDto(
            title = "new course",
            description = "new course description",
            category = "new category",
            file = mockFile,
            programId = programs[0].id.toString()
        )

        `when`(imageService.savePublic(mockFile)).thenReturn(
            "https://mock.pirulo/media/public/filename.jpg"
        )

        val obtainedValue = courseServices.create(courseRequest)
        val id = UUID.fromString(obtainedValue?.id!!)
        val expectedValue = CourseMapper.buildCourseDto(courseRepository.findById(id).get())

        assertEquals(obtainedValue, expectedValue)
    }

    @Test
    fun `test throw create a course`() {
        val courseRequest = CourseRequestDto(
            title = "new course",
            description = "new course description",
            category = "new category",
            file = mockFile,
            programId = "029ce681-9f90-45e7-af7f-e74a8cfb4b57"
        )

        assertThrows<NotFoundException> {
            courseServices.create(courseRequest)
        }
    }

    @Test
    fun `test course stats - void`() {
        val uuid = courses[2].id.toString()

        val expectedValue = CourseStatsResponseDto(
            id = uuid,
            title = courses[2].name,
            description = courses[2].description,
            category = courses[2].category,
            image = "",
            totalAssignments = 0,
            totalSubscriptions = 0,
            totalIncome = 0.0,
            mostPopularAssignment = null,
            mostProfitableAssignment = null,
            assignments = mutableSetOf()
        )

        val obtainedValue = courseServices.getCourseStats(uuid)

        assertEquals(obtainedValue, expectedValue)
    }

    @Test
    fun `test course stats`() {
        val uuid = courses[1].id.toString()

        val expectedValue = CourseStatsResponseDto(
            id = uuid,
            title = courses[1].name,
            description = courses[1].description,
            category = courses[1].category,
            image = "",
            totalAssignments = 1,
            totalSubscriptions = 0,
            totalIncome = 0.0,
            mostPopularAssignment = EventMapper.buildEventStatsDto(events[1]),
            mostProfitableAssignment = EventMapper.buildEventStatsDto(events[1]),
            assignments = mutableSetOf(EventMapper.buildEventStatsDto(events[1]))
        )

        val obtainedValue = courseServices.getCourseStats(uuid)

        assertEquals(obtainedValue, expectedValue)
    }
}