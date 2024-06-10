package com.example

import com.arangodb.entity.ArangoDBVersion
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces
import io.micronaut.serde.annotation.SerdeImport


@Controller("/")
@SerdeImport(ArangoDBVersion::class)
class ArangoResource(private val svc: ArangoService) {

    @Get("/version")
    @Produces(MediaType.APPLICATION_JSON)
    fun version(): ArangoDBVersion = svc.getVersion()

    @Get("/order")
    @Produces(MediaType.APPLICATION_JSON)
    fun order(): Order = svc.orderRoundTrip()

}
