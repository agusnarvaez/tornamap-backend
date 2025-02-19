package ar.edu.unsam.pds.models

import jakarta.persistence.*
import java.io.Serializable
import java.util.*

@Entity @Table(name = "APP_COURSE")
class Course(
    val title: String,

    @Column(length = 1024)
    val description: String,

    var category: String,
    @Column(length = 1024)
    var image: String
) : Timestamp(), Serializable {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    lateinit var id: UUID

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "course", cascade = [CascadeType.ALL], orphanRemoval = true)
    val events = mutableSetOf<Event>()

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "course", cascade = [CascadeType.ALL], orphanRemoval = true)
    val reviews= mutableSetOf<Review>()

    fun addEvent(event: Event) {
        events.add(event)
        event.attachCourse(this)
    }

    fun removeEvent(event: Event) {
        events.removeIf{ it.id == event.id }
    }

    fun totalIncome(): Double {
        return events.sumOf { it.totalIncome() }
    }

    fun mostPopularEvent(): Event {
        return events.maxByOrNull { it.subscribedUsers.size }!!
    }

    fun mostProfitableEvent(): Event {
        return events.maxByOrNull { it.totalIncome() }!!
    }

    fun totalSubscribedUsers(): Int {
        return events.sumOf { it.totalSubscribedUsers() }
    }

    fun eventsNames(): Set<String> {
        return events.map { it.activeDays() }.toSet()
    }

    fun averageRating(): Double {
        if (reviews.isEmpty()) {
            return 0.0
        }

        val sum = reviews.sumOf { it.rating.toDouble() }
        return sum / reviews.size
    }

}