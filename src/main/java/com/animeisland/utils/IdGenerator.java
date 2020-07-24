package com.animeisland.utils;

import java.util.Random;

/**
 * id生成器
 * @author Frank Fang
 * @since 1.0
 */
public class IdGenerator {

    /**
     * 基本id生成器
     * 1. 该算法生成的id由两部分组成：base(基于日期, 共6位) + random(随机产生, 取决于max的值)
     * 2. random的取值范围为：[0, max) 所以共可产生max个随机数
     * 3. 该方法不适用于分布式系统的id生成
     * @param date 规格化的日期
     * @param regex 正则表达式
     * @param max 能产生多少个不同的id
     * @return id 生成的id
     */
    public static Integer baseGenerator(String date, String regex, int max) {
        // 将规格化的日期转换为字符串
        String[] array = date.split(regex);
        String base = array[0] + array[1]; // yyyy + MM
        // 将字符串转换为Integer类型
        Integer id_base = Integer.valueOf(base);
        // 生成随机数
        Random random = new Random();
        // 返回生成的id
        return id_base * max + random.nextInt(max);
    }

    public static String videoIdGenerator(Integer mediaId, int order) {
        return Integer.toHexString(mediaId).toUpperCase() + Integer.toHexString(order).toUpperCase();
    }

}
