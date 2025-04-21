package ar.edu.unsam.pds.bootstrap

import ar.edu.unsam.pds.models.Program
import ar.edu.unsam.pds.repository.CourseRepository
import ar.edu.unsam.pds.repository.ProgramRepository
import ar.edu.unsam.pds.repository.UserRepository
import org.springframework.context.annotation.DependsOn
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Component(value = "InitPrograms.beanName")
@DependsOn(value = ["InitUsers.beanName", "InitCourses.beanName"])
@Profile(value = ["dev", "prod", "test"])
class InitPrograms(
    private val courseRepository: CourseRepository,
    private val programRepository: ProgramRepository,
    private val userRepository: UserRepository
) : BootstrapGeneric("Programs") {

    override fun doAfterPropertiesSet() {
        /* ---------- ADMIN  ------------------------------------------------ */
        val admin = userRepository.findByEmail("admin@admin.com")
            .orElseThrow { IllegalStateException(
                "InitPrograms: falta el usuario admin@admin.com – se crea en InitUsers ?") }

        /* ---------- CURSOS  ---------------------------------------------- */
        val mateI  = courseRepository.findCourseByName("Matemática I")   // ← usa el mismo acento
            ?: throw IllegalStateException(
                "InitPrograms: falta el curso 'Matemática I' – se crea en InitCourses ?")

        saveIfAbsent(
            Program(
                name = "Tecnicatura Universitaria en Programación Informática",
                description = """La carrera tienen un bloque curricular constituido por asignaturas básicas de matemática,
                    electricidad y magnetismo, y los fundamentos básicos de la computación y la programación.
                    Luego se dividen en bloques curriculares específicos de Programación.
                    """.trimIndent(),
            ).apply {
                addAdmin(admin)
                addCourse(courseRepository.findAll())
            }
        )

        saveIfAbsent(
            Program(
                name = "Tecnicatura en Redes Informáticas",
                description =
                    """
                    La carrera tiene un bloque curricular constituido por asignaturas básicas de matemática, electricidad y magnetismo,
                    y los fundamentos básicos de la computación y la programación.
                    """
                        .trimIndent(),
            ).apply {
                addAdmin(admin)
                addCourse(courseRepository.findAll())
            }
        )

        saveIfAbsent(
            Program(
                name = "Licenciatura en Ciencia de datos",
                description =
                    """
                    La carrera tiene un bloque curricular constituido por asignaturas básicas de matemática, electricidad y magnetismo,
                    y los fundamentos básicos de la computación y la programación.
                    """
                        .trimIndent(),
            ).apply {
                addAdmin(admin)
                addCourse(listOf(mateI))
            }
        )
    }

    private fun saveIfAbsent(p: Program) {
        if (programRepository.findByName(p.name) == null) {
            programRepository.save(p)
        }
    }

}
