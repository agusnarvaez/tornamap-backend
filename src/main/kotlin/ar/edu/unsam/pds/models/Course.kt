package ar.edu.unsam.pds.models

import jakarta.persistence.*
import java.io.Serializable
import java.util.*

@Entity @Table(name = "APP_COURSE")
class

Course(
    val title: String,

    @Column(length = 1024)
    val description: String,

) : Timestamp(), Serializable {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    lateinit var id: UUID

    @ManyToMany
    @JoinTable(
        name = "app_course_program",
        joinColumns = [JoinColumn(name = "course_id")],
        inverseJoinColumns = [JoinColumn(name = "program_id")]
    )
    var programList:MutableSet<Program>=mutableSetOf()
}