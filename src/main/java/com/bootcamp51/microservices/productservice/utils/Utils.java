package com.bootcamp51.microservices.productservice.utils;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import static com.bootcamp51.microservices.productservice.constant.ConstantGeneral.*;
import static com.bootcamp51.microservices.productservice.constant.ConstantGeneral.FACTOR_NEGATIVE;

public class Utils {

    public static String getNumberOperation(){
        Random random = new Random();
        int randomNumber = 10000000 * random.nextInt(90000000);
        return String.valueOf(randomNumber);
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
}
