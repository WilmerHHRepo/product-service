package com.bootcamp51.microservices.productservice.service.utils;

import static com.bootcamp51.microservices.productservice.constant.ConstantParameter.*;

import static com.bootcamp51.microservices.productservice.enums.EnumProduct.*;

import static com.bootcamp51.microservices.productservice.enums.EnumTypeClient.*;

import static com.bootcamp51.microservices.productservice.enums.EnumErrorMenssage.*;
import com.bootcamp51.microservices.productservice.model.Client;
import com.bootcamp51.microservices.productservice.model.JointAccount;
import com.bootcamp51.microservices.productservice.model.ProductSales;
import com.bootcamp51.microservices.productservice.model.vo.Parameter;
import com.bootcamp51.microservices.productservice.model.vo.RuleSales;
import com.bootcamp51.microservices.productservice.repository.ClientRepository;
import com.bootcamp51.microservices.productservice.repository.ParameterRepository;
import com.bootcamp51.microservices.productservice.service.ProductSalesService;
import lombok.RequiredArgsConstructor;
import org.apache.el.util.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductSalesServiceUtil {

    private final Logger logger = LoggerFactory.getLogger(ProductSalesServiceUtil.class);

    private final ParameterRepository parameterRepository;

    private final ClientRepository clientRepository;

    public ProductSalesServiceUtil(ClientRepository clientRepository, ParameterRepository parameterRepository) {
        this.clientRepository = clientRepository;
        this.parameterRepository = parameterRepository;
    }

    public Client productSales(Client client, ProductSales newProductSales, JointAccount jointAccount) throws Exception {
        try {
            ProductSalesService<Client, ProductSales, JointAccount> exec = (b, c) -> {
                Optional.ofNullable(b).ifPresent(e -> {
                    Mono<Parameter> Parameter1001 =  parameterRepository.findByCodParameter(CODE_PARAMETER_1001);

                    List<ProductSales> productSalesList = Optional.ofNullable(client.getProducts()).orElse(new ArrayList<>()).stream().filter(f ->
                            e.getIndTypeProduct().equals(f.getIndTypeProduct()) && e.getIndProduct().equals(f.getIndProduct())
                    ).collect(Collectors.toList());

                    Optional<RuleSales> RuleSales =  Optional.ofNullable(Objects.requireNonNull(Parameter1001.block()).getListParameter().getListRuleSales()).orElse(new ArrayList<>()).stream().filter(g ->
                            client.getIndTypeClient().equals(g.getIndTypeClient()) && e.getIndProduct().equals(g.getIndProduct())
                    ).findFirst();
                    if (RuleSales.isPresent()){
                        if (!RuleSales.get().getNumber().equals(ALL)) {
                            if (Integer.valueOf(productSalesList.size()).compareTo(Integer.valueOf(RuleSales.get().getNumber())) >= 0) {
                                logger.warn(ERROR1001.getDescription().concat(" - ").concat(ERROR1001.getDescription()));
                                throw new RuntimeException(ERROR1001.getDescription());
                            }
                        }
                    }else {
                        logger.warn(ERROR1002.getDescription().concat(" - ").concat(ERROR1002.getDescription()));
                        throw new RuntimeException(ERROR1002.getDescription());
                    }
                    client.setProducts(new ArrayList<>());
                    client.getProducts().add(e);
                    clientRepository.addProductToClient(e, client.getId());
                    if (e.getIndProduct().equals(COMMERCIALCREDIT.getCode()) && client.getIndTypeClient().equals(COMPANY.getCode())) {
                        Optional.ofNullable(c.getMembers()).ifPresent(members -> {
                            for (String m: members){
                                clientRepository.addProductToClient(e, m);
                                clientRepository.addJointAccountToProductToClient(b, m, c);
                            }
                            clientRepository.addJointAccountToProductToClient(b, client.getId(), c);
                        });
                    }
                });
                return client;
            };
            return exec.execute(newProductSales, jointAccount);

        }catch (Exception e){
            logger.error("ERROR: " + e.getMessage());
            throw new Exception(e);
        }





    }



}
