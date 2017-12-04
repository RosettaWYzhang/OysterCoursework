package com.tfl.billing;

import java.text.SimpleDateFormat;
import java.util.Date;


public class DateFormatter {
    public String format(long time) {
        return SimpleDateFormat.getInstance().format(new Date(time));
    }
}
