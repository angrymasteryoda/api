package com.michael.api;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by: Michael Risher
 * Date: 2/26/15
 * Time: 7:23 PM
 */
public class Time {
	private int hour;
	private int minute;
	private int second;

	public Time() {
		this( 0, 0, 0 );
	}

	public Time( int hour ) {
		this( hour, 0, 0 );
	}

	public Time( int hour, int minute ) {
		this( hour, minute, 0 );
	}

	/**
	 * Sets the time with user input
	 *
	 * @param hour
	 * @param minute
	 * @param second
	 */
	public Time( int hour, int minute, int second ) {
		this.hour = ( ( hour >= 0 && hour < 24 ) ? hour : 0 );
		this.minute = ( ( minute >= 0 && minute < 60 ) ? minute : 0 );
		this.second = ( ( second >= 0 && second < 60 ) ? second : 0 );
	}

	/**
	 * Returns military time (ie 0-23 hours)
	 *
	 * @return time formated to military
	 */
	public String toMilitary() {
		return String.format( "%02d:%02d:%02d", hour, minute, second );
	}

	public String toString() {
		return String.format( "%02d:%02d:%02d %s", ( hour == 0 || hour == 12 ) ? 12 : ( hour % 12 ), minute, second, ( hour < 12 ) ? "AM" : "PM" );
	}

	/**
	 * Sets the time based off your computer's clock
	 */
	public void setToSystemTime() {
		Date d = new Date( System.currentTimeMillis() );
		SimpleDateFormat f = new SimpleDateFormat( "HH:mm:ss" );
		String time = f.format( d );
		String[] split = time.split( ":" );
		this.hour = Integer.parseInt( split[0] );
		this.minute = Integer.parseInt( split[1] );
		this.second = Integer.parseInt( split[2] );
	}

	public String getSYSPrint() {
		setToSystemTime();
		return toString();
	}

	public static String mkTime(){
		SimpleDateFormat f = new SimpleDateFormat( "HH:mm:ss" );
		String time = f.format( System.currentTimeMillis() );
		return time;
	}

	/**
	 * get the current UNIX timestamp
	 * @return timestamp
	 */
	public static long getStaticUnix(){
		return System.currentTimeMillis() / 1000L;
	}

	/**
	 * Converts Unix time to Calendar instance.
	 * @return calendar object
	 */
	public static Calendar unixToCalendar(long unixTime){
		unixTime *= 1000;
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(unixTime);
		return calendar;
	}

	public int getHour() {
		return hour;
	}

	public void setHour( int hour ) {
		this.hour = hour;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute( int minute ) {
		this.minute = minute;
	}

	public int getSecond() {
		return second;
	}

	public void setSecond( int second ) {
		this.second = second;
	}
}

