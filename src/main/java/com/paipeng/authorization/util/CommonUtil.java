package com.paipeng.authorization.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtil {
    public static String getProjHomePath() {
        return System.getenv("PROJ_HOME");
    }

    public static String getSystemTime(String format) {
        if (format.isEmpty())
            return "";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date());
    }
}
