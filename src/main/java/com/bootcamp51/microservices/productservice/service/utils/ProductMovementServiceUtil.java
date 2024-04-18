package com.bootcamp51.microservices.productservice.service.utils;


import com.bootcamp51.microservices.productservice.client.ApiClient;
import com.bootcamp51.microservices.productservice.enums.EnumTypeMovement;
import com.bootcamp51.microservices.productservice.model.Client;
import com.bootcamp51.microservices.productservice.model.Commission;
import com.bootcamp51.microservices.productservice.model.Movement;
import com.bootcamp51.microservices.productservice.model.ProductSales;
import com.bootcamp51.microservices.productservice.model.vo.Parameter;
import com.bootcamp51.microservices.productservice.model.vo.RuleMovement;
import com.bootcamp51.microservices.productservice.repository.ClientRepository;
import com.bootcamp51.microservices.productservice.repository.CommissionRepository;
import com.bootcamp51.microservices.productservice.repository.ParameterRepository;
import com.bootcamp51.microservices.productservice.service.ProductMovementService;
import com.bootcamp51.microservices.productservice.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import static com.bootcamp51.microservices.productservice.enums.EnumErrorMenssage.*;
import static com.bootcamp51.microservices.productservice.constant.ConstantGeneral.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import static com.bootcamp51.microservices.productservice.constant.ConstantParameter.*;

@Service
public class ProductMovementServiceUtil {

    private final Logger logger = LoggerFactory.getLogger(ProductMovementServiceUtil.class);

    private final ParameterRepository parameterRepository;

    private final ClientRepository clientRepository;

    private final CommissionRepository commissionRepository;

    private final ApiClient apiClient;


    public ProductMovementServiceUtil(ClientRepository clientRepository, ParameterRepository parameterRepository, CommissionRepository commissionRepository, ApiClient apiClient) throws Exception {
        this.clientRepository = clientRepository;
        this.parameterRepository = parameterRepository;
        this.commissionRepository = commissionRepository;
        this.apiClient = apiClient;
    }

    public Movement productMovement(Movement movement, String account) throws Exception  {

        try{
            ProductMovementService<Movement> exec = (b) -> {
                AtomicReference<Movement> mov = new AtomicReference<>(new Movement());
                Mono<Client> monoClient = apiClient.findByAccount(account);
                Client client = monoClient.doOnSubscribe(System.out::println).block();
                Optional.ofNullable(b).ifPresent( c -> {
                    Mono<Parameter> parameter1001 =  parameterRepository.findByCodParameter(CODE_PARAMETER_1001);
                    Parameter param = parameter1001.doOnSubscribe(System.out::println).block();
                    Optional<ProductSales> productMovement = Optional.ofNullable(Objects.requireNonNull(client).getProducts()).orElse(new ArrayList<>()).stream().filter(d ->
                            d.getNumAccount().equals(account)
                    ).findFirst();
                    if (productMovement.isPresent()){
                        Optional.ofNullable(Objects.requireNonNull(param).getListParameter().getListRuleMovement()).orElse(new ArrayList<>()).stream().filter(e ->
                                client.getIndTypeClient().equals(e.getIndTypeClient()) && productMovement.get().getIndProduct().equals(e.getIndProduct())
                        ).findFirst().ifPresent(ruleMovement -> {
                            mov.set(executeAccountOperations(productMovement.get(), ruleMovement, b, client));
                        });
                    } else {
                        logger.warn(ERROR1006.getDescription().concat(" - ").concat(ERROR1006.getDescription()));
                        throw new RuntimeException(ERROR1006.getDescription());
                    }
                });
                return mov.get();
            };
            return exec.execute(movement);
        }catch (Exception e){
            logger.error("ERROR: {}", e.getMessage());
            throw new Exception(e);
        }
    }


