package ar.edu.unsam.pds.bootstrap

import ar.edu.unsam.pds.models.Building
import ar.edu.unsam.pds.repository.BuildingRepository
import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile

@Component(value = "InitBuilding.beanName")
@Profile(value = ["dev", "prod", "test"])
class InitBuilding: BootstrapGeneric("Buildings") {
    @Autowired private lateinit var buildingRepository: BuildingRepository

    override fun doAfterPropertiesSet() {
        val tornavias = Building(name= "Tornavías").apply {
            buildingRepository.save(this)
        }

        val aularioN1 = Building(name= "Aulario Nave 1").apply {
            buildingRepository.save(this)
        }

        val aularioN2 = Building(name= "Aulario Nave 2").apply {
            buildingRepository.save(this)
        }
        val aularioN3 = Building(name= "Aulario Nave 3").apply {
            buildingRepository.save(this)
        }

        val aularioNA = Building(name= "Aulario Nave A").apply {
            buildingRepository.save(this)
        }
    }
}