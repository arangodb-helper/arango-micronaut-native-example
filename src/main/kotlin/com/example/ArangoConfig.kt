package com.example


import com.arangodb.Protocol
import com.arangodb.config.ArangoConfigProperties
import com.arangodb.config.HostDescription
import io.micronaut.context.annotation.ConfigurationProperties
import java.util.*

@ConfigurationProperties("adb")
class ArangoConfig {
    var hosts: Optional<List<String>> = Optional.empty()
    var password: Optional<String> = Optional.empty()
    var protocol: Optional<Protocol> = Optional.empty()
    var useSsl: Optional<Boolean> = Optional.empty()
    var ssl: SslConfig = SslConfig()

    @ConfigurationProperties("ssl")
    class SslConfig {
        lateinit var trustStoreFile: String
        lateinit var trustStorePassword: String
        lateinit var trustStoreType: String
    }
}

class ArangoConfigAdapter(private val config: ArangoConfig) : ArangoConfigProperties {
    override fun getHosts(): Optional<MutableList<HostDescription>> {
        return config.hosts.map { it.map { x -> HostDescription.parse(x) }.toMutableList() }
    }

    override fun getPassword(): Optional<String> {
        return config.password
    }

    override fun getProtocol(): Optional<Protocol> {
        return config.protocol
    }

    override fun getUseSsl(): Optional<Boolean> {
        return config.useSsl
    }
}