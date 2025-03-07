package ar.edu.unsam.pds.repository

import ar.edu.unsam.pds.models.Period
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*


interface PeriodRepository: JpaRepository<Period, UUID> {
    @Query("""
        SELECT p FROM Period p
        WHERE p.title LIKE concat('%', :query, '%')
        OR p.description LIKE concat('%', :query, '%')
        ORDER BY 
        CASE 
            WHEN p.title LIKE concat('%', :query, '%') THEN 1
            ELSE 3
        END
    """)
    fun getAllBy(@Param("query") query: String): MutableList<Period>

    @Query("""
    SELECT periods FROM Program i
    JOIN i.periods periods
    JOIN i.admin admins
    WHERE admins.id = :#{#principal.user.id}
    AND (periods.title LIKE concat('%', :query, '%') 
         OR periods.description LIKE concat('%', :query, '%'))
    ORDER BY 
        CASE 
            WHEN periods.title LIKE concat('%', :query, '%') THEN 1
            ELSE 3
        END
    """
    )
    fun getAllByPrincipal(@Param("query")query: String, @Param("principal") principal: Principal): MutableList<Period>

    @Query(
        """
        SELECT COUNT(periods.id) = 1
        FROM Program i
        JOIN i.periods periods
        JOIN i.admin admins
        WHERE periods.id = :idPeriod AND admins.id = :#{#principal.user.id}
    """
    )
    fun isOwner(@Param("idPeriod") idPeriod: UUID, @Param("principal") principal: Principal): Boolean
}