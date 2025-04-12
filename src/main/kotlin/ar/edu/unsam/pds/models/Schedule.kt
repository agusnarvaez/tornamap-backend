package ar.edu.unsam.pds.models
import jakarta.persistence.*
import java.io.Serializable
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

@Entity @Table(name = "APP_SCHEDULE")
class Schedule(
    var startTime: LocalTime,
    var endTime: LocalTime,
    var weekDay: DayOfWeek?,
    var date: LocalDate?,
    var isVirtual: Boolean,


    ) : Timestamp(), Serializable {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    lateinit var id: UUID

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "event_id", nullable = true)
    lateinit var event: Event

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "classroom_id", nullable = true)
    var classroom: Classroom? = null
}
