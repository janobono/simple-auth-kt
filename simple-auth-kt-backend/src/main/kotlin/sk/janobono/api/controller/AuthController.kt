package sk.janobono.api.controller

import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import sk.janobono.api.service.AuthApiService
import sk.janobono.api.service.so.AuthenticationRequestSO
import sk.janobono.api.service.so.AuthenticationResponseSO
import javax.validation.Valid

@Tag(name = "auth", description = "authentication endpoint")
@RestController
@RequestMapping
class AuthController(private val authApiService: AuthApiService) {

    @PostMapping("/authenticate")
    fun authenticate(@RequestBody authenticationRequest: @Valid AuthenticationRequestSO?): ResponseEntity<AuthenticationResponseSO> {
        LOGGER.debug("authenticate({})", authenticationRequest)
        return ResponseEntity(authApiService.authenticate(authenticationRequest!!), HttpStatus.OK)
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(AuthController::class.java)
    }
}
