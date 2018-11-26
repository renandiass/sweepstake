package com.sweepstake.client

import com.sweepstake.data.entity.ClientEntity
import com.sweepstake.data.repository.ClientRepository
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.body
import reactor.core.publisher.Mono
import java.lang.Exception
import javax.validation.ConstraintViolationException
import javax.validation.Validator
import javax.validation.constraints.NotBlank

@Component
class ClientHandler(private val clientService: ClientService,
                    private val requestHandler: RequestHandler) {

    fun register(request: ServerRequest): Mono<ServerResponse> {
        return requestHandler.withValidBody({
            clientRequest ->
            clientRequest.flatMap { abc -> clientService.createClient(abc) }
                         .flatMap { ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(Mono.just(it)) }
        }, request, Client::class.java)
    }
}

@Service
class ClientService(private val clientRepository: ClientRepository) {
    fun createClient(client: Client): Mono<ClientEntity> {
            return clientRepository.save(ClientEntity(client.email, client.name, client.password))
    }
}

data class Client (@get:NotBlank val name: String, @get:NotBlank val email: String, @get:NotBlank val password: String)

@Component
class RequestHandler(private val validator: Validator) {
    fun <BODY> withValidBody(
            block: (Mono<BODY>) -> Mono<ServerResponse>,
            request: ServerRequest, bodyClass: Class<BODY>): Mono<ServerResponse> {

        return request
                .bodyToMono(bodyClass)
                .flatMap { body ->
                    val violations = validator.validate(body)
                    if (violations.isEmpty())
                        block.invoke(Mono.just(body))
                    else
                        throw ConstraintViolationException(violations)
                }
    }
}