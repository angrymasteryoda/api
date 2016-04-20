package com.michael.api.math;

/**
 * Created with IntelliJ IDEA.
 * User: rcc
 * Date: 11/18/15
 * Time: 8:35 AM
 * To change this template use File | Settings | File Templates.
 */
public class Random extends java.util.Random {
	private final static char[] letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();

	/**
	 * Gives a string of random lowercase letters of the length specified
	 * @param len length of the string to return
	 * @return random string
	 */
	public static String randomLowercaseLetters( int len ) {
		java.util.Random rand = new java.util.Random();
		String s = "";
		for ( int i = 0; i < len; i++ ) {
			s += letters[ rand.nextInt( 25 ) ];
		}
		return s;
	}

	/**
	 * Gives a string of random Uppercase letters of the length specified
	 * @param len length of the string to return
	 * @return random string
	 */
	public static String randomUppercaseLetters( int len ) {
		java.util.Random rand = new java.util.Random();
		String s = "";
		for ( int i = 0; i < len; i++ ) {
			s += letters[ rand.nextInt( 25 ) + 26 ];
		}
		return s;
	}

	/**
	 * Gives a string of random uppercase and lowercase letters of the length specified
	 * @param len length of the string to return
	 * @return random string
	 */
	public static String randomLetters( int len ) {
		java.util.Random rand = new java.util.Random();
		String s = "";
		for ( int i = 0; i < len; i++ ) {
			s += letters[ rand.nextInt( 51 ) ];
		}
		return s;
	}

	/**
	 * Gives a string of random alpha numeric of the length specified
	 * @param len length of the string to return
	 * @return random string
	 */
	public static String randomAlphaNumeric( int len ) {
		java.util.Random rand = new java.util.Random();
		String s = "";
		for ( int i = 0; i < len; i++ ) {
			s += letters[ rand.nextInt( letters.length ) ];
		}
		return s;
	}

	/**
	 * Gives a string of random numbers of the length specified
	 * @param len length of the string to return
	 * @return random string
	 */
	public static String randomNumbers( int len ) {
		java.util.Random rand = new java.util.Random();
		String s = "";
		for ( int i = 0; i < len; i++ ) {
			s += letters[ rand.nextInt( 10 ) + 52 ];
		}
		return s;
	}

	/**
	 * Gives a random int within the range of the lower bound inclusive and the upper bound exclusive
	 * @param lower lower bound inclusive
	 * @param upper upper bound exclusive
	 * @return random int
	 */
	public static int randomInt( int lower, int upper ) {
		java.util.Random rand = new java.util.Random();
		return rand.nextInt( upper ) + lower;
	}

	/**
	 * Gives a random byte within the range of the lower bound inclusive and the upper bound exclusive
	 * @param lower lower bound inclusive
	 * @param upper upper bound exclusive
	 * @return random byte if lower exceeds -128 returns 0 if upper bound exceeds 128 returns 0
	 */
	public static byte randomByte( int lower, int upper ) {
		if ( lower < -128 ){return 0;}
		if ( upper > 128 ){return 0;}
		java.util.Random rand = new java.util.Random();
		return (byte)( rand.nextInt( upper ) + lower );
	}

	/**
	 * Gives a random float within the range of the lower bound inclusive and the upper bound exclusive
	 * @param lower lower bound inclusive
	 * @param upper upper bound exclusive
	 * @return random float
	 */
	public static float randomFloat( float lower, float upper ) {
		java.util.Random rand = new java.util.Random();
		return rand.nextFloat() * ( upper - lower ) + lower;
	}

	/**
	 * Gives a random double within the range of the lower bound inclusive and the upper bound exclusive
	 * @param lower lower bound inclusive
	 * @param upper upper bound exclusive
	 * @return random double
	 */
	public static double randomDouble( double lower, double upper ) {
		java.util.Random rand = new java.util.Random();
		return rand.nextDouble() * ( upper - lower ) + lower;
	}
}
