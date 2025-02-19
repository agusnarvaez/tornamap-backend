package ar.edu.unsam.pds.models

import ar.edu.unsam.pds.exceptions.ValidationException
import jakarta.persistence.*
import java.io.Serializable
import java.util.*

@Entity @Table(name = "APP_USER")
class User(
    var name: String,
    var lastName: String,
    var email: String,
    var image: String = "",
    var isAdmin: Boolean = false,
    var credits: Double = 0.0
) : Timestamp(), Serializable {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    lateinit var id: UUID

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "app_user_assignment",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "assignment_id")]
    )
    val assignmentsList = mutableSetOf<Event>()

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    val reviews= mutableSetOf<Review>()

    fun subscribedCourses(): Set<Course> {
        return assignmentsList.map { it.course }.toSet()
    }

    fun addAssignment(event: Event) {
        if (assignmentsList.any { it.id == event.id }) {
            throw ValidationException("El usuario ya está subscripto a esta asignación")
        } else {
            assignmentsList.add(event)
        }
    }

    fun removeAssignment(event: Event) {
        if (!assignmentsList.any { it.id == event.id }) {
            throw ValidationException("El usuario no está subscripto a esta asignación")
        } else {
            assignmentsList.removeIf { it.id == event.id }
        }
    }

    fun hasEnoughCredits(credits: Double): Boolean {
        return this.credits >= credits
    }

    fun payCredits(credits: Double) {
        if (hasEnoughCredits(credits)) {
            this.credits -= credits
        } else {
            throw ValidationException("El usuario no tiene suficientes créditos")
        }
    }

    fun subscribe(event: Event) {
        if (hasEnoughCredits(event.price)) {
            payCredits(event.price)
        } else {
            throw ValidationException("El usuario no tiene suficientes créditos")
        }
        addAssignment(event)
    }
}