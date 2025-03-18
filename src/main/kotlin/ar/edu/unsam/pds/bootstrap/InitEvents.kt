package ar.edu.unsam.pds.bootstrap

import ar.edu.unsam.pds.models.Event
import ar.edu.unsam.pds.models.Course
import ar.edu.unsam.pds.models.Schedule
import ar.edu.unsam.pds.repository.EventRepository
import ar.edu.unsam.pds.repository.InstitutionRepository
import ar.edu.unsam.pds.repository.ScheduleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.DependsOn
import org.springframework.stereotype.Component

@Component(value = "InitAssignments.beanName")
@DependsOn(value = ["InitCourses.beanName", "InitSchedules.beanName"])
class InitEvents : BootstrapGeneric("Events") {
    @Autowired private lateinit var scheduleRepository: ScheduleRepository
    @Autowired private lateinit var institutionRepository: InstitutionRepository
    @Autowired private lateinit var eventRepository: EventRepository

    override fun doAfterPropertiesSet() {
        // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        // Estrellas en Movimiento + Ballet Clásico para Principiantes #################################################
        val course11 = this.findByNameAndCourseTitle(
            name = "Redes Locales",
            title = "ESTO NO IRIA, BORRAR EN OTRA RAMA"
        )

        val event111 = Event(
            name = "Parcial",
            isApproved = true,
            schedule = findRandomSchedule()
        )

        course11?.addEvent(event111)
        eventRepository.save(event111)

        val event112 = Event(
            name = "Final",
            isApproved = true,
            schedule = findRandomSchedule()
        )

        course11?.addEvent(event112)
        eventRepository.save(event112)

        val event113 = Event(
            name = "Recuperatorio",
            isApproved = true,
            schedule = findRandomSchedule()
        )

        course11?.addEvent(event113)
        eventRepository.save(event113)

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