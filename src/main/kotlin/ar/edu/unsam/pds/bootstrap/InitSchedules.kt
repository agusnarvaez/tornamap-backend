package ar.edu.unsam.pds.bootstrap

import ar.edu.unsam.pds.exceptions.NotFoundException
import ar.edu.unsam.pds.models.Classroom
import ar.edu.unsam.pds.models.Event
import ar.edu.unsam.pds.models.Schedule
import ar.edu.unsam.pds.models.*
import ar.edu.unsam.pds.repository.ClassroomRepository
import ar.edu.unsam.pds.repository.EventRepository
import ar.edu.unsam.pds.repository.ScheduleRepository
import ar.edu.unsam.pds.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.DependsOn
import org.springframework.stereotype.Component
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime

@Component(value = "InitSchedules.beanName")
@DependsOn(value = ["InitEvents.beanName", "InitClassroom.beanName"])
class InitSchedules : BootstrapGeneric("Schedules") {
    @Autowired private lateinit var eventRepository: EventRepository
    @Autowired private lateinit var userRepository: UserRepository
    @Autowired private lateinit var scheduleRepository: ScheduleRepository
    @Autowired private lateinit var classroomRepository: ClassroomRepository

    override fun doAfterPropertiesSet() {
        val event1 = findEvent("Cursada Algoritmos I")
        val event2 = findEvent("Parcial Telecomunicaciones y Redes")
        val event3 = findEvent("Final Matematica I")
        val carlos = userByEmail("cscirica@estudiantes.unsam.edu.ar")
        val dodino = userByEmail("dodain@estudiantes.unsam.edu.ar")
        val mc = userByEmail("mcabeledo@estudiantes.unsam.edu.ar")

        val schedule1 = Schedule(
            startTime = LocalTime.of(8, 30),
            endTime = LocalTime.of(10, 0),
            weekDay = DayOfWeek.MONDAY,
            date = LocalDate.now(),
            isVirtual = false,
        ).apply {
            event = event1
            assignUserToSchedule(dodino, this)
        }

        scheduleRepository.save(schedule1)
        event1.addSchedule(schedule1)

        val schedule2 = Schedule(
            startTime = LocalTime.of(10, 30),
            endTime = LocalTime.of(12, 0),
            weekDay = DayOfWeek.FRIDAY,
            date = LocalDate.now(),
            isVirtual = true,
        ).apply {
            event = event2
            assignUserToSchedule(mc , this)
        }

        scheduleRepository.save(schedule2)
        event2.addSchedule(schedule2)

        val schedule3 = Schedule(
            startTime = LocalTime.of(10, 30),
            endTime = LocalTime.of(12, 0),
            weekDay = DayOfWeek.FRIDAY,
            date = LocalDate.now(),
            isVirtual = true,
        ).apply {
            event = event3
            assignUserToSchedule(carlos, this)
        }

        scheduleRepository.save(schedule3)
        event3.addSchedule(schedule3)

    }

    fun findEvent(name: String): Event {
        return eventRepository.findByName(name).orElseThrow{ NotFoundException("No se halló un evento con ese nombre") }
    }

    fun findClassroomByName(name: String): Classroom? {
        val allClasrooms = classroomRepository.findAll()
        validateClassroomList(allClasrooms)
        validateClassroomSearch(allClasrooms,name)
        return allClasrooms.find { it.name == name }!!
    }

    private fun validateClassroomList(classrooms: List<Classroom>) {
        if (classrooms.isEmpty()) {
            throw NotFoundException("No hay aulas cargadas")
        }
    }

    private fun validateClassroomSearch(classrooms: List<Classroom>, classroomName: String) {
        if (!classrooms.map {it.name}.contains(classroomName)) {
            throw NotFoundException("No hay un aula con el nombre indicado.")
        }
    }

    fun userByEmail(mail: String): User {
        return userRepository.findByEmail(mail).orElseThrow { NotFoundException("No se encontró profesor con el email suministrado") }
    }

}