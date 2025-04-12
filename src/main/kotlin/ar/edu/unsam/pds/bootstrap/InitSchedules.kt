package ar.edu.unsam.pds.bootstrap

import ar.edu.unsam.pds.exceptions.NotFoundException
import ar.edu.unsam.pds.models.Building
import ar.edu.unsam.pds.models.Classroom
import ar.edu.unsam.pds.models.Event
import ar.edu.unsam.pds.models.enums.RecurrenceWeeks
import ar.edu.unsam.pds.models.Schedule
import ar.edu.unsam.pds.repository.ClassroomRepository
import ar.edu.unsam.pds.repository.EventRepository
import ar.edu.unsam.pds.repository.ScheduleRepository
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
    @Autowired private lateinit var scheduleRepository: ScheduleRepository
    @Autowired private lateinit var classRoomRepository: ClassroomRepository

    override fun doAfterPropertiesSet() {
        val parcial = Event(
            name = "Parcial",
            isApproved = true
        )
        eventRepository.save(parcial)

        val labo1 = findClassroomByName("Laboratorio de Computación 1")

        val schedule1 = Schedule(
            startTime = LocalTime.of(8, 30),
            endTime = LocalTime.of(10, 0),
            weekDay = DayOfWeek.MONDAY,
            date = LocalDate.of(2025, 3, 20),
            isVirtual = false
        ).apply {
            event = parcial
            classroom = labo1
        }

        scheduleRepository.save(schedule1)

        val schedule2 = Schedule(
            startTime = LocalTime.of(10, 30),
            endTime = LocalTime.of(12, 0),
            weekDay = DayOfWeek.FRIDAY,
            date = LocalDate.of(2025, 3, 21),
            isVirtual = true
        ).apply {
            event = parcial
        }

        scheduleRepository.save(schedule2)

    }

    fun findRandomEvent(): Event {
        if(eventRepository.findAll().isEmpty()) {
            throw NotFoundException("Error hallando un evento al azar, no existen eventos")
        }
        return eventRepository.findAll().random()
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

}