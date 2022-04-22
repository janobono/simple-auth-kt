package sk.janobono.api.service.so

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "User")
data class UserSO(
    val id: Long? = null,
    val username: String,
    val enabled: Boolean,
    val authorities: List<AuthoritySO>,
    val attributes: Map<String, String>
) {

    override fun equals(other: Any?): Boolean {
        return (other is UserSO) && (other.id == id)
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}