    public Movement transferBetweenAccountsClient(Movement movement, String originAccount, String destinationAccount, String document) throws Exception {
        try{
            ProductMovementService<Movement> exec = (a) ->{
                Mono<Client> monoClient = apiClient.findByDocument(document);
                Client client = Objects.requireNonNull(monoClient.doOnSubscribe(System.out::println).block());

                Optional<ProductSales> productSalesDestination = Optional.ofNullable(client.getProducts()).orElse(new ArrayList<>()).stream().filter(productOrigin ->
                        productOrigin.getNumAccount().equals(destinationAccount)
                ).findFirst();
                if (!productSalesDestination.isPresent()) {
                    logger.warn(ERROR1007.getDescription().concat(" - ").concat(ERROR1007.getDescription()));
                    throw new RuntimeException(ERROR1007.getDescription());
                }

                Optional<ProductSales> productSalesOrigin = Optional.of(client.getProducts()).orElse(new ArrayList<>()).stream().filter(productOrigin ->
                        productOrigin.getNumAccount().equals(originAccount)
                ).findFirst();

                movement.setNumOperation(Utils.getNumberOperation());
                movement.setResgistrationDate(new Date());
                movement.setOriginAccount(originAccount);
                movement.setDestinationAccount(destinationAccount);

                if (productSalesOrigin.isPresent()) {
                    BigDecimal availableBalance = productSalesOrigin.get().getAvailableBalance();
                    if (movement.getOperationAmount().compareTo(availableBalance) > 0) {
                        logger.warn(ERROR1005.getCode().concat(" - ").concat(ERROR1005.getDescription()));
                        throw new RuntimeException(ERROR1005.getDescription());
                    }
                    BigDecimal factor = Utils.getFactorOperator(CODE_RETREAT);
                    BigDecimal operationAmount = new BigDecimal(movement.getOperationAmount().multiply(factor).toString());
                    BigDecimal newAvailable = productSalesOrigin.get().getAvailableBalance().add(operationAmount);
                    productSalesOrigin.get().setAvailableBalance(newAvailable);
                    productSalesOrigin.get().setCountableBalance(newAvailable);
                    clientRepository.addJointMovementToProductToClient(productSalesOrigin.get(), client.getId(), movement).subscribe(System.out::println);
                } else {
                    logger.warn(ERROR1005.getDescription().concat(" - ").concat(ERROR1005.getDescription()));
                    throw new RuntimeException(ERROR1005.getDescription());
                }
                BigDecimal factor = Utils.getFactorOperator(CODE_PAYMENT);
                BigDecimal operationAmount = new BigDecimal(movement.getOperationAmount().multiply(factor).toString());
                BigDecimal newAvailable = productSalesDestination.get().getAvailableBalance().add(operationAmount);
                productSalesDestination.get().setAvailableBalance(newAvailable);
                productSalesDestination.get().setCountableBalance(newAvailable);
                clientRepository.addJointMovementToProductToClient(productSalesDestination.get(), client.getId(), movement).subscribe(System.out::println);
                return movement;
            };
            return exec.execute(movement);
        }catch (Exception e){
            logger.error("ERROR: {}", e.getMessage());
            throw new Exception(e);
        }

    }

    public Movement transferBetweenAccountsThirdParties(Movement movement, String originAccount, String destinationAccount, String originDocument, String destinationDocument) throws Exception {
        try {
            ProductMovementService<Movement> exec = (a) ->{
                Mono<Client> monoOriginclient = apiClient.findByDocument(originDocument);
                Mono<Client> monoDestinationclient = apiClient.findByDocument(destinationDocument);
                Client originClient = Objects.requireNonNull(monoOriginclient.doOnSubscribe(System.out::println).block());
                Client destinationClient = Objects.requireNonNull(monoDestinationclient.doOnSubscribe(System.out::println).block());

                Optional<ProductSales> productSalesDestination = Optional.ofNullable(destinationClient.getProducts()).orElse(new ArrayList<>()).stream().filter(productOrigin ->
                        productOrigin.getNumAccountCodeInterbank().equals(destinationAccount)
                ).findFirst();
                if (!productSalesDestination.isPresent()) {
                    logger.warn(ERROR1007.getDescription().concat(" - ").concat(ERROR1007.getDescription()));
                    throw new RuntimeException(ERROR1007.getDescription());
                }

                Optional<ProductSales> productSalesOrigin = Optional.of(originClient.getProducts()).orElse(new ArrayList<>()).stream().filter(productOrigin ->
                        productOrigin.getNumAccount().equals(originAccount)
                ).findFirst();

                movement.setNumOperation(Utils.getNumberOperation());
                movement.setResgistrationDate(new Date());
                movement.setOriginAccount(originAccount);
                movement.setDestinationAccount(destinationAccount);

                if (productSalesOrigin.isPresent()) {
                    BigDecimal availableBalance = productSalesOrigin.get().getAvailableBalance();
                    if (movement.getOperationAmount().compareTo(availableBalance) > 0) {
                        logger.warn(ERROR1005.getCode().concat(" - ").concat(ERROR1005.getDescription()));
                        throw new RuntimeException(ERROR1005.getDescription());
                    }
                    BigDecimal factor = Utils.getFactorOperator(CODE_RETREAT);
                    BigDecimal operationAmount = new BigDecimal(movement.getOperationAmount().multiply(factor).toString());
                    BigDecimal newAvailable = productSalesOrigin.get().getAvailableBalance().add(operationAmount);
                    productSalesOrigin.get().setAvailableBalance(newAvailable);
                    productSalesOrigin.get().setCountableBalance(newAvailable);
                    clientRepository.addJointMovementToProductToClient(productSalesOrigin.get(), originClient.getId(), movement).subscribe(System.out::println);
                } else {
                    logger.warn(ERROR1005.getDescription().concat(" - ").concat(ERROR1005.getDescription()));
                    throw new RuntimeException(ERROR1005.getDescription());
                }
                BigDecimal factor = Utils.getFactorOperator(CODE_PAYMENT);
                BigDecimal operationAmount = new BigDecimal(movement.getOperationAmount().multiply(factor).toString());
                BigDecimal newAvailable = productSalesDestination.get().getAvailableBalance().add(operationAmount);
                productSalesDestination.get().setAvailableBalance(newAvailable);
                productSalesDestination.get().setCountableBalance(newAvailable);
                clientRepository.addJointMovementToProductToClient(productSalesDestination.get(), destinationClient.getId(), movement).subscribe(System.out::println);

                return movement;
            };
            return exec.execute(movement);
        } catch (Exception e) {
            logger.error("ERROR: {}", e.getMessage());
            throw new Exception(e);
        }
    }


