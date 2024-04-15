package com.bootcamp51.microservices.productservice.controller;

import com.bootcamp51.microservices.productservice.dto.ProductMovementDTO;
import com.bootcamp51.microservices.productservice.model.Client;
import com.bootcamp51.microservices.productservice.model.Movement;
import com.bootcamp51.microservices.productservice.service.utils.ProductMovementServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bootcamp51/ms/product/Movement")
public class ProductMovementController {

    @Autowired
    private ProductMovementServiceUtil productMovementServiceUtil;

    @PatchMapping("/{cuenta}")
    public ResponseEntity<Client> productMovement(@RequestBody ProductMovementDTO productMovementRequest, @PathVariable String cuenta)  throws Exception  {
        Client client = productMovementRequest.getClient();
        Movement movement = productMovementRequest.getMovement();
        client = productMovementServiceUtil.productMovement(client, movement, cuenta);
        return new ResponseEntity<>(client, HttpStatus.ACCEPTED);
    }

}
