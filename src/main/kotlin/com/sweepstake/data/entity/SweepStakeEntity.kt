package com.sweepstake.data.entity

import org.springframework.data.cassandra.core.mapping.Column
import org.springframework.data.cassandra.core.mapping.PrimaryKey
import org.springframework.data.cassandra.core.mapping.Table

@Table("sw_st_client")
data class ClientEntity(
        @PrimaryKey(value = "des_email") var email: String,
        @Column(value = "des_name") var name: String,
        @Column(value = "des_password") var password: String)