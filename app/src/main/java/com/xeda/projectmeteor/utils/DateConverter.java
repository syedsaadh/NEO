package com.xeda.projectmeteor.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Xeda on 24-09-2017.
 */

public class DateConverter {
    public static Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    public static long getCurrentTime(){
        Long tsLong = System.currentTimeMillis();
        return tsLong;
    }
    public static String getDateFromTimeStamp(long timestamp, String format){
        try{
            DateFormat dateFormat = new SimpleDateFormat(format);
            Date newDate = (new Date(timestamp));
            return dateFormat.format(newDate);
        }
        catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
    public static Date getDateFromString(String dateString, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            Date date = sdf.parse(dateString);
            return date;
        } catch (ParseException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
