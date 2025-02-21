package ar.edu.unsam.pds.mappers

import ar.edu.unsam.pds.dto.response.AssignmentResponseDto
import ar.edu.unsam.pds.dto.response.AssignmentStatsResponseDto
import ar.edu.unsam.pds.models.Assignment
import ar.edu.unsam.pds.models.Institution
import java.time.LocalDate

object AssignmentMapper {

    fun buildAssignmentDto(assignment: Assignment): AssignmentResponseDto {
        return AssignmentResponseDto(
            id = assignment.id.toString(),
            quotas = assignment.quotas,
            quantityAvailable = assignment.quantityAvailable(),
            isActive = assignment.isActive,
            price = assignment.price,
            schedule = ScheduleMapper.buildScheduleDto(assignment.schedule)
        )
    }

    fun buildAssignmentStatsDto(assignment: Assignment): AssignmentStatsResponseDto {
        return AssignmentStatsResponseDto(
            id = assignment.id.toString(),
            quotas = assignment.quotas,
            quantityAvailable = assignment.quantityAvailable(),
            isActive = assignment.isActive,
            price = assignment.price,
            schedule = ScheduleMapper.buildScheduleDto(assignment.schedule),
            name = assignment.name(),
            subscribers = assignment.subscribedUsers.map { UserMapper.buildUserDto(it) }.toMutableSet(),
            totalIncome = assignment.totalIncome(),
            status = assignment.status()
        )
    }
}