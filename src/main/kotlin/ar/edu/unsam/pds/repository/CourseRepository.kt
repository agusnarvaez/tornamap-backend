package ar.edu.unsam.pds.repository

import ar.edu.unsam.pds.models.Course
import ar.edu.unsam.pds.models.Event
import ar.edu.unsam.pds.models.Program
import ar.edu.unsam.pds.security.models.Principal
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import java.time.LocalDate
import java.util.*

@RepositoryRestResource(exported = false)
interface CourseRepository : JpaRepository<Course, UUID> {
    fun findByName(name: String): MutableList<Course>

    fun findAllByOrderByNameAsc(): List<Course>

    fun findByProgramsNameContainingIgnoreCase(name: String): List<Course>

    fun findByNameContainingIgnoreCase(name: String): List<Course>

}