package ar.edu.unsam.pds.repository

import ar.edu.unsam.pds.models.Period
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import java.util.*

@RepositoryRestResource(exported = false)
interface PeriodRepository: JpaRepository<Period, UUID> {
    fun findAllByOrderByStartDateDesc(): List<Period>
}