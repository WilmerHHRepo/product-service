package com.bootcamp51.microservices.productservice.controller;

import com.bootcamp51.microservices.productservice.client.ApiClient;
import com.bootcamp51.microservices.productservice.dto.ProductMovementDTO;
import com.bootcamp51.microservices.productservice.model.Client;
import com.bootcamp51.microservices.productservice.model.Movement;
import com.bootcamp51.microservices.productservice.service.utils.ProductMovementServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/bootcamp51/ms/product/Movement")
public class ProductMovementController {

    @Autowired
    private ProductMovementServiceUtil productMovementServiceUtil;

    @Autowired
    private ApiClient apiClient;

    @PatchMapping("/{cuenta}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<Client> productMovement(@RequestBody ProductMovementDTO productMovementRequest, @PathVariable String cuenta)  throws Exception  {
        Client client = productMovementRequest.getClient();
        Movement movement = productMovementRequest.getMovement();
        Mono<Client> monoClient = apiClient.findByID(client.getId());
        monoClient.subscribe(e ->
                {
                    try {
                        productMovementServiceUtil.productMovement(e, movement, cuenta);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
        );
        return Mono.empty();
    }

}
