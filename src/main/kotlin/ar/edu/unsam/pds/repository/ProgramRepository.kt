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
    @Query(
        """
        SELECT p FROM Program p
        WHERE p.name LIKE concat('%', :query, '%')
        OR p.description LIKE concat('%', :query, '%')
    """
    )
    fun getAllBy(@Param("query") query: String?): MutableList<Program>

    @Query(
        """
        SELECT p FROM Program p JOIN p.admin admins
        WHERE admins.id = :#{#principal.user.id}
        AND p.name LIKE concat('%', :query, '%')
        OR p.description LIKE concat('%', :query, '%')
    """
    )
    fun getAllByPrincipal(@Param("query")query: String?, @Param("principal") principal: Principal): MutableList<Program>

    @Query(
        """
        SELECT p FROM Program p
        JOIN p.courses c
        WHERE c.id = :courseId
    """
    )
    fun findByCourseId(@Param("courseId") courseId: UUID): Program

    @Query(
        """
        SELECT COUNT(p.id) = 1
        FROM Program p
        JOIN p.admin admins
        WHERE p.id = :idProgram AND admins.id = :#{#principal.user.id}
    """
    )
    fun isOwner(@Param("idProgram") idCourse: UUID, @Param("principal") principal: Principal): Boolean

    @Query(
        """
        SELECT CASE WHEN COUNT(users) = 0 THEN true ELSE false END
        FROM Program p
        JOIN p.courses courses
        JOIN courses.events events
        LEFT JOIN events.users users
        WHERE p.id = :idProgram
    """
    )
    fun isDeletable(@Param("idProgram") id: UUID): Boolean
}