package org.littermappingtool.backend.utils;

import java.text.ParseException;
import java.util.Date;

/**
 * Custom JSTL functions for JSP pages (paired with WEB-INF/functions.tld)
 */
public class JSTLFunctions {

    /**
     * Count days between a day and now.
     *
     * @param date the beginning date represented as long
     * @return Number of days
     * @throws ParseException the parse exception
     */
    public static int countDaysUntilToday(Long fromDate) throws ParseException {
        return DateTimeUtils.getDaysBetweenDates(new Date(), new Date(fromDate));
    }
    
    /**
     * Date as long to date-time string.
     *
     * @param date the date represented as long
     * @return the formated date as string
     * @throws ParseException the parse exception
     */
    public static String dateToDateTimeString(Long date) throws ParseException {
		return DateTimeUtils.formatDate(new Date(date), DateTimeUtils.DATE_TIME_FORMAT);
	}
        
    /**
     * Handle apostrophes (single quotes) by adding backslashes.
     *
     * @param str the input string with some apostrophes
     * @return the string with safe to use apostrophes
     */
    public static String handleApostrophes(String str) {
		return str.replace("'", "\\'");
	}
}
