package org.nuaa.undefined.BigDataEveryWhere.mr.ecommerce;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/7/26 19:08
 */
public class ConstCommerceValue {
    public final static String MAN_SEX = "1";
    public final static String WOMAN_SEX = "2";
    public final static String FAIL_CONSUME_CODE = "0";
    public final static String SUCCESS_CONSUME_CODE = "1";
    public final static Map<String, String> CITY_CODE_NAME_MAP = new HashMap<String, String>(){{
        put("110000", "北京");
        put("120000", "天津");
        put("130000", "河北");
        put("140000", "山西");
        put("150000", "内蒙古");
        put("210000", "辽宁");
        put("220000", "吉林");
        put("230000", "黑龙江");
        put("310000", "上海");
        put("320000", "江苏");
        put("330000", "浙江");
        put("340000", "安徽");
        put("350000", "福建");
        put("360000", "江西");
        put("370000", "山东");
        put("410000", "河南");
        put("420000", "湖北");
        put("430000", "湖南");
        put("440000", "广东");
        put("450000", "广西");
        put("460000", "海南");
        put("500000", "重庆");
        put("510000", "四川");
        put("520000", "贵州");
        put("530000", "云南");
        put("540000", "西藏");
        put("610000", "陕西");
        put("620000", "甘肃");
        put("630000", "青海");
        put("640000", "宁夏");
        put("650000", "新疆");
        put("710000", "台湾");
        put("810000", "香港");
    }};

    public final static List<String> CITY_CODE_LIST = CITY_CODE_NAME_MAP.keySet().stream().collect(Collectors.toList());

    public final static List<String> MORE_RAGE_CITY = new ArrayList<String>(){{
        add("110000");
        add("310000");
        add("320000");
        add("330000");
        add("440000");
        add("350000");
    }};

    private static Random random = new Random();

    public static String randomCityCode() {
        if (random.nextInt(100) <= 20) {
            return CITY_CODE_LIST.get(random.nextInt(CITY_CODE_LIST.size()));
        } else {
            return MORE_RAGE_CITY.get(random.nextInt(MORE_RAGE_CITY.size()));
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            System.out.println(CITY_CODE_NAME_MAP.get(randomCityCode()));
        }
    }
}
