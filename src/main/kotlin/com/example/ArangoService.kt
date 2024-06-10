package com.example

import com.arangodb.ArangoDB
import com.arangodb.entity.ArangoDBVersion
import jakarta.inject.Singleton
import java.util.*

@Singleton
class ArangoService(private val adb: ArangoDB) {

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

