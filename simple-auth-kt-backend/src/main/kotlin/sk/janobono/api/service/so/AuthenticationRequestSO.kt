package sk.janobono.api.service.so

import io.swagger.v3.oas.annotations.media.Schema
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@Schema(name = "AuthenticationRequest")
data class AuthenticationRequestSO(
    @NotBlank @Size(max = 255) val username: String,
    @NotBlank @Size(max = 255) val password: String
) {
    override fun toString(): String {
        return "AuthenticationRequestSO(username='$username')"
    }
}
