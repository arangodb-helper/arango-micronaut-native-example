package com.example
import io.micronaut.runtime.EmbeddedApplication
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import io.restassured.specification.RequestSpecification
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import jakarta.inject.Inject

@MicronautTest
class DemoTest {

    @Inject
    lateinit var application: EmbeddedApplication<*>

    @Test
    fun testItWorks() {
        Assertions.assertTrue(application.isRunning)
    }

    @Test
    fun testHelloEndpoint(spec: RequestSpecification) {
        val server = spec
            .`when`().get("/version")
            .then()
            .statusCode(200)
            .extract()
            .jsonPath()
            .getString("server")
        org.assertj.core.api.Assertions.assertThat(server).isEqualTo("arango")
    }

    @Test
    fun testOrderEndpoint(spec: RequestSpecification) {
        val server = spec
            .`when`().get("/order")
            .then()
            .statusCode(200)
            .extract()
            .jsonPath()
            .getString("product")
        org.assertj.core.api.Assertions.assertThat(server).isEqualTo("pizza")
    }

}
