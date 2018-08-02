package org.nuaa.undefined.BigDataEveryWhere.mr.ecommerce;

import java.sql.Timestamp;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/7/27 9:21
 */
public class JobUtil {
    /**
     * x / y 并返回保留两位小数的百分数
     * @param x
     * @param y
     * @return
     */
    public static String polish(double x, double y) {
        return String.format("%.2f", (x / y) * 100) + "%";
    }

    /**
     * 返回yyyy-MM-dd 形式下日期较早的一个
     * @param time1
     * @param time2
     * @return
     */
    public static String compare(String time1, String time2) {
        if (time1 == null) {
            return time2;
        }
        return Timestamp.valueOf(time1+" 00:00:00").getTime() <= Timestamp.valueOf(time2+" 00:00:00").getTime() ?
                time1 : time2;
    }
}
