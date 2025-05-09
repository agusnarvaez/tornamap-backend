package ar.edu.unsam.pds.bootstrap

import ar.edu.unsam.pds.models.User
import ar.edu.unsam.pds.repository.UserRepository
import ar.edu.unsam.pds.security.models.Principal
import ar.edu.unsam.pds.security.repository.PrincipalRepository
import ar.edu.unsam.pds.services.StorageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component(value = "InitUsers.beanName")
@Profile(value = ["dev", "prod", "test"])
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
                password = encode("admin")
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
                name = "Carlos",
                lastName = "Scirica",
                email = "cscirica@estudiantes.unsam.edu.ar",
                image = storageService.defaultImage()
            )
        )

        userRepository.save(
            User(
                name = "Fernando",
                lastName = "Dodino",
                email = "dodain@estudiantes.unsam.edu.ar",
                image = storageService.defaultImage()
            )
        )

        userRepository.save(
            User(
                name = "Maria Claudia",
                lastName = "Abeledo",
                email = "mcabeledo@estudiantes.unsam.edu.ar",
                image = storageService.defaultImage()
            )
        )

        userRepository.save(
            User(
                name = "Gaston",
                lastName = "Aguilera",
                email = "gAguilera@estudiantes.unsam.edu.ar",
                image = storageService.defaultImage()
            )
        )
        userRepository.save(
            User(
                name = "Pedro Facundo",
                lastName = "Iriso",
                email = "pedroiriso@gmail.com",
                image = storageService.defaultImage()
            )
        )
        userRepository.save(
            User(
                name = "Fabio Sergio",
                lastName = " Bruschetti",
                email = "fSBruschetti@estudiantes.unsam.edu.ar",
                image = storageService.defaultImage()
            )
        )

        userRepository.save(
            User(
                name = "Monica",
                lastName = "Hencek",
                email = "mHencek@estudiantes.unsam.edu.ar",
                image = storageService.defaultImage()
            )
        )
        userRepository.save(
            User(
                name = "Juan José",
                lastName = "López",
                email = "jJLopez@estudiantes.unsam.edu.ar",
                image = storageService.defaultImage()
            )
        )
    }

    fun encode(clave: String): String {
        return passwordEncoder.encode(clave)
    }
}