package com.zhangheng.file.util;

import java.math.BigDecimal;

public class DigitalFormatUtil {
    public static Double formatDouble2(Double num){
        BigDecimal bg = new BigDecimal(num);
        Double count= bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return count;
    }
}
