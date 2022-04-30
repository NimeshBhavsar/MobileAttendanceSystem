package com.krishna.attendance.Core.Utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateUtilityService {

    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";


    /**
     * Pattern: "yyyy-MM-dd"
     *
     * @param date
     * @return
     */
    public static String format_yyyMMdd(Date date) {
        if (date == null)
            return "";
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    public static Date parse_yyyyMMdd(String date) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd").parse(date);
    }

    public static Date parse_yyyyMMddHHmmss(String date) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
    }

    /**
     * Pattern: "EEE, d MMM yyyy"
     *
     * @param date
     * @return
     */
    public static String format_EEEdMMMyyyy(Date date) {
        if (date != null)
            return new SimpleDateFormat("EEE, d MMM yyyy").format(date);
        return "";
    }

    public static String format_EEEdMMMyyyy_HHmm(Date date) {
        if (date != null)
            return new SimpleDateFormat("EEE, d MMM yyyy (HH:mm)").format(date);
        return "";
    }

   /* public static String formatAsMySql(Date date){
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }*/

    public static String format_EEEdMMM(Date date) {
        if (date != null)
            return new SimpleDateFormat("EEE, d MMM").format(date);
        return "";
    }

    public static String format_yyyyMMddHHmmss(Date date) {
        if (date == null)
            return "";
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    public static long getDaysDifference(Date compareDate) {
        Calendar today = Calendar.getInstance();
        return TimeUnit.MICROSECONDS.toDays(
                today.getTimeInMillis() - compareDate.getTime());
    }

    // todo: redundant lines in below commented codes. redo the logic if you uncomment it
    /*public static java.util.Date getDateFromDatePicker(DatePicker datePicker) {
        if (datePicker == null)
            return null;
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        String dateString = DateUtilityService.simpleMySqlFormatDateOnly(calendar.getTime());
        try {
            return DateUtilityService.parseMySqlDateOnly(dateString);//calendar.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }*/

}
