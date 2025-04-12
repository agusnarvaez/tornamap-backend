package ar.edu.unsam.pds.models

import jakarta.persistence.*
import java.io.Serializable
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

@Entity @Table(name = "APP_SCHEDULE")
class Schedule(
    val startTime: LocalTime,
    val endTime: LocalTime,
    val weekDay: DayOfWeek?,
    val date: LocalDate?,
    val isVirtual: Boolean,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "classroom_id", nullable = true)
    val classroom: Classroom?,

) : Timestamp(), Serializable {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    lateinit var id: UUID

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "event_id")
    lateinit var event: Event

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "scheduleList")
    val assignedUsers = mutableSetOf<User>()

    fun isBeforeEndDate(enteredDate: LocalDate): Boolean {
        if (date == null) {
            throw IllegalStateException("No se puede verificar la fecha porque 'date' es nulo en este Schedule")
        }
        return enteredDate.isBefore(date) || enteredDate.isEqual(date)
    }

    fun getUserNames(): List<String> {
        return assignedUsers.map { it.fullName() }
    }

    fun assignUserToSchedule(user: User, schedule: Schedule) {
        this.assignedUsers.add(user)
        user.scheduleList.add(schedule)
    }


}
