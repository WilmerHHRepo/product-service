package com.bootcamp51.microservices.productservice.controller;

import com.bootcamp51.microservices.productservice.client.ApiClient;
import com.bootcamp51.microservices.productservice.dto.ProductMovementDTO;
import com.bootcamp51.microservices.productservice.model.Client;
import com.bootcamp51.microservices.productservice.model.Movement;
import com.bootcamp51.microservices.productservice.service.utils.ProductMovementServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequestMapping("/bootcamp51/ms/product/Movement")
public class ProductMovementController {

    @Autowired
    private ProductMovementServiceUtil productMovementServiceUtil;

    @Autowired
    private ApiClient apiClient;

    @PatchMapping("/{cuenta}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Movement productMovement(@RequestBody ProductMovementDTO productMovementRequest, @PathVariable String cuenta)  throws Exception  {
        Client client = productMovementRequest.getClient();
        Movement movement = productMovementRequest.getMovement();
        AtomicReference<Movement> mov = new AtomicReference<>(new Movement());
        Mono<Client> monoClient = apiClient.findByID(client.getId());
        monoClient.subscribe(e ->
                {
                    try {
                        mov.set(productMovementServiceUtil.productMovement(e, movement, cuenta));
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
        );
        return mov.get();
    }


    




}
