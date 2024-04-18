package com.bootcamp51.microservices.productservice.service.utils;

import static com.bootcamp51.microservices.productservice.constant.ConstantParameter.*;

import static com.bootcamp51.microservices.productservice.enums.EnumProduct.*;

import static com.bootcamp51.microservices.productservice.enums.EnumTypeClient.*;

import static com.bootcamp51.microservices.productservice.enums.EnumErrorMenssage.*;

import com.bootcamp51.microservices.productservice.client.ApiClient;
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

    private final ApiClient apiClient;

    public ProductSalesServiceUtil(ClientRepository clientRepository, ParameterRepository parameterRepository, ApiClient apiClient) {
        this.clientRepository = clientRepository;
        this.parameterRepository = parameterRepository;
        this.apiClient = apiClient;
    }

    public Client productSales(ProductSales newProductSales, JointAccount jointAccount, String document) throws Exception {
        try {
            ProductSalesService<Client, ProductSales, JointAccount> exec = (b, c) -> {
                Mono<Client> monoClient = apiClient.findByDocument(document);
                Client client = monoClient.doOnSubscribe(System.out::println).block();
                Optional.ofNullable(b).ifPresent(e -> {
                    Mono<Parameter> parameter1001 =  parameterRepository.findByCodParameter(CODE_PARAMETER_1001);
                    Parameter param = parameter1001.doOnSubscribe(System.out::println).block();
                    List<ProductSales> productSalesList = Optional.ofNullable(Objects.requireNonNull(client).getProducts()).orElse(new ArrayList<>()).stream().filter(f ->
                            e.getIndTypeProduct().equals(f.getIndTypeProduct()) && e.getIndProduct().equals(f.getIndProduct())
                    ).collect(Collectors.toList());

                    Optional<RuleSales> RuleSales =  Optional.ofNullable(Objects.requireNonNull(param).getListParameter().getListRuleSales()).orElse(new ArrayList<>()).stream().filter(g ->
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
                    clientRepository.addProductToClient(e, client.getId()).subscribe(System.out::println);
                    if (e.getIndProduct().equals(COMMERCIAL_CREDIT.getCode()) && client.getIndTypeClient().equals(COMPANY.getCode())) {
                        Optional.ofNullable(c.getMembers()).ifPresent(members -> {
                            for (String m: members){
                                clientRepository.addProductToClient(e, m).subscribe(System.out::println);
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
            logger.error("ERROR: {}", e.getMessage());
            throw new Exception(e);
        }





    }



}
