package ar.edu.unsam.pds.services

import ar.edu.unsam.pds.dto.request.ProgramRequestDto
import ar.edu.unsam.pds.exceptions.InternalServerError
import ar.edu.unsam.pds.exceptions.NotFoundException
import ar.edu.unsam.pds.models.Program
import ar.edu.unsam.pds.repository.ProgramRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class ProgramService(
    private val programRepository: ProgramRepository,
) {

    fun getAll(): List<Program> {
        return programRepository.findAllByOrderByNameAsc()
    }

    fun getById(programId: String): Program {
        val eventUUID = UUID.fromString(programId)
        val matchingProgram = programRepository.findById(eventUUID)
            .orElseThrow { NotFoundException("Program no encontrado para el uuid suministrado") }
        return matchingProgram
    }
    @Transactional(readOnly = true)
    fun getAllById(programsId: List<String>): List<Program> {
        val programsUUID = programsId.map {
            try {
                UUID.fromString(it)
            } catch (e: IllegalArgumentException) {
                throw InternalServerError("El id del programa no es un UUID valido")
            }
        }.toMutableList()

        return programRepository.findAllById(programsUUID)
    }

    fun create(program: ProgramRequestDto): UUID {
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

    fun update(idProgram: String, program: ProgramRequestDto): Program {
        val programToUpdate = getById(idProgram)
        programToUpdate.name = program.name
        programToUpdate.description = program.description
        programRepository.save(programToUpdate)
        return programToUpdate
    }

    fun delete(idProgram: String) {
        val programDeleted = getById(idProgram)
        programRepository.delete(programDeleted)
    }
}