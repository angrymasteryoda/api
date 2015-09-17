package com.michael.api;

import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by: Michael Risher
 * Date: 2/26/15
 * Time: 7:42 PM
 */
public class Numbers {
	/**
	 * @param dec
	 * @return
	 * @deprecated 09/16/15
	 */
	private static String decimalToBinary( long dec ) {
		String bin = "";
		String result = "";
		while ( dec != 0 ) {
			bin += ( dec % 2 );
			dec /= 2;
		}
		for ( int i = bin.length() - 1; i >= 0; i-- ) {
			result += bin.charAt( i );
		}
		return result;
	}

	/**
	 * converts decimal long to a binary long
	 *
	 * @param dec
	 * @return binary
	 * @deprecated 09/16/15
	 */
	public static String getDecimalToBinary( long dec ) {
		return decimalToBinary( dec );
	}

	private static String decimalToOctal( long dec ) {
		String bin = "";
		String result = "";
		while ( dec != 0 ) {
			bin += ( dec % 8 );
			dec /= 8;
		}
		for ( int i = bin.length() - 1; i >= 0; i-- ) {
			result += bin.charAt( i );
		}
		return result;
	}

	/**
	 * Converts decimal in to octal long
	 *
	 * @param dec
	 * @return octal
	 * @deprecated 09/16/15
	 */
	public static String getDecimalToOctal( long dec ) {
		return decimalToOctal( dec );
	}

	/**
	 * @param dec
	 * @return
	 * @deprecated 09/16/15
	 */
	private static String decimalToHexadecimal( long dec ) {
		String par1str = "";
		String result = "";
		while ( dec != 0 ) {
			par1str += "" + getValueOverBase10( (int)( dec % 16 ) );
			dec /= 16;
		}

		for ( int i = par1str.length() - 1; i >= 0; i-- ) {
			result += par1str.charAt( i );
		}
		return result;
	}

	/**
	 * Converts decimal to hexadecimal
	 *
	 * @param dec
	 * @return hexadecimal
	 * @deprecated 09/16/15
	 */
	public static String getDecimalToHexadecimal( long dec ) {
		return decimalToHexadecimal( dec );
	}

	/**
	 * Converts octal to decimal
	 *
	 * @param oct
	 * @return decimal
	 * @deprecated 09/16/15
	 */
	private static String octalToDecimal( long oct ) {
		String nums = "" + oct;
		int octal = 0;
		String result = "";
		for ( int i = 0; i < nums.length(); i++ ) {
			String indexNum = "" + nums.charAt( i );
			int num = Integer.parseInt( indexNum );
			if ( num > 7 ) {
				return "Invalid octal";
			}
		}
		for ( int i = 1; i <= nums.length(); i++ ) {
			String indexNum = "" + nums.charAt( i - 1 );
			int num = Integer.parseInt( indexNum );
			int power = (int)Math.pow( 8, ( nums.length() - i ) );

			octal += ( num * power );
		}
		result = "" + octal;
		return result;
	}//TODO deleteme after done

	/**
	 * Converts octal to decimal
	 *
	 * @param oct
	 * @return decimal
	 * @deprecated 09/16/15
	 */
	public static String getOctalToDecimal( long oct ) {
		return octalToDecimal( oct );
	}

	/**
	 * Converts octal to binary
	 *
	 * @param oct
	 * @return binary
	 * @deprecated 09/16/15
	 */
	public static String getOctalToBinary( long oct ) {
		long dec = Integer.parseInt( octalToDecimal( oct ) );
		return decimalToBinary( dec );
	}

	/**
	 * Converts octal to hexadecimal
	 *
	 * @param oct
	 * @return hexadecimal
	 * @deprecated 09/16/15
	 */
	public static String getOctalToHexadecimal( long oct ) {
		long dec = Integer.parseInt( octalToDecimal( oct ) );
		return decimalToHexadecimal( dec );
	}

	/**
	 * Converts hexadecimal to decimal
	 *
	 * @param hex
	 * @return decimal
	 * @deprecated 09/16/15
	 */
	private static String hexadecimalToDecimal( String hex ) {
		String result = "";
		String num = "";
		int dec = 0;
		for ( int i = 1; i <= hex.length(); i++ ) {
			String hexIndex = "" + hex.charAt( i - 1 );
			num = "" + getNumberOverBase10( hexIndex );
			if ( num.equals( "-1" ) ) return "Error was unable to convert number";
			int number = Integer.parseInt( num );
			int power = (int)Math.pow( 16, ( hex.length() - i ) );
			dec += ( number * power );
		}

		result = "" + dec;
		return result;
	}

	/**
	 * Converts hexadecimal to decimal
	 *
	 * @param hex
	 * @return decimal
	 * @deprecated 09/16/15
	 */
	public static String getHexadecimalToDecimal( String hex ) {
		return hexadecimalToDecimal( hex );
	}

	/**
	 * Converts hexadecimal to binary
	 *
	 * @param hex
	 * @return binary
	 * @deprecated 09/16/15
	 */
	public static String getHexadecimalToBinary( String hex ) {
		long dec = Integer.parseInt( hexadecimalToDecimal( hex ) );
		return decimalToBinary( dec );
	}

