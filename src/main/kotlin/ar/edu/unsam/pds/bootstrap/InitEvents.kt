package ar.edu.unsam.pds.bootstrap

import ar.edu.unsam.pds.models.Event
import ar.edu.unsam.pds.models.Course
import ar.edu.unsam.pds.repository.EventRepository
import ar.edu.unsam.pds.repository.ProgramRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.DependsOn
import org.springframework.stereotype.Component
import ar.edu.unsam.pds.exceptions.NotFoundException

@Component(value = "InitEvents.beanName")
@DependsOn(value = ["InitCourses.beanName", "InitPrograms.beanName"])
class InitEvents : BootstrapGeneric("Events") {
//    @Autowired private lateinit var scheduleRepository: ScheduleRepository
    @Autowired private lateinit var programRepository: ProgramRepository
    @Autowired private lateinit var eventRepository: EventRepository

    override fun doAfterPropertiesSet() {
        // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        val mate1 = this.findCourseByName(name = "Matemática I")
        val algo1 = this.findCourseByName(name = "Algoritmos I")
        val algo2 = this.findCourseByName(name = "Algoritmos II")
        val algo3 = this.findCourseByName(name = "Algoritmos III")
        val telecomuncacionesYRedes = this.findCourseByName(name = "Telecomunicaciones y Redes")
        val progHerrModer = this.findCourseByName(name = "Programación con Herramientas Modernas")
        val proyectosDeSoft = this.findCourseByName(name = "Proyectos de Software")
        val paradigmasDeProg = this.findCourseByName(name = "Paradigmas de Programación")
        val electricidadYMagnetismo = this.findCourseByName(name = "Electricidad y Magnetismo")
        val mate2 = this.findCourseByName(name = "Matemática II")
        val mate3 = this.findCourseByName(name = "Matemática III")
        val metodosNumericos = this.findCourseByName(name = "Métodos Numéricos")
        val caso = this.findCourseByName(name = "Conceptos de Arquitecturas y Sistemas Operativos")
        val laboratorioCompu1 = this.findCourseByName(name = "Laboratorio de Computación I")
        val laboratorioCompu2 = this.findCourseByName(name = "Laboratorio de Computación II")
        val spd = this.findCourseByName(name = "Sistemas de Procesamiento de Datos")


        val cursadaMate1TM = Event(
            name = "Turno Mañana",
            isApproved = true,
            course = mate1!!
        )
        eventRepository.save(cursadaMate1TM)

        val cursadaMate1TT = Event(
            name = "Turno Tarde",
            isApproved = true,
            course = mate1
        )
        eventRepository.save(cursadaMate1TT)

        val cursadaMate1TN = Event(
            name = "Turno Noche",
            isApproved = true,
            course = mate1
        )
        eventRepository.save(cursadaMate1TN)

        val cursadaMate2 = Event(
            name = "Cursada Matemática II",
            isApproved = true,
            course = mate2!!
        )
        eventRepository.save(cursadaMate2)

        val cursadaMate3 = Event(
            name = "Cursada Matemática III",
            isApproved = true,
            course = mate3!!
        )
        eventRepository.save(cursadaMate3)

        val cursadaMate3B = Event(
            name = "Mate III - Comisión B",
            isApproved = true,
            course = mate3
        )
        eventRepository.save(cursadaMate3B)

        val cursadaAlgo1 = Event(
            name = "Cursada Algoritmos I",
            isApproved = true,
            course = algo1!!
        )
        eventRepository.save(cursadaAlgo1)

        val cursadaAlgo2 = Event(
            name = "Cursada Algoritmos II",
            isApproved = true,
            course = algo2!!
        )
        eventRepository.save(cursadaAlgo2)

        val redes = Event(
            name = "Cursada Telecomunicaciones y Redes",
            isApproved = true,
            course = telecomuncacionesYRedes!!
        )
        eventRepository.save(redes)

        val metodosNum = Event(
            name = "Cursada Métodos Numéricos",
            isApproved = true,
            course = metodosNumericos!!
        )
        eventRepository.save(metodosNum)

        val phm = Event(
            name = "Cursada PHM",
            isApproved = true,
            course = progHerrModer!!
        )
        eventRepository.save(phm)

        val proyecDeSoft = Event(
            name = "Cursada Proyectos de Software",
            isApproved = true,
            course = proyectosDeSoft!!
        )
        eventRepository.save(proyecDeSoft)

        val paradigmas = Event(
            name = "Cursada Paradigmas de Programación",
            isApproved = true,
            course = paradigmasDeProg!!
        )
        eventRepository.save(paradigmas)

        val cursadaEyMN1= Event(
            name = "Cursada EyM",
            isApproved = true,
            course = electricidadYMagnetismo!!
        )
        eventRepository.save(cursadaEyMN1)

        val cursadaEyMN2= Event(
            name = "Cursada EyM - lab Jueves",
            isApproved = true,
            course = electricidadYMagnetismo
        )
        eventRepository.save(cursadaEyMN2)

        val cursadaEyMT= Event(
            name = "Cursada EyM TT",
            isApproved = true,
            course = electricidadYMagnetismo
        )
        eventRepository.save(cursadaEyMT)

        val cursadaCASO= Event(
            name = "Cursada CASO",
            isApproved = true,
            course = caso!!
        )
        eventRepository.save(cursadaCASO)

        val cursadaLabo1 = Event(
            name = "Cursada Labo1",
            isApproved = true,
            course = laboratorioCompu1!!
        )
        eventRepository.save(cursadaLabo1)

        val cursadaLabo2 = Event(
            name = "Cursada Labo2",
            isApproved = true,
            course = laboratorioCompu2!!
        )
        eventRepository.save(cursadaLabo2)

        val cursadaSPD = Event(
            name = "Cursada SPD",
            isApproved = true,
            course = spd!!
        )
        eventRepository.save(cursadaSPD)



      }

    fun findCourseByName(name: String): Course? {
        val programsList = programRepository.findAll()
        val allCourses = programsList.map { it.courses }.flatten()
        if (allCourses.isEmpty()) {
            throw NotFoundException("No se hallaron materias")
        }
        return allCourses.find { it.name == name }
    }
}