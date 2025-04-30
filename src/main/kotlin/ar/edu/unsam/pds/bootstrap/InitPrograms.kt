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
import org.springframework.stereotype.Component

@Component(value = "InitPrograms.beanName")
@DependsOn(value = ["InitUsers.beanName", "InitCourses.beanName"])
@Profile(value = ["dev", "prod", "test"])
class InitPrograms : BootstrapGeneric("Programs") {
    @Autowired private lateinit var courseRepository: CourseRepository
    @Autowired private lateinit var programRepository: ProgramRepository
    @Autowired private lateinit var userRepository: UserRepository

    override fun doAfterPropertiesSet() {

        // Tecnicatura en Programación Informática
        val matI  = courseRepository.findCourseByName("Matemática I")!!
        val matII = courseRepository.findCourseByName("Matemática II")!!
        val matIII = courseRepository.findCourseByName("Matemática III")!!
        val eym = courseRepository.findCourseByName("Electricidad y Magnetismo")!!
        val labI = courseRepository.findCourseByName("Laboratorio de Computación I")!!
        val labII = courseRepository.findCourseByName("Laboratorio de Computación II")!!
        val redes = courseRepository.findCourseByName("Telecomunicaciones y Redes")!!
        val num = courseRepository.findCourseByName("Métodos Numéricos")!!
        val spd = courseRepository.findCourseByName("Sistemas de Procesamiento de Datos")!!
        val algI = courseRepository.findCourseByName("Algoritmos I")!!
        val algII = courseRepository.findCourseByName("Algoritmos II")!!
        val algIII = courseRepository.findCourseByName("Algoritmos III")!!
        val ca = courseRepository.findCourseByName("Conceptos de Arquitecturas y Sistemas Operativos")!!
        val phm = courseRepository.findCourseByName("Programación con Herramientas Modernas")!!
        val ps = courseRepository.findCourseByName("Proyectos de Software")!!
        val pp = courseRepository.findCourseByName("Paradigmas de Programación")!!
        val bd = courseRepository.findCourseByName("Base de Datos")!!
        val spc = courseRepository.findCourseByName("Seminario de  Programación Concurrente, Paralela y Distribuida")!!

        //  Tecnicatura en Redes Informáticas
        val riI = courseRepository.findCourseByName("Redes de Información I")!!
        val riII = courseRepository.findCourseByName("Redes de Información II")!!
        val riIII = courseRepository.findCourseByName("Redes de Información III")!!
        val pI = courseRepository.findCourseByName("Proyecto I")!!
        val pII = courseRepository.findCourseByName("Proyecto II")!!
        val pIII = courseRepository.findCourseByName("Proyecto III")!!
        val ar = courseRepository.findCourseByName("Administración de Redes de Computadoras")!!
        val sac = courseRepository.findCourseByName("Sistemas Avanzados de Comunicación")!!

        /* --------- 1) Creo el Program --------- */
        val tpi = Program(
            name = "Tecnicatura en Programación Informática",
            description = """
            La carrera tiene un bloque curricular constituido por asignaturas básicas de matemática,
            electricidad y magnetismo, y los fundamentos básicos de la computación y la programación.
            Luego se dividen en bloques curriculares específicos de Programación.
        """.trimIndent()
        ).apply {
            addAdmin(userByEmail("admin@admin.com"))
        }

        /* --------- 2) Lo persisto para que obtenga su UUID --------- */
        val savedTpi = programRepository.save(tpi)

        /* --------- 3) Ahora enlazo los cursos y los salvo --------- */
        listOf(
            matI, matII, matIII, eym, labI, labII, redes, num, spd,
            algI, algII, algIII, ca, phm, ps, pp, bd, spc
        ).forEach { course ->
            course.addPrograms(listOf(savedTpi))   // sincroniza ambos lados
            courseRepository.save(course)          // genera filas en app_course_program
        }

        /* --------- 1) Creo el Program --------- */
        val tri = Program(
            name = "Tecnicatura en Redes Informáticas",
                description =
                    """
                    La carrera tiene un bloque curricular constituido por asignaturas básicas de matemática, electricidad y magnetismo,
                    y los fundamentos básicos de la computación y la programación.
                    """
                        .trimIndent()
        ).apply {
                addAdmin(userByEmail("admin@admin.com"))
        }
        /* --------- 2) Lo persisto para que obtenga su UUID --------- */
        val savedTri = programRepository.save(tri)

        /* --------- 3) Ahora enlazo los cursos y los salvo --------- */
        listOf(
            riI, riII, riIII, pI, pII, pIII, ar, sac
        ).forEach { course ->
            course.addPrograms(listOf(savedTri))   // sincroniza ambos lados
            courseRepository.save(course)          // genera filas en app_course_program
        }

        /* --------- 1) Creo el Program --------- */
        val lcd = Program(
            name = "Licenciatura en Ciencia de Datos",
            description =
                """
                La carrera tiene un bloque curricular constituido por asignaturas básicas de matemática, electricidad y magnetismo,
                y los fundamentos básicos de la computación y la programación.
                """
                    .trimIndent()
        ).apply {
            addAdmin(userByEmail("admin@admin.com"))
        }
        /* --------- 2) Lo persisto para que obtenga su UUID --------- */
        //val savedLcd =
            programRepository.save(lcd)

        /* --------- 3) Ahora enlazo los cursos y los salvo --------- */
//        listOf(
//            matI, matII, matIII, eym, labI, labII, redes, num, spd,
//            algI, algII, algIII, ca, phm, ps, pp, bd, spc
//        ).forEach { course ->
//            course.addPrograms(listOf(savedLcd))   // sincroniza ambos lados
//            courseRepository.save(course)          // genera filas en app_course_program
//        }

    }

    fun userByEmail(mail : String): User {
        return userRepository.findByEmail(mail).orElseThrow {
            NotFoundException("usuario no encontrado")
        }
    }
}
