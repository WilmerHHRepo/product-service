package com.bootcamp51.microservices.productservice.client;

import com.bootcamp51.microservices.productservice.model.Client;
import io.netty.handler.logging.LogLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.logging.AdvancedByteBufFormat;

/**
 * Class ApiClient.
 * author by Wilmer Huaqui.
 */
@Component
public class ApiClient {

  @Value("${rutaApiClient:}")
  private String rutaApiClient;

  @Value("${retryApiClient:}")
  private Integer retryApiClient;

  @Value("${timeIntervalApiClient}")
  private Integer timeIntervalApiClient;

  private static final Logger logger = LoggerFactory.getLogger(ApiClient.class);


  /**
   * findByAccount for id.
   *
   * @param id input
   * @return Client.
   */
  public Mono<Client> findByAccount(String id) {
    HttpClient httpClient = HttpClient.create()
        .wiretap("",
            LogLevel.DEBUG, AdvancedByteBufFormat.TEXTUAL);
    WebClient webClient = WebClient.builder()
        .clientConnector(new ReactorClientHttpConnector(httpClient))
        .baseUrl(rutaApiClient).build();

    return webClient.get()
        .uri("/by-cta/" + id)
        .retrieve()
        .bodyToMono(Client.class);
  }

  /**
   * findByDocument by document.
   *
   * @param document input.
   * @return client.
   */
  public Mono<Client> findByDocument(String document) {
    HttpClient httpClient = HttpClient.create()
        .wiretap("",
            LogLevel.DEBUG, AdvancedByteBufFormat.TEXTUAL);
    WebClient webClient = WebClient.builder()
        .clientConnector(new ReactorClientHttpConnector(httpClient))
        .baseUrl(rutaApiClient).build();

    return webClient.get()
        .uri("/by-document/" + document)
        .retrieve()
        .bodyToMono(Client.class);
  }


}
