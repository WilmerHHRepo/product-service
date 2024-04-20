package com.bootcamp51.microservices.productservice.controller;

import com.bootcamp51.microservices.productservice.client.ApiClient;
import com.bootcamp51.microservices.productservice.dto.ProductMovementDTO;
import com.bootcamp51.microservices.productservice.model.Movement;
import com.bootcamp51.microservices.productservice.service.utils.ProductMovementServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;


/**
 * class ProductMovementController.
 */
@RestController
@RequestMapping("/movement")
public class ProductMovementController {

    @Autowired
    private ProductMovementServiceUtil productMovementServiceUtil;

    @Autowired
    private ApiClient apiClient;

    /**
     * REST productMovement.
     *
     * @param account number account
     * @return Movement
     * @throws Exception for error
     */
    @PatchMapping("/{account}")
    public ResponseEntity<Movement> productMovement(
            @RequestBody Movement movement,
            @PathVariable String account)  throws Exception  {
        //Movement movement = productMovementRequest.getMovement();
        movement = productMovementServiceUtil.productMovement(movement, account);
        return new ResponseEntity<>(movement, HttpStatus.ACCEPTED);
    }

    /**
     * REST transferBetweenAccounts.
     *
     * @param movement for movement
     * @param origin for Origin Account
     * @param destination for destination Account
     * @param document for number document client
     * @return Movement
     * @throws Exception for error
     */
    @PatchMapping("/by-between-client/{origin}/and/{destination}")
    public ResponseEntity<Movement> transferBetweenAccountsClient(
            @RequestBody Movement movement,
            @PathVariable String origin,
            @PathVariable String destination,
            @PathParam("document") String document) throws Exception {
        movement = productMovementServiceUtil.transferBetweenAccountsClient(movement, origin, destination, document);
        return new ResponseEntity<>(movement, HttpStatus.ACCEPTED);
    }

    @PatchMapping("/by-between-third-parties/{origin}/and/{destination}")
    public ResponseEntity<Movement> transferBetweenAccountsThirdParties(
            @RequestBody Movement movement,
            @PathVariable String origin,
            @PathVariable String destination,
            @PathParam("originDocument") String originDocument,
            @PathParam("destinationDocument") String destinationDocument
            ) throws Exception {
        movement = productMovementServiceUtil.transferBetweenAccountsThirdParties(movement, origin, destination, originDocument, destinationDocument);
        return new ResponseEntity<>(movement, HttpStatus.ACCEPTED);
    }



}
