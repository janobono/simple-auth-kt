package sk.janobono.api.service.so

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "AuthenticationResponse")
data class AuthenticationResponseSO(
    val bearer: String
)
