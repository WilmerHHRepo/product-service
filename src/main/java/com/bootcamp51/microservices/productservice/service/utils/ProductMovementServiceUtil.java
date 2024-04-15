package com.bootcamp51.microservices.productservice.service.utils;


import com.bootcamp51.microservices.productservice.enums.EnumTypeMovement;
import com.bootcamp51.microservices.productservice.model.Client;
import com.bootcamp51.microservices.productservice.model.Movement;
import com.bootcamp51.microservices.productservice.model.ProductSales;
import com.bootcamp51.microservices.productservice.model.vo.Parameter;
import com.bootcamp51.microservices.productservice.repository.ClientRepository;
import com.bootcamp51.microservices.productservice.repository.ParameterRepository;
import com.bootcamp51.microservices.productservice.service.ProductMovementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import static com.bootcamp51.microservices.productservice.enums.EnumErrorMenssage.*;
import static com.bootcamp51.microservices.productservice.constant.ConstantGeneral.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import static com.bootcamp51.microservices.productservice.constant.ConstantParameter.CODE_PARAMETER_1001;

@Service
public class ProductMovementServiceUtil {

    private final Logger logger = LoggerFactory.getLogger(ProductMovementServiceUtil.class);

    private final ParameterRepository parameterRepository;

    private final ClientRepository clientRepository;

    public ProductMovementServiceUtil(ClientRepository clientRepository, ParameterRepository parameterRepository) {
        this.clientRepository = clientRepository;
        this.parameterRepository = parameterRepository;
    }

    public Client productMovement(Client client, Movement movement, String cuenta)  throws Exception  {
        ProductMovementService<Client, Movement> exec = (a, b) -> {
            Optional.ofNullable(b).ifPresent( c -> {
                Parameter Parameter1001 =  parameterRepository.findByCodParameter(CODE_PARAMETER_1001);

                Optional<ProductSales> productSales = Optional.ofNullable(a.getProducts()).orElse(new ArrayList<>()).stream().filter(d ->
                    d.getNumAccount().equals(cuenta)
                ).findFirst();

                if (productSales.isPresent()){

                    Optional.ofNullable(Parameter1001.getListParameter().getListRuleMovement()).orElse(new ArrayList<>()).stream().filter(e ->
                            client.getIndTypeClient().equals(e.getIndTypeClient()) && productSales.get().getIndProduct().equals(e.getIndProduct())
                    ).findFirst().ifPresent(param -> {
                        BigDecimal availableBalance = productSales.get().getAvailableBalance();
                        BigDecimal countableBalance = productSales.get().getCountableBalance();

                        Integer totalMovementsDeposit = (int)Optional.ofNullable(productSales.get().getMovements()).orElse(new ArrayList<>()).stream().filter(g ->
                                g.getIndTypeMovement().equals(EnumTypeMovement.DEPOSITO.getCode())).count();
                        Integer totalMovementsRetreat = (int)Optional.ofNullable(productSales.get().getMovements()).orElse(new ArrayList<>()).stream().filter(g ->
                                g.getIndTypeMovement().equals(EnumTypeMovement.RETIRO.getCode())).count();

                        BigDecimal factor = getFactorOperator(b.getIndTypeMovement());

                        switch (productSales.get().getIndProduct()){
                            case CODE_SAVINGSACCOUNT:
                                if (totalMovementsDeposit.compareTo(Integer.valueOf(param.getMovement().getDeposit())) >= 0) {
                                    logger.warn(ERROR1003.getDescription().concat(" - ").concat(ERROR1003.getDescription()));
                                    throw new RuntimeException(ERROR1003.getDescription());
                                }
                                if (totalMovementsRetreat.compareTo(Integer.valueOf(param.getMovement().getRetreat())) >= 0) {
                                    logger.warn(ERROR1004.getDescription().concat(" - ").concat(ERROR1004.getDescription()));
                                    throw new RuntimeException(ERROR1004.getDescription());
                                }
                                if (b.getIndTypeMovement().equals(CODE_RETIRO)){
                                    if (availableBalance.compareTo(movement.getOperationAmount()) >= 0) {
                                        logger.warn(ERROR1005.getDescription().concat(" - ").concat(ERROR1005.getDescription()));
                                        throw new RuntimeException(ERROR1005.getDescription());
                                    }
                                }


                                //grabar movimiento
                                if (b.getIndTypeMovement().equals(EnumTypeMovement.DEPOSITO.getCode())){
                                    factor = new BigDecimal("1");
                                }

                                b.setRestrationDate(new Date());
                                BigDecimal operationAmount = new BigDecimal(b.getOperationAmount().multiply(factor).toString());
                                BigDecimal newAvailable = productSales.get().getAvailableBalance().add(operationAmount);
                                productSales.get().setAvailableBalance(newAvailable);
                                productSales.get().setCountableBalance(newAvailable);
                                clientRepository.addJointMovementToProductToClient(productSales.get(), a.getId(), b);
                                break;
                        }
                    });
                }else {
                    // no existe producto
                }

            });

            return null;
        };

        return exec.execute(client, movement);
    }


    private BigDecimal getFactorOperator(String typeMovement){
        BigDecimal factor = new BigDecimal(FACTOR_POSITIVE);
        switch (typeMovement){
            case CODE_DEPOSITO:
                factor = new BigDecimal(FACTOR_POSITIVE);
                break;
            case CODE_RETIRO:
                factor = new BigDecimal(FACTOR_NEGATIVE);
                break;
            case CODE_PAGO:
                factor = new BigDecimal(FACTOR_POSITIVE);
                break;
            case CODE_CONSUMO:
                factor = new BigDecimal(FACTOR_NEGATIVE);
                break;
        }
        return factor;

    }


}
