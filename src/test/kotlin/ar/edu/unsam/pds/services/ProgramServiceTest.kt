package ar.edu.unsam.pds.services

import ar.edu.unsam.pds.BootstrapNBTest
import ar.edu.unsam.pds.dto.request.ProgramRequestDto
import ar.edu.unsam.pds.dto.response.ProgramDetailResponseDto
import ar.edu.unsam.pds.dto.response.ProgramResponseDto
import ar.edu.unsam.pds.exceptions.NotFoundException
import ar.edu.unsam.pds.exceptions.PermissionDeniedException
import ar.edu.unsam.pds.exceptions.ValidationException
import ar.edu.unsam.pds.mappers.InstitutionMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.`when`

class ProgramServiceTest : BootstrapNBTest() {
    private lateinit var institutionService: ProgramService
    private lateinit var assignmentService: AssignmentService

    @BeforeEach
    fun setUpInstitutionServiceTest() {
        institutionService = ProgramService(
            institutionRepository = institutionRepository,
            principalRepository = principalRepository,
            userRepository = userRepository,
            imageService = imageService
        )

        assignmentService = AssignmentService(
            assignmentRepository = assignmentRepository,
            userRepository = userRepository,
            scheduleRepository = scheduleRepository,
            courseRepository = courseRepository,
            paymentRepository = paymentRepository,
            emailService = emailService
        )
    }

    @Test
    fun `test get all institutions`() {
        val obtainedValue = institutionService.getAll("").toList()
        val expectedValue = programs.map {
            InstitutionMapper.buildInstitutionDto(it)
        }

        assertEquals(obtainedValue, expectedValue)
    }

    @Test
    fun `test get dance institution`() {
        val obtainedValue = institutionService.getAll("Enchanted Dance").toList()

        val expectedValue = listOf(programs[0]).map {
            InstitutionMapper.buildInstitutionDto(it)
        }

        assertEquals(expectedValue, obtainedValue)
    }

    @Test
    fun `test get mathematics institution`() {
        val obtainedValue = institutionService.getAll("mathematics ins").toList()

        val expectedValue = listOf(programs[1]).map {
            InstitutionMapper.buildInstitutionDto(it)
        }

        assertEquals(obtainedValue, expectedValue)
    }

    @Test
    fun `test get yoga institution`() {
        val obtainedValue = institutionService.getAll("yoga_category").toList()

        val expectedValue = listOf(programs[2]).map {
            InstitutionMapper.buildInstitutionDto(it)
        }

        assertEquals(obtainedValue, expectedValue)
    }

    @Test
    fun `test get all institutions - adam_email_com`() {
        val obtainedValue = institutionService.getAllByPrincipal("", principals[0]).toList()
        val expectedValue = listOf(InstitutionMapper.buildInstitutionDto(programs[0]))

        assertEquals(obtainedValue, expectedValue)
    }

    @Test
    fun `test get dance institution - adam_email_com`() {
        val obtainedValue = institutionService.getAllByPrincipal("Enchanted Dance", principals[0]).toList()
        val expectedValue = listOf(InstitutionMapper.buildInstitutionDto(programs[0]))

        assertEquals(obtainedValue, expectedValue)
    }

    @Test
    fun `test get mathematics institution - adam_email_com`() {
        val obtainedValue = institutionService.getAllByPrincipal("mathematics ins", principals[0]).toList()
        val expectedValue = listOf<ProgramResponseDto>()

        assertEquals(obtainedValue, expectedValue)
    }

    @Test
    fun `test get yoga institution - adam_email_com`() {
        val obtainedValue = institutionService.getAllByPrincipal("yoga_category", principals[0]).toList()
        val expectedValue = listOf<ProgramResponseDto>()

        assertEquals(obtainedValue, expectedValue)
    }

    @Test
    fun `test get a particular institution`() {
        val uuid = programs[0].id.toString()
        val obtainedValue = institutionService.getById(uuid)
        val expectedValue = InstitutionMapper.buildInstitutionDetailDto(programs[0])

        assertEquals(obtainedValue, expectedValue)
    }

    @Test
    fun `test throw get a particular institution`() {
        val uuid = "029ce681-9f90-45e7-af7f-e74a8cfb4b57"
        assertThrows<NotFoundException> {
            institutionService.getById(uuid)
        }
    }

    @Test
    fun `test create institution`() {
        val institution = ProgramRequestDto(
            name = "name",
            description = "description",
            category = "category",
            file = mockFile
        )

        `when`(imageService.savePublic(mockFile)).thenReturn(mockFileName)

        val obtainedValue = institutionService.create(institution, principals[0])
        val expectedValue = ProgramResponseDto(
            id = obtainedValue.id,
            name = "name",
            description = "description",
            category = "category",
            image = mockFileName
        )

        assertEquals(obtainedValue, expectedValue)
    }

    @Test
    fun `test delete institution`() {
        `when`(imageService.savePublic(mockFile)).thenReturn(mockFileName)

        val institutionRequest = ProgramRequestDto(
            name = "name",
            description = "description",
            category = "category",
            file = mockFile
        )

        val id = institutionService.create(institutionRequest, principals[0]).id

        // #############################################################################################################
        val obtainedValue = institutionService.getById(id)
        val expectedValue = ProgramDetailResponseDto(
            id = id,
            name = "name",
            description = "description",
            category = "category",
            image = mockFileName,
            courses = mutableSetOf()
        )

        assertEquals(obtainedValue, expectedValue)

        // #############################################################################################################
        institutionService.delete(id, principals[0])

        assertThrows<NotFoundException> {
            institutionService.getById(id)
        }
    }

    @Test
    fun `test delete institution - is not owner`() {
        val uuid = programs[1].id.toString()

        assertThrows<PermissionDeniedException> {
            institutionService.delete(uuid, principals[0])
        }
    }

    @Test
    fun `test delete institution - is not deletable`() {
        `when`(emailService.sendSubscriptionConfirmationEmail(
            to = users[0].email,
            courseName = assignments[0].course.title,
            userName = users[0].name
        )).then {  }

        `when`(emailService.sendPaymentConfirmationEmail(
            to = users[0].email,
            amount = assignments[0].price,
            userName = users[0].name,
            transactionId = "ID_GENERADO_POR_OTRO_METODO"
        )).then {  }

        assignmentService.subscribe(
            idUser = users[0].id.toString(),
            idAssignment = assignments[0].id.toString()
        )

        assertThrows<ValidationException> {
            institutionService.delete(programs[0].id.toString(), principals[0])
        }
    }

// error aqui deberia dar un NotFoundException, pero rebota primero en este PermissionDeniedException
//    @Test
//    fun `test delete institution - not exist`() {
//        assertThrows<NotFoundException> {
//            institutionService.deleteInstitution(UUID.randomUUID().toString(), principals[0])
//        }
//    }
}