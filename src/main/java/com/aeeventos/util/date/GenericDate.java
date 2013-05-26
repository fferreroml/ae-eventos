package com.aeeventos.util.date;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * This class represent a Date Warning: in the <tt>Calendar</tt> class, the
 * months have an index starting from 0
 */
public class GenericDate implements Serializable{

	/**
	 */
	private static final long serialVersionUID = 1L;

	private Date date;

	private GregorianCalendar gregorian;

	private static Locale locale = new Locale("es","AR");


	/**
	 * Creates a new GenericDate representing this moment
	 */
	public GenericDate() {
		date = new Date();
		gregorian = new GregorianCalendar();
	}

	/**
	 * Create a new GenericDate
	 *
	 * @param year
	 *            The GenericDate year
	 * @param month
	 *            The GenericDate month
	 * @param dayOfMonth
	 *            The GenericDate day of the month
	 * @param hourOfDay
	 *            The GenericDate hour of the day
	 * @param minute
	 *            The GenericDate minute
	 * @param second
	 *            The GenericDate second
	 */
	public GenericDate(int year, int month, int dayOfMonth, int hourOfDay,
			int minute, int second) {
		this.gregorian = new GregorianCalendar(year, month - 1, dayOfMonth,
				hourOfDay, minute, second);
		this.date = gregorian.getTime();
	}

	/**
	 * Create a new GenericDate
	 *
	 * @param year
	 *            The GenericDate year
	 * @param month
	 *            The GenericDate month
	 * @param dayOfMonth
	 *            The GenericDate day of the month
	 */
	public GenericDate(int year, int month, int dayOfMonth) {
		this(year, month, dayOfMonth, 0, 0, 0);
	}

	/**
	 * Create a new GenericDate
	 *
	 * @param year
	 *            The GenericDate year
	 * @param month
	 *            The GenericDate month
	 * @param dayOfMonth
	 *            The GenericDate day of the month
	 * @param hourOfDay
	 *            The GenericDate hour of the day
	 * @param minute
	 *            The GenericDate minute
	 */
	public GenericDate(int year, int month, int dayOfMonth, int hourOfDay,
			int minute) {
		this(year, month, dayOfMonth, hourOfDay, minute, 0);
	}

	/**
	 * Creates a new GenericDate
	 *
	 * @param date
	 *            A Date that will be converted to GenericDate
	 */
	public GenericDate(Date date) {
		this.date = date;
		this.gregorian = new GregorianCalendar();
		this.gregorian.setTime(date);
	}

	/**
	 * Creates a new GenericDate
	 *
	 * @param date
	 *            The string format could be "YYYY/MM/DD hh:mm:ss" or
	 *            "YYYY/MM/DD"
	 */
	public GenericDate(String date) {

		String[] partes = date.split(" ");

		String[] sup = partes[0].split("-");
		int year = Integer.parseInt(sup[0]);
		int month = Integer.parseInt(sup[1]) - 1;
		int day = Integer.parseInt(sup[2]);

		if (partes.length == 1) {
			this.gregorian = new GregorianCalendar(year, month, day);
		} else {
			String[] inf = partes[1].split(":");
			int hour = Integer.parseInt(inf[0]);
			int minute = Integer.parseInt(inf[1]);
			int second = Integer.parseInt(inf[2]);

			this.gregorian = new GregorianCalendar(year, month, day, hour,
					minute, second);
		}
		this.date = gregorian.getTime();
	}

