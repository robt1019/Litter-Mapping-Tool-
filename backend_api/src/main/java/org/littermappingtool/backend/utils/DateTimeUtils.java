package org.littermappingtool.backend.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * The DateTimeUtils.
 */
public class DateTimeUtils {

	/** The Constant DATE_TIME_FORMAT. */
	public static final String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm";

	/** The Constant DATE_HOUR_FORMAT. */
	public static final String DATE_HOUR_FORMAT = "dd/MM/yyyy HH";

	/** The Constant DATE_FORMAT. */
	public static final String DATE_FORMAT = "dd/MM/yyyy";

	/** The Constant DATE_FORMAT_FULLMONTH. */
	public static final String DATE_FORMAT_FULLMONTH = "MMMM dd, yyyy";

	/** The Constant HOUR_FORMAT. */
	public static final String HOUR_FORMAT = "HH:mm";

	/** The Constant HOUR_FORMAT. */
	public static final String HOUR_FORMAT_12H = "hh:mm a";

	/** The Constant YEAR_FORMAT. */
	public static final String YEAR_FORMAT = "yyyy";

	/** The Constant TIME_ZONE. */
	public static final String TIME_ZONE = "UTC";

	/** The Constant NO_TIME_FLAG. */
	public static final String NO_TIME_FLAG = "NO_TIME";

	/** The Constant SORT_DATE_TIME_FORMAT. */
	public static final String SORT_DATE_TIME_FORMAT = "yyyy/MM/dd HH:mm";

	/** The Constant SORT_DATE_FORMAT. */
	public static final String SORT_DATE_FORMAT = "yyyy/MM/dd";

	/**
	 * Convert date format.
	 *
	 * @param inputDate the input date
	 * @param oldFormat the old format
	 * @param newFormat the new format
	 * @return the string
	 * @throws ParseException the parse exception
	 */
	public static String convertFormat(String inputDate, String oldFormat, String newFormat) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(oldFormat);
		Date date = dateFormat.parse(inputDate);
		dateFormat = new SimpleDateFormat(newFormat);
		return dateFormat.format(date);
	}

	/**
	 * Format a date.
	 *
	 * @param date the date
	 * @param formatType the format type
	 * @return the string
	 * @throws ParseException the parse exception
	 */
	public static String formatDate(Date date, String formatType) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(formatType);
		dateFormat.setTimeZone(TimeZone.getTimeZone(TIME_ZONE));
		return dateFormat.format(date);
	}

	/**
	 * Format a date.
	 *
	 * @param date the date
	 * @param formatType the format type
	 * @return the date
	 * @throws ParseException the parse exception
	 */
	public static Date formatDate(String date, String formatType) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(formatType);
		dateFormat.setTimeZone(TimeZone.getTimeZone(TIME_ZONE));
		return dateFormat.parse(date);
	}

	/**
	 * Gets the days between dates.
	 *
	 * @param dateOne the date one
	 * @param dateTwo the date two
	 * @param formatType the format type
	 * @return the days between dates
	 * @throws ParseException
	 */
	public static int getDaysBetweenDates(String dateOne, String dateTwo, String formatType) throws ParseException {
		SimpleDateFormat myFormat = new SimpleDateFormat(formatType);
		Date date1 = myFormat.parse(dateOne);
		Date date2 = myFormat.parse(dateTwo);
		return (int) TimeUnit.DAYS.convert(date2.getTime() - date1.getTime(), TimeUnit.MILLISECONDS);
	}

	/**
	 * Gets the days between dates.
	 *
	 * @param dateOne the date one
	 * @param dateTwo the date two
	 * @return the days between dates
	 */
	public static int getDaysBetweenDates(Date dateOne, Date dateTwo) {
		return (int) TimeUnit.DAYS.convert(dateOne.getTime() - dateTwo.getTime(), TimeUnit.MILLISECONDS);
	}

	/**
	 * Subtract days.
	 *
	 * @param date the input date
	 * @param days the days to subtract
	 * @return the result date
	 */
	public static Date subtractDays(Date date, int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -days);
		return calendar.getTime();
	}
}
