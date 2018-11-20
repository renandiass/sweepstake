package com.sweepstake.configuration

import com.datastax.driver.core.PlainTextAuthProvider
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.data.cassandra.config.AbstractReactiveCassandraConfiguration
import org.springframework.data.cassandra.repository.config.EnableReactiveCassandraRepositories

@Configuration
@EnableReactiveCassandraRepositories(basePackages = ["com.sweepstake.data.repository"])
class CassandraConfig(@Value("\${cassandra.contact-points}") private val cassandraContactPoints: String,
                      @Value("\${cassandra.port}") private val cassandraPort: Int,
                      @Value("\${cassandra.keyspace}") private val cassandraKeyspace: String): AbstractReactiveCassandraConfiguration() {

    override fun getKeyspaceName() = cassandraKeyspace

    override fun getContactPoints() = cassandraContactPoints

    override fun getPort() = cassandraPort

    override fun getEntityBasePackages() = arrayOf("com.sweepstake.data.entity")

    override fun getAuthProvider() = PlainTextAuthProvider("cassandra", "cassandra")
}