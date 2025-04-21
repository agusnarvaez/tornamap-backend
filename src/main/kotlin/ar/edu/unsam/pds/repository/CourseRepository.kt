package ar.edu.unsam.pds.repository

import ar.edu.unsam.pds.models.Course
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import java.util.*

@RepositoryRestResource(exported = false)
interface CourseRepository : JpaRepository<Course, UUID> {

    @EntityGraph(attributePaths = ["programs", "events", "events.schedules", "events.schedules.assignedUsers", "events.schedules.classroom", "events.schedules.classroom.building"])
    override fun findById(id: UUID): Optional<Course>

    fun findCourseByName(courseName: String): Course?

    @EntityGraph(attributePaths = ["programs", "events", "events.schedules", "events.schedules.assignedUsers"])
    fun findAllByOrderByNameAsc(): List<Course>

    @EntityGraph(attributePaths = ["programs", "events", "events.schedules", "events.schedules.assignedUsers"])
    @Query("""
        SELECT DISTINCT c FROM Course c 
        LEFT JOIN c.programs p
        LEFT JOIN c.events e
        LEFT JOIN e.schedules s
        LEFT JOIN s.assignedUsers u
        WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :query, '%'))
        OR LOWER(p.name) LIKE LOWER(CONCAT('%', :query, '%'))
        OR LOWER(CONCAT(u.name, ' ', u.lastName)) LIKE LOWER(CONCAT('%', :query, '%'))
    """)
    fun searchByNameOrProgramOrProfessor(@Param("query") query: String): List<Course>

}