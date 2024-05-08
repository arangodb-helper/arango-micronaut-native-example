package com.example


import com.arangodb.config.ArangoConfigProperties
import com.arangodb.config.HostDescription
import io.micronaut.context.annotation.ConfigurationProperties
import java.util.*

@ConfigurationProperties("adb")
class ArangoConfig {
    var hosts: Optional<List<String>> = Optional.empty()
    var password: Optional<String> = Optional.empty()
}

class ArangoConfigAdapter(private val config: ArangoConfig) : ArangoConfigProperties {
    override fun getHosts(): Optional<MutableList<HostDescription>> {
        return config.hosts.map { it.map { x -> HostDescription.parse(x) }.toMutableList() }
    }

    override fun getPassword(): Optional<String> {
        return config.password
    }
}