package ar.edu.unsam.pds.bootstrap

import ar.edu.unsam.pds.exceptions.NotFoundException
import ar.edu.unsam.pds.models.Event
import ar.edu.unsam.pds.models.Schedule
import ar.edu.unsam.pds.models.*
import ar.edu.unsam.pds.repository.ClassroomRepository
import ar.edu.unsam.pds.repository.EventRepository
import ar.edu.unsam.pds.repository.ScheduleRepository
import ar.edu.unsam.pds.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.DependsOn
import org.springframework.stereotype.Component
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime

@Component(value = "InitSchedules.beanName")
@DependsOn(value = ["InitEvents.beanName", "InitClassroom.beanName"])
class InitSchedules : BootstrapGeneric("Schedules") {
    @Autowired private lateinit var eventRepository: EventRepository
    @Autowired private lateinit var userRepository: UserRepository
    @Autowired private lateinit var scheduleRepository: ScheduleRepository
    @Autowired private lateinit var classroomRepository: ClassroomRepository

    override fun doAfterPropertiesSet() {
        val cursadaMate1TM = findEvent("Cursada Matemática I")
//        val cursadaMate1TT = findEvent("Cursada Matematica I")
//        val cursadaMate1TN = findEvent("Cursada Matematica I")

        val cursadaAlgo1 = findEvent("Cursada Algoritmos I")
        val cursadaAlgo2 = findEvent("Cursada Algoritmos II")
//        val cursadaAlgo3 = findEvent("Cursada Algoritmos III")
        val cursadaRedes = findEvent("Cursada Telecomunicaciones y Redes")
        val cursadaPHM = findEvent("Cursada PHM")
        val cursadaMetodosNumericos = findEvent("Cursada Métodos Numéricos")
        val cursadaProyecSoft = findEvent("Cursada Proyectos de Software")
        val cursadaParadigmas = findEvent("Cursada Paradigmas de Programación")
        val cursadaMate2 = findEvent("Cursada Matemática II")
        val cursadaMate3 = findEvent("Cursada Matemática III")
//        val cursadaEyM = findEvent("Cursada EyM")
        val cursadaCaso = findEvent("Cursada CASO")
//        val cursadaMate3 = findEvent("Cursada Matemática III")
//        val cursadaMate3 = findEvent("Cursada Matemática III")

        val carlos = userByEmail("cscirica@estudiantes.unsam.edu.ar")
        val dodino = userByEmail("dodain@estudiantes.unsam.edu.ar")
        val mc = userByEmail("mcabeledo@estudiantes.unsam.edu.ar")
//        val event1 = findEvent("Cursada Algoritmos I")
       /*
        val event3 = findEvent("Final Matematica I")*/


//      Turno Mañana
        val scheduleMate1TM= Schedule(
            startTime = LocalTime.of(9, 0),
            endTime = LocalTime.of(12, 0),
            weekDay = DayOfWeek.MONDAY,
            date = LocalDate.now(),
            isVirtual = false,
        ).apply {
            event = cursadaMate1TM
            classroom = classroomRepository.findByName("Aula A28").orElseThrow { NotFoundException("No se halló el aula") }
            assignUserToSchedule(dodino, this)
        }
        scheduleRepository.save(scheduleMate1TM)
        cursadaMate1TM.addSchedule(scheduleMate1TM)

        val scheduleMate1TM2= Schedule(
            startTime = LocalTime.of(9, 0),
            endTime = LocalTime.of(12, 0),
            weekDay = DayOfWeek.THURSDAY,
            date = LocalDate.now(),
            isVirtual = false,
        ).apply {
            event = cursadaMate1TM
            classroom = classroomRepository.findByName("Aula A28").orElseThrow { NotFoundException("No se halló el aula") }
            assignUserToSchedule(dodino, this)
        }
        scheduleRepository.save(scheduleMate1TM2)
        cursadaMate1TM.addSchedule(scheduleMate1TM2)

//      Turno Tarde
        val scheduleMate1TT= Schedule(
            startTime = LocalTime.of(14, 0),
            endTime = LocalTime.of(18, 0),
            weekDay = DayOfWeek.MONDAY,
            date = LocalDate.now(),
            isVirtual = false,
        ).apply {
            event = cursadaMate1TM
            classroom = classroomRepository.findByName("Aula A28").orElseThrow { NotFoundException("No se halló el aula") }
            assignUserToSchedule(dodino, this)
        }
        scheduleRepository.save(scheduleMate1TT)
        cursadaMate1TM.addSchedule(scheduleMate1TT)

        val scheduleMate1TT2= Schedule(
            startTime = LocalTime.of(17, 0),
            endTime = LocalTime.of(19, 0),
            weekDay = DayOfWeek.THURSDAY,
            date = LocalDate.now(),
            isVirtual = false,
        ).apply {
            event = cursadaMate1TM
            classroom = classroomRepository.findByName("Aula A28").orElseThrow { NotFoundException("No se halló el aula") }
            assignUserToSchedule(dodino, this)
        }
        scheduleRepository.save(scheduleMate1TT2)
        cursadaMate1TM.addSchedule(scheduleMate1TT2)
//      Turno Noche
        val scheduleMate1TN= Schedule(
            startTime = LocalTime.of(18, 0),
            endTime = LocalTime.of(22, 0),
            weekDay = DayOfWeek.MONDAY,
            date = LocalDate.now(),
            isVirtual = false,
        ).apply {
            event = cursadaMate1TM
            classroom = classroomRepository.findByName("Aula A28").orElseThrow { NotFoundException("No se halló el aula") }
            assignUserToSchedule(dodino, this)
        }
        scheduleRepository.save(scheduleMate1TN)
        cursadaMate1TM.addSchedule(scheduleMate1TN)

        val scheduleMate1TN2= Schedule(
            startTime = LocalTime.of(19, 0),
            endTime = LocalTime.of(22, 0),
            weekDay = DayOfWeek.THURSDAY,
            date = LocalDate.now(),
            isVirtual = false,
        ).apply {
            event = cursadaMate1TM
            classroom = classroomRepository.findByName("Aula A28").orElseThrow { NotFoundException("No se halló el aula") }
            assignUserToSchedule(dodino, this)
        }
        scheduleRepository.save(scheduleMate1TN2)
        cursadaMate1TM.addSchedule(scheduleMate1TN2)

        /*.................:Algo1:...............................*/
        val scheduleAlgo1Martes= Schedule(
            startTime = LocalTime.of(19, 0),
            endTime = LocalTime.of(22, 0),
            weekDay = DayOfWeek.TUESDAY,
            date = LocalDate.now(),
            isVirtual = false,
        ).apply {
            event = cursadaAlgo1
            classroom = classroomRepository.findByName("Aula A28").orElseThrow { NotFoundException("No se halló el aula") }
            assignUserToSchedule(dodino, this)
        }
        scheduleRepository.save(scheduleAlgo1Martes)
        cursadaAlgo1.addSchedule(scheduleAlgo1Martes)

        val scheduleAlgo1jueves= Schedule(
            startTime = LocalTime.of(19, 0),
            endTime = LocalTime.of(22, 0),
            weekDay = DayOfWeek.THURSDAY,
            date = LocalDate.now(),
            isVirtual = false,
        ).apply {
            event = cursadaAlgo1
            classroom = classroomRepository.findByName("Aula A28").orElseThrow { NotFoundException("No se halló el aula") }
            assignUserToSchedule(dodino, this)
        }
        scheduleRepository.save(scheduleAlgo1jueves)
        cursadaAlgo1.addSchedule(scheduleAlgo1jueves)

        /*.................:Algo2:...............................*/
        val scheduleAlgo2Martes= Schedule(
            startTime = LocalTime.of(19, 0),
            endTime = LocalTime.of(22, 0),
            weekDay = DayOfWeek.TUESDAY,
            date = LocalDate.now(),
            isVirtual = false,
        ).apply {
            event = cursadaAlgo2
            classroom = classroomRepository.findByName("Aula A28").orElseThrow { NotFoundException("No se halló el aula") }
            assignUserToSchedule(dodino, this)
        }
        scheduleRepository.save(scheduleAlgo2Martes)
        cursadaAlgo2.addSchedule(scheduleAlgo2Martes)

        val scheduleAlgo2Jueves= Schedule(
            startTime = LocalTime.of(19, 0),
            endTime = LocalTime.of(22, 0),
            weekDay = DayOfWeek.THURSDAY,
            date = LocalDate.now(),
            isVirtual = false,
        ).apply {
            event = cursadaAlgo2
            classroom = classroomRepository.findByName("Aula A28").orElseThrow { NotFoundException("No se halló el aula") }
            assignUserToSchedule(dodino, this)
        }
        scheduleRepository.save(scheduleAlgo2Jueves)
        cursadaAlgo2.addSchedule(scheduleAlgo2Jueves)

        /*.................:Redes:...............................*/
        val scheduleRedesMiercoles= Schedule(
            startTime = LocalTime.of(18, 0),
            endTime = LocalTime.of(22, 0),
            weekDay = DayOfWeek.WEDNESDAY,
            date = LocalDate.now(),
            isVirtual = false,
        ).apply {
            event = cursadaRedes
            classroom = classroomRepository.findByName("Aula A28").orElseThrow { NotFoundException("No se halló el aula") }
            assignUserToSchedule(mc, this)
        }
        scheduleRepository.save(scheduleRedesMiercoles)
        cursadaRedes.addSchedule(scheduleRedesMiercoles)

        val scheduleRedesViernes= Schedule(
            startTime = LocalTime.of(18, 0),
            endTime = LocalTime.of(22, 0),
            weekDay = DayOfWeek.FRIDAY,
            date = LocalDate.now(),
            isVirtual = false,
        ).apply {
            event = cursadaRedes
            classroom = classroomRepository.findByName("Aula A28").orElseThrow { NotFoundException("No se halló el aula") }
            assignUserToSchedule(mc, this)
        }
        scheduleRepository.save(scheduleRedesViernes)
        cursadaRedes.addSchedule(scheduleRedesViernes)

        /*.................:Metodos numericos:...............................*/
        val scheduleMetodosNumericos= Schedule(
            startTime = LocalTime.of(16, 0),
            endTime = LocalTime.of(22, 0),
            weekDay = DayOfWeek.MONDAY,
            date = LocalDate.now(),
            isVirtual = false,
        ).apply {
            event = cursadaMetodosNumericos
            classroom = classroomRepository.findByName("Aula A28").orElseThrow { NotFoundException("No se halló el aula") }
            assignUserToSchedule(mc, this)
        }
        scheduleRepository.save(scheduleMetodosNumericos)
        cursadaMetodosNumericos.addSchedule(scheduleMetodosNumericos)

        /*.................:PHM:...............................*/
        val schedulePHMLunes= Schedule(
            startTime = LocalTime.of(18, 0),
            endTime = LocalTime.of(22, 0),
            weekDay = DayOfWeek.MONDAY,
            date = LocalDate.now(),
            isVirtual = false,
        ).apply {
            event = cursadaPHM
            classroom = classroomRepository.findByName("Aula A28").orElseThrow { NotFoundException("No se halló el aula") }
            assignUserToSchedule(mc, this)
        }
        scheduleRepository.save(schedulePHMLunes)
        cursadaPHM.addSchedule(schedulePHMLunes)

        val schedulePHMJueves= Schedule(
            startTime = LocalTime.of(18, 0),
            endTime = LocalTime.of(22, 0),
            weekDay = DayOfWeek.THURSDAY,
            date = LocalDate.now(),
            isVirtual = false,
        ).apply {
            event = cursadaPHM
            classroom = classroomRepository.findByName("Aula A28").orElseThrow { NotFoundException("No se halló el aula") }
            assignUserToSchedule(mc, this)
        }
        scheduleRepository.save(schedulePHMJueves)
        cursadaPHM.addSchedule(schedulePHMJueves)

        /*.................:Proyectos de Software:...............................*/
        val scheduleProyecDeSoftMiercoles= Schedule(
            startTime = LocalTime.of(18, 0),
            endTime = LocalTime.of(22, 0),
            weekDay = DayOfWeek.WEDNESDAY,
            date = LocalDate.now(),
            isVirtual = true,
        ).apply {
            event = cursadaProyecSoft
            classroom = classroomRepository.findByName("Aula A28").orElseThrow { NotFoundException("No se halló el aula") }
            assignUserToSchedule(mc, this)
        }
        scheduleRepository.save(scheduleProyecDeSoftMiercoles)
        cursadaProyecSoft.addSchedule(scheduleProyecDeSoftMiercoles)

        val scheduleProyecDeSoftViernes= Schedule(
            startTime = LocalTime.of(18, 0),
            endTime = LocalTime.of(22, 0),
            weekDay = DayOfWeek.FRIDAY,
            date = LocalDate.now(),
            isVirtual = true,
        ).apply {
            event = cursadaProyecSoft
            classroom = classroomRepository.findByName("Aula A28").orElseThrow { NotFoundException("No se halló el aula") }
            assignUserToSchedule(mc, this)
        }
        scheduleRepository.save(scheduleProyecDeSoftViernes)
        cursadaProyecSoft.addSchedule(scheduleProyecDeSoftViernes)

        /*.................:Paradigmas de Prog:...............................*/
        val scheduleParadigmasDeProg= Schedule(
            startTime = LocalTime.of(16, 0),
            endTime = LocalTime.of(22, 0),
            weekDay = DayOfWeek.TUESDAY,
            date = LocalDate.now(),
            isVirtual = false,
        ).apply {
            event = cursadaParadigmas
            classroom = classroomRepository.findByName("Aula A28").orElseThrow { NotFoundException("No se halló el aula") }
            assignUserToSchedule(mc, this)
        }
        scheduleRepository.save(scheduleParadigmasDeProg)
        cursadaParadigmas.addSchedule(scheduleParadigmasDeProg)

        /*.................:Mate2:...............................*/
        val scheduleMate2Martes= Schedule(
            startTime = LocalTime.of(18, 0),
            endTime = LocalTime.of(22, 0),
            weekDay = DayOfWeek.TUESDAY,
            date = LocalDate.now(),
            isVirtual = false,
        ).apply {
            event = cursadaMate2
            classroom = classroomRepository.findByName("Aula A28").orElseThrow { NotFoundException("No se halló el aula") }
            assignUserToSchedule(carlos, this)
        }
        scheduleRepository.save(scheduleMate2Martes)
        cursadaMate2.addSchedule(scheduleMate2Martes)

        val scheduleMate2Jueves= Schedule(
            startTime = LocalTime.of(17, 0),
            endTime = LocalTime.of(19, 0),
            weekDay = DayOfWeek.THURSDAY,
            date = LocalDate.now(),
            isVirtual = false,
        ).apply {
            event = cursadaMate2
            classroom = classroomRepository.findByName("Aula A28").orElseThrow { NotFoundException("No se halló el aula") }
            assignUserToSchedule(carlos, this)
        }
        scheduleRepository.save(scheduleMate2Jueves)
        cursadaMate2.addSchedule(scheduleMate2Jueves)
        /*.................:Mate3:...............................*/
        val scheduleMate3= Schedule(
            startTime = LocalTime.of(16, 0),
            endTime = LocalTime.of(22, 0),
            weekDay = DayOfWeek.FRIDAY,
            date = LocalDate.now(),
            isVirtual = false,
        ).apply {
            event = cursadaMate3
            classroom = classroomRepository.findByName("Aula A28").orElseThrow { NotFoundException("No se halló el aula") }
            assignUserToSchedule(mc, this)
        }
        scheduleRepository.save(scheduleMate3)
        cursadaMate3.addSchedule(scheduleMate3)

        /*.................:Caso:...............................*/
        val scheduleCasoLunes= Schedule(
            startTime = LocalTime.of(18, 0),
            endTime = LocalTime.of(22, 0),
            weekDay = DayOfWeek.MONDAY,
            date = LocalDate.now(),
            isVirtual = false,
        ).apply {
            event = cursadaCaso
            classroom = classroomRepository.findByName("Aula A28").orElseThrow { NotFoundException("No se halló el aula") }
            assignUserToSchedule(mc, this)
        }
        scheduleRepository.save(scheduleCasoLunes)
        cursadaCaso.addSchedule(scheduleCasoLunes)

        val scheduleCasoMiercoles= Schedule(
            startTime = LocalTime.of(18, 0),
            endTime = LocalTime.of(22, 0),
            weekDay = DayOfWeek.THURSDAY,
            date = LocalDate.now(),
            isVirtual = false,
        ).apply {
            event = cursadaCaso
            classroom = classroomRepository.findByName("Aula A28").orElseThrow { NotFoundException("No se halló el aula") }
            assignUserToSchedule(mc, this)
        }
        scheduleRepository.save(scheduleCasoMiercoles)
        cursadaCaso.addSchedule(scheduleCasoMiercoles)




//
//        val scheduleEyM = Schedule(
//            startTime = LocalTime.of(18, 0),
//            endTime = LocalTime.of(22, 0),
//            weekDay = DayOfWeek.MONDAY,
//            date = LocalDate.now(),
//            isVirtual = false,
//        ).apply {
//            event = cursadaEyM
//            classroom = classroomRepository.findByName("Aula A28").orElseThrow { NotFoundException("No se halló el aula") }
//            assignUserToSchedule(dodino, this)
//        }
//        scheduleRepository.save(scheduleEyM)


//        val eventCharla = findEvent("Charla de Bienvenida")




       /* val schedule1 = Schedule(
            startTime = LocalTime.of(8, 30),
            endTime = LocalTime.of(10, 0),
            weekDay = DayOfWeek.MONDAY,
            date = LocalDate.now(),
            isVirtual = false,
        ).apply {
            event = event1
            classroom = classroomRepository.findByName("Aula A28").orElseThrow { NotFoundException("No se halló el aula") }
            assignUserToSchedule(dodino, this)
        }

        scheduleRepository.save(schedule1)
        event1.addSchedule(schedule1)*/

        /*val schedule2 = Schedule(
            startTime = LocalTime.of(10, 30),
            endTime = LocalTime.of(12, 0),
            weekDay = DayOfWeek.FRIDAY,
            date = LocalDate.now(),
            isVirtual = true,
        ).apply {
            event = event2
            assignUserToSchedule(mc , this)
        }

        scheduleRepository.save(schedule2)
        event2.addSchedule(schedule2)*/

        /*val schedule3 = Schedule(
            startTime = LocalTime.of(10, 30),
            endTime = LocalTime.of(12, 0),
            weekDay = DayOfWeek.FRIDAY,
            date = LocalDate.now(),
            isVirtual = true,
        ).apply {
            event = event3
            assignUserToSchedule(carlos, this)
        }

        scheduleRepository.save(schedule3)
        event3.addSchedule(schedule3)

        val schedule4 = Schedule(
            startTime = LocalTime.of(10, 30),
            endTime = LocalTime.of(12, 0),
            weekDay = DayOfWeek.FRIDAY,
            date = LocalDate.now(),
            isVirtual = true,
        ).apply {
            event = event1
            assignUserToSchedule(carlos, this)
        }
        event1.addSchedule(schedule4)
        scheduleRepository.save(schedule4)*/

//        val schedule5 = Schedule(
//            startTime = LocalTime.of(8, 30),
//            endTime = LocalTime.of(10, 0),
//            weekDay = DayOfWeek.MONDAY,
//            date = LocalDate.now(),
//            isVirtual = false,
//        ).apply {
//            event = event4
//            classroom = classroomRepository.findByName("Aula A28").orElseThrow { NotFoundException("No se halló el aula") }
//            assignUserToSchedule(dodino, this)
//        }
//        scheduleRepository.save(schedule5)

        /*val scheduleCharla = Schedule(
            startTime = LocalTime.of(10, 30),
            endTime = LocalTime.of(12, 0),
            weekDay = DayOfWeek.FRIDAY,
            date = LocalDate.now(),
            isVirtual = true,
        ).apply {
            event = eventCharla
            assignUserToSchedule(carlos, this)
        }
        eventCharla.addSchedule(scheduleCharla)
        scheduleRepository.save(scheduleCharla)*/
    }

    fun findEvent(name: String): Event {
        return eventRepository.findByName(name).orElseThrow{ NotFoundException("No se halló un evento con ese nombre") }
    }


//    private fun validateClassroomList(classrooms: List<Classroom>) {
//        if (classrooms.isEmpty()) {
//            throw NotFoundException("No hay aulas cargadas")
//        }
//    }

//    private fun validateClassroomSearch(classrooms: List<Classroom>, classroomName: String) {
//        if (!classrooms.map {it.name}.contains(classroomName)) {
//            throw NotFoundException("No hay un aula con el nombre indicado.")
//        }
//    }

    fun userByEmail(mail: String): User {
        return userRepository.findByEmail(mail).orElseThrow { NotFoundException("No se encontró profesor con el email suministrado") }
    }

}