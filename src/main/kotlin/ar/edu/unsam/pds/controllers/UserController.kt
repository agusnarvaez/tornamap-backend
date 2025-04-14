package ar.edu.unsam.pds.controllers

import ar.edu.unsam.pds.dto.request.LoginForm
import ar.edu.unsam.pds.dto.request.RegisterFormDto
import ar.edu.unsam.pds.dto.request.UserRequestUpdateDto
import ar.edu.unsam.pds.dto.response.UserDetailResponseDto
import ar.edu.unsam.pds.dto.response.UserResponseDto
import ar.edu.unsam.pds.mappers.UserMapper
import ar.edu.unsam.pds.security.models.Principal
import ar.edu.unsam.pds.services.UserService
import io.swagger.v3.oas.annotations.Operation
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/users")
@CrossOrigin("*")
class UserController : UUIDValid() {
    @Autowired lateinit var userService: UserService

    @GetMapping("")
    @Operation(summary = "Get all users")
    fun getAll(){}

    @GetMapping("profile")
    @Operation(summary = "Get user profile")
    fun getProfile(
        request: HttpServletRequest
    ): ResponseEntity<UserDetailResponseDto> {
        val auth: Authentication = request.userPrincipal as Authentication
        val principalUser = (auth.principal as Principal).getUser()

        return ResponseEntity.ok(UserMapper.buildUserDetailDto(principalUser))
    }

    @PostMapping("login")
    fun login(
        @RequestBody @Valid user: LoginForm,
        request: HttpServletRequest,
        response: HttpServletResponse
    ): ResponseEntity<UserDetailResponseDto> {
        return ResponseEntity.ok(userService.login(user, request, response))
    }

    @PostMapping("logout")
    fun logout(
        request: HttpServletRequest
    ): ResponseEntity<Map<String, String>> {
        request.logout()
        return ResponseEntity.ok(mapOf("message" to "Se ha deslogeado correctamente."))
    }

    @PostMapping("register")
    @Operation(summary = "Register a new user")
    fun register(
        @RequestBody @Valid form: RegisterFormDto
    ): ResponseEntity<UserResponseDto> {
        val registeredUser = userService.register(form)
        return ResponseEntity.ok(registeredUser)
    }

    @PatchMapping(value = ["/{idUser}"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    @Operation(summary = "Update a user's details")
    fun updateDetail(
        @PathVariable idUser: String,
        @ModelAttribute @Valid user: UserRequestUpdateDto
    ): ResponseEntity<UserResponseDto> {
        this.validatedUUID(idUser)
        val originalUser = userService.updateDetail(idUser, user)
        return ResponseEntity.ok().body(originalUser)
    }

    @DeleteMapping("")
    @Operation(summary = "Delete account")
    fun deleteAccount(
        request: HttpServletRequest,
        response: HttpServletResponse
    ): ResponseEntity<Map<String, String>> {
        userService.deleteAccount(request, response)
        return ResponseEntity.ok(mapOf("message" to "Cuenta eliminada correctamente."))
    }

}
