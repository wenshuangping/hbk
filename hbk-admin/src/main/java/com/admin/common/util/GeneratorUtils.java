package com.admin.common.util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class GeneratorUtils {

    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }


    /*定义LocalDateTime转字符串的格式*/
    private static final DateTimeFormatter DTF3 = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    /*生成APP支付宝授权target_id: 商户标识该次用户授权请求的ID,该值在商户端应保持唯一*/
    public static String getAliauthTargetId() {
        return "MKALIAUTH" + LocalDateTime.now().format(DTF3) + GeneratorUtils.getRandomStrByLength(5);
    }


    /**
     * 生成一定长度的随机字符串
     * @param length 指定字符串长度
     * @return 一定长度的字符串
     */
    public static String getRandomStrByLength(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }


    /**
     * 生成当前时间的yyyyMMddHHmmss格式字符串
     */
    public static String getDateStr3() {
        return LocalDateTime.now().format(DTF3);
    }

    private static final SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 将Date转字符串
     * @param date
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String formatDateToStr1(Date date) {
        if (date == null) {
            return null;
        }
        return sdf1.format(date);
    }

}
