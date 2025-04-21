package ar.edu.unsam.pds.services

import ar.edu.unsam.pds.repository.ProgramRepository
import ar.edu.unsam.pds.repository.UserRepository
import ar.edu.unsam.pds.security.repository.PrincipalRepository
import org.springframework.stereotype.Service

@Service
class ProgramService(
    private val programRepository: ProgramRepository,
    private val principalRepository: PrincipalRepository,
    private val userRepository: UserRepository,
    private val imageService: StorageService
) {

    fun getAll() {}

    fun getProgram(){}

    fun findProgramById(){}

    fun findProgramByCourseId() {}

    fun createProgram() {}

    fun deleteProgram() {}
}