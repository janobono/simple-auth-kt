package sk.janobono

import java.security.KeyPairGenerator
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.*

object KeyGenerator {

    @JvmStatic
    fun main(args: Array<String>) {
        val keyGen = KeyPairGenerator.getInstance("RSA")
        keyGen.initialize(1024)
        val keyPair = keyGen.generateKeyPair()
        val privateKey = keyPair.private as RSAPrivateKey
        val publicKey = keyPair.public as RSAPublicKey
        println("PRIVATE KEY:")
        val pkcs8EncodedKeySpec = PKCS8EncodedKeySpec(privateKey.encoded)
        println(Base64.getEncoder().encodeToString(pkcs8EncodedKeySpec.encoded))
        println("PUBLIC KEY:")
        val x509EncodedKeySpec = X509EncodedKeySpec(publicKey.encoded)
        println(Base64.getEncoder().encodeToString(x509EncodedKeySpec.encoded))
    }
}
