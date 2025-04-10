package ar.edu.unsam.pds.models

import ar.edu.unsam.pds.exceptions.ValidationException
import ar.edu.unsam.pds.models.enums.Status
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    var course: Course,

) : Timestamp(), Serializable {

    @OneToMany(mappedBy = "event", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    lateinit var schedules: MutableSet<Schedule>

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    lateinit var id: UUID

    //Professors
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "eventList")
    val users = mutableSetOf<User>()

    @ManyToOne(fetch = FetchType.EAGER)
    @Nullable
    var period: Period? = null

/*    fun status(): String {
        return if (schedule.isBeforeEndDate(LocalDate.now())) {
            Status.CONFIRMED.name
        } else {
            Status.FINISHED.name
        }
    }
*/
    fun addUserToSchedule(schedule:Schedule, user:User) {
        validateScheduleInEvent(schedule)
        validateUserInEvent(user)
        schedule.assignProfessor(user)
    }

    fun attachCourse(course: Course) {
        this.course = course
    }

    fun getCourseName(): String = course.name

    fun getProgramNames(): List<String> = course.programNames()

    fun getProfessorNames(): List<String> = users.map { it.fullName() }

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

    fun validateUserInEvent(user: User) {
        if (!users.contains(user)) {
            throw ValidationException("El usuario indicado no es parte del evento")
        }
    }
}

