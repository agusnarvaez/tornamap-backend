package ar.edu.unsam.pds.services

import ar.edu.unsam.pds.dto.request.LoginForm
import ar.edu.unsam.pds.dto.request.RegisterFormDto
import ar.edu.unsam.pds.dto.request.UserRequestUpdateDto
import ar.edu.unsam.pds.dto.response.CourseResponseDto
import ar.edu.unsam.pds.dto.response.UserDetailResponseDto
import ar.edu.unsam.pds.dto.response.UserResponseDto
import ar.edu.unsam.pds.exceptions.InternalServerError
import ar.edu.unsam.pds.exceptions.NotFoundException
import ar.edu.unsam.pds.mappers.EventMapper
import ar.edu.unsam.pds.mappers.CourseMapper
import ar.edu.unsam.pds.mappers.UserMapper
import ar.edu.unsam.pds.models.User
import ar.edu.unsam.pds.repository.UserRepository
import ar.edu.unsam.pds.security.models.Principal
import ar.edu.unsam.pds.security.repository.PrincipalRepository
import ar.edu.unsam.pds.tools.clearCookies
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class UserService(
    private val userRepository: UserRepository,
    private val principalRepository: PrincipalRepository,
    private val storageService: StorageService,
    private val rememberMeServices: TokenBasedRememberMeServices
) {

    fun getAll(): MutableList<User> {
        return userRepository.findAllByOrderByNameAsc()
    }

    fun login(user: LoginForm, request: HttpServletRequest, response: HttpServletResponse): UUID {
        try {
            request.login(user.email, user.password)
        } catch (e: ServletException) {
            throw NotFoundException("Usuario y/o contraseña invalidos.")
        }

        val auth: Authentication = request.userPrincipal as Authentication

        if (user.rememberMe) {
            rememberMeServices.loginSuccess(request, response, auth)
        }

        return (auth.principal as Principal).getUser().id
    }

    @Transactional
    fun register(form: RegisterFormDto): UUID {
        if (principalRepository.findUserByEmail(form.email).isPresent) {
            throw InternalServerError("El correo ya está en uso. Si elimino su cuenta y quiere recuperarla dirijase a @pirulo")
        }
        val encryptedPassword = encryptPassword(form.password)

        val newUser = User(
            name = form.name,
            lastName = form.lastName,
            email = form.email,
            image = storageService.defaultImage(),
            isAdmin = form.isAdmin,
        )
        userRepository.save(newUser)

        val principal = Principal().apply {
            username = form.email
            password = encryptedPassword
            initProperties()
            user = newUser
        }
        principalRepository.save(principal)

        return newUser.id
    }

    private fun encryptPassword(password: String): String {
        val encoder = BCryptPasswordEncoder()
        return encoder.encode(password)
    }


    @Transactional
    fun updateDetail(idUser: String, userDetail: UserRequestUpdateDto): User {
        val user = findUserById(idUser)

        if (userDetail.image != null) {
            val imageName = storageService.updatePrivate(user.image, userDetail.image)

            user.image = imageName
        }

        user.name = userDetail.name
        user.lastName = userDetail.lastName
        user.email = userDetail.email

        userRepository.save(user)
        return user
    }

    private fun findUserById(idUser: String): User {
        val uuid = UUID.fromString(idUser)
        return userRepository.findById(uuid).orElseThrow {
            NotFoundException("Usuario no encontrado para el uuid suministrado")
        }
    }

    @Transactional
    fun deleteAccount(request: HttpServletRequest, response: HttpServletResponse) {
        val auth: Authentication = request.userPrincipal as Authentication
        val id = (auth.principal as Principal).id

        principalRepository.findById(id).map { principal ->

            principalRepository.deleteWithDestructionOfAllSessions(principal)
            storageService.deletePrivate(principal.getUser().image)
            clearCookies(request, response)

        }.orElseThrow {
            NotFoundException("Usuario no encontrado para el uuid suministrado")
        }
    }
}