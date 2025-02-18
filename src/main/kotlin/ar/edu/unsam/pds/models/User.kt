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
    var isAdmin: Boolean = false
) : Timestamp(), Serializable {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    lateinit var id: UUID

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "app_user_event",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "event_id")]
    )

    val eventList = mutableSetOf<Event>()

    fun subscribedCourses(): Set<Course> {
        return eventList.map { it.course }.toSet()
    }

    fun addEvent(event: Event) {
        if (eventList.any { it.id == event.id }) {
            throw ValidationException("El usuario ya está subscripto a esta asignación")
        } else {
            eventList.add(event)
        }
    }

    fun removeEvent(event: Event) {
        if (!eventList.any { it.id == event.id }) {
            throw ValidationException("El usuario no está suscripto a esta asignación")
        } else {
            eventList.removeIf { it.id == event.id }
        }
    }
}