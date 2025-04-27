package ar.edu.unsam.pds.services

import ar.edu.unsam.pds.dto.request.ProgramRequestDto
import ar.edu.unsam.pds.exceptions.NotFoundException
import ar.edu.unsam.pds.models.Program
import ar.edu.unsam.pds.repository.ProgramRepository
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ProgramService(
    private val programRepository: ProgramRepository,
) {

    fun getAll(): List<Program> {
        return programRepository.findAllByOrderByNameAsc()
    }

    fun getProgram(programId: String): Program {
        val eventUUID = UUID.fromString(programId)
        val matchingProgram = programRepository.findById(eventUUID)
            .orElseThrow { NotFoundException("Program no encontrado para el uuid suministrado") }
        return matchingProgram
    }

    fun createProgram(program: ProgramRequestDto): UUID {
        if (programRepository.existsByName(program.name)) {
            throw NotFoundException("Ya existe un programa con el nombre ${program.name}")
        }
        val newProgram = Program(
            name = program.name,
            description = program.description,
        )
        programRepository.save(newProgram)
        return newProgram.id
    }

    fun updateProgram(idProgram: String, program: ProgramRequestDto): Program {
        val programToUpdate = getProgram(idProgram)
        programToUpdate.name = program.name
        programToUpdate.description = program.description
        programRepository.save(programToUpdate)
        return programToUpdate
    }

    fun deleteProgram(idProgram: String) {
        val programDeleted = getProgram(idProgram)
        programRepository.delete(programDeleted)
    }
}