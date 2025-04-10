package ar.edu.unsam.pds.bootstrap

import ar.edu.unsam.pds.exceptions.NotFoundException
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
    @Autowired private lateinit var classRoomRepository: ClassroomRepository

    override fun doAfterPropertiesSet() {
        val event1 = findEvent("Recuperatorio")
        val event2 = findEvent("Parcial")
        val event3 = findEvent("Final")
        val teacher = userByEmail("cscirica@estudiantes.unsam.edu.ar")
        val labo1 = findClassroomByName("Laboratorio de Computación 1")

        val schedule1 = Schedule(
            startTime = LocalTime.of(8, 30),
            endTime = LocalTime.of(10, 0),
            weekDay = DayOfWeek.MONDAY,
            date = LocalDate.of(2025, 3, 20),
            isVirtual = false,
            classroom = labo1
        ).apply {
            event = event1
        }

        scheduleRepository.save(schedule1)
        event1.addSchedule(schedule1)

        val schedule2 = Schedule(
            startTime = LocalTime.of(10, 30),
            endTime = LocalTime.of(12, 0),
            weekDay = DayOfWeek.FRIDAY,
            date = LocalDate.of(2025, 3, 21),
            isVirtual = true,
            classroom = null,
        ).apply {
            event = event2
        }

        scheduleRepository.save(schedule2)

        event2.addSchedule(schedule2)

        val schedule3 = Schedule(
            startTime = LocalTime.of(10, 30),
            endTime = LocalTime.of(12, 0),
            weekDay = DayOfWeek.FRIDAY,
            date = LocalDate.of(2025, 3, 21),
            isVirtual = true,
            classroom = null
        ).apply {
            event = event3
        }

        scheduleRepository.save(schedule3)
        event3.addSchedule(schedule3)

    }

    fun findEvent(name: String): Event {
        return eventRepository.findByName(name).orElseThrow{ NotFoundException("No se halló un evento con ese nombre") }
    }

    fun findClassroomByName(name: String): Classroom? {
        val allClasrooms = classRoomRepository.findAll()
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