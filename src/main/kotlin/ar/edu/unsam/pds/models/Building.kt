package ar.edu.unsam.pds.models

import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.*
import java.io.Serializable
import java.util.UUID

@Entity @Table(name = "APP_BUILDING")
data class Building(
    val name: String
) : Serializable {
    @Id @GeneratedValue(strategy = GenerationType.UUID)

    lateinit var id: UUID

    @OneToMany(mappedBy = "building", fetch = FetchType.EAGER)
    val classrooms: MutableList<Classroom> = mutableListOf()
}