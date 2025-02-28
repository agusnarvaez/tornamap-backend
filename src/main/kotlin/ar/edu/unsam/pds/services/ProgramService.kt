package ar.edu.unsam.pds.services

import ar.edu.unsam.pds.dto.request.ProgramRequestDto
import ar.edu.unsam.pds.dto.response.ProgramDetailResponseDto
import ar.edu.unsam.pds.dto.response.ProgramResponseDto
import ar.edu.unsam.pds.exceptions.NotFoundException
import ar.edu.unsam.pds.exceptions.PermissionDeniedException
import ar.edu.unsam.pds.exceptions.ValidationException
import ar.edu.unsam.pds.mappers.ProgramMapper
import ar.edu.unsam.pds.models.Program
import ar.edu.unsam.pds.repository.ProgramRepository
import ar.edu.unsam.pds.repository.UserRepository
import ar.edu.unsam.pds.security.models.Principal
import ar.edu.unsam.pds.security.repository.PrincipalRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class ProgramService(
    private val programRepository: ProgramRepository,
    private val principalRepository: PrincipalRepository,
    private val userRepository: UserRepository,
    private val imageService: StorageService
) {

    fun getAll(query: String?): List<ProgramResponseDto> {
        val programs = programRepository.getAllBy(query)
        return programs.map { ProgramMapper.buildProgramDto(it) }
    }

    fun getAllByPrincipal(query: String?, principal: Principal): List<ProgramResponseDto> {
        val programs = programRepository.getAllByPrincipal(query, principal)
        return programs.map { ProgramMapper.buildProgramDto(it) }
    }

    fun getProgram(programId: String): ProgramDetailResponseDto {
        val program = findProgramById(programId)
        return ProgramMapper.buildProgramDetailDto(program)
    }

    fun findProgramById(programId: String): Program {
        val uuid = UUID.fromString(programId)
        return programRepository.findById(uuid).orElseThrow {
            NotFoundException("Carrera no encontrada para el uuid suministrado")
        }
    }

    fun findProgramByCourseId(courseId: UUID): Program {
        return programRepository.findByCourseId(courseId)
    }

    @Transactional
    fun createProgram(program: ProgramRequestDto, principal: Principal): ProgramResponseDto {
        principal.getUser().isAdmin = true
        val imageName = imageService.savePublic(program.file)
        val newProgram = Program(
            name = program.name,
            description = program.description
        ).apply {
            addAdmin(principal.getUser())
        }
        userRepository.save(principal.getUser())
        principalRepository.save(principal)
        programRepository.save(newProgram)

        return ProgramMapper.buildProgramDto(newProgram)
    }

    @Transactional
    fun deleteProgram(programId: String, principal: Principal) {
        val uuid = UUID.fromString(programId)

        if (!programRepository.isOwner(uuid, principal)) {
            throw PermissionDeniedException("No se puede borrar una carrera de la cual no se es propietario")
        }

        if (!programRepository.isDeletable(UUID.fromString(programId))) {
            throw ValidationException("No se puede eliminar una carrera con usuarios inscriptos")
        }

        val program = programRepository.findById(uuid).orElseThrow {
            NotFoundException("Carrera no encontrada")
        }
    }
}