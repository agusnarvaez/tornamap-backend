package ar.edu.unsam.pds.utils

import ar.edu.unsam.pds.dto.response.*
import ar.edu.unsam.pds.models.*

object Mapper {
    fun buildUserDto(user: User): UserResponseDto {
        return UserResponseDto(
            user.name,
            user.lastName,
            user.email,
            user.image,
            user.id
        )
    }

    fun buildInstitutionDto(institution: Institution): InstitutionResponseDto {
        return InstitutionResponseDto(
            institution.id,
            institution.name,
            institution.description,
            institution.category,
            institution.image
        )
    }

    fun buildInstitutionDetailDto(institution: Institution): InstitutionDetailResponseDto {
        return InstitutionDetailResponseDto(
            institution.id,
            institution.name,
            institution.description,
            institution.category,
            institution.image,
            institution.courses
        )
    }

    fun buildCourseDto(course: Course): CourseResponseDto {
        return CourseResponseDto(
            course.id,
            course.title,
            course.description,
            course.category,
            course.image
        )
    }

    fun buildCourseDetailDto(course: Course): CourseDetailResponseDto {
        return CourseDetailResponseDto(
            course.id,
            course.title,
            course.description,
            course.category,
            course.image,
            course.assignments
        )
    }

    fun buildAssignmentDto(assignment: Assignment): AssignmentResponseDto {
        return AssignmentResponseDto(
            assignment.id,
            assignment.quotas,
            assignment.isActive,
            assignment.price,
            buildScheduleDto(assignment.schedule)

            )
    }

    fun buildScheduleDto(schedule: Schedule): ScheduleResponseDto {
        return ScheduleResponseDto(
            schedule.days,
            schedule.startTime,
            schedule.endTime,
            schedule.startDate,
            schedule.endDate,
            schedule.recurrenceWeeks,
            schedule.generateSchedule()
        )
    }
}