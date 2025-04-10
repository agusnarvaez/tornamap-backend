package ar.edu.unsam.pds.repository

import ar.edu.unsam.pds.models.Event
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import java.time.LocalDate
import java.util.*

@RepositoryRestResource(exported = false)
interface EventRepository : JpaRepository<Event, UUID> {
    fun findByName(name: String): Optional<Event>

    fun findAllByOrderByNameAsc(): List<Event>

/*
    fun findByClassroomIDAndDate(
        @Param("classroomID") classroomID: String,
        @Param("eventDate") eventDate: LocalDate
    ): List<Event>
*/

    @Query("""
    SELECT e FROM Event e
    JOIN e.course c
    JOIN c.programs p
    LEFT JOIN e.users prof
    WHERE 
        (:searchQuery IS NULL OR 
         LOWER(c.name) LIKE LOWER(CONCAT('%', :searchQuery, '%')) OR
         LOWER(p.name) LIKE LOWER(CONCAT('%', :searchQuery, '%')) OR
         LOWER(prof.name) LIKE LOWER(CONCAT('%', :searchQuery, '%')) OR
         LOWER(prof.lastName) LIKE LOWER(CONCAT('%', :searchQuery, '%')))
    ORDER BY c.name ASC
""")
    fun findByQuery(
        @Param("searchQuery") searchQuery: String
    ): List<Event>
//    {
//        this.findByName(searchQuery)
//        this.findByProfessor(searchQuery)
//        this.findByCourse(searchQuery)
//        courseService.findProgram(searchQuery)
//    }

}