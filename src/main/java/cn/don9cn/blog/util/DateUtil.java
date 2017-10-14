package cn.don9cn.blog.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *@Author: liuxindong
 *@Description: 日期工具类
 *@Create: 2017/10/14 18:54
 *@Modify:
 **/
public class DateUtil {

    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String getCreateDateString(){
        return LocalDateTime.now().format(formatter);
    }

    public static String getModifyDateString(){
        return LocalDateTime.now().format(formatter);
    }

    /**
     * 获取30天之前的日期
     * @return
     */
    public static String getEarly30Date(){
        LocalDateTime time = LocalDateTime.now().minusDays(30);
        return time.format(formatter);
    }

}
