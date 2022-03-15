package site.chengjunjie.demo.hutool.util;

import cn.hutool.core.util.NumberUtil;

import java.math.RoundingMode;

public class NumUtil {
    /**
     * 保留小数设置
     * @param num
     * @return
     */
    public static double round(double num){
        return NumberUtil.round(num, 2, RoundingMode.HALF_UP).doubleValue();
    }
}
