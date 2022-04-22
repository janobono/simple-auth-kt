package sk.janobono.api.service.so

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "Authority")
data class AuthoritySO(
    val id: Long,
    val name: String
) {
    override fun equals(other: Any?): Boolean {
        return (other is AuthoritySO) && (other.id == id)
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
