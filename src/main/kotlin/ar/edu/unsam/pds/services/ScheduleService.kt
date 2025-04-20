package ar.edu.unsam.pds.services


import ar.edu.unsam.pds.exceptions.NotFoundException
import ar.edu.unsam.pds.models.Schedule
import ar.edu.unsam.pds.repository.ClassroomRepository
import ar.edu.unsam.pds.repository.EventRepository
import ar.edu.unsam.pds.repository.ScheduleRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.*

@Service
class ScheduleService(
    val scheduleRepository: ScheduleRepository,
    val classroomRepository: ClassroomRepository,
    val eventRepository : EventRepository
) {
    fun getAll(): List<Schedule> {
        return scheduleRepository.findAll()
    }

    fun findById(idSchedule: String): Schedule {
        val uuid = UUID.fromString(idSchedule)
        return scheduleRepository.findById(uuid).orElseThrow {
            NotFoundException("Schedule no encontrado para el uuid suministrado")
        }
    }

    @Transactional
    fun editSchedule(schedule: Schedule) {
        this.scheduleRepository.save(schedule)
    }

    @Transactional
    fun createSchedule(schedule: Schedule):Schedule {
        scheduleRepository.save(schedule)

        return schedule
    }

    fun deleteSchedule(idSchedule: UUID) {
        scheduleRepository.deleteById(idSchedule)
    }

}
