package net.juicy.api.utils.util;

import lombok.experimental.UtilityClass;

import java.util.concurrent.TimeUnit;
import java.util.Date;

@UtilityClass
public class TimeUtil {

    public static String makeReadable(int secs) {

        long day = secs / 3600 / 24;
        long hour = secs / 3600 % 24;
        long min = secs / 60 % 60;
        long sec = secs % 60;

        StringBuilder s = new StringBuilder();

        if (day > 0)
            s.append(day).append("д. ");

        if (hour > 0)
            s.append(hour).append("ч. ");

        if (min > 0)
            s.append(min).append("м. ");

        if (sec > 0)
            s.append(sec).append("с. ");

        return s.toString();

    }
    
    public static String makeReadable24(int secs) {

        long hour = secs / 3600 % 24;
        long min = secs / 60 % 60;
        long sec = secs % 60;

        return (hour < 10 ? "0" : "") + hour + ":" +
                (min < 10 ? "0" : "") + min + ":" +
                (sec < 10 ? "0" : "") + sec;

    }
    
    public static Date currentAddMinutes(int minutes) {

        Date date = new Date();
        date.setTime(date.getTime() + TimeUnit.MINUTES.toMillis(minutes));

        return date;

    }
    
    public static String ticksToRealTimeString(long ticks) {

        if (ticks == 0L)
            return "06:00";

        if (ticks == 18000L)
            return "00:00";

        if (ticks % 1000L == 0L)
            ticks -= ticks % 1000L;

        StringBuilder sb = new StringBuilder();

        if (ticks <= 17000L) {

            long time = ticks / 1000L + 6L;
            sb.append((time >= 10L) ? Long.valueOf(time) : ("0" + time)).append(":00");

        } else
            sb.append("0").append((ticks - 18000L) / 1000L).append(":00");

        return sb.toString();

    }
}
