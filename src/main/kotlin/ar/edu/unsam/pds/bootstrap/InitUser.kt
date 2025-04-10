package ar.edu.unsam.pds.bootstrap

import ar.edu.unsam.pds.models.User
import ar.edu.unsam.pds.repository.UserRepository
import ar.edu.unsam.pds.security.models.Principal
import ar.edu.unsam.pds.security.repository.PrincipalRepository
import ar.edu.unsam.pds.services.StorageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component(value = "InitUsers.beanName")
class InitUser : BootstrapGeneric("users") {
    @Autowired private lateinit var passwordEncoder: PasswordEncoder
    @Autowired private lateinit var principalRepository: PrincipalRepository
    @Autowired private lateinit var storageService: StorageService
    @Autowired private lateinit var userRepository: UserRepository

    override fun doAfterPropertiesSet() {
        // region user = ADMIN @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        principalRepository.save(
            Principal().apply {
                username = "admin@admin.com"
                password = encode("123")
                user = User(
                    name = "Luciano",
                    lastName = "D'Fabio",
                    email = "admin@admin.com",
                    image = storageService.defaultImage(),
                    isAdmin = true
                )
                this.initProperties()
            }
        )
        // endregion
        // region user = USER @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        userRepository.save(
            User(
                name = "Beatriz",
                lastName = "Artesi",
                email = "bartesi@estudiantes.unsam.edu.ar",
                image = storageService.defaultImage()
            )
        )
    }

    fun encode(clave: String): String {
        return passwordEncoder.encode(clave)
    }
}