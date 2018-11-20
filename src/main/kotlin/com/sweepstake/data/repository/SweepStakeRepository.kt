package com.sweepstake.data.repository

import com.sweepstake.data.entity.ClientEntity
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface ClientRepository : ReactiveCrudRepository<ClientEntity, String>