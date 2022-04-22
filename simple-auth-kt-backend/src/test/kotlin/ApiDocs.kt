package sk.janobono

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.test.context.TestPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import java.nio.file.Files
import java.nio.file.Paths

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(
    properties = [
        "spring.flyway.enabled=false",
        "spring.datasource.url=jdbc:h2:mem:testdb",
        "spring.datasource.driverClassName=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=password",
        "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect"
    ]
)
internal class ApiDocs {

    @Autowired
    var mvc: MockMvc? = null

    @Autowired
    var webApplicationContext: WebApplicationContext? = null

    @BeforeEach
    fun setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext!!).build()
    }

    @Test
    @Throws(Exception::class)
    fun apiDocs() {
        val mvcResult = mvc!!.perform(MockMvcRequestBuilders.get("/v3/api-docs.yaml")).andReturn()
        Assertions.assertThat(mvcResult.response.status).isEqualTo(HttpStatus.OK.value())
        Files.write(
            Paths.get(System.getProperty("java.io.tmpdir"), "api-docs.yml"),
            mvcResult.response.contentAsString.toByteArray()
        )
    }
}
