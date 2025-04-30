package ar.edu.unsam.pds.tools

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass


fun clearCookies(request: HttpServletRequest, response: HttpServletResponse) {
    request.cookies?.let {it.forEach { cookie ->
        cookie.maxAge = 0
        cookie.value = null
        cookie.path = "/"
        response.addCookie(cookie)
    }}
}

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [EachUuidValidator::class])
annotation class EachUuid(
    val message: String = "UUID inválido",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)

class EachUuidValidator : ConstraintValidator<EachUuid, List<String>> {
    private val uuidRegex = Regex("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}\$")

    override fun isValid(value: List<String>?, context: ConstraintValidatorContext): Boolean {
        return value?.all { uuidRegex.matches(it) } ?: true // Permite lista nula o vacía
    }
}