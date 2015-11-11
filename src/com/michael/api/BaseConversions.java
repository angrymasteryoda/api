package com.michael.api;

import com.michael.api.math.ApiMath;

/**
 * Created By: Michael Risher
 * Date: 9/16/15
 * Time: 2:38 PM
 */
public class BaseConversions {
	private static boolean prettyPrint = false;
	private static int fractionLimit = 16;

	/**
	 * converts decimal to binary can handle negatives
	 * @param in
	 * @return
	 */
	public static String decimalToBinary( long in ) {
		boolean isNegative = false;
		if ( in < 0 ) {
			in = ApiMath.abs( in );
			isNegative = true;
		}
		String temp = "";
		String result = "";
		while ( in != 0 ) {
			temp += ( in % 2 );
			in /= 2;
		}

		for ( int i = temp.length() - 1; i >= 0; i-- ) {
			result += temp.charAt( i );
		}

		if ( isNegative ) {
			return prettyPrint ? prettyPrint( twoComplement( result ) ) : twoComplement( result );
		}
		result = fullValue( result, '0' );
		return prettyPrint ? prettyPrint( result ) : result;
	}

	/**
	 * Converter decimal to bases 2-35
	 * @param in
	 * @param base
	 * @return
	 * @author Michael Risher
	 */
	public static String decimalToBaseN( double in, int base ) {
		String number = Double.toString( in );
		String decimal = number.split( "\\." )[0];
		String frac = number.split( "\\." )[1];

		int count = 0;
		double temp = new Double( "." + frac );
		String fraction = "";
		while ( count < fractionLimit ){
			temp *= base;
			long t = Long.parseLong( Double.toString( temp ).split( "\\." )[0]);
			fraction += "" + t;
			temp -= t;
			if( temp == 0 ){
				break;
			}
			count++;
		}

		return decimalToBaseN( new Long( decimal ), base ) + "." + fraction;

	}

	/**
	 * Converter decimal to bases 2-35
	 * @param in
	 * @param base
	 * @return
	 * @author Michael Risher
	 */
	public static String decimalToBaseN( long in, int base ) {
		String result = "";
		String digit = "";
		boolean isHigh = base > 36;
		boolean negSupported = false;
		boolean isNegative = false;
		if ( base <= 1 || base > 36 ) {
			String error = "Base " + base + " is to ";
			error += isHigh ? "high " : "low ";
			error += "the range is only 2-35";
			return error;
		}

		if ( base == 2 /*|| base == 8*/ || base == 16 ) {
			//only support negatives for these currently
			negSupported = true;
		}

		if ( in < 0 && negSupported ) {
			//gets abs in conversion
			isNegative = true;
			result = decimalToBinary( in );
			if ( base == 16 ) {
				result = breakBinToHex( result );
			}

			return prettyPrint ? prettyPrint( result ) : result;
		}

		while ( in != 0 ) {
			digit += "" + getValue( (int)( in % base ) );
			in /= base;
		}

		for ( int i = digit.length() - 1; i >= 0; i-- ) {
			result += digit.charAt( i );
		}
		return result;

	}

	/**
	 * Only works with non-negative numbers
	 * @param in
	 * @param base
	 * @return
	 */
	public static String baseNToDecimal( String in, int base ){
		String result = "";
		long digit = 0;
		int finalizedDigits = 0;
		for ( int i = 1; i <= in.length(); i++ ) {
			String readChar = "" + in.charAt( i - 1 );
			digit = getNumber( readChar );
			int power = (int)Math.pow( base, ( in.length() - i ) );
			finalizedDigits += ( digit * power );
		}
		result = "" + finalizedDigits;
		return result;
	}

	/**
	 * converts 8 byte string into a space after every byte to make easier to read
	 * @param in 8byte string
	 * @return pretty string. such pretty. such wow.
	 */
	public static String prettyPrint( String in ){
		String result = "";
		for ( int i = 0; i < in.length(); i++ ) {
			result += in.charAt( i );
			if ( ( i + 1 ) % 8 == 0 ) {
				result += " ";
			}
		}
		return result.trim();
	}

