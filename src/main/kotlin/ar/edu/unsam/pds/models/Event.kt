package ar.edu.unsam.pds.models

import ar.edu.unsam.pds.exceptions.ValidationException
import jakarta.persistence.*
import org.springframework.lang.Nullable
import java.io.Serializable
import java.time.LocalDate
import java.util.*

@Entity @Table(name = "APP_EVENT")
class Event(
    var name: String,
    var isApproved: Boolean,
    var isCancelled: Boolean = false,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    var course: Course,

) : Timestamp(), Serializable {

    @OneToMany(mappedBy = "event", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @OrderBy("weekDay ASC, date ASC")
    var schedules: MutableList<Schedule> = mutableListOf()

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    lateinit var id: UUID

    @ManyToOne(fetch = FetchType.EAGER)
    @Nullable
    var period: Period? = null

    fun addUserToSchedule(schedule:Schedule, user:User) {
        validateScheduleInEvent(schedule)
        schedule.assignUserToSchedule(user, schedule)
    }

    private fun hasUpcomingSchedules():Boolean = schedules.any { it.date?.isAfter(LocalDate.now()) ?: false }

    fun attachCourse(course: Course) {
        this.course = course
    }

    fun getCourseName(): String = course.name
    fun addPeriod(period: Period) {
        this.period = period
    }

//    fun addUser(user: User) {
//        if (validateUserId(user)) {
//            throw ValidationException("El usuario ya es parte de este evento")
//        }
//        users.add(user)
//    }

    fun getProgramNames(): List<String> = course.programNames()

    fun getProfessorNames(): Set<String> = schedules.flatMap { it.getUserNames() }.toSet()

    fun addSchedule(schedule: Schedule) = schedules.add(schedule)

    fun removeSchedule(schedule: Schedule){
        validateScheduleInEvent(schedule)
        schedules.remove(schedule)
    }

    fun validateScheduleInEvent(schedule: Schedule) {
        if (!schedules.contains(schedule)) {
            throw ValidationException("El horario indicado no es parte del evento")
        }
    }
}