	/**
	 * Converts hexadecimal to hexadecimal
	 *
	 * @param hex
	 * @return octal
	 * @deprecated 09/16/15
	 */
	public static String getHexadecimalToOctal( String hex ) {
		long dec = Integer.parseInt( hexadecimalToDecimal( hex ) );
		return decimalToHexadecimal( dec );
	}

	/**
	 * Converter decimal to bases 2-35
	 *
	 * @param dec
	 * @param base
	 * @return
	 * @deprecated 09/16/15
	 */
	private static String decimalToBaseN( long dec, int base ) {
		String result = "";
		String digit = "";
		boolean isHigh = base > 36;
		if ( base <= 1 || base > 36 ) {
			String error = "Base " + base + " is to ";
			error += isHigh ? "high " : "low ";
			error += "the range is only 2-35";
			return error;
		}
		if ( base == 2 ) {
			return decimalToBinary( dec );
		} else if ( base == 8 ) {
			return decimalToOctal( dec );
		} else if ( base == 10 ) {
			result = "" + dec;
			return result;
		} else if ( base == 16 ) {
			return decimalToHexadecimal( dec );
		}

		while ( dec != 0 ) {
			digit += "" + getValueOverBase10( (int)( dec % base ) );
			dec /= base;
		}

		for ( int i = digit.length() - 1; i >= 0; i-- ) {
			result += digit.charAt( i );
		}
		return result;

	}

	/**
	 * Converter decimal to bases 2-35
	 *
	 * @param dec
	 * @param base
	 * @return baseN
	 * @deprecated 09/16/15
	 */
	public static String getDecimalToBaseN( long dec, int base ) {
		return decimalToBaseN( dec, base );
	}

	/**
	 * Converts from base n to decimal
	 *
	 * @param inputNumber
	 * @param base
	 * @return
	 * @deprecated 09/16/15
	 */
	private static String baseNToDecimal( String inputNumber, int base ) {
		String result = "";
		long digit = 0;
		int finalizedDigits = 0;
		for ( int i = 1; i <= inputNumber.length(); i++ ) {
			String readChar = "" + inputNumber.charAt( i - 1 );
			digit = getNumberOverBase10( readChar );
			int power = (int)Math.pow( base, ( inputNumber.length() - i ) );
			//System.out.println(digit);
			finalizedDigits += ( digit * power );
		}
		result = "" + finalizedDigits;
		return result;
	}

	/**
	 * Converts from base n to decimal
	 *
	 * @param inputNumber
	 * @param base
	 * @return decimal
	 * @deprecated 09/16/15
	 */
	public static String getBaseNToDecimal( String inputNumber, int base ) {
		return baseNToDecimal( inputNumber, base );
	}

	/**
	 * Takes a letter and turns it into the alpha-numerical value
	 *
	 * @param num
	 * @return alpha-numerical value
	 * @deprecated 09/16/15
	 */
	private static int getNumberOverBase10( String num ) {

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
	 * @deprecated 09/16/15
	 */
	private static char getValueOverBase10( int num ) {
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

	/**
	 * changes a hexadecimal value into the color
	 *
	 * @param hex
	 */
	public static Color hexToColor( String hex ) {
		int[] hexColors = { 0, 0, 0 };
		if ( hex.length() < 6 ) {
			Logger.getLogger( Numbers.class.getName() ).log( Level.SEVERE, "Not valid hexadecimal color" );
			return null;
		}
		String temp = "" + hex.charAt( 0 ) + hex.charAt( 1 );
		hexColors[0] = Integer.parseInt( hexadecimalToDecimal( temp ) );
		temp = "" + hex.charAt( 2 ) + hex.charAt( 3 );
		hexColors[1] = Integer.parseInt( hexadecimalToDecimal( temp ) );
		temp = "" + hex.charAt( 4 ) + hex.charAt( 5 );
		hexColors[2] = Integer.parseInt( hexadecimalToDecimal( temp ) );

		return new Color( hexColors[0], hexColors[1], hexColors[2] );

	}

	/**
	 * @param r
	 * @param g
	 * @param b
	 * @return
	 */
	public static String colorToHex( int r, int g, int b ) {
		final short BASE = 16;
		String[] hex = { "", "", "" };

		hex[0] = getDecimalToBaseN( r, BASE );
		hex[1] = getDecimalToBaseN( g, BASE );
		hex[2] = getDecimalToBaseN( b, BASE );

		if ( r == 0 ) {
			hex[0] = "00";
		}
		if ( g == 0 ) {
			hex[1] = "00";
		}
		if ( b == 0 ) {
			hex[2] = "00";
		}

		for ( int i = 0; i < hex.length; i++ ) {
			if ( hex[i].length() != 2 ) {
				hex[i] = "0" + hex[i];
			}
		}

		String finalResult = hex[0] + hex[1] + hex[2];
		return finalResult;
	}

	//TODO fix this when decimal is present
	public static String commaFormat( String num ) {
		String sNum = "" + num;


		if ( sNum.length() < 4 ) {
			return sNum;
		}

		return commaFormat( sNum.substring( 0, sNum.length() - 3 ) ) + "," + sNum.substring( sNum.length() - 3, sNum.length() );
	}

	public static String commaFormat( int num ) {
		return commaFormat( "" + num );
	}

}