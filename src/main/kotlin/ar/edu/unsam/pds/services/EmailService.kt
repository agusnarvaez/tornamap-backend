package ar.edu.unsam.pds.services

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import java.nio.file.Files
import java.nio.file.Paths

@Service
@ConditionalOnProperty(name = ["email.enabled"], havingValue = "true", matchIfMissing = true)
class EmailService(
    private val mailSender: JavaMailSender
) {

    @Async("taskExecutor")
    fun sendEmail(to: String, subject: String, htmlContent: String) {
        val message = mailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, true)

        helper.setTo(to)
        helper.setSubject(subject)
        helper.setText(htmlContent, true)

        mailSender.send(message)
    }

    private fun loadTemplate(filePath: String): String {
        val path = Paths.get(filePath)
        return String(Files.readAllBytes(path))
    }

    private fun replacePlaceholders(template: String, placeholders: Map<String, String>): String {
        var result = template
        for ((key, value) in placeholders) {
            result = result.replace(key, value)
        }
        return result
    }
}


@Service
@ConditionalOnProperty(name = ["email.enabled"], havingValue = "false", matchIfMissing = true)
class NoOpEmailService(mailSender: JavaMailSender) : EmailService(mailSender) {
    override fun sendEmail(to: String, subject: String, htmlContent: String) {}
}
