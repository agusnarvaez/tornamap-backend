package ar.edu.unsam.pds.repository

import ar.edu.unsam.pds.models.Building
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

@RepositoryRestResource(exported = false)
interface BuildingRepository : JpaRepository<Building, UUID>