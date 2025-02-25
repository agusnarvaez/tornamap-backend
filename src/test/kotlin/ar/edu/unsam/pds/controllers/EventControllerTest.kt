package ar.edu.unsam.pds.controllers

import ar.edu.unsam.pds.dto.request.EventRequestDto
import ar.edu.unsam.pds.dto.request.ScheduleRequestDto
import ar.edu.unsam.pds.dto.request.SubscribeRequestDto
import ar.edu.unsam.pds.dto.response.EventResponseDto
import ar.edu.unsam.pds.dto.response.ScheduleResponseDto
import ar.edu.unsam.pds.models.User
import ar.edu.unsam.pds.models.enums.RecurrenceWeeks
import ar.edu.unsam.pds.security.models.Principal
import ar.edu.unsam.pds.services.EventService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

@ExtendWith(MockitoExtension::class)
class EventControllerTest {
    @Mock
    private lateinit var eventService: EventService
    private lateinit var eventController: EventController

    private lateinit var eventReq: EventRequestDto
    private lateinit var eventRes: EventResponseDto
    private lateinit var scheduleReq: ScheduleRequestDto
    private lateinit var scheduleRes: ScheduleResponseDto
    private lateinit var subscribeRequest: SubscribeRequestDto
    private lateinit var principal: Principal
    private lateinit var user: User

    @BeforeEach
    fun setUp(eventController : EventController) {
        eventController.eventService = eventService

        scheduleReq = ScheduleRequestDto(
            days = listOf(DayOfWeek.FRIDAY),
            startTime = LocalTime.now(),
            endTime = LocalTime.now(),
            startDate = LocalDate.now(),
            endDate = LocalDate.now(),
            recurrenceWeeks = RecurrenceWeeks.WEEKLY,
        )

        scheduleRes = ScheduleResponseDto(
            days = listOf(DayOfWeek.FRIDAY),
            startTime = LocalTime.now(),
            endTime = LocalTime.now(),
            startDate = LocalDate.now(),
            endDate = LocalDate.now(),
            recurrenceWeeks = RecurrenceWeeks.WEEKLY.name,
            listDates = listOf(LocalDate.now().toString())
        )

        eventReq = EventRequestDto(
            idCourse = UUID.randomUUID().toString(),
            quotas = 10,
            price = 100.0,
            schedule = scheduleReq
        )

        eventRes = ar.edu.unsam.pds.dto.response.EventRequestDto(
            id = UUID.randomUUID().toString(),
            quotas = 10,
            quantityAvailable = 100,
            isActive = true,
            price = 100.0,
            schedule = scheduleRes
        )

        subscribeRequest = SubscribeRequestDto(
            idUser = UUID.randomUUID().toString(),
            idAssignment = eventRes.id
        )

        user = User(
            name = "Adam",
            lastName = "AdamAdam",
            email = "adam@email.com",
            image = "",
            credits = 100000.0,
            isAdmin = true
        )

        principal = Principal().apply {
            id = UUID.randomUUID()
            username = this@EventControllerTest.user.email
            password = "123"
            user = this@EventControllerTest.user
            this.initProperties()
        }
    }

    @Test
    fun `test assignmentAll`() {
        val assignments = listOf(eventRes)

        `when`(eventService.getAll()).thenReturn(assignments)

        val responseEntity = eventController.getAll()

        assert(responseEntity.statusCode == HttpStatus.OK)
        assert(responseEntity.body == assignments)
    }

    @Test
    fun `test assignmentItem`() {
        val uuid = UUID.randomUUID().toString()
        `when`(eventService.getEvent(uuid)).thenReturn(eventRes)

        val responseEntity = eventController.getEvent(uuid)

        assert(responseEntity.statusCode == HttpStatus.OK)
        assert(responseEntity.body == eventRes)
    }

    @Test
    fun `test subscribe to assignment`() {
        val response = SubscribeResponseDto(
            idUser = subscribeRequest.idUser!!,
            idAssignment = subscribeRequest.idAssignment!!,
            message = "subscribe",
            date = LocalDate.now()
        )

        `when`(
            eventService.subscribe(
                idUser = subscribeRequest.idUser!!,
                idAssignment = subscribeRequest.idAssignment!!
            )
        ).thenReturn(response)

        val responseEntity = eventController.subscribeToAssignment(subscribeRequest)

        assert(responseEntity.statusCode == HttpStatus.OK)
        assert(responseEntity.body == response)
    }

    @Test
    fun `test unsubscribe to assignment`() {
        val response = SubscribeResponseDto(
            idUser = subscribeRequest.idUser!!,
            idAssignment = subscribeRequest.idAssignment!!,
            message = "unsubscribe",
            date = LocalDate.now()
        )

        `when`(
            eventService.unsubscribe(
                idUser = subscribeRequest.idUser!!,
                idAssignment = subscribeRequest.idAssignment!!
            )
        ).thenReturn(response)

        val responseEntity = eventController.unsubscribeToAssignment(subscribeRequest)

        assert(responseEntity.statusCode == HttpStatus.OK)
        assert(responseEntity.body == response)
    }

    @Test
    fun `test create a particular assignment`() {
        `when`(eventService.createEvent(eventReq)).thenReturn(eventRes)

        val responseEntity = eventController.createEvent(eventReq)

        assert(responseEntity.statusCode == HttpStatus.OK)
        assert(responseEntity.body == eventRes)
    }

    @Test
    fun `test delete a particular assignment`() {
        val uuid = UUID.randomUUID().toString()
        `when`(eventService.deleteEvent(uuid, principal)).then { }

        val responseEntity = eventController.deleteEvent(uuid, principal)

        assert(responseEntity.statusCode == HttpStatus.OK)
        assert(responseEntity.body == mapOf("message" to "Assignment eliminado correctamente."))
    }

    @Test
    fun `test get assignment subscribed users`() {
        val uuid = UUID.randomUUID().toString()

        val listOfSubscriptions = listOf(UserSubscribedResponseDto(
            name = user.name,
            lastName = user.lastName,
            email = user.email
        ))

        `when`(eventService.getUsersInEvent(uuid)).thenReturn(listOfSubscriptions)

        val responseEntity = eventController.getAssignmentSuscribedUsers(uuid)

        assert(responseEntity.statusCode == HttpStatus.OK)
        assert(responseEntity.body == listOfSubscriptions)
    }
}

