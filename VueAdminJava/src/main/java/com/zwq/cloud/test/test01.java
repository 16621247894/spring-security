package com.zwq.cloud.test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author wuqing.zhu
 * @date 2021/09/22 15:13
 */
public class test01 {
    public static void main(String[] args) {
        LocalDate date1 = LocalDate.now();
        System.out.println("获取当前时间为:" + date1);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date2 = stringToLocalDate("2021-09-11", dateTimeFormatter);
        System.out.println("获取的日期2:" + date2);
        System.out.println("相差多少天:" + (date1.toEpochDay() - date2.toEpochDay()));

    }

    /**
     * 字符串转换为LocalDate
     *
     * @param date      入参日期
     * @param formatter 入参格式化格式
     * @return
     */
    public static LocalDate stringToLocalDate(String date, DateTimeFormatter formatter) {
        return LocalDate.parse(date, formatter);
    }
}
