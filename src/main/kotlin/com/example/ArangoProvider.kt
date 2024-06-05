package com.example

import com.arangodb.ArangoDB
import com.arangodb.serde.ArangoSerde
import io.micronaut.context.annotation.Factory
import io.micronaut.serde.ObjectMapper
import jakarta.inject.Singleton
import java.security.KeyStore
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory

@Factory
class ArangoProvider {
    @Singleton
    fun arangoDB(config: ArangoConfig, mapper: ObjectMapper): ArangoDB = ArangoDB.Builder()
        .loadProperties(ArangoConfigAdapter(config))
        .sslContext(createSslContext(config))

        // ArangoSerde implementation based on Micronaut serialization
        .serde(object : ArangoSerde {
            override fun serialize(value: Any?): ByteArray {
                return mapper.writeValueAsBytes(value)
            }

            override fun <T : Any?> deserialize(content: ByteArray, clazz: Class<T>): T? {
                return mapper.readValue(content, clazz)
            }
        })
        .build()


    private fun createSslContext(config: ArangoConfig): SSLContext {
        val ks = KeyStore.getInstance(config.ssl.trustStoreType)
        ks.load(
            Thread.currentThread().contextClassLoader.getResourceAsStream(config.ssl.trustStoreFile),
            config.ssl.trustStorePassword.toCharArray()
        )
        val tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        tmf.init(ks)
        val sc = SSLContext.getInstance("TLS")
        sc.init(null, tmf.trustManagers, null)
        return sc
    }
}
