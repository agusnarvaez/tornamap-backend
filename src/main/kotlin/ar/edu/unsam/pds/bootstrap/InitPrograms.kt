package ar.edu.unsam.pds.bootstrap

import ar.edu.unsam.pds.exceptions.NotFoundException
import ar.edu.unsam.pds.models.Program
import ar.edu.unsam.pds.models.User
import ar.edu.unsam.pds.repository.CourseRepository
import ar.edu.unsam.pds.repository.EventRepository
import ar.edu.unsam.pds.repository.ProgramRepository
import ar.edu.unsam.pds.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.DependsOn
import org.springframework.core.env.Environment
import org.springframework.core.env.Profiles
import org.springframework.stereotype.Component

@Component(value = "InitPrograms.beanName")
@DependsOn(value = ["InitUsers.beanName", "InitCourses.beanName"])
class InitPrograms : BootstrapGeneric("Programs") {
    @Autowired private lateinit var courseRepository: CourseRepository
    @Autowired private lateinit var programRepository: ProgramRepository
    @Autowired private lateinit var userRepository: UserRepository
    @Autowired private lateinit var environment: Environment

    fun urlBase() = "http://${this.getDomain()}:8080/media/public"

    fun getDomain() =
        if (environment.acceptsProfiles(Profiles.of("prod"))) "149.50.143.203"
        else "localhost"

    override fun doAfterPropertiesSet() {
        programRepository.save(
            Program(
                name = "Tecnicatura Universitaria en Programación Informática",
                description =
                    """
                    La carrera tienen un bloque curricular constituido por asignaturas básicas de matemática,
                    electricidad y magnetismo, y los fundamentos básicos de la computación y la programación.
                    Luego se dividen en bloques curriculares específicos de Programación.
                    """
                        .trimIndent(),
            ).apply {
                addAdmin(userByEmail("admin@admin.com"))
                addCourse(courseRepository.findAll().first())
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
                addCourse(courseRepository.findAll().first())
            }
        )
    }

    fun userByEmail(mail : String): User {
        return userRepository.findByEmail(mail).orElseThrow {
            NotFoundException("usuario no encontrado")
        }
    }
}
