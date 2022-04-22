package sk.janobono.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.validation.annotation.Validated
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@ConfigurationProperties("app")
@ConstructorBinding
@Validated
data class ConfigProperties(
    @NotEmpty val issuer: String,
    @NotEmpty val jwtPrivateKey: String,
    @NotEmpty val jwtPublicKey: String,
    @NotNull val jwtExpiration: Int
)