	public GenericDate(String strDate, String pattern) {
		if (strDate.equals("")) {
			this.gregorian = new GregorianCalendar();
			this.date = gregorian.getTime();
		} else {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(pattern);
				Date date = sdf.parse(strDate);
				this.date = date;
				this.gregorian = new GregorianCalendar();
				this.gregorian.setTime(date);
			} catch (ParseException e) {
				this.gregorian = new GregorianCalendar();
				this.date = gregorian.getTime();
			}
		}
	}

	// Getters
	public int getYear() {
		return gregorian.get(Calendar.YEAR);
	}

	public int getMonth() {
		return gregorian.get(Calendar.MONTH) + 1;
	}

	public int getDayOfMonth() {
		return gregorian.get(Calendar.DAY_OF_MONTH);
	}

	public int getHourOfDay() {
		return gregorian.get(Calendar.HOUR_OF_DAY);
	}

	public int getMinute() {
		return gregorian.get(Calendar.MINUTE);
	}

	public int getSecond() {
		return gregorian.get(Calendar.SECOND);
	}

	public Date getDate() {
		return this.date;
	}

	// Setters
	public void setYear(int year) {
		gregorian.set(Calendar.YEAR, year);
		this.date = gregorian.getTime();
	}

	public void setMonth(int month) {
		gregorian.set(Calendar.MONTH, month);
		this.date = gregorian.getTime();
	}

	public void setDayOfMonth(int dayOfMonth) {
		gregorian.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		this.date = gregorian.getTime();
	}

	public void setHourOfDay(int hourOfDay) {
		gregorian.set(Calendar.HOUR_OF_DAY, hourOfDay);
		this.date = gregorian.getTime();
	}

	public void setMinute(int minute) {
		gregorian.set(Calendar.MINUTE, minute);
		this.date = gregorian.getTime();
	}

	public void setSecond(int second) {
		gregorian.set(Calendar.SECOND, second);
		this.date = gregorian.getTime();
	}

	public void setDate(Date date) {
		this.date = date;
		this.gregorian.setTime(this.date);
	}

	/**
	 * Compare this GenericDate representation with another GenericDate
	 *
	 * @param compareTo
	 *            The GenericDate to be compared
	 * @return True if the time of this GenericDate is after the time
	 *         represented by compareTo, false otherwise.
	 */
	public boolean after(GenericDate compareTo) {
		return gregorian.after(compareTo);
	}

	/**
	 * Compare this GenericDate representation with another GenericDate
	 *
	 * @param compareTo
	 *            The GenericDate to be compared
	 * @return True if the time of this GenericDate is before the time
	 *         represented by compareTo, false otherwise.
	 */
	public boolean before(GenericDate compareTo) {
		return gregorian.before(compareTo);
	}

	/**
	 * Add days to the actual date
	 * @param days, The days to be added as an int value
	 */
	public void addDays(int days) {
		this.setDayOfMonth(this.getDayOfMonth() + days);
	}

	/**
	 * Substract days to the actual date
	 * @param days, The days to be substracted as an int value
	 */
	public void substractDays(int days) {
		this.setDayOfMonth(this.getDayOfMonth() - days);
	}

	/**
	 * @return Date string with System format
	 */
	public String getDefaultFormat() {
		String strRet = "";
		if (date != null) {
			DateFormat df = DateFormat.getDateInstance(DateFormat.DEFAULT, locale);
			strRet = df.format(this.date);
		}
		return strRet;
	}

	/**
	 * @return Date string with 'MM/dd/yyyy HH:mm' format
	 */
	public String getCompleteFormat() {
		String strRet = "";
		if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm", locale);
			strRet = sdf.format(this.date);
		}
		return strRet;
	}

	/**
	 * @return Date string with 'DD-MM-YY' format
	 */
	public String getShortFormat() {
		DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, locale);
		return df.format(this.date);
	}

	/**
	 * @return Date string with 'DD-MM-YYYY' format
	 */
	public String getMediumFormat() {
		DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
		return df.format(date);
	}

	/**
	 * @return Date string with 'DD de [nombre_mes] de YYYY' format
	 */
	public String getLongFormat() {
		DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, locale);
		return df.format(date);
	}

	/**
	 * @return Date string with '[nombre_dia_semana] DD de [nombre_mes] de YYYY'
	 *         format
	 */
	public String getFullFormat() {
		DateFormat df = DateFormat.getDateInstance(DateFormat.FULL, locale);

		return df.format(date);
	}

	/**
	 * @return Date string with YYYYMMDDhhmmss format
	 */
	public String getFileNameFormat() {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSS", locale);
		if (this.date == null)
			return "";
		else {
			return df.format(this.date);
		}

	}

	/**
	 * @param pattern, The pattern used to convert the date to String as a String value
	 * @return Date string with specefic format
	*/
	public String getSpecificPattern(String pattern) {
		SimpleDateFormat df = new SimpleDateFormat(pattern, locale);
		if (this.date == null)
			return "";
		else {
			return df.format(this.date);
		}

	}

	/**
	 * @return Date string with 'YYYY-MM-DD hh:mm:ss' format
	 */
	public String getSQL() {
		if (this.date == null)
			return "null";
		else
			return "'" + this.getYear() + "-" + this.getMonth() + "-"
					+ this.getDayOfMonth() + " " + this.getHourOfDay() + ":"
					+ this.getMinute() + ":" + this.getSecond() + "'";
	}

	@Override
	/**
	 * @return Date string with YYYY-MM-DD hh:mm:ss format
	 */
	public String toString() {
		if (this.date == null)
			return "null";
		else
			return this.getDayOfMonth() + "/" + this.getMonth() + "/"
					+ this.getYear();
	}

	/**
	 * Creates a new GenericDate representing this moment
	 *
	 * @return A new GenericDate representing this moment
	 */
	public static GenericDate now() {
		return new GenericDate();
	}


}