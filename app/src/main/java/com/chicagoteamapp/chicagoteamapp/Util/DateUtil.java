package com.chicagoteamapp.chicagoteamapp.Util;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

    public static String formatDate(Date date) {
        SimpleDateFormat dateFormat =
                new SimpleDateFormat("EEEE LLLL d", Locale.getDefault());
        String[] d = dateFormat.format(date).split(" ");
        d[0] = d[0].substring(0, 1)
                .toUpperCase(Locale.getDefault()) + d[0].substring(1);
        d[1] = d[1].substring(0, 1)
                .toUpperCase(Locale.getDefault()) + d[1].substring(1);
        String result = Arrays.toString(d);
        return result.substring(1, result.length() - 1);
    }
}
