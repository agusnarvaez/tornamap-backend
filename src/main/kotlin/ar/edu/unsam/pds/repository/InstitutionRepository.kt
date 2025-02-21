package ar.edu.unsam.pds.repository

import ar.edu.unsam.pds.models.Program
import ar.edu.unsam.pds.security.models.Principal
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import java.util.*

@RepositoryRestResource(exported = false)
interface InstitutionRepository : JpaRepository<Program, UUID> {
    @Query(
        """
        SELECT i FROM Program i
        WHERE i.name LIKE concat('%', :query, '%')
        OR i.description LIKE concat('%', :query, '%')
        OR i.category LIKE concat('%', :query, '%')
    """
    )
    fun getAllBy(@Param("query") query: String): MutableList<Program>

    @Query(
        """
        SELECT i FROM Program i JOIN i.admin admins
        WHERE admins.id = :#{#principal.user.id}
        AND (i.name LIKE concat('%', :query, '%')
        OR i.description LIKE concat('%', :query, '%')
        OR i.category LIKE concat('%', :query, '%'))
    """
    )
    fun getAllByPrincipal(@Param("query")query: String, @Param("principal") principal: Principal): MutableList<Program>

    @Query(
        """
        SELECT i FROM Program i
        JOIN i.courses c
        WHERE c.id = :courseId
    """
    )
    fun findByCourseId(@Param("courseId") courseId: UUID): Program

    @Query(
        """
        SELECT COUNT(i.id) = 1
        FROM Program i
        JOIN i.admin admins
        WHERE i.id = :idInstitution AND admins.id = :#{#principal.user.id}
    """
    )
    fun isOwner(@Param("idInstitution") idCourse: UUID, @Param("principal") principal: Principal): Boolean

    @Query(
        """
        SELECT CASE WHEN COUNT(users) = 0 THEN true ELSE false END
        FROM Program i
        JOIN i.courses courses
        JOIN courses.assignments assignments
        LEFT JOIN assignments.subscribedUsers users
        WHERE i.id = :idInstitution
    """
    )
    fun isDeletable(@Param("idInstitution") id: UUID): Boolean
}