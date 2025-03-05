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
            Course(title="Redes Locales", description = null)
        )


    }

    fun findByName(name: String): Program? {
        return programRepository.findAll().find { it.name.contains(name) }
    }
}
