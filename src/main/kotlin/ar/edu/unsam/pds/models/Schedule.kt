package ar.edu.unsam.pds.models

import ar.edu.unsam.pds.models.enums.RecurrenceWeeks
import jakarta.persistence.*
import jakarta.validation.constraints.AssertTrue
import java.io.Serializable
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import java.time.temporal.TemporalAdjusters
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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "app_schedule_user",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "schedule_id")]
    )
    val assignedProfessors = mutableSetOf<User>()

    fun isBeforeEndDate(enteredDate: LocalDate): Boolean {
        if (date == null) {
            throw IllegalStateException("No se puede verificar la fecha porque 'date' es nulo en este Schedule")
        }
        return enteredDate.isBefore(date) || enteredDate.isEqual(date)
    }


}
