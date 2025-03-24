package ar.edu.unsam.pds.repository

import ar.edu.unsam.pds.models.Course
import ar.edu.unsam.pds.models.Period
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import ar.edu.unsam.pds.security.models.Principal
import java.util.*


interface PeriodRepository: JpaRepository<Period, UUID>