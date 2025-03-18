package ar.edu.unsam.pds.repository

import ar.edu.unsam.pds.models.Course
import ar.edu.unsam.pds.models.Period
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import ar.edu.unsam.pds.security.models.Principal
import java.util.*


interface PeriodRepository: JpaRepository<Period, UUID> {
    @Query("""
        SELECT p FROM Period p
        WHERE  FUNCTION('TEXT',p.startDate) LIKE concat('%', :query, '%')
        ORDER BY FUNCTION('TEXT',p.startDate)
    """)
    fun getAllBy(@Param("query") query: String): MutableList<Period>

    @Query("""
    SELECT period FROM Event i
    JOIN i.period period
    JOIN i.admin admins
    WHERE admins.id = :#{#principal.user.id} 
    AND (
           FUNCTION('TEXT', period.startDate) LIKE concat('%', :query, '%')
           )
    ORDER BY FUNCTION('TEXT',period.startDate)
    """
    )
    fun getAllByPrincipal(@Param("query")query: String, @Param("principal") principal: Principal): MutableList<Period>

    @Query(
        """
        SELECT COUNT(period.id) = 1
        FROM Event i
        JOIN i.period period
        JOIN i.admin admins
        WHERE period.id = :idPeriod AND admins.id = :#{#principal.user.id}
    """
    )
    fun isOwner(@Param("idPeriod") idPeriod: UUID, @Param("principal") principal: Principal): Boolean


    @Query("""
    SELECT p FROM Period p
    JOIN Event e ON e.period = p
    WHERE e.id = :idEvent
""")
    fun findByEventId(@Param("idEvent") id: UUID): Period

}