	/**
	 * applies 1's complement to number
	 *
	 * @param in binary input
	 * @return
	 */
	private static String oneComplement( final String in ) {
		String temp = "";
		for ( int i = 0; i < in.length(); i++ ) {
			if ( in.charAt( i ) == '0' ) {
				temp += '1';
			} else if ( in.charAt( i ) == '1' ) {
				temp += '0';
			}
		}
		return temp;
	}

	/**
	 * applies the 2's complement to number
	 * @param in
	 * @return
	 */
	private static String twoComplement( final String in ) {
		String temp = oneComplement( in );
		char[] charArr = temp.toCharArray();
		boolean carry = false;
		for ( int i = temp.length() - 1; i >= 0; i-- ) {
			if ( temp.charAt( i ) == '0' ) {
				charArr[i] = '1';
				break;
			}
			if ( i - 1 >= 0 ) {
				carry = true;
			}
		}
		if ( carry ) {
			temp = "0" + new String( charArr );
		} else {
			temp = new String( charArr );
		}
		//IO.println( padding( temp, '1' ) );
		return fullValue( temp, '1' );
	}

	/**
	 * makes a full 8 byte string
	 * @param in partial byte string
	 * @param pad pad with usually '1' or '0'
	 * @return full 8 byte string
	 */
	private static String fullValue( String in, char pad ){
		int fillByte = 64 - in.length();
		int length = 64;
		char[] result = new char[ length ];
		for ( int i = 0; i < length; i++ ){
			result[i] = pad;
		}
		char[] inArr = in.toCharArray();
		for ( int i = inArr.length - 1; i >= 0; i-- ) {
			int j = result.length - 1 - ((inArr.length -1 )-  i);
			result[j] = inArr[i];
		}
		return new String( result );
	}

	/**
	 * pads the binary number to nearest byte
	 * @param in input string
	 * @param pad pad usually '1' or '0'
	 * @return padded string
	 */
	private static String padding( String in, char pad ){
		int bytes = ( (int)Math.ceil( in.length() / 8 ) ) + 1;
		int fillByte = ( 8 * bytes ) - in.length();
		int length = bytes * 8;
		char[] result = new char[ length ];
		for ( int i = 0; i < length; i++ ){
			result[i] = pad;
		}
		char[] inArr = in.toCharArray();
		for ( int i = inArr.length - 1; i >= 0; i-- ) {
			int j = result.length - 1 - ((inArr.length -1 )-  i);
			result[j] = inArr[i];
		}
		return new String( result );
	}

	/**
	 * pads the binary number to nearest byte
	 * @param in input string
	 * @param pad pad usually 1 or 0
	 * @return padded string
	 */
	private static String padding( String in, int pad ){
		return padding( in, Integer.toString( pad ).charAt( 0 ) );
	}

	/**
	 * Takes a letter and turns it into the alpha-numerical value
	 *
	 * @param num
	 * @return alpha-numerical value
	 */
	private static int getNumber( String num ) {

		String abc = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		for ( int i = 0; i < abc.length(); i++ ) {
			String check = "" + abc.charAt( i );
			if ( num.equalsIgnoreCase( check ) ) {
				return 10 + i;
			}
		}
		try {
			int digit = Integer.parseInt( num );
			return digit;
		} catch ( NumberFormatException e ) {
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * Will take an int value and convert it to a letter
	 *
	 * @param num
	 * @return Alpha value of number if returns ! there is an error
	 */
	private static char getValue( int num ) {
		String digit = "" + num;
		if ( num < 10 ) return digit.charAt( 0 );
		String abc = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		for ( int i = 0; i < abc.length(); i++ ) {
			if ( num == ( i + 10 ) ) {
				return abc.charAt( i );
			}
		}
		return '!';
	}

	private static String breakBinToHex( final String in ) {
		if ( in.length() != 64 ) {
			return "not an 8 byte value failed";
		}
		String result = "";
		for ( int i = 0; i < in.length(); i+= 4 ) {
			String temp = "";
			temp += in.charAt( i );
			temp += in.charAt( i + 1 );
			temp += in.charAt( i + 2);
			temp += in.charAt( i + 3);

			String val = baseNToDecimal( temp, 2 );
			result += getValue( Integer.parseInt( val ) );
		}

		return result;
	}

	public static void setPrettyPrint( boolean prettyPrint ) {
		BaseConversions.prettyPrint = prettyPrint;
	}
}
