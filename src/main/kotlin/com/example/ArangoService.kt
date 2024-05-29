package com.example

import com.arangodb.ArangoDB
import com.arangodb.entity.ArangoDBVersion
import com.arangodb.serde.ArangoSerde
import io.micronaut.serde.ObjectMapper
import jakarta.inject.Singleton
import java.util.*

@Singleton
class ArangoService(config: ArangoConfig, mapper: ObjectMapper) {

    private val adb: ArangoDB = ArangoDB.Builder()
        .loadProperties(ArangoConfigAdapter(config))

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

    fun getVersion(): ArangoDBVersion {
        return adb.version
    }

    fun orderRoundTrip(): Order {
        return adb.db().query(
            "RETURN @order",
            Order::class.java,
            mapOf("order" to Order("pizza", 11.22, Date()))
        ).first()
    }

}

