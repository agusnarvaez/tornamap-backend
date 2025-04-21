package ar.edu.unsam.pds.models

import jakarta.persistence.*
import java.io.Serializable
import java.util.*

@Entity @Table(name = "APP_USER")
class User(
    var name: String,
    var lastName: String,
    @Column(unique = true) var email: String,
    var image: String = "",
    var isAdmin: Boolean = false
) : Timestamp(), Serializable {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    lateinit var id: UUID

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "app_user_schedule",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "schedule_id")]
    )
    val scheduleList = mutableSetOf<Schedule>()

    fun fullName(): String {
        return "$name $lastName"
    }
}