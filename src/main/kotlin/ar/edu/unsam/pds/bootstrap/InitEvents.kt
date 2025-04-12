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
import ar.edu.unsam.pds.models.User
import ar.edu.unsam.pds.repository.UserRepository

@Component(value = "InitEvents.beanName")
@DependsOn(value = ["InitCourses.beanName", "InitPrograms.beanName"])
class InitEvents : BootstrapGeneric("Events") {
    @Autowired private lateinit var scheduleRepository: ScheduleRepository
    @Autowired private lateinit var programRepository: ProgramRepository
    @Autowired private lateinit var eventRepository: EventRepository
    @Autowired private lateinit var userRepository: UserRepository

    override fun doAfterPropertiesSet() {
        // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        val course11 = this.findCourseByName(
            name = "Matematica I"
        )

        val course22 = this.findCourseByName(
            name = "Electricidad y Magnetismo"
        )

        val event111 = Event(
            name = "Parcial",
            isApproved = true,
            course = course11!!
        )

        eventRepository.save(event111)

        val event112 = Event(
            name = "Final",
            isApproved = true,
            course = course11
        )

        eventRepository.save(event112)

        val event113 = Event(
            name = "Recuperatorio",
            isApproved = true,
            course = course22!!
        )

        eventRepository.save(event113)

      }

    fun findCourseByName(name: String): Course? {
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