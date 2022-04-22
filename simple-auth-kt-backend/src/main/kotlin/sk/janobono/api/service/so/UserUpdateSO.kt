package sk.janobono.api.service.so

import io.swagger.v3.oas.annotations.media.Schema
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Schema(name = "UserUpdate")
data class UserUpdateSO(
    @NotBlank @Size(max = 255) val username: String,
    val password: String,
    @NotNull val enabled: Boolean,
    val authorities: List<AuthoritySO>,
    val attributes: Map<String, String>
) {
    override fun toString(): String {
        return "UserUpdateSO(username='$username', enabled=$enabled, authorities=$authorities, attributes=$attributes)"
    }
}
