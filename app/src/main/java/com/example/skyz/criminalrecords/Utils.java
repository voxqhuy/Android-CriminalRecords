package com.example.skyz.criminalrecords;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Vo Huy on 4/15/2018.
 */
public class Utils {

    public static String formatDate (Date date){
        String datePattern = "EE, MM dd, yyyy";
        SimpleDateFormat format = new SimpleDateFormat(datePattern, Locale.US);
        return format.format(date);
    }
}
