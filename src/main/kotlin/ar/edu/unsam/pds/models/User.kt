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

    fun assignedCourses(): Set<Course> {
        return eventList.map { it.course }.toSet()
    }

    fun addEvent(event: Event) {
        if (eventList.any { it.id == event.id }) {
            throw ValidationException("El usuario ya forma parte de este evento")
        } else {
            eventList.add(event)
        }
    }

    fun removeEvent(event: Event) {
        if (!eventList.any { it.id == event.id }) {
            throw ValidationException("El usuario no forma parte de este evento")
        } else {
            eventList.removeIf { it.id == event.id }
        }
    }
}