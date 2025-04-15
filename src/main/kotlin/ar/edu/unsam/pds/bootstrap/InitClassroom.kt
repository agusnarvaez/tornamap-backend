package ar.edu.unsam.pds.bootstrap

import ar.edu.unsam.pds.exceptions.NotFoundException
import ar.edu.unsam.pds.models.Building
import ar.edu.unsam.pds.models.Classroom
import ar.edu.unsam.pds.models.enums.ClassroomType
import ar.edu.unsam.pds.repository.BuildingRepository
import ar.edu.unsam.pds.repository.ClassroomRepository
import org.springframework.stereotype.Component
import org.springframework.context.annotation.DependsOn
import org.springframework.beans.factory.annotation.Autowired

@Component(value = "InitClassroom.beanName")
@DependsOn(value = ["InitBuilding.beanName"])
class InitClassroom : BootstrapGeneric("Classroom") {
    @Autowired private lateinit var classroomRepository: ClassroomRepository
    @Autowired private lateinit var buildingRepository: BuildingRepository

    override fun doAfterPropertiesSet() {
        val tornavias = findBuildingByName("Tornavías")

        val laboComputacion1 = Classroom(
            code = "LC1",
            name = "Laboratorio de Computación 1",
            capacity = 50,
            floor = 1,
            type = ClassroomType.LABORATORY,
            building = tornavias
        )
        classroomRepository.save(laboComputacion1)

        val aulaA28 = Classroom(
            code = "A28",
            name = "Aula A28",
            capacity = 30,
            floor = 2,
            type = ClassroomType.CLASSROOM,
            building = tornavias
        )

        val salaLectura = Classroom(
            code = "SAL",
            name = "Sala de Lectura",
            capacity = 20,
            floor = 1,
            type = ClassroomType.LECTURE,
            building = tornavias
        )

        val carpaAuditorio = Classroom(
            code = "CAR",
            name = "Carpa Auditorio",
            capacity = 100,
            floor = 0,
            type = ClassroomType.AUDITORIUM,
            building = tornavias
        )

        val cidi = Classroom(
            code = "CID",
            name = "Centro de investigacion y desarrollo de informatica",
            capacity = 50,
            floor = 1,
            type = ClassroomType.LABORATORY,
            building = tornavias
        )

        val aulaA10 = Classroom(
            code = "A10",
            name = "Aula 10",
            capacity = 50,
            floor = 1,
            type = ClassroomType.LABORATORY,
            building = tornavias
        )

        val aulaA6 = Classroom(
            code = "A6",
            name = "Aula 6",
            capacity = 50,
            floor = 1,
            type=  ClassroomType.CLASSROOM,
            building = tornavias
        )

        //A6 Bis¿?

        val aulaA7 = Classroom(
            code = "A7",
            name = "Aula 7",
            capacity = 50,
            floor = 1,
            type=  ClassroomType.CLASSROOM,
            building = tornavias
        )

        val aulaA5 = Classroom(
            code = "A5",
            name = "Aula 5",
            capacity = 50,
            floor = 1,
            type=  ClassroomType.CLASSROOM,
            building = tornavias
        )

        val aulaA1 = Classroom(
            code = "A1",
            name = "Aula 1",
            capacity = 50,
            floor = 1,
            type = ClassroomType.CLASSROOM,
            building = tornavias
        )
        classroomRepository.save(aulaA1)

        val aulaA2 = Classroom(
            code = "A2",
            name = "Aula 2",
            capacity = 50,
            floor = 1,
            type = ClassroomType.CLASSROOM,
            building = tornavias
        )
        classroomRepository.save(aulaA2)

        val aulaA3 = Classroom(
            code = "A3",
            name = "Aula 3",
            capacity = 50,
            floor = 1,
            type = ClassroomType.CLASSROOM,
            building = tornavias
        )
        classroomRepository.save(aulaA3)

        val aulaA4 = Classroom(
            code = "A4",
            name = "Aula 4",
            capacity = 50,
            floor = 1,
            type = ClassroomType.CLASSROOM,
            building = tornavias
        )
        classroomRepository.save(aulaA4)

        val aulaA8 = Classroom(
            code = "A8",
            name = "Aula 8",
            capacity = 50,
            floor = 1,
            type = ClassroomType.CLASSROOM,
            building = tornavias
        )
        classroomRepository.save(aulaA8)

        val aulaA9 = Classroom(
            code = "A9",
            name = "Aula 9",
            capacity = 50,
            floor = 1,
            type = ClassroomType.CLASSROOM,
            building = tornavias
        )
        classroomRepository.save(aulaA9)

        val aulaA11 = Classroom(
            code = "A11",
            name = "Aula 11",
            capacity = 50,
            floor = 1,
            type = ClassroomType.CLASSROOM,
            building = tornavias
        )
        classroomRepository.save(aulaA11)

        val aulaA12 = Classroom(
            code = "A12",
            name = "Aula 12",
            capacity = 50,
            floor = 1,
            type = ClassroomType.CLASSROOM,
            building = tornavias
        )
        classroomRepository.save(aulaA12)

        val aulaA13 = Classroom(
            code = "A13",
            name = "Aula 13",
            capacity = 50,
            floor = 1,
            type = ClassroomType.CLASSROOM,
            building = tornavias
        )
        classroomRepository.save(aulaA13)

        val aulaA14 = Classroom(
            code = "A14",
            name = "Aula 14",
            capacity = 50,
            floor = 1,
            type = ClassroomType.CLASSROOM,
            building = tornavias
        )
        classroomRepository.save(aulaA14)

        val aulaA15 = Classroom(
            code = "A15",
            name = "Aula 15",
            capacity = 50,
            floor = 1,
            type = ClassroomType.CLASSROOM,
            building = tornavias
        )
        classroomRepository.save(aulaA15)

        val aulaA16 = Classroom(
            code = "A16",
            name = "Aula 16",
            capacity = 50,
            floor = 1,
            type = ClassroomType.CLASSROOM,
            building = tornavias
        )
        classroomRepository.save(aulaA16)

        val aulaA17 = Classroom(
            code = "A17",
            name = "Aula 17",
            capacity = 50,
            floor = 1,
            type = ClassroomType.CLASSROOM,
            building = tornavias
        )
        classroomRepository.save(aulaA17)

        val aulaA18 = Classroom(
            code = "A18",
            name = "Aula 18",
            capacity = 50,
            floor = 1,
            type = ClassroomType.CLASSROOM,
            building = tornavias
        )
        classroomRepository.save(aulaA18)

        val aulaA19 = Classroom(
            code = "A19",
            name = "Aula 19",
            capacity = 50,
            floor = 1,
            type = ClassroomType.CLASSROOM,
            building = tornavias
        )
        classroomRepository.save(aulaA19)

        val aulaA20 = Classroom(
            code = "A20",
            name = "Aula 20",
            capacity = 50,
            floor = 1,
            type = ClassroomType.CLASSROOM,
            building = tornavias
        )
        classroomRepository.save(aulaA20)

        val aulaA21 = Classroom(
            code = "A21",
            name = "Aula 21",
            capacity = 50,
            floor = 1,
            type = ClassroomType.CLASSROOM,
            building = tornavias
        )
        classroomRepository.save(aulaA21)

        val aulaA22 = Classroom(
            code = "A22",
            name = "Aula 22",
            capacity = 50,
            floor = 1,
            type = ClassroomType.CLASSROOM,
            building = tornavias
        )
        classroomRepository.save(aulaA22)

        val aulaA23 = Classroom(
            code = "A23",
            name = "Aula 23",
            capacity = 50,
            floor = 1,
            type = ClassroomType.CLASSROOM,
            building = tornavias
        )
        classroomRepository.save(aulaA23)

        val aulaA24 = Classroom(
            code = "A24",
            name = "Aula 24",
            capacity = 50,
            floor = 1,
            type = ClassroomType.CLASSROOM,
            building = tornavias
        )
        classroomRepository.save(aulaA24)


        val laboQuimica = Classroom(
            code = "LQ",
            name = "Laboratorio de Quimica",
            capacity = 50,
            floor = 1,
            type = ClassroomType.LABORATORY,
            building = tornavias
        )
        classroomRepository.save(laboQuimica)

        val laboBiologia= Classroom(
            code = "LB",
            name = "Laboratorio de Biologia",
            capacity = 50,
            floor = 1,
            type = ClassroomType.LABORATORY,
            building = tornavias
        )
        classroomRepository.save(laboBiologia)
        //hay 2 labos?

        val laboFisica = Classroom(
            code = "LF",
            name = "Laboratorio de Física",
            capacity = 50,
            floor = 1,
            type = ClassroomType.LABORATORY,
            building = tornavias
        )
        classroomRepository.save(laboFisica)

        val laboNeuro = Classroom(
            code = "LN",
            name = "Laboratorio de Neuroingeniería",
            capacity = 50,
            floor = 1,
            type = ClassroomType.LABORATORY,
            building = tornavias
        )
        classroomRepository.save(laboNeuro)

        val laboElectronica2 = Classroom(
            code = "LE2",
            name = "Laboratorio de Electrónica 2",
            capacity = 50,
            floor = 1,
            type = ClassroomType.LABORATORY,
            building = tornavias
        )
        classroomRepository.save(laboElectronica2)

        val laboTermodinamica = Classroom(
            code = "LT",
            name = "Laboratorio de Termodinámica",
            capacity = 50,
            floor = 1,
            type = ClassroomType.LABORATORY,
            building = tornavias
        )
        classroomRepository.save(laboTermodinamica)

        val laboComputacion4 = Classroom(
            code = "LC4",
            name = "Laboratorio de Computación 4",
            capacity = 50,
            floor = 1,
            type = ClassroomType.LABORATORY,
            building = tornavias
        )
        classroomRepository.save(laboComputacion4)


        classroomRepository.save(cidi)
        classroomRepository.save(aulaA10)
        classroomRepository.save(aulaA28)
        classroomRepository.save(salaLectura)
        classroomRepository.save(carpaAuditorio)
        classroomRepository.save(aulaA6)
        classroomRepository.save(aulaA7)
        classroomRepository.save(aulaA5)

    }

    fun findBuildingByName(name: String) : Building {
        val allBuildings = buildingRepository.findAll()
        validateBuildingList(allBuildings)
        validateBuildingSearch(allBuildings,name)
        return allBuildings.find { it.name == name }!!
    }

    fun validateBuildingList(buildings: List<Building>) {
        if (buildings.isEmpty()) {
            throw NotFoundException("No hay edificios cargados")
        }
    }

    fun validateBuildingSearch(buildings: List<Building>, buildingName: String) {
        if (!buildings.map {it.name}.contains(buildingName)) {
            throw NotFoundException("No hay un edificio con el nombre indicado.")
        }
    }
}