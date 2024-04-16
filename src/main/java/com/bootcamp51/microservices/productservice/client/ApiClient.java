package com.bootcamp51.microservices.productservice.client;

import com.bootcamp51.microservices.productservice.model.Client;
import io.netty.handler.logging.LogLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.logging.AdvancedByteBufFormat;
import reactor.util.retry.Retry;

import java.time.Duration;

@Component
public class ApiClient {

    @Value("${rutaApiClient:}")
    private String rutaApiClient;

    @Value("${reintentosApiClient:}")
    private Integer retryApiClient;

    @Value("${timeIntervalApiClient}")
    private Integer timeIntervalApiClient;

    private static final Logger logger = LoggerFactory.getLogger(ApiClient.class);


    public Mono<Client> findByID(String id){
        HttpClient httpClient = HttpClient.create()
                .wiretap("",
                        LogLevel.DEBUG, AdvancedByteBufFormat.TEXTUAL);
        WebClient webClient = WebClient.builder().clientConnector(new ReactorClientHttpConnector(httpClient))
                .baseUrl(rutaApiClient).build();

        return webClient.get()
                .uri("/by-id/" + id)
                .retrieve()
                .bodyToMono(Client.class);
//                .bodyToMono(Client.class).map(m -> null)
//                .retryWhen(Retry.backoff(retryApiClient, Duration.ofSeconds(timeIntervalApiClient)).filter(this::retry)
//                        .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> {
//                            throw new RuntimeException("error de conexion");
//
//                        }))
//                .doOnError(throwable -> {
//                    if (throwable instanceof WebClientResponseException) {
//
//                    }
//                    return null;
//
//                });

    }


}
