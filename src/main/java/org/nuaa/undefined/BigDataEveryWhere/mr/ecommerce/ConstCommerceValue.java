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
        put("110000", "北京市");
        put("120000", "天津市");
        put("130000", "河北省");
        put("140000", "山西省");
        put("150000", "内蒙古自治区");
        put("210000", "辽宁省");
        put("220000", "吉林省");
        put("230000", "黑龙江省");
        put("310000", "上海市");
        put("320000", "江苏省");
        put("330000", "浙江省");
        put("340000", "安徽省");
        put("350000", "福建省");
        put("360000", "江西省");
        put("370000", "山东省");
        put("410000", "河南省");
        put("420000", "湖北省");
        put("430000", "湖南省");
        put("440000", "广东省");
        put("450000", "广西壮族自治区");
        put("460000", "海南省");
        put("500000", "重庆市");
        put("510000", "四川省");
        put("520000", "贵州省");
        put("530000", "云南省");
        put("540000", "西藏自治区");
        put("610000", "陕西省");
        put("620000", "甘肃省");
        put("630000", "青海省");
        put("640000", "宁夏回族自治区");
        put("650000", "新疆维吾尔自治区");
        put("710000", "台湾省");
        put("810000", "香港特别行政区");
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
