package com.bootcamp51.microservices.productservice.service.utils;

import static com.bootcamp51.microservices.productservice.constant.ConstantParameter.*;
import com.bootcamp51.microservices.productservice.model.Client;
import com.bootcamp51.microservices.productservice.model.ProductSales;
import com.bootcamp51.microservices.productservice.model.vo.Parameter;
import com.bootcamp51.microservices.productservice.repository.ParameterRepository;
import com.bootcamp51.microservices.productservice.service.ProductSalesService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductSalesServiceUtil {

    @Autowired
    private ParameterRepository parameterRepository;

    public Client productSales(Client client, ProductSales newProductSales, ObjectId id) {


        ProductSalesService<Client, ProductSales, ObjectId> exec = (b, c) -> {
            Optional.ofNullable(b).ifPresent(d -> {
                Parameter Parameter1001 =  parameterRepository.findByCodParameter(CODE_PARAMETER_1001);

                List<ProductSales> listProductSales = Optional.ofNullable(client.getProducts()).orElse(new ArrayList<>()).stream().filter(e ->
                    d.getIndTypeProduct().equals(e.getIndTypeProduct()) && d.getIndProduct().equals(e.getIndProduct())
                ).collect(Collectors.toList());

                Optional.ofNullable(Parameter1001.getListParameter().getListRuleSales()).orElse(new ArrayList<>()).stream().filter(f ->
                                client.getDesTypeClient().equals(f.getIndTypeClient()) && d.getIndProduct().equals(f.getIndProduct())
                        ).findFirst().ifPresent(g ->{
                            if (Integer.valueOf(g.getNumber()).compareTo(listProductSales.size()) > 0){
                                String abc = "Supera la cantidad de cuentas por cliente";
                                throw new StringIndexOutOfBoundsException(abc);
                            }
                });
                client.setProducts(new ArrayList<>());
                client.getProducts().add(d);
                //Graba aqui - invocar servicio
            });
            return client;
        };

        return exec.execute(newProductSales, id);


    }



}
