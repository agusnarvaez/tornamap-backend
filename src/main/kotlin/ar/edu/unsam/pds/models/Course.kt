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

    fun userNames(): List<String> {
        return events.flatMap { it.getProfessorNames() }
    }

}