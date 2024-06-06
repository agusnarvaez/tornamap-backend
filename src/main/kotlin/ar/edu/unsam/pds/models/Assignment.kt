package ar.edu.unsam.pds.models

import ar.edu.unsam.pds.exceptions.ValidationException
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.io.Serializable
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@Entity @Table(name = "APP_ASSIGNMENT")
@EntityListeners(AuditingEntityListener::class)
class Assignment(
    val quotas: Int,
    var isActive: Boolean,
    val price: Double,

    @ManyToOne(fetch = FetchType.EAGER)
    var schedule: Schedule

) : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    lateinit var id: UUID

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "assignmentsList")
    val subscribedUsers = mutableSetOf<User>()

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    lateinit var course: Course

    @CreatedDate
    @Column(name = "REGISTER_DATE", nullable = false, updatable = false)
    lateinit var registerDate: LocalDateTime

    @LastModifiedDate
    @Column(name = "LAST_UPDATE", nullable = false)
    lateinit var lastUpdate: LocalDateTime

    fun status(): String {
        return if (schedule.isBeforeEndDate(LocalDate.now())) {
            Status.CONFIRMED.name
        } else {
            Status.FINISHED.name
        }
    }

    fun quantityAvailable(): Int {
        return quotas - subscribedUsers.size
    }

    fun attachCourse(course: Course) {
        this.course = course
    }

    fun addSubscribedUser(user: User) {
        if (quotas > subscribedUsers.size) {
            subscribedUsers.add(user)
        } else {
            throw ValidationException("No hay cupos disponibles")
        }
    }

    fun removeSubscribedUser(user: User) {
        if (!subscribedUsers.any { it.id == user.id }) {
            throw ValidationException("El usuario no está subscripto")
        } else {
            subscribedUsers.removeIf { it.id == user.id }
        }
    }

    fun hasAnySubscribedUser(): Boolean {
        return subscribedUsers.isNotEmpty()
    }

    fun totalSubscribedUsers(): Int {
        return subscribedUsers.size
    }

    fun totalIncome(): Double {
        return price * subscribedUsers.size
    }

    fun name(): String {
        return schedule.days.joinToString(", ") { it.name }
    }
}

enum class Status {
    CONFIRMED, FINISHED
}