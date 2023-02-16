package com.bjs.hedge.util;

import java.math.BigDecimal;
import java.util.Random;

/**
 * 精度计算器
 *
 * @author KD
 */
public class MathUtils {

    /**
     * 获取2个数字之间的随机数
     */
    public static BigDecimal random(BigDecimal begin, BigDecimal end) {
        begin = new BigDecimal(begin.stripTrailingZeros().toPlainString());
        end = new BigDecimal(end.stripTrailingZeros().toPlainString());
        int scale = begin.scale() > end.scale() ? begin.scale() : end.scale();
        if (scale == 0) {
            scale = 1;
        }
        Random random = new Random();
        BigDecimal s = new BigDecimal(Math.pow(10, scale));
        int tmp = end.multiply(s).intValue() - begin.multiply(s).intValue() - 1;
        if (tmp <= 0) {
            tmp = 1;
        }
        int r = random.nextInt(tmp) + begin.multiply(s).intValue() + 1;
        return new BigDecimal(r).divide(s, scale, BigDecimal.ROUND_DOWN);
    }

    /**
     * 加法
     */
    public static BigDecimal add(BigDecimal a, BigDecimal b, int scale, int roundingMode) {
        return a.add(b).setScale(scale, roundingMode);
    }

    /**
     * 加法
     */
    public static BigDecimal add(BigDecimal a, BigDecimal b, int scale) {
        return a.add(b).setScale(scale, BigDecimal.ROUND_DOWN);
    }

    /**
     * 加法(保留12位精度)
     */
    public static BigDecimal addScale12(BigDecimal a, BigDecimal b) {
        return add(a, b, 12);
    }
    public static BigDecimal addScale8(BigDecimal a, BigDecimal b) {
        return add(a, b, 8);
    }

    /**
     * 加法
     */
    public static BigDecimal add(BigDecimal a, BigDecimal b, BigDecimal c, int scale) {
        return a.add(b).add(c).setScale(scale, BigDecimal.ROUND_DOWN);
    }

    /**
     * 加法(保留12位精度)
     */
    public static BigDecimal addScale12(BigDecimal a, BigDecimal b, BigDecimal c) {
        return add(a, b, c, 12);
    }

    /**
     * 减法
     */
    public static BigDecimal sub(BigDecimal a, BigDecimal b) {
        return a.subtract(b);
    }

    /**
     * 减法
     */
    public static BigDecimal sub(BigDecimal a, BigDecimal b, int scale, int roundingMode) {
        return a.subtract(b).setScale(scale, roundingMode);
    }

    /**
     * 减法
     */
    public static BigDecimal sub(BigDecimal a, BigDecimal b, int scale) {
        return a.subtract(b).setScale(scale, BigDecimal.ROUND_DOWN);
    }

    /**
     * 减法(保留12位精度)
     */
    public static BigDecimal subScale12(BigDecimal a, BigDecimal b) {
        return sub(a, b, 12);
    }

    /**
     * 减法
     */
    public static BigDecimal subScale12(BigDecimal a, BigDecimal b, BigDecimal c) {
        return sub(a, b, c, 12);
    }

    /**
     * 减法
     */
    public static BigDecimal sub(BigDecimal a, BigDecimal b, BigDecimal c, int scale) {
        return a.subtract(b).subtract(c).setScale(scale, BigDecimal.ROUND_DOWN);
    }

    /**
     * 乘法
     */
    public static BigDecimal mul(BigDecimal a, BigDecimal b, int scale, int roundingMode) {
        return a.multiply(b).setScale(scale, roundingMode);
    }

    /**
     * 乘法
     */
    public static BigDecimal mul(BigDecimal a, BigDecimal b, int scale) {
        return a.multiply(b).setScale(scale, BigDecimal.ROUND_DOWN);
    }

    /**
     * 乘法(保留12位精度)
     */
    public static BigDecimal mulScale12(BigDecimal a, BigDecimal b) {
        return mul(a, b, 12);
    }

    /**
     * 除法
     */
    public static BigDecimal div(BigDecimal a, BigDecimal b, int scale, int roundingMode) {
        if (BigDecimal.ZERO.compareTo(b) == 0) {
            return BigDecimal.ZERO;
        }
        return a.divide(b, scale, roundingMode);
    }

    /**
     * 除法
     */
    public static BigDecimal div(BigDecimal a, BigDecimal b, int scale) {
        return div(a, b, scale, BigDecimal.ROUND_DOWN);
    }

    /**
     * 除法(保留12位精度)
     */
    public static BigDecimal divScale12(BigDecimal a, BigDecimal b) {
        return div(a, b, 12);
    }


//	//转金额类型
//	public static BigDecimal toMoney(BigDecimal v){
//		BigDecimal a = new BigDecimal(String.valueOf(v));
//		return a.setScale(2, BigDecimal.ROUND_DOWN).BigDecimalValue();
//	}
//	
//	//转数量类型
//	public static BigDecimal toCoinNum(BigDecimal v){
//		BigDecimal a = new BigDecimal(String.valueOf(v));
//		return a.setScale(4, BigDecimal.ROUND_DOWN).BigDecimalValue();
//	}

    /**
     * 自定义精度保留
     *
     * @param a
     * @param scale
     * @return
     */
    public static BigDecimal toScaleNum(BigDecimal a, int scale) {
        return toScaleNum(a, scale, BigDecimal.ROUND_DOWN);
    }

    /**
     * 自定义精度保留
     *
     * @param a
     * @param scale
     * @return
     */
    public static BigDecimal toScaleNum(BigDecimal a, int scale, int roundingMode) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        return a.setScale(scale, roundingMode);
    }

    /**
     * 获取double，为null时返回默认
     *
     * @param db
     * @param def
     * @return
     */
    public static Double getDouble(Double db, Double def) {
        if (db == null) {
            return def;
        }
        return db;
    }

    /**
     * 获取Integer，为null时返回默认
     *
     * @param db
     * @param def
     * @return
     */
    public static Integer getInteger(Integer db, Integer def) {
        if (db == null) {
            return def;
        }
        return db;
    }

    /**
     * 获取Integer，为null时返回默认
     *
     * @param db
     * @param def
     * @return
     */
    public static BigDecimal getBigDecimal(BigDecimal db, BigDecimal def) {
        if (db == null || db.compareTo(BigDecimal.ZERO) == 0) {
            return def;
        }
        return db;
    }

    public static void main(String [] args){
        System.out.println(MathUtils.mul(new BigDecimal(3.55), new BigDecimal(1), 20));
    }

}
