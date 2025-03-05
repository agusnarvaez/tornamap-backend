package ar.edu.unsam.pds.bootstrap

import ar.edu.unsam.pds.models.Course
import ar.edu.unsam.pds.models.Program
import ar.edu.unsam.pds.repository.CourseRepository
import ar.edu.unsam.pds.repository.ProgramRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.DependsOn
import org.springframework.core.env.Environment
import org.springframework.core.env.Profiles
import org.springframework.stereotype.Component

@Component(value = "InitCourses.beanName")
@DependsOn(value = ["InitPrograms.beanName"])
class InitCourses : BootstrapGeneric("Courses") {
    @Autowired private lateinit var programRepository: ProgramRepository
    @Autowired private lateinit var courseRepository: CourseRepository
    @Autowired private lateinit var environment: Environment

    fun urlBase() = "http://${this.getDomain()}:8080/media/public"

    fun getDomain() =
        if (environment.acceptsProfiles(Profiles.of("prod"))) "149.50.143.203"
        else "localhost"

    override fun doAfterPropertiesSet() {

        courseRepository.save(
            Course(
                    title="Matematica I",
                    description="Asignatura introductoria que aborda los fundamentos del análisis en una variable, álgebra y geometría. Se aprenden conceptos clave como funciones, límites, derivadas, polinomios y ecuaciones, desarrollando habilidades esenciales para el razonamiento matemático y la resolución de problemas.")
        )

        courseRepository.save(
            Course(
                title="Laboratorio de Informatica I",
                description = "Asignatura introductoria que aborda las nociones básicas de una computadora, utilización de programas e introducción a la resolución de pequeños problemas.")
            )

        courseRepository.save(
            Course(title="Redes Locales", description = null)
        )




    }

    fun findProgramByName(name: String): Program? {
        return programRepository.findAll().find { it.name.contains(name) }
    }
}
