package ar.edu.unsam.pds.bootstrap

import ar.edu.unsam.pds.exceptions.NotFoundException
import ar.edu.unsam.pds.models.Program
import ar.edu.unsam.pds.models.User
import ar.edu.unsam.pds.repository.CourseRepository
import ar.edu.unsam.pds.repository.ProgramRepository
import ar.edu.unsam.pds.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.DependsOn
import org.springframework.context.annotation.Profile
import org.springframework.core.env.Environment
import org.springframework.core.env.Profiles
import org.springframework.stereotype.Component

@Component(value = "InitPrograms.beanName")
@DependsOn(value = ["InitUsers.beanName", "InitCourses.beanName"])
@Profile(value = ["dev", "prod", "test"])
class InitPrograms : BootstrapGeneric("Programs") {
    @Autowired private lateinit var courseRepository: CourseRepository
    @Autowired private lateinit var programRepository: ProgramRepository
    @Autowired private lateinit var userRepository: UserRepository
    @Autowired private lateinit var environment: Environment

    //fun urlBase() = "http://${this.getDomain()}:8080/media/public"

    fun getDomain() =
        if (environment.acceptsProfiles(Profiles.of("prod"))) "149.50.143.203"
        else "localhost"

    override fun doAfterPropertiesSet() {
        programRepository.save(
            Program(
                name = "Tecnicatura en Programación Informática",
                description =
                    """
                    La carrera tienen un bloque curricular constituido por asignaturas básicas de matemática,
                    electricidad y magnetismo, y los fundamentos básicos de la computación y la programación.
                    Luego se dividen en bloques curriculares específicos de Programación.
                    """
                        .trimIndent(),
            ).apply {
                addAdmin(userByEmail("admin@admin.com"))
                addCourse(mutableListOf(courseRepository.findCourseByName("Matematica I")!!))
                addCourse(mutableListOf(courseRepository.findCourseByName("Matematica II")!!))
                addCourse(mutableListOf(courseRepository.findCourseByName("Matematica III")!!))
                addCourse(mutableListOf(courseRepository.findCourseByName("Electricidad y Magnetismo")!!))
                addCourse(mutableListOf(courseRepository.findCourseByName("Laboratorio de Computación I")!!))
                addCourse(mutableListOf(courseRepository.findCourseByName("Laboratorio de Computación II")!!))
                addCourse(mutableListOf(courseRepository.findCourseByName("Telecomunicaciones y Redes")!!))
                addCourse(mutableListOf(courseRepository.findCourseByName("Métodos Numéricos")!!))
                addCourse(mutableListOf(courseRepository.findCourseByName("Sistemas de Procesamiento de Datos")!!))
                addCourse(mutableListOf(courseRepository.findCourseByName("Algoritmos I")!!))
                addCourse(mutableListOf(courseRepository.findCourseByName("Algoritmos II")!!))
                addCourse(mutableListOf(courseRepository.findCourseByName("Algoritmos III")!!))
                addCourse(mutableListOf(courseRepository.findCourseByName("Conceptos de Arquitecturas y Sistemas Operativos")!!))
                addCourse(mutableListOf(courseRepository.findCourseByName("Programación con Herramientas Modernas")!!))
                addCourse(mutableListOf(courseRepository.findCourseByName("Proyectos de Software")!!))
                addCourse(mutableListOf(courseRepository.findCourseByName("Paradigmas de Programación")!!))
                addCourse(mutableListOf(courseRepository.findCourseByName("Base de Datos")!!))
                addCourse(mutableListOf(courseRepository.findCourseByName("Seminario de  Programación Concurrente, Paralela y Distribuida")!!))
            }
        )

        programRepository.save(
            Program(
                name = "Tecnicatura en Redes Informáticas",
                description =
                    """
                    La carrera tiene un bloque curricular constituido por asignaturas básicas de matemática, electricidad y magnetismo,
                    y los fundamentos básicos de la computación y la programación.
                    """
                        .trimIndent(),
            ).apply {
                addAdmin(userByEmail("admin@admin.com"))
                //addCourse(courseRepository.findAll())
                addCourse(mutableListOf(courseRepository.findCourseByName("Matematica I")!!))
                addCourse(mutableListOf(courseRepository.findCourseByName("Matematica II")!!))
                addCourse(mutableListOf(courseRepository.findCourseByName("Matematica III")!!))
                addCourse(mutableListOf(courseRepository.findCourseByName("Electricidad y Magnetismo")!!))
                addCourse(mutableListOf(courseRepository.findCourseByName("Laboratorio de Computación I")!!))
                addCourse(mutableListOf(courseRepository.findCourseByName("Laboratorio de Computación II")!!))
                addCourse(mutableListOf(courseRepository.findCourseByName("Sistemas de Procesamiento de Datos")!!))
                addCourse(mutableListOf(courseRepository.findCourseByName("Telecomunicaciones y Redes")!!))
                addCourse(mutableListOf(courseRepository.findCourseByName("Algoritmos I")!!))
                addCourse(mutableListOf(courseRepository.findCourseByName("Conceptos de Arquitecturas y Sistemas Operativos")!!))
                addCourse(mutableListOf(courseRepository.findCourseByName("Redes de Información I")!!))
                addCourse(mutableListOf(courseRepository.findCourseByName("Redes de Información II")!!))
                addCourse(mutableListOf(courseRepository.findCourseByName("Redes de Información III")!!))
                addCourse(mutableListOf(courseRepository.findCourseByName("Proyecto I")!!))
                addCourse(mutableListOf(courseRepository.findCourseByName("Proyecto II")!!))
                addCourse(mutableListOf(courseRepository.findCourseByName("Proyecto III")!!))
                addCourse(mutableListOf(courseRepository.findCourseByName("Administración de Redes de Computadoras")!!))
                addCourse(mutableListOf(courseRepository.findCourseByName("Sistemas Avanzados de Comunicación")!!))
            }
        )

        programRepository.save(
            Program(
                name = "Licenciatura en Ciencia de datos",
                description =
                    """
                    La carrera tiene un bloque curricular constituido por asignaturas básicas de matemática, electricidad y magnetismo,
                    y los fundamentos básicos de la computación y la programación.
                    """
                        .trimIndent(),
            ).apply {
                addAdmin(userByEmail("admin@admin.com"))
                //addCourse(mutableListOf(courseRepository.findCourseByName("Matematica I")!!))
            }
        )
    }

    fun userByEmail(mail : String): User {
        return userRepository.findByEmail(mail).orElseThrow {
            NotFoundException("usuario no encontrado")
        }
    }
}
