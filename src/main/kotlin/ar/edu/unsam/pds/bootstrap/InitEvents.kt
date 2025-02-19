package ar.edu.unsam.pds.bootstrap

import ar.edu.unsam.pds.models.Event
import ar.edu.unsam.pds.models.Course
import ar.edu.unsam.pds.models.Schedule
import ar.edu.unsam.pds.repository.AssignmentRepository
import ar.edu.unsam.pds.repository.InstitutionRepository
import ar.edu.unsam.pds.repository.ScheduleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.DependsOn
import org.springframework.stereotype.Component

@Component(value = "InitAssignments.beanName")
@DependsOn(value = ["InitCourses.beanName", "InitSchedules.beanName"])
class InitAssignments : BootstrapGeneric("Assignments") {
    @Autowired private lateinit var scheduleRepository: ScheduleRepository
    @Autowired private lateinit var institutionRepository: InstitutionRepository
    @Autowired private lateinit var assignmentRepository: AssignmentRepository

    override fun doAfterPropertiesSet() {
        // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        // Estrellas en Movimiento + Ballet Clásico para Principiantes #################################################
        val course11 = this.findByNameAndCourseTitle(
            name = "Estrellas en Movimiento",
            title = "Ballet Clásico para Principiantes"
        )

        val event111 = Event(
            name = "Parcial",
            isApproved = true,
            schedule = findRandomSchedule()
        )

        course11?.addAssignment(assignment111)
        assignmentRepository.save(assignment111)

        val event112 = Event(
            name = "Final",
            isApproved = true,
            schedule = findRandomSchedule()
        )

        course11?.addAssignment(assignment112)
        assignmentRepository.save(assignment112)

        val event113 = Event(
            name = "Recuperatorio",
            isApproved = true,
            schedule = findRandomSchedule()
        )

        course11?.addAssignment(assignment113)
        assignmentRepository.save(assignment113)

      }

    //EL TITLE NO IRIA
    fun findByNameAndCourseTitle(name: String, title: String): Course? {
        val course = institutionRepository.findAll().find { it.name.contains(name) }.let { institution ->
            institution?.courses.let { courses ->
                courses?.find { it.title.contains(title) }
            }
        } ?: error("error find to $name and $title")

        return course
    }

    fun findRandomSchedule(): Schedule {
        scheduleRepository.findAll().randomOrNull()?.let {
            return it
        } ?: error("error find random schedule, schedule repository is empty")
    }
}