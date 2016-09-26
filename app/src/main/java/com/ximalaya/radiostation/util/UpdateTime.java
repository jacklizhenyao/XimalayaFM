package com.lanou.radiostation.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by user on 2016/7/27.
 */
public class UpdateTime {
    /**
     * @param time 传入的时间参数 单位毫秒
     * @param isToSecond 是否精确到秒 列如 几秒前更新
     */
    public String getUpdateTime(long time,boolean isToSecond){
        boolean secondFlag=isToSecond;//是否精确到秒级别
        long currentTime=System.currentTimeMillis(); //获取当前时间
        int year=getYear(currentTime);
        int yearData=getYear(time);
        if (year>yearData){
            //如果是其他年份的 直接 显示201x-00更新
            if (getMonth(time)<10){
                return getYear(time)+"-"+"0"+getMonth(time)+"更新";
            }else {
                return getYear(time)+"-"+getMonth(time)+"更新";
            }
        }else {
            //如果是今年的 就需要判断是当月还是其他月份的
            int month=getMonth(currentTime);//当前的月份
            int monthData=getMonth(time);//数据提供的月份
            if (month>monthData){
                //如果当前的月份大于数据提供的月份 只显示几个月之前跟新的即可
                return month-monthData+"个月前更新";
            }else {
                //当前的月份 判断天数
                int day=getDay(currentTime);
                int dayData=getDay(time);
                if (day>dayData){
                    return day-dayData+"天前更新";
                }else {
                    int hour=getHour(currentTime);
                    int hourData=getHour(time);
                    if (hour>hourData){
                        return hour-hourData+"小时前更新";
                    }else {
                        int min=getMinutes(currentTime);
                        int minData=getMinutes(time);
                        if (min>minData){
                            return min-minData+"分钟前更新";
                        }else {
                            if(secondFlag){
                                int second=getSecond(currentTime);
                                int secondData=getSecond(time);
                                return second-secondData+"秒前更新";
                            }else {
                                return min-minData+"分钟前更新";
                            }
                        }
                    }

                }
            }
        }

    }
    //获取当前的年 返回int类型
    public int getYear(long time){
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date date=new Date( time);
        String updateTime=dateFormat.format(date);
        String yearStr= updateTime.substring(0,4);
        int year=Integer.parseInt(yearStr);
        return year;
    }
    //获取当前的年 返回int类型
    public int getMonth(long time){
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date date=new Date( time);
        String updateTime=dateFormat.format(date);
        String monthStr= updateTime.substring(5,7);
        int month=Integer.parseInt(monthStr);
        return month;
    }
    //获取当前的年 返回int类型
    public int getDay(long time){
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date date=new Date(time);
        String updateTime=dateFormat.format(date);
        String dayStr= updateTime.substring(8);
        int day=Integer.parseInt(dayStr);
        return day;
    }
    //判断当前小时 返回int类型
    public int getHour(long time){
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=new Date(time);
        String updateTime=dateFormat.format(date);
        String hourStr= updateTime.substring(11,13);
        int hour=Integer.parseInt(hourStr);
        //系统时间不是中国的需要多加8小时
        return hour+8;
    }
    //判断当前分钟 返回int类型
    public int getMinutes(long time){
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=new Date(time);
        String updateTime=dateFormat.format(date);
        String minStr= updateTime.substring(14,16);
        int min=Integer.parseInt(minStr);
        return min;
    }
    //判断当前秒 返回int类型
    public int getSecond(long time){
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=new Date(time);
        String updateTime=dateFormat.format(date);
        String secondStr= updateTime.substring(17);
        int second=Integer.parseInt(secondStr);
        return second;
    }
}
