package ar.edu.unsam.pds.repository

import ar.edu.unsam.pds.models.Classroom
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import java.util.*

@RepositoryRestResource(exported = false)
interface ClassroomRepository : JpaRepository<Classroom, UUID> {

    //Filtrar aulas según un edificio
    @Query("SELECT c FROM Classroom c WHERE c.building.id = :buildingId")
    fun findByBuildingId(@Param("buildingId") buildingId: UUID): List<Classroom>


}