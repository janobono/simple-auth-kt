package sk.janobono.component

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.interfaces.DecodedJWT
import org.springframework.stereotype.Component
import sk.janobono.config.ConfigProperties
import sk.janobono.dal.domain.Authority
import sk.janobono.dal.domain.User
import java.security.KeyFactory
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.*
import java.util.concurrent.TimeUnit

@Component
class JwtToken(configProperties: ConfigProperties) {
    private val algorithm: Algorithm
    private val expiration: Long
    private val issuer: String

    init {
        algorithm = Algorithm.RSA256(
            getPublicKey(configProperties.jwtPublicKey), getPrivateKey(configProperties.jwtPrivateKey)
        )
        expiration = TimeUnit.SECONDS.toMillis(configProperties.jwtExpiration.toLong())
        issuer = configProperties.issuer
    }

    private fun getPublicKey(base64PublicKey: String): RSAPublicKey {
        val decoded = Base64.getDecoder().decode(base64PublicKey)
        val keySpec = X509EncodedKeySpec(decoded)
        val keyFactory = KeyFactory.getInstance("RSA")
        return keyFactory.generatePublic(keySpec) as RSAPublicKey
    }

    private fun getPrivateKey(base64PrivateKey: String): RSAPrivateKey {
        val decoded = Base64.getDecoder().decode(base64PrivateKey)
        val keySpec = PKCS8EncodedKeySpec(decoded)
        val keyFactory = KeyFactory.getInstance("RSA")
        return keyFactory.generatePrivate(keySpec) as RSAPrivateKey
    }

    fun expiresAt(issuedAt: Long): Long {
        return issuedAt + expiration
    }

    fun generateToken(user: User, issuedAt: Long): String {
        val jwtBuilder = JWT.create()
            .withIssuer(issuer)
            .withSubject(user.username)
            .withClaim("id", user.id)
            .withClaim("enabled", user.enabled)
            .withArrayClaim(
                "authorities",
                user.authorities!!
                    .sortedBy(Authority::id)
                    .map { "${it.id}:${it.name}" }
                    .toTypedArray()
            )
            .withIssuedAt(Date(issuedAt))
            .withExpiresAt(Date(expiresAt(issuedAt)))
        user.attributes!!.forEach { (key, value) ->
            jwtBuilder.withClaim("$issuer:$key", value)
        }
        return jwtBuilder.sign(algorithm)
    }

    @Throws(JWTVerificationException::class)
    private fun decodeToken(token: String): DecodedJWT {
        val verifier = JWT.require(algorithm)
            .withIssuer(issuer)
            .build()
        return verifier.verify(token)
    }

    fun parseToken(token: String): User {
        val jwt = decodeToken(token)
        val user = User()
        user.username = jwt.subject
        user.id = jwt.claims["id"]!!.asLong()
        user.enabled = jwt.claims["enabled"]!!.asBoolean()

        user.authorities = jwt.claims["authorities"]!!.asList(String::class.java)
            .map { Authority(it.substringBefore(":").toLong(), it.substringAfter(":")) }

        user.attributes = jwt.claims.entries
            .filter {
                it.key.startsWith(issuer)
            }.associate { Pair(it.key.replaceFirst("$issuer:", ""), it.value.asString()) }
        return user
    }
}
