package com.jun.financialrecords.util;

/**
 * Created by jun on 2015/5/29.
 */
public class NormalFunctionUtils {

    public static float getFloatFormat(float a, int len) {
        int num = 10;
        while (len > 1) {
            num = num * 10;
            len--;
        }
        float b = (float) (Math.round(a * num)) / num;
        return b;
    }
}
