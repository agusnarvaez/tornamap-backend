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

@Component(value = "InitEvents.beanName")
@DependsOn(value = ["InitCourses.beanName", "InitPrograms.beanName"])
class InitEvents : BootstrapGeneric("Events") {
    @Autowired private lateinit var scheduleRepository: ScheduleRepository
    @Autowired private lateinit var programRepository: ProgramRepository
    @Autowired private lateinit var eventRepository: EventRepository

    override fun doAfterPropertiesSet() {
        // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        val mate1 = this.findCourseByName(
            name = "Matematica I"
        )

        val redes = this.findCourseByName(
            name = "Telecomunicaciones y Redes"
        )

        val algo1 = this.findCourseByName(
            name = "Algoritmos I"
        )

        val event1 = Event(
            name = "Cursada Algoritmos I",
            isApproved = true,
            course = algo1!!
        )
        eventRepository.save(event1)

        val eventCharla = Event(
            name = "Charla de Bienvenida",
            isApproved = true,
            course = algo1
        )
        eventRepository.save(eventCharla)

        val event2 = Event(
            name = "Parcial Telecomunicaciones y Redes",
            isApproved = true,
            course = redes!!
        )
        eventRepository.save(event2)

        val event3 = Event(
            name = "Final Matematica I",
            isApproved = true,
            course = mate1!!
        )

        eventRepository.save(event3)

      }

    fun findCourseByName(name: String): Course? {
        val programsList = programRepository.findAll()
        val allCourses = programsList.map { it.courses }.flatten()
        if (allCourses.isEmpty()) {
            throw NotFoundException("No se hallaron materias")
        }
        return allCourses.find { it.name == name }
    }
}