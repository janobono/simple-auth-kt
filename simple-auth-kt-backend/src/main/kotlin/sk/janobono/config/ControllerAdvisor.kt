package sk.janobono.config

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.server.ResponseStatusException
import java.io.PrintWriter
import java.io.StringWriter
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@ControllerAdvice
class ControllerAdvisor {

    @ExceptionHandler(ResponseStatusException::class)
    fun handleResponseStatusException(
        responseStatusException: ResponseStatusException, request: WebRequest?
    ): ResponseEntity<Any> {
        LOGGER.warn("handleResponseStatusException({})", responseStatusException.status)
        return ResponseEntity(
            ExceptionBody(
                LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS),
                toMessage(responseStatusException),
                toStackTrace(responseStatusException)
            ), responseStatusException.status
        )
    }

    @ExceptionHandler(Exception::class)
    fun handleException(exception: Exception, request: WebRequest?): ResponseEntity<Any> {
        LOGGER.warn("handleException", exception)
        return ResponseEntity(
            ExceptionBody(
                LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS), toMessage(exception), toStackTrace(exception)
            ), HttpStatus.INTERNAL_SERVER_ERROR
        )
    }

    private fun toMessage(throwable: Throwable): String {
        return throwable.localizedMessage?.toString() ?: throwable.toString()
    }

    private fun toStackTrace(throwable: Throwable): String {
        val stringWriter = StringWriter()
        PrintWriter(stringWriter).use { printWriter ->
            throwable.printStackTrace(printWriter)
            printWriter.flush()
        }
        return stringWriter.toString()
    }

    data class ExceptionBody(
        val timestamp: LocalDateTime, val message: String, val stackTrace: String
    )

    companion object {
        private val LOGGER = LoggerFactory.getLogger(ControllerAdvisor::class.java)
    }
}
