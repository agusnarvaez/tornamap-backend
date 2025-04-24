package ar.edu.unsam.pds.repository

import ar.edu.unsam.pds.models.Program
import ar.edu.unsam.pds.security.models.Principal
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import java.util.*

@RepositoryRestResource(exported = false)
interface ProgramRepository : JpaRepository<Program, UUID> {
    fun findAllByOrderByNameAsc(): MutableList<Program>

    fun existsByName(name: String): Boolean
}