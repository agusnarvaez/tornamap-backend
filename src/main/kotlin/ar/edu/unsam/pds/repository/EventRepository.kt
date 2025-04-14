package ar.edu.unsam.pds.repository

import ar.edu.unsam.pds.models.Course
import ar.edu.unsam.pds.models.Event
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import java.time.LocalDate
import java.util.*

@RepositoryRestResource(exported = false)
interface EventRepository : JpaRepository<Event, UUID> {
    fun findByName(name: String): Optional<Event>

    @EntityGraph(attributePaths = ["course", "course.programs", "schedules","schedules.classroom","schedules.classroom.building", "schedules.assignedUsers"])
    fun findBySchedules_IdIn(scheduleIds: List<UUID>): List<Event>

}