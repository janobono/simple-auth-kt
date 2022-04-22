package sk.janobono.api.service.so

import io.swagger.v3.oas.annotations.media.Schema
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Schema(name = "UserCreate")
data class UserCreateSO(
    @NotBlank @Size(max = 255) val username: String,
    @NotBlank @Size(max = 255) val password: String,
    @NotNull val enabled: Boolean,
    val authorities: List<AuthoritySO>,
    val attributes: Map<String, String>
) {
    override fun toString(): String {
        return "UserCreateSO(username='$username', enabled=$enabled, authorities=$authorities, attributes=$attributes)"
    }
}
