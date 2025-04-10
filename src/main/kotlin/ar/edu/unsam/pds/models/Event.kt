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

    @OneToMany(mappedBy = "event", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var schedule: MutableList<Schedule> = mutableListOf()
) : Timestamp(), Serializable {

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
    }*/

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

    fun getCourseName(): String {
        return course.name
    }

    fun getProgramNames(): List<String> {
        return course.programNames()
    }

    fun getProfessorNames(): List<String> {
        return users.map { it.fullName() }
    }
}
