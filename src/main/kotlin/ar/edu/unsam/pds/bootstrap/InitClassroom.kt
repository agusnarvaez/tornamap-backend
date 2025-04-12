package ar.edu.unsam.pds.bootstrap

import ar.edu.unsam.pds.exceptions.NotFoundException
import ar.edu.unsam.pds.models.Building
import ar.edu.unsam.pds.models.Classroom
import ar.edu.unsam.pds.models.enums.ClassroomType
import ar.edu.unsam.pds.repository.BuildingRepository
import ar.edu.unsam.pds.repository.ClassroomRepository
import java.util.UUID
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

        val labo1 = Classroom(
            name = "Laboratorio de Computación 1",
            capacity = 50,
            floor = 1,
            type = ClassroomType.LABORATORY,
            building = tornavias
        )

        val aulaA28 = Classroom(
            name = "Aula A28",
            capacity = 30,
            floor = 2,
            type = ClassroomType.CLASSROOM,
            building = tornavias
        )

        val salaLectura = Classroom(
            name = "Sala de Lectura",
            capacity = 20,
            floor = 1,
            type = ClassroomType.LECTURE,
            building = tornavias
        )

        val carpaAuditorio = Classroom(
            name = "Carpa Auditorio",
            capacity = 100,
            floor = 0,
            type = ClassroomType.AUDITORIUM,
            building = tornavias
        )

        classroomRepository.save(labo1)
        classroomRepository.save(aulaA28)
        classroomRepository.save(salaLectura)
        classroomRepository.save(carpaAuditorio)
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