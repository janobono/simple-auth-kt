package sk.janobono.api.service

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import sk.janobono.api.service.so.AuthenticationRequestSO
import sk.janobono.api.service.so.AuthenticationResponseSO
import sk.janobono.component.JwtToken
import sk.janobono.dal.repository.UserRepository

@Service
class AuthApiService {

    private var passwordEncoder: PasswordEncoder? = null

    private var jwtToken: JwtToken? = null

    private var userRepository: UserRepository? = null

    @Autowired
    fun setPasswordEncoder(passwordEncoder: PasswordEncoder?) {
        this.passwordEncoder = passwordEncoder
    }

    @Autowired
    fun setJwtToken(jwtToken: JwtToken?) {
        this.jwtToken = jwtToken
    }

    @Autowired
    fun setUserRepository(userRepository: UserRepository?) {
        this.userRepository = userRepository
    }

    fun authenticate(authenticationRequestSO: AuthenticationRequestSO): AuthenticationResponseSO {
        LOGGER.debug("authenticate({})", authenticationRequestSO)
        val user = userRepository!!.findByUsername(authenticationRequestSO.username.lowercase())
            .orElseThrow {
                ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "User not found."
                )
            }
        if (!user.enabled!!) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "User disabled.")
        }
        if (!passwordEncoder!!.matches(authenticationRequestSO.password, user.password)) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid credentials.")
        }
        val issuedAt = System.currentTimeMillis()
        val authenticationResponse = AuthenticationResponseSO(
            jwtToken!!.generateToken(user, issuedAt)
        )
        LOGGER.info("authenticate({}) - {}", authenticationRequestSO, authenticationResponse)
        return authenticationResponse
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(AuthApiService::class.java)
    }
}
