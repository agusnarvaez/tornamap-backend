package ar.edu.unsam.pds.models

import ar.edu.unsam.pds.models.enums.RecurrenceWeeks
import jakarta.persistence.*
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
    val day: DayOfWeek,
    val date: LocalDate,
    val isVirtual: Boolean,
) : Timestamp(), Serializable {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    lateinit var id: UUID


}