package com.bootcamp51.microservices.productservice.utils;

import com.bootcamp51.microservices.productservice.enums.EnumTypeMovement;
import com.bootcamp51.microservices.productservice.model.ProductSales;
import org.springframework.data.mongodb.core.aggregation.ArithmeticOperators;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.bootcamp51.microservices.productservice.constant.ConstantGeneral.FACTOR_POSITIVE;
import static com.bootcamp51.microservices.productservice.constant.ConstantGeneral.FACTOR_NEGATIVE;
import static com.bootcamp51.microservices.productservice.constant.ConstantGeneral.CODE_DEPOSIT;
import static com.bootcamp51.microservices.productservice.constant.ConstantGeneral.CODE_RETREAT;
import static com.bootcamp51.microservices.productservice.constant.ConstantGeneral.CODE_PAYMENT;
import static com.bootcamp51.microservices.productservice.constant.ConstantGeneral.CODE_CONSUMPTION;


public class Utils {

    public static String getNumberOperation(){
        LocalDateTime currentDate =  LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return currentDate.format(formatter);
    }

    public static Date getFirstDay(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();

    }

    public static Date getLastDay(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

    public static BigDecimal getFactorOperator(String typeMovement){
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

    public static Integer getTotalMovementsDeposit(ProductSales productMovement) {
        Date lastDay = getLastDay();
        Date FirstDay = getFirstDay();
        return (Integer) (int) Optional.ofNullable(productMovement.getMovements()).orElse(new ArrayList<>()).stream().filter(g ->
                g.getIndTypeMovement().equals(EnumTypeMovement.DEPOSIT.getCode()) && g.getResgistrationDate().compareTo(lastDay) <= 0 && g.getResgistrationDate().compareTo(FirstDay) >= 0
        ).count();
    }
    public  static Integer getTotalMovementsRetreat(ProductSales productMovement) {
        Date lastDay = getLastDay();
        Date FirstDay = getFirstDay();
        return (Integer) (int) Optional.ofNullable(productMovement.getMovements()).orElse(new ArrayList<>()).stream().filter(g ->
                g.getIndTypeMovement().equals(EnumTypeMovement.RETREAT.getCode()) && g.getResgistrationDate().compareTo(lastDay) <= 0 && g.getResgistrationDate().compareTo(FirstDay) >= 0
        ).count();
    }




}
