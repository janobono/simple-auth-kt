package sk.janobono

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import sk.janobono.config.ConfigProperties

@SpringBootApplication(scanBasePackages = ["sk.janobono"])
@EnableConfigurationProperties(ConfigProperties::class)
class SimpleAuthKtBackend {
}

fun main(args: Array<String>) {
    runApplication<SimpleAuthKtBackend>(*args)
}
