package ar.edu.unsam.pds.models

import jakarta.persistence.*
import java.io.Serializable
import java.util.*

@Entity @Table(name = "APP_COURSE")
class Course(
    val name: String,

    @Column(length = 1024)
    val description: String,

    ) : Timestamp(), Serializable {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    lateinit var id: UUID

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "course", cascade = [CascadeType.ALL], orphanRemoval = true)
    val events = mutableSetOf<Event>()

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "courses", cascade = [CascadeType.ALL])
    val programs = mutableSetOf<Program>()

    fun programNames(): List<String> {
        return programs.map { it.name }
    }

    fun events(): String = this.events.joinToString(", ") { it.name }

    fun professors(): String = this.events.flatMap { it.getProfessorNames() }.toSet().joinToString(" - ")

    fun modality(): String {
        val isVirtual =  this.events.map { it.schedules.any{ it.isVirtual } }
        val isPresential = this.events.map { it.schedules.any{ !it.isVirtual } }
        return when {
            isVirtual.all { it } -> "Virtual"
            isPresential.all { it } -> "Presencial"
            else -> "Virtual - Presencial"
        }
    }

//    fun formattedSchedules() : String = this.events.flatMap { it.schedules }.joinToString(", ") { "${it.startTime} - ${it.endTime} ${it.translateAndFormatWeekDay()}" }
    fun formattedSchedules(): String =this.events.flatMap { it.schedules }.joinToString(separator = "\n")
                                {"${it.translateAndFormatWeekDay()} : ${it.startTime} - ${it.endTime}"}
}