package com.example.spring.hutool.core;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;

import java.util.Calendar;
import java.util.Date;

public class DateDemo {

    public static void main(String[] args) {
        //当前时间
        Date date1 = DateUtil.date();
        //当前时间
        Date date2 = DateUtil.date(Calendar.getInstance());
        //当前时间
        Date date3 = DateUtil.date(System.currentTimeMillis());
        //当前时间字符串，格式：yyyy-MM-dd HH:mm:ss
        String now = DateUtil.now();
        //当前日期字符串，格式：yyyy-MM-dd
        String today = DateUtil.today();


        String dateStr = "2017-03-01";
        Date date = DateUtil.parse(dateStr);

        //结果 2017/03/01
        String format = DateUtil.format(date, "yyyy/MM/dd");

        //常用格式的格式化，结果：2017-03-01
        String formatDate = DateUtil.formatDate(date);

        //结果：2017-03-01 00:00:00
        String formatDateTime = DateUtil.formatDateTime(date);

        //结果：00:00:00
        String formatTime = DateUtil.formatTime(date);

        String dateStr1 = "2017-03-01 22:33:23";
        Date datew = DateUtil.parse(dateStr1);

        String dateStr2 = "2017-04-01 23:33:23";
        Date datee = DateUtil.parse(dateStr2);

        //相差一个月，31天
        long betweenDay = DateUtil.between(date1, date2, DateUnit.DAY);


    }
}
