package ar.edu.unsam.pds.repository

import ar.edu.unsam.pds.models.Event
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import java.util.*

@RepositoryRestResource(exported = false)
interface EventRepository : JpaRepository<Event, UUID> {

    @EntityGraph(attributePaths = ["course", "course.programs", "schedules","schedules.classroom","schedules.classroom.building", "schedules.assignedUsers"])
    override fun findById(id: UUID): Optional<Event>

    fun findByName(name: String): Optional<Event>

    @EntityGraph(attributePaths = ["course", "course.programs", "schedules","schedules.classroom","schedules.classroom.building", "schedules.assignedUsers"])
    fun findBySchedules_IdIn(scheduleIds: List<UUID>): List<Event>

}