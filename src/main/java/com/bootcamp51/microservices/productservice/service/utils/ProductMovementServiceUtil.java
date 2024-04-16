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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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

    public ProductMovementServiceUtil(ClientRepository clientRepository, ParameterRepository parameterRepository, CommissionRepository commissionRepository, ApiClient apiClient) {
        this.clientRepository = clientRepository;
        this.parameterRepository = parameterRepository;
        this.commissionRepository = commissionRepository;
    }

    public Movement productMovement(Client client, Movement movement, String account)  throws Exception  {
        AtomicReference<Movement> mov = new AtomicReference<>(new Movement());

        ProductMovementService<Movement, Client, Movement> exec = (a, b) -> {
            Optional.ofNullable(b).ifPresent( c -> {
                Parameter parameter1001 =  parameterRepository.findByCodParameter(CODE_PARAMETER_1001);

                Optional<ProductSales> productMovement = Optional.ofNullable(a.getProducts()).orElse(new ArrayList<>()).stream().filter(d ->
                    d.getNumAccount().equals(account)
                ).findFirst();
                if (productMovement.isPresent()){
                    Optional.ofNullable(parameter1001.getListParameter().getListRuleMovement()).orElse(new ArrayList<>()).stream().filter(e ->
                            a.getIndTypeClient().equals(e.getIndTypeClient()) && productMovement.get().getIndProduct().equals(e.getIndProduct())
                    ).findFirst().ifPresent(ruleMovement -> {
                        mov.set(executeAccountOperations(productMovement.get(), ruleMovement, b, a.getId()));
                    });
                }else {
                    logger.warn(ERROR1006.getDescription().concat(" - ").concat(ERROR1006.getDescription()));
                    throw new RuntimeException(ERROR1006.getDescription());
                }
            });
            //return clientRepository.findById(a.getId());
            return mov.get();
        };
        return exec.execute(client, movement);
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

    private Movement executeAccountOperations(ProductSales productMovement, RuleMovement ruleMovement, Movement movement, String id) {

        BigDecimal availableBalance = productMovement.getAvailableBalance();
        BigDecimal countableBalance = productMovement.getCountableBalance();
        Date lastDay = getLastDay();
        Date FirstDay = getFirstDay();

        Integer totalMovementsDeposit = (int)Optional.ofNullable(productMovement.getMovements()).orElse(new ArrayList<>()).stream().filter(g ->
                g.getIndTypeMovement().equals(EnumTypeMovement.DEPOSITO.getCode()) && g.getResgistrationDate().compareTo(lastDay) <= 0 && g.getResgistrationDate().compareTo(FirstDay) >= 0
        ).count();
        Integer totalMovementsRetreat = (int)Optional.ofNullable(productMovement.getMovements()).orElse(new ArrayList<>()).stream().filter(g ->
                g.getIndTypeMovement().equals(EnumTypeMovement.RETIRO.getCode()) && g.getResgistrationDate().compareTo(lastDay) <= 0 && g.getResgistrationDate().compareTo(FirstDay) >= 0
        ).count();

        BigDecimal factor = getFactorOperator(movement.getIndTypeMovement());

        if (movement.getIndTypeMovement().equals(CODE_RETREAT) || movement.getIndTypeMovement().equals(CODE_CONSUMPTION)) {
            if (movement.getOperationAmount().compareTo(availableBalance) > 0) {
                logger.warn(ERROR1005.getCode().concat(" - ").concat(ERROR1005.getDescription()));
                throw new RuntimeException(ERROR1005.getDescription());
            }
            if (totalMovementsRetreat.compareTo(Integer.valueOf(ruleMovement.getMovement().getRetreat())) >= 0) {
                logger.warn(ERROR1004.getCode().concat(" - ").concat(ERROR1004.getDescription()));
                movement.setRelativeAmount(movement.getOperationAmount()); /// elq ue se entrega al client
                movement.setOperationAmount(movement.getRelativeAmount().add(new BigDecimal(ruleMovement.getCommission())));  // el que se cobra de la cuenta
                movement.setCommission(new BigDecimal(ruleMovement.getCommission()));
                executeCommissionOperations(productMovement, ruleMovement, movement);
                //throw new RuntimeException(ERROR1004.getDescription());
            }
        }

        if (movement.getIndTypeMovement().equals(CODE_DEPOSIT) || movement.getIndTypeMovement().equals(CODE_PAYMENT)) {
            if (totalMovementsDeposit.compareTo(Integer.valueOf(ruleMovement.getMovement().getDeposit())) >= 0) {
                logger.warn(ERROR1003.getCode().concat(" - ").concat(ERROR1003.getDescription()));
                movement.setRelativeAmount(movement.getOperationAmount().subtract(new BigDecimal(ruleMovement.getCommission()))); /// elq ue se entrega al client
                movement.setCommission(new BigDecimal(ruleMovement.getCommission()));
                //throw new RuntimeException(ERROR1003.getDescription());
            }
        }

        movement.setResgistrationDate(new Date());
        BigDecimal operationAmount = new BigDecimal(movement.getOperationAmount().multiply(factor).toString());
        BigDecimal newAvailable = productMovement.getAvailableBalance().add(operationAmount);
        productMovement.setAvailableBalance(newAvailable);
        productMovement.setCountableBalance(newAvailable);
        clientRepository.addJointMovementToProductToClient(productMovement, id, movement);
        return movement;
    }

    private void executeCommissionOperations(ProductSales productMovement, RuleMovement ruleMovement, Movement movement) {
        Commission commission =  new Commission();
        commission.setIndTypeProduct(productMovement.getIndTypeProduct());
        commission.setDesTypeProduct(productMovement.getDesTypeProduct());
        commission.setIndProduct(productMovement.getIndProduct());
        commission.setDesProduct(productMovement.getDesProduct());
        commission.setIndTypeMovement(movement.getIndTypeMovement());
        commission.setDesTypeMovement(movement.getDesTypeMovement());
        commission.setNumAccount(productMovement.getNumAccount());
        commission.setAvailableBalance(productMovement.getAvailableBalance());
        commission.setRelativeAmount(movement.getRelativeAmount());
        commission.setOperationAmount(movement.getOperationAmount());
        commission.setCommission(new BigDecimal(ruleMovement.getCommission()));
        commission.setResgistrationDate(new Date());
        commissionRepository.save(commission);
    }



}
