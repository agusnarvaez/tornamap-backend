package ar.edu.unsam.pds.services

import ar.edu.unsam.pds.BootstrapNBTest
import ar.edu.unsam.pds.dto.response.SubscribeResponseDto
import ar.edu.unsam.pds.utils.Mapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDate

class AssignmentServiceTest : BootstrapNBTest()  {
    private lateinit var assignmentService: AssignmentService

    @BeforeEach
    fun prepareTestData() {
        assignmentService = AssignmentService(
            assignmentRepository = assignmentRepository,
            userRepository = userRepository
        )
    }

    @Test
    fun `test all assignments`() {
        val obtainedValue = assignmentService.getAll().toList()
        val expectedValue = assignments.map {
            Mapper.buildAssignmentDto(it)
        }

        assertEquals(obtainedValue, expectedValue)
    }

    @Test
    fun `test get a particular user`() {
        val obtainedValue = assignmentService.getAssignment(assignments[0].id.toString())
        val expectedValue = Mapper.buildAssignmentDto(assignments[0])

        assertEquals(obtainedValue, expectedValue)
    }

    @Test
    fun `test subscribe to assignment`() {
        val obtainedValue = assignmentService.subscribe(
            idUser = users[0].id.toString(),
            idAssignment = assignments[0].id.toString()
        )
        val expectedValue = SubscribeResponseDto(
            idUser = users[0].id.toString(),
            idAssignment = assignments[0].id.toString(),
            message = "Suscripción exitosa",
            date = LocalDate.of(2024, 6, 5)
        )

        assertEquals(obtainedValue, expectedValue)
    }

    @Test
    fun `test unsubscribe to assignment`() {
        assignmentService.subscribe(
            idUser = users[0].id.toString(),
            idAssignment = assignments[0].id.toString()
        )

        val obtainedValue = assignmentService.unsubscribe(
            idUser = users[0].id.toString(),
            idAssignment = assignments[0].id.toString()
        )
        val expectedValue = SubscribeResponseDto(
            idUser = users[0].id.toString(),
            idAssignment = assignments[0].id.toString(),
            message = "Desuscripción exitosa",
            date = LocalDate.of(2024, 6, 5)
        )

        assertEquals(obtainedValue, expectedValue)
    }
}