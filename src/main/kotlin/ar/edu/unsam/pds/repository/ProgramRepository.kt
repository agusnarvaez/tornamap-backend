package ar.edu.unsam.pds.repository

import ar.edu.unsam.pds.models.Program
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import java.util.*

@RepositoryRestResource(exported = false)
interface ProgramRepository : JpaRepository<Program, UUID>{

    fun findByName(name: String): Program?
}