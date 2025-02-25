package ar.edu.unsam.pds.repository

import ar.edu.unsam.pds.dto.response.ScheduleResponseDto
import ar.edu.unsam.pds.models.Course
import ar.edu.unsam.pds.models.Schedule
import ar.edu.unsam.pds.security.models.Principal
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import java.util.*

@RepositoryRestResource(exported = false)
interface ScheduleRepository : JpaRepository<Schedule, UUID>{

    @Query("""
    SELECT c FROM Schedule c
    WHERE FUNCTION('TEXT', c.weekDay) LIKE concat('%', :query, '%')
    OR FUNCTION('DATE_FORMAT', c.date, '%Y-%m-%d') LIKE concat('%', :query, '%')
""")
    fun getAllBy(@Param("query") query: String): MutableList<Schedule>


    @Query("""
    SELECT schedule FROM Event i
    JOIN i.schedule schedule
    JOIN i.admin admins
    WHERE admins.id = :#{#principal.user.id}
    AND (
        FUNCTION('TEXT', schedule.weekDay) LIKE concat('%', :query, '%')
        OR FUNCTION('DATE_FORMAT', schedule.date, '%Y-%m-%d') LIKE concat('%', :query, '%')
    )
""")
    fun getByPrincipal(@Param("query") query: String, @Param("principal") principal: Principal): Schedule?

/*en principio por lo que vi cada event tenia un solo schedule*/

    @Query("""
        SELECT COUNT(schedules.id) = 1
        FROM Event i
        JOIN i.schedules schedules
        JOIN i.admin admins
        WHERE schedules.id = :idSchedule AND admins.id = :#{#principal.user.id}
    """)
    fun isOwner(@Param("idSchedule") idSchedule: UUID, @Param("principal") principal: Principal): Boolean


}