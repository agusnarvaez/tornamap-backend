package ar.edu.unsam.pds.repository

import ar.edu.unsam.pds.models.Event
import ar.edu.unsam.pds.security.models.Principal
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import java.util.*

@RepositoryRestResource(exported = false)
interface EventRepository : JpaRepository<Event, UUID>{


    @Query(
        """
        SELECT COUNT(course.id) = 1
            FROM Program i
            JOIN i.courses course
            JOIN course.events events
            JOIN i.admin admins
            WHERE events.id = :eventId AND admins.id = :#{#principal.user.id}
        """
    )

    fun isOwner(@Param("eventId") courseId: UUID, @Param("principal") principal: Principal) : Boolean

}