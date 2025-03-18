package ar.edu.unsam.pds.models

import ar.edu.unsam.pds.exceptions.ValidationException
import ar.edu.unsam.pds.models.enums.Status
import jakarta.persistence.*
import java.io.Serializable
import java.time.LocalDate
import java.util.*

@Entity @Table(name = "APP_EVENT")
class Event(
    var name: String,
    var isApproved: Boolean,
    var isCancelled: Boolean = false,

) : Timestamp(), Serializable {
    @ManyToOne(fetch = FetchType.EAGER)
    lateinit var schedule: Schedule

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    lateinit var id: UUID

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "eventList")
    val users = mutableSetOf<User>()

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    lateinit var course: Course

    @ManyToOne(fetch = FetchType.EAGER)
    lateinit var period: Period

    fun status(): String {
        return if (schedule.isBeforeEndDate(LocalDate.now())) {
            Status.CONFIRMED.name
        } else {
            Status.FINISHED.name
        }
    }

    fun attachCourse(course: Course) {
        this.course = course
    }

    fun addUser(user: User) {
        if (validateUserId(user)) {
            throw ValidationException("El usuario ya es parte de este evento")
        }
        users.add(user)
    }

    fun removeUser(user: User) {
        if (!validateUserId(user)) {
            throw ValidationException("El usuario no está subscripto")
        }
        users.removeAll { it.id == user.id }
    }

    fun validateUserId(user: User) = users.any { it.id == user.id }
    
    fun hasAnyUser(): Boolean {
        return users.isNotEmpty()
    }

    fun userCount(): Int {
        return users.size
    }
    
    fun activeDays(): String {
        return schedule.days.joinToString(", ") { it.name }
    }
}
