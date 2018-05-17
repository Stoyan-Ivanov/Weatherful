package com.stoyan.weatherful.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateTransformation {
    private static final int TIME_MULTIPLIER = 1000;

    public static String getDayOfTheWeekFromTimestamp(String unixTime ) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date dateFormat = new java.util.Date(Long.valueOf(unixTime) * TIME_MULTIPLIER);
        return sdf.format(dateFormat);
    }

    public static String getDateFromTimestamp(String unixTime) {
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(Long.valueOf(unixTime) * TIME_MULTIPLIER);

        return date.get(Calendar.DAY_OF_MONTH) + "."
                + date.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.ENGLISH)
                + "." + date.get(Calendar.YEAR);
    }

    public static String getTimeFromUnixTime(long unixTime) {
        return new SimpleDateFormat("HH:mm:ss")
                .format(new Date(unixTime * TIME_MULTIPLIER));
    }
}
