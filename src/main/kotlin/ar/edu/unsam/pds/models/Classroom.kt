package ar.edu.unsam.pds.models

import ar.edu.unsam.pds.models.enums.ClassroomType
import ar.edu.unsam.pds.models.enums.Floor
import jakarta.persistence.*
import java.io.Serializable
import java.util.UUID

@Entity @Table(name = "APP_CLASSROOM")
class Classroom(
    val name: String,
    val type: ClassroomType,
    val capacity: Int,
    val floor: Floor,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "building_id", nullable = false)
    val building: Building
    ): Serializable {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    lateinit var id: UUID


/*    init {
        require(floor in setOf(-1, 0, 1)) { "floor solo puede ser -1, 0 o 1" }
    }
APLICAMOS UN ENUM PERO TAMBIÉN TENEMOS ESTA IMPLEMENTACIÓN, HAY ALGUNA RECOMENDADA? */
}