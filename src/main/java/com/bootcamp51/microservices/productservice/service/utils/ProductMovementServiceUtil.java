package com.bootcamp51.microservices.productservice.service.utils;


import com.bootcamp51.microservices.productservice.client.ApiClient;
import com.bootcamp51.microservices.productservice.enums.EnumTypeMovement;
import com.bootcamp51.microservices.productservice.model.Client;
import com.bootcamp51.microservices.productservice.model.Movement;
import com.bootcamp51.microservices.productservice.model.ProductSales;
import com.bootcamp51.microservices.productservice.model.vo.Parameter;
import com.bootcamp51.microservices.productservice.model.vo.RuleMovement;
import com.bootcamp51.microservices.productservice.repository.ClientRepository;
import com.bootcamp51.microservices.productservice.repository.ParameterRepository;
import com.bootcamp51.microservices.productservice.service.ProductMovementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Mono;

import static com.bootcamp51.microservices.productservice.enums.EnumErrorMenssage.*;
import static com.bootcamp51.microservices.productservice.constant.ConstantGeneral.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import static com.bootcamp51.microservices.productservice.constant.ConstantParameter.CODE_PARAMETER_1001;

@Service
public class ProductMovementServiceUtil {

    private final Logger logger = LoggerFactory.getLogger(ProductMovementServiceUtil.class);

    private final ParameterRepository parameterRepository;

    private final ClientRepository clientRepository;

    private final ApiClient apiClient;

    public ProductMovementServiceUtil(ClientRepository clientRepository, ParameterRepository parameterRepository, ApiClient apiClient) {
        this.clientRepository = clientRepository;
        this.parameterRepository = parameterRepository;
        this.apiClient = apiClient;
    }

    public Mono<Client> productMovement(Client client, Movement movement, String account)  throws Exception  {
        ProductMovementService<Client, Movement> exec = (a, b) -> {
            Optional.ofNullable(b).ifPresent( c -> {
                Parameter parameter1001 =  parameterRepository.findByCodParameter(CODE_PARAMETER_1001);

                Optional<ProductSales> productSales = Optional.ofNullable(a.getProducts()).orElse(new ArrayList<>()).stream().filter(d ->
                    d.getNumAccount().equals(account)
                ).findFirst();
                if (productSales.isPresent()){
                    Optional.ofNullable(parameter1001.getListParameter().getListRuleMovement()).orElse(new ArrayList<>()).stream().filter(e ->
                            a.getIndTypeClient().equals(e.getIndTypeClient()) && productSales.get().getIndProduct().equals(e.getIndProduct())
                    ).findFirst().ifPresent(param -> {
                        executeAccountOperations(productSales.get(), param, b, a.getId());
                    });
                }else {
                    logger.warn(ERROR1006.getDescription().concat(" - ").concat(ERROR1006.getDescription()));
                    throw new RuntimeException(ERROR1006.getDescription());
                }
            });
            //return clientRepository.findById(a.getId());
            return apiClient.findByID(a.getId()).block();
        };
        return Mono.just(exec.execute(client, movement));
    }


    private BigDecimal getFactorOperator(String typeMovement){
        BigDecimal factor = new BigDecimal(FACTOR_POSITIVE);
        switch (typeMovement){
            case CODE_DEPOSIT:
                factor = new BigDecimal(FACTOR_POSITIVE);
                break;
            case CODE_RETREAT:
                factor = new BigDecimal(FACTOR_NEGATIVE);
                break;
            case CODE_PAYMENT:
                factor = new BigDecimal(FACTOR_POSITIVE);
                break;
            case CODE_CONSUMPTION:
                factor = new BigDecimal(FACTOR_NEGATIVE);
                break;
        }
        return factor;
    }

    private Date getFirstDay(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();

    }

    private Date getLastDay(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

    private void executeAccountOperations(ProductSales productSales, RuleMovement ruleMovement, Movement movement, String id){
        BigDecimal availableBalance = productSales.getAvailableBalance();
        BigDecimal countableBalance = productSales.getCountableBalance();
        Date lastDay = getLastDay();
        Date FirstDay = getFirstDay();

        Integer totalMovementsDeposit = (int)Optional.ofNullable(productSales.getMovements()).orElse(new ArrayList<>()).stream().filter(g ->
                g.getIndTypeMovement().equals(EnumTypeMovement.DEPOSITO.getCode()) && g.getResgistrationDate().compareTo(lastDay) <= 0 && g.getResgistrationDate().compareTo(FirstDay) >= 0
        ).count();
        Integer totalMovementsRetreat = (int)Optional.ofNullable(productSales.getMovements()).orElse(new ArrayList<>()).stream().filter(g ->
                g.getIndTypeMovement().equals(EnumTypeMovement.RETIRO.getCode()) && g.getResgistrationDate().compareTo(lastDay) <= 0 && g.getResgistrationDate().compareTo(FirstDay) >= 0
        ).count();

        BigDecimal factor = getFactorOperator(movement.getIndTypeMovement());

        if (movement.getIndTypeMovement().equals(CODE_RETREAT) || movement.getIndTypeMovement().equals(CODE_CONSUMPTION)){
            if (movement.getOperationAmount().compareTo(availableBalance) > 0) {
                logger.warn(ERROR1005.getCode().concat(" - ").concat(ERROR1005.getDescription()));
                throw new RuntimeException(ERROR1005.getDescription());
            }
            if (totalMovementsRetreat.compareTo(Integer.valueOf(ruleMovement.getMovement().getRetreat())) >= 0) {
                logger.warn(ERROR1004.getCode().concat(" - ").concat(ERROR1004.getDescription()));
                throw new RuntimeException(ERROR1004.getDescription());
            }
        }

        if (movement.getIndTypeMovement().equals(CODE_DEPOSIT) || movement.getIndTypeMovement().equals(CODE_PAYMENT)){
            if (totalMovementsDeposit.compareTo(Integer.valueOf(ruleMovement.getMovement().getDeposit())) >= 0) {
                logger.warn(ERROR1003.getCode().concat(" - ").concat(ERROR1003.getDescription()));
                throw new RuntimeException(ERROR1003.getDescription());
            }
        }

        movement.setResgistrationDate(new Date());
        BigDecimal operationAmount = new BigDecimal(movement.getOperationAmount().multiply(factor).toString());
        BigDecimal newAvailable = productSales.getAvailableBalance().add(operationAmount);
        productSales.setAvailableBalance(newAvailable);
        productSales.setCountableBalance(newAvailable);
        clientRepository.addJointMovementToProductToClient(productSales, id, movement);
    }
}