    private Movement executeAccountOperations(ProductSales productMovement, RuleMovement ruleMovement, Movement movement, Client client) {
        BigDecimal availableBalance = productMovement.getAvailableBalance();
        Date lastDay = Utils.getLastDay();
        Date FirstDay = Utils.getFirstDay();
        movement.setNumOperation(Utils.getNumberOperation());
        Integer totalMovementsDeposit = Utils.getTotalMovementsDeposit(productMovement);
        Integer totalMovementsRetreat = Utils.getTotalMovementsRetreat(productMovement);
        BigDecimal factor = Utils.getFactorOperator(movement.getIndTypeMovement());

        if (movement.getIndTypeMovement().equals(EnumTypeMovement.RETREAT.getCode()) || movement.getIndTypeMovement().equals(EnumTypeMovement.CONSUMPTION.getCode())) {
            if (movement.getOperationAmount().compareTo(availableBalance) > 0) {
                logger.warn(ERROR1005.getCode().concat(" - ").concat(ERROR1005.getDescription()));
                throw new RuntimeException(ERROR1005.getDescription());
            }
            if (totalMovementsRetreat.compareTo(Integer.valueOf(ruleMovement.getMovement().getRetreat())) >= 0) {
                logger.warn(ERROR1004.getCode().concat(" - ").concat(ERROR1004.getDescription()));
                movement.setRelativeAmount(movement.getOperationAmount()); /// elq ue se entrega al client
                movement.setOperationAmount(movement.getRelativeAmount().add(new BigDecimal(ruleMovement.getCommission())));  // el que se cobra de la cuenta
                movement.setCommission(new BigDecimal(ruleMovement.getCommission()));
                executeCommissionOperations(productMovement, ruleMovement, movement, client);
                //throw new RuntimeException(ERROR1004.getDescription());
            }
        }

        if (movement.getIndTypeMovement().equals(EnumTypeMovement.DEPOSIT.getCode()) || movement.getIndTypeMovement().equals(EnumTypeMovement.PAYMENT.getCode())) {
            if (totalMovementsDeposit.compareTo(Integer.valueOf(ruleMovement.getMovement().getDeposit())) >= 0) {
                logger.warn(ERROR1003.getCode().concat(" - ").concat(ERROR1003.getDescription()));
                movement.setRelativeAmount(movement.getOperationAmount().subtract(new BigDecimal(ruleMovement.getCommission()))); /// elq ue se entrega al client
                movement.setCommission(new BigDecimal(ruleMovement.getCommission()));
                executeCommissionOperations(productMovement, ruleMovement, movement, client);
                //throw new RuntimeException(ERROR1003.getDescription());
            }
        }

        movement.setResgistrationDate(new Date());
        BigDecimal operationAmount = new BigDecimal(movement.getOperationAmount().multiply(factor).toString());
        BigDecimal newAvailable = productMovement.getAvailableBalance().add(operationAmount);
        productMovement.setAvailableBalance(newAvailable);
        productMovement.setCountableBalance(newAvailable);
        clientRepository.addJointMovementToProductToClient(productMovement, client.getId(), movement).subscribe(System.out::println);
        return movement;
    }

    private void executeCommissionOperations(ProductSales productMovement, RuleMovement ruleMovement, Movement movement, Client client) {
        Commission commission =  new Commission();
        commission.setNumOperation(movement.getNumOperation());
        commission.setIndTypeProduct(productMovement.getIndTypeProduct());
        commission.setDesTypeProduct(productMovement.getDesTypeProduct());
        commission.setIndProduct(productMovement.getIndProduct());
        commission.setDesProduct(productMovement.getDesProduct());
        commission.setIndTypeDocument(client.getIndTypeDocument());
        commission.setDesTypeDocument(client.getDesTypeDocument());
        commission.setNumDocument(client.getNumDocument());
        commission.setIndTypeMovement(movement.getIndTypeMovement());
        commission.setDesTypeMovement(movement.getDesTypeMovement());
        commission.setNumAccount(productMovement.getNumAccount());
        commission.setAvailableBalance(productMovement.getAvailableBalance());
        commission.setRelativeAmount(movement.getRelativeAmount());
        commission.setOperationAmount(movement.getOperationAmount());
        commission.setCommission(new BigDecimal(ruleMovement.getCommission()));
        commission.setRegistrationDate(new Date());
        commissionRepository.save(commission).subscribe(System.out::println);;
    }



}
