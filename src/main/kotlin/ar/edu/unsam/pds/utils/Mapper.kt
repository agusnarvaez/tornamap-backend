package ar.edu.unsam.pds.utils

import ar.edu.unsam.pds.dto.response.ScheduleResponseDto
import ar.edu.unsam.pds.dto.response.AssignmentResponseDto
import ar.edu.unsam.pds.dto.response.SubscribeResponseDto
import ar.edu.unsam.pds.dto.response.UserResponseDto
import ar.edu.unsam.pds.dto.response.InstitutionResponseDto
import ar.edu.unsam.pds.dto.response.InstitutionDetailResponseDto
import ar.edu.unsam.pds.dto.response.CourseResponseDto
import ar.edu.unsam.pds.dto.response.CourseDetailResponseDto
import ar.edu.unsam.pds.models.Assignment
import ar.edu.unsam.pds.models.Course
import ar.edu.unsam.pds.models.Institution
import ar.edu.unsam.pds.models.User
import ar.edu.unsam.pds.models.Schedule

import java.time.LocalDate

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
            institution.courses.map { buildCourseDto(it) }.toMutableSet()
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
            course.assignments.map { buildAssignmentDto(it) }.toMutableSet()
        )
    }

    fun buildAssignmentDto(assignment: Assignment): AssignmentResponseDto {
        return AssignmentResponseDto(
            assignment.id,
            assignment.quotas,
            assignment.quantityAvailable(),
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
            schedule.recurrenceWeeks.name,
            schedule.generateSchedule()
        )
    }

    fun patchUser(user: User, userDetail: UserResponseDto): User {
        userDetail.name.let { user.name = it }
        userDetail.lastName.let { user.lastName = it }
        userDetail.email.let { user.email = it }
        userDetail.image.let { user.image = it }
        return user
    }

    fun subscribeResponse(idUser: String, idAssignment: String): SubscribeResponseDto {
        return SubscribeResponseDto(idUser, idAssignment, "Suscripción exitosa", LocalDate.now())
    }

    fun unsubscribeResponse(idUser: String, idAssignment: String): SubscribeResponseDto {
        return SubscribeResponseDto(idUser, idAssignment, "Desuscripción exitosa", LocalDate.now())
    }
}