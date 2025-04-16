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

        val aulaT28 = Classroom(
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

        val aulaT10 = Classroom(
            code = "A10",
            name = "Aula 10",
            capacity = 50,
            floor = 0,
            type = ClassroomType.LABORATORY,
            building = tornavias
        )

        val aulaT6 = Classroom(
            code = "A6",
            name = "Aula 6",
            capacity = 50,
            floor = 0,
            type=  ClassroomType.CLASSROOM,
            building = tornavias
        )

//A6 Bis¿?

        val aulaT7 = Classroom(
            code = "A7",
            name = "Aula 7",
            capacity = 50,
            floor = 0,
            type=  ClassroomType.CLASSROOM,
            building = tornavias
        )

        val aulaT5 = Classroom(
            code = "A5",
            name = "Aula 5",
            capacity = 50,
            floor = 0,
            type=  ClassroomType.CLASSROOM,
            building = tornavias
        )

        val aulaT1 = Classroom(
            code = "A1",
            name = "Aula 1",
            capacity = 50,
            floor = 0,
            type = ClassroomType.CLASSROOM,
            building = tornavias
        )
        classroomRepository.save(aulaT1)

        val aulaT2 = Classroom(
            code = "A2",
            name = "Aula 2",
            capacity = 50,
            floor = 0,
            type = ClassroomType.CLASSROOM,
            building = tornavias
        )
        classroomRepository.save(aulaT2)

        val aulaT3 = Classroom(
            code = "A3",
            name = "Aula 3",
            capacity = 50,
            floor = 0,
            type = ClassroomType.CLASSROOM,
            building = tornavias
        )
        classroomRepository.save(aulaT3)

        val aulaT4 = Classroom(
            code = "A4",
            name = "Aula 4",
            capacity = 50,
            floor = 0,
            type = ClassroomType.CLASSROOM,
            building = tornavias
        )
        classroomRepository.save(aulaT4)

        val aulaT8 = Classroom(
            code = "A8",
            name = "Aula 8",
            capacity = 50,
            floor = 0,
            type = ClassroomType.CLASSROOM,
            building = tornavias
        )
        classroomRepository.save(aulaT8)

        val aulaT9 = Classroom(
            code = "A9",
            name = "Aula 9",
            capacity = 50,
            floor = 0,
            type = ClassroomType.CLASSROOM,
            building = tornavias
        )
        classroomRepository.save(aulaT9)

        val aulaT11 = Classroom(
            code = "A11",
            name = "Aula 11",
            capacity = 50,
            floor = 0,
            type = ClassroomType.CLASSROOM,
            building = tornavias
        )
        classroomRepository.save(aulaT11)

        val aulaT12 = Classroom(
            code = "A12",
            name = "Aula 12",
            capacity = 50,
            floor = 0,
            type = ClassroomType.CLASSROOM,
            building = tornavias
        )
        classroomRepository.save(aulaT12)

        val aulaT13 = Classroom(
            code = "A13",
            name = "Aula 13",
            capacity = 50,
            floor = 0,
            type = ClassroomType.CLASSROOM,
            building = tornavias
        )
        classroomRepository.save(aulaT13)

        val aulaT14 = Classroom(
            code = "A14",
            name = "Aula 14",
            capacity = 50,
            floor = 0,
            type = ClassroomType.CLASSROOM,
            building = tornavias
        )
        classroomRepository.save(aulaT14)

        val aulaT15 = Classroom(
            code = "A15",
            name = "Aula 15",
            capacity = 50,
            floor = 0,
            type = ClassroomType.CLASSROOM,
            building = tornavias
        )
        classroomRepository.save(aulaT15)

        val aulaT16 = Classroom(
            code = "A16",
            name = "Aula 16",
            capacity = 50,
            floor = 0,
            type = ClassroomType.CLASSROOM,
            building = tornavias
        )
        classroomRepository.save(aulaT16)

        val aulaT17 = Classroom(
            code = "A17",
            name = "Aula 17",
            capacity = 50,
            floor = 0,
            type = ClassroomType.CLASSROOM,
            building = tornavias
        )
        classroomRepository.save(aulaT17)

        val aulaT18 = Classroom(
            code = "A18",
            name = "Aula 18",
            capacity = 50,
            floor = 0,
            type = ClassroomType.CLASSROOM,
            building = tornavias
        )
        classroomRepository.save(aulaT18)

        val aulaT19 = Classroom(
            code = "A19",
            name = "Aula 19",
            capacity = 50,
            floor = 0,
            type = ClassroomType.CLASSROOM,
            building = tornavias
        )
        classroomRepository.save(aulaT19)

        val aulaT20 = Classroom(
            code = "A20",
            name = "Aula 20",
            capacity = 50,
            floor = 0,
            type = ClassroomType.CLASSROOM,
            building = tornavias
        )
        classroomRepository.save(aulaT20)

        val aulaT21 = Classroom(
            code = "A21",
            name = "Aula 21",
            capacity = 50,
            floor = 0,
            type = ClassroomType.CLASSROOM,
            building = tornavias
        )
        classroomRepository.save(aulaT21)

        val aulaT22 = Classroom(
            code = "A22",
            name = "Aula 22",
            capacity = 50,
            floor = 0,
            type = ClassroomType.CLASSROOM,
            building = tornavias
        )
        classroomRepository.save(aulaT22)

        val aulaT23 = Classroom(
            code = "A23",
            name = "Aula 23",
            capacity = 50,
            floor = 0,
            type = ClassroomType.CLASSROOM,
            building = tornavias
        )
        classroomRepository.save(aulaT23)

        val aulaT24 = Classroom(
            code = "A24",
            name = "Aula 24",
            capacity = 50,
            floor = 0,
            type = ClassroomType.CLASSROOM,
            building = tornavias
        )
        classroomRepository.save(aulaT24)

        val laboQuimica = Classroom(
            code = "LQ",
            name = "Laboratorio de Quimica",
            capacity = 50,
            floor = 0,
            type = ClassroomType.LABORATORY,
            building = tornavias
        )
        classroomRepository.save(laboQuimica)

        val laboBiologia= Classroom(
            code = "LB",
            name = "Laboratorio de Biologia",
            capacity = 50,
            floor = 0,
            type = ClassroomType.LABORATORY,
            building = tornavias
        )
        classroomRepository.save(laboBiologia)

        val laboFisica = Classroom(
            code = "LF",
            name = "Laboratorio de Física",
            capacity = 50,
            floor = 0,
            type = ClassroomType.LABORATORY,
            building = tornavias
        )
        classroomRepository.save(laboFisica)

        val laboNeuro = Classroom(
            code = "LN",
            name = "Laboratorio de Neuroingeniería",
            capacity = 50,
            floor = 0,
            type = ClassroomType.LABORATORY,
            building = tornavias
        )
        classroomRepository.save(laboNeuro)

        val laboElectronica2 = Classroom(
            code = "LE2",
            name = "Laboratorio de Electrónica 2",
            capacity = 50,
            floor = 0,
            type = ClassroomType.LABORATORY,
            building = tornavias
        )
        classroomRepository.save(laboElectronica2)

        val laboTermodinamica = Classroom(
            code = "LT",
            name = "Laboratorio de Termodinámica",
            capacity = 50,
            floor = 0,
            type = ClassroomType.LABORATORY,
            building = tornavias
        )
        classroomRepository.save(laboTermodinamica)

        val laboComputacion4 = Classroom(
            code = "LC4",
            name = "Laboratorio de Computación 4",
            capacity = 50,
            floor = 0,
            type = ClassroomType.LABORATORY,
            building = tornavias
        )
        classroomRepository.save(laboComputacion4)

        val  aulaT30= Classroom(
            code = "A30",
            name = "Aula 30",
            capacity = 50,
            floor = 1,
            type = ClassroomType.CLASSROOM,
            building = tornavias
        )
        classroomRepository.save(aulaT30)

        val  aulaT31= Classroom(
            code = "A31",
            name = "Aula 31",
            capacity = 50,
            floor = 1,
            type = ClassroomType.CLASSROOM,
            building = tornavias
        )
        classroomRepository.save(aulaT31)

        val  aulaT32= Classroom(
            code = "A32",
            name = "Aula 32",
            capacity = 50,
            floor = 1,
            type = ClassroomType.CLASSROOM,
            building = tornavias
        )
        classroomRepository.save(aulaT32)

        val  aulaT33= Classroom(
            code = "A33",
            name = "Aula 33",
            capacity = 50,
            floor = 1,
            type = ClassroomType.CLASSROOM,
            building = tornavias
        )
        classroomRepository.save(aulaT33)

        val  aulaT34= Classroom(
            code = "A34",
            name = "Aula 34",
            capacity = 50,
            floor = 1,
            type = ClassroomType.CLASSROOM,
            building = tornavias
        )
        classroomRepository.save(aulaT34)

        val aulaT35= Classroom(
            code = "A35",
            name = "Aula 35",
            capacity = 50,
            floor = 1,
            type = ClassroomType.CLASSROOM,
            building = tornavias
        )
        classroomRepository.save(aulaT35)

        val laboComputacion2= Classroom(
            code = "LC2",
            name = "Laboratorio de Computacion 2",
            capacity = 50,
            floor = 1,
            type = ClassroomType.LABORATORY,
            building = tornavias
        )
        classroomRepository.save(laboComputacion2)

        val laboComputacion3 = Classroom(
            code = "LC3",
            name = "Laboratorio de Computación 3",
            capacity = 50,
            floor = 1,
            type = ClassroomType.LABORATORY,
            building = tornavias
        )
        classroomRepository.save(laboComputacion3)

        val laboImagenes = Classroom(
            code = "LI",
            name = "Laboratorio de Imágenes",
            capacity = 50,
            floor = 1,
            type = ClassroomType.LABORATORY,
            building = tornavias
        )
        classroomRepository.save(laboImagenes)

        val laboOptica = Classroom(
            code = "LO",
            name = "Laboratorio de Óptica",
            capacity = 50,
            floor = 1,
            type = ClassroomType.LABORATORY,
            building = tornavias
        )
        classroomRepository.save(laboOptica)

        val laboBiomedica = Classroom(
            code = "LBM",
            name = "Laboratorio de Biomédica",
            capacity = 50,
            floor = 1,
            type = ClassroomType.LABORATORY,
            building = tornavias
        )
        classroomRepository.save(laboBiomedica)

        val laboElectronica1 = Classroom(
            code = "LE1",
            name = "Laboratorio de Electrónica 1",
            capacity = 50,
            floor = 1,
            type = ClassroomType.LABORATORY,
            building = tornavias
        )
        classroomRepository.save(laboElectronica1)

        val laboElectronica3 = Classroom(
            code = "LE3",
            name = "Laboratorio de Electrónica 3",
            capacity = 50,
            floor = 1,
            type = ClassroomType.LABORATORY,
            building = tornavias
        )
        classroomRepository.save(laboElectronica3)

        val laboElectronica4 = Classroom(
            code = "LE4",
            name = "Laboratorio de Electrónica 4",
            capacity = 50,
            floor = 1,
            type = ClassroomType.LABORATORY,
            building = tornavias
        )
        classroomRepository.save(laboElectronica4)

        val laboCienciasHumanas = Classroom(
            code = "LCH",
            name = "Laboratorio de Ciencias Humanas",
            capacity = 50,
            floor = 1,
            type = ClassroomType.LABORATORY,
            building = tornavias
        )
        classroomRepository.save(laboCienciasHumanas)

        classroomRepository.save(cidi)
        classroomRepository.save(aulaT10)
        classroomRepository.save(aulaT28)
        classroomRepository.save(salaLectura)
        classroomRepository.save(carpaAuditorio)
        classroomRepository.save(aulaT6)
        classroomRepository.save(aulaT7)
        classroomRepository.save(aulaT5)

        val aulario= findBuildingByName("Aulario")




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