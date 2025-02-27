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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "event_id", nullable = true)
    val event: Event,

) : Timestamp(), Serializable {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    lateinit var id: UUID

   fun isBeforeEndDate(enteredDate: LocalDate): Boolean {
        return enteredDate.isBefore(date) || enteredDate.isEqual(date)
    }


}
