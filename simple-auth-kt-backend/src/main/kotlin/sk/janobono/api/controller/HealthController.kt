package sk.janobono.api.controller

import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "health", description = "health endpoint")
@RestController
@RequestMapping(path = ["/health"])
class HealthController {

    @GetMapping
    fun health(): ResponseEntity<String> {
        LOGGER.debug("health()")
        return ResponseEntity("OK", HttpStatus.OK)
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(HealthController::class.java)
    }
}
