package com.example.spring.freemark.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MathUtil {

    /**
     * 默认除法运算精度
     */
    private static final int DEFAULT_SCALE = 10;

    /**
     * 转换为{@link BigDecimal}
     * <p>为保证精度，先转成{@link String}然后再用构造函数</p>
     *
     * @param value 数值
     * @return {@link BigDecimal}
     */
    private static BigDecimal convert(Number value) {
        return value == null ? BigDecimal.ZERO : new BigDecimal(value.toString());
    }

    /**
     * 提供（相对）精确的除法运算
     * <p>当发生除不尽的情况时，精确到小数点以后10位，以后的数字四舍五入</p>
     *
     * @param v1 被除数
     * @param v2 除数
     * @return 商
     */
    public static BigDecimal div(Number v1, Number v2) {
        return convert(v1).divide(convert(v2), DEFAULT_SCALE, RoundingMode.HALF_UP);
    }
}
