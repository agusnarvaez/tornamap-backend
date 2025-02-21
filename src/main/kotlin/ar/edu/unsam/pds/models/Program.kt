package ar.edu.unsam.pds.models

import jakarta.persistence.*
import java.io.Serializable
import java.util.*

@Entity @Table(name = "APP_PROGRAM")
class Program(
    val name: String,

    @Column(length = 1024)
    val description: String,
    //  Es necesaria la descripción?

    //  Timestamp() es algo que también queda deprecado porque es para los
    //  créditos y demás implementaciones de las subscripciones?
) : Timestamp(), Serializable {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    lateinit var id: UUID

    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name="program_id", referencedColumnName = "id")
    val courses: MutableSet<Course> = mutableSetOf()

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "app_user_program",
        joinColumns = [JoinColumn(name = "program_id")],
        inverseJoinColumns = [JoinColumn(name = "user_id")]
    )
    val admin = mutableSetOf<User>()

    fun addCourse(course: Course) {
        courses.add(course)
    }

    fun addAdmin(user: User) {
        admin.add(user)
    }

}