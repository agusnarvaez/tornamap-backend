package ar.edu.unsam.pds.bootstrap

import ar.edu.unsam.pds.models.Event
import ar.edu.unsam.pds.models.Course
import ar.edu.unsam.pds.models.Schedule
import ar.edu.unsam.pds.repository.EventRepository
import ar.edu.unsam.pds.repository.ProgramRepository
import ar.edu.unsam.pds.repository.ScheduleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.DependsOn
import org.springframework.stereotype.Component
import ar.edu.unsam.pds.exceptions.NotFoundException
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime

@Component(value = "InitEvents.beanName")
@DependsOn(value = ["InitCourses.beanName", "InitPrograms.beanName"])
class InitEvents : BootstrapGeneric("Events") {
    @Autowired private lateinit var scheduleRepository: ScheduleRepository
    @Autowired private lateinit var programRepository: ProgramRepository
    @Autowired private lateinit var eventRepository: EventRepository

    override fun doAfterPropertiesSet() {
        // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        // Estrellas en Movimiento + Ballet Clásico para Principiantes #################################################
        val course11 = this.findByName(
            name = "Matematica I"
        )

        val event111 = Event(
            name = "Parcial",
            isApproved = true,
            schedule = mutableListOf(Schedule(
                startTime = LocalTime.of(10, 0),
                endTime = LocalTime.of(12, 0),
                weekDay = DayOfWeek.MONDAY,
                date = LocalDate.of(2023, 10, 30),
                isVirtual = false,
                classroom = null
            )),
            course = course11!!
        )

        course11.addEvent(event111)
        eventRepository.save(event111)

      }

    fun findByName(name: String): Course? {
        val programsList = programRepository.findAll()
        val allCourses = programsList.map { it.courses }.flatten()
        if (allCourses.isEmpty()) {
            throw NotFoundException("No se hallaron materias")
        }
        return allCourses.find { it.name == name }
    }

    fun findRandomSchedule(): Schedule {
        scheduleRepository.findAll().randomOrNull()?.let {
            return it
        } ?: error("error find random schedule, schedule repository is empty")
    }
}