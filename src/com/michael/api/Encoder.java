package com.michael.api;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by: Michael Risher
 * Date: 2/26/15
 * Time: 7:44 PM
 */
public class Encoder {

	private static boolean complex;
	private final static int[] phrasechain = new int[25000];
	private final static int[] simpleChain = new int[25000];

	public Encoder() {

	}

	/**
	 * Encrypts the string input with custom phrasecode
	 *
	 * @param par1str
	 * @param par2str
	 * @return encrpyted text
	 */
	public static String getEncrypt( String par1str, String par2str ) {
		phraseCode( par2str );
		return encrypt( par1str );
	}

	/**
	 * Decrypts the string input with custom phrasecode
	 *
	 * @param par1str
	 * @param par2str
	 * @return decrypted text
	 */
	public static String getDecrypt( String par1str, String par2str ) {
		phraseCode( par2str );
		return decrypt( par1str );
	}

	/**
	 * Encrypts the string input with default phrasecode
	 *
	 * @param par1str
	 * @return encrypted text
	 */
	public static String getEncrypt( String par1str ) {
		defaultPhraseCode();
		return encrypt( par1str );
	}

	/**
	 * Decrypts the string input with default phrasecode
	 *
	 * @param par1str
	 * @return decrypted text
	 */
	public static String getDecrypt( String par1str ) {
		defaultPhraseCode();
		return decrypt( par1str );
	}

	/**
	 * encrypts the inputted string into rot13 encryption
	 *
	 * @param par1str
	 * @return rot13
	 */
	public static String getRot13( String par1str ) {
		return rot13( par1str );
	}

	/**
	 * takes the input and encrypts into md5 hash
	 *
	 * @param par1str
	 * @return md5
	 */
	public static String getMd5( String par1str ) {
		return hashPassword( par1str );
	}

	/**
	 * sets the default phrasecode
	 */
	private static void defaultPhraseCode() {
		phraseCode( "google fly fish hop money hot winter noob michael family guy" );
	}

	/**
	 * Encrypts the string input
	 *
	 * @param s
	 * @return
	 * @author Michael Risher
	 */
	private static String encrypt( String s ) {
		stripNumbs();
		String result = "";
		int l = s.length();
		char ch;
		int ck = 0;

		for ( int i = 0; i < l; i++ ) {
			if ( phrasechain[ck] == 0 ) ck = 0;
			ch = s.charAt( i );
			if ( ch != 10 ) {
				if ( !complex ) {
					ch += simpleChain[ck];
				} else {
					ch += phrasechain[ck];
				}
			}
			ck++;
			result += ch;
		}
		return result;

	}

	/**
	 * Decrypts the string input
	 *
	 * @param s
	 * @return
	 * @author Michael Risher
	 */
	private static String decrypt( String s ) {
		stripNumbs();
		String result = "";
		char ch;
		int ck = 0;

		for ( int i = 0; i < s.length(); i++ ) {

			if ( phrasechain[ck] == 0 ) ck = 0;
			ch = s.charAt( i );
			if ( ch != 10 ) {
				if ( !complex ) {
					ch -= simpleChain[ck];
				} else {
					ch -= phrasechain[ck];
				}
			}
			ck++;
			result += ch;
		}
		return result;
	}

	/**
	 * encrypts the inputted string into rot13 encryption
	 *
	 * @param s
	 * @return
	 * @author Michael Risher
	 */
	private static String rot13( String s ) {
		String result = "";
		for ( int i = 0; i < s.length(); i++ ) {
			char c = s.charAt( i );
			if ( c >= 'a' && c <= 'm' ) c += 13;
			else if ( c >= 'A' && c <= 'M' ) c += 13;
			else if ( c >= 'n' && c <= 'z' ) c -= 13;
			else if ( c >= 'N' && c <= 'Z' ) c -= 13;

			result += c;
		}
		return result;
	}

	/**
	 * makes a custom phrase chain
	 *
	 * @param s
	 * @author Michael Risher
	 */
	private static void phraseCode( String s ) {
		int ch;
		for ( int i = 0; i < s.length(); i++ ) {
			phrasechain[i] = 0;
		}
		for ( int i = 0; i < s.length(); i++ ) {
			ch = (int)s.charAt( i );
			phrasechain[i] = ch;
			stripNumbs();
		}
	}

	/**
	 * make a simple chain from the phrasechain
	 *
	 * @author Michael Risher
	 */
	private static void stripNumbs() {
		for ( int i = 0; i < phrasechain.length; i++ ) {
			int n = phrasechain[i];
			simpleChain[i] = n % 10;
		}
	}

	/**
	 * takes the input and encrypts into md5 hash
	 *
	 * @param password
	 * @return
	 */
	private static String hashPassword( String password ) {
		String hashword = null;
		try {
			MessageDigest md5 = MessageDigest.getInstance( "MD5" );
			md5.update( password.getBytes() );
			BigInteger hash = new BigInteger( 1, md5.digest() );
			hashword = hash.toString( 16 );
		} catch ( NoSuchAlgorithmException nsae ) {
			// ignore
		}
		return hashword;
	}

	/**
	 * get the binary version of your string
	 *
	 * @param words
	 * @return binary
	 */
	public static String wordsToBinary( String words ) {
		String result = "";
		for ( int i = 0; i < words.length(); i++ ) {
			String binary = Numbers.getDecimalToBinary( (int)words.charAt( i ) );
			if ( binary.length() < 8 ) {
				int zneeded = 8 - binary.length();
				for ( int j = 0; j < zneeded; j++ ) {
					binary = "0" + binary;
				}
			}
			result += binary;
		}
		return result;
	}

	/**
	 * Gets the binary version of the string back into words
	 *
	 * @param binary
	 * @return
	 */
	public static String binaryToWords( String binary ) {
		String si = "", result = "";
		for ( int i = 1; i <= binary.length(); i++ ) {
			si += binary.charAt( i - 1 );
			if ( i % 8 == 0 ) {
				char letter = (char)Integer.parseInt( Numbers.getBaseNToDecimal( si, 2 ) );
				result += letter;
				si = "";
			}
		}
		return result;
	}

	/**
	 * Converts words to hex value
	 *
	 * @param words
	 * @return hexadecimal string
	 */
	public static String wordsToHex( String words ) {
		String result = "";
		for ( int i = 0; i < words.length(); i++ ) {
			String hex = Numbers.getDecimalToBaseN( (int)words.charAt( i ), 16 );
			if ( hex.length() < 2 ) {
				int zneeded = 2 - hex.length();
				for ( int j = 0; j < zneeded; j++ ) {
					hex = "0" + hex;
				}
			}
			result += hex;
		}
		return result;
	}

	/**
	 * converts hex to acsii words
	 *
	 * @param hex
	 * @return words
	 */
	public static String hexToWords( String hex ) {
		String result = "";
		String si = "";
		for ( int i = 1; i <= hex.length(); i++ ) {
			si += hex.charAt( i - 1 );
			if ( i % 2 == 0 ) {
				char letter = (char)Integer.parseInt( Numbers.getBaseNToDecimal( si, 16 ) );
				result += letter;
				si = "";
			}
		}
		return result;
	}

	/**
	 * converts words to base n
	 *
	 * @param words
	 * @param base
	 * @return base n values
	 */
	public static String wordsToBaseNOverHex( String words, int base ) {
		if ( base < 16 && base > 36 ) return "Needs to be above base 16 and less than or equal to 36";
		String result = "";
		for ( int i = 0; i < words.length(); i++ ) {
			String baseOver = Numbers.getDecimalToBaseN( (int)words.charAt( i ), base );
			if ( baseOver.length() < 2 ) {
				int zneeded = 2 - baseOver.length();
				for ( int j = 0; j < zneeded; j++ ) {
					baseOver = "0" + baseOver;
				}
			}
			result += baseOver;
		}
		return result;
	}

	/**
	 * converts base n values to words
	 *
	 * @param baseOver
	 * @param base
	 * @return words
	 */
	public static String baseNOverHexToWords( String baseOver, int base ) {
		String result = "";
		String si = "";
		for ( int i = 1; i <= baseOver.length(); i++ ) {
			si += baseOver.charAt( i - 1 );
			if ( i % 2 == 0 ) {
				char letter = (char)Integer.parseInt( Numbers.getBaseNToDecimal( si, base ) );
				result += letter;
				si = "";
			}
		}
		return result;
	}

	/**
	 * encrypt words to hex than do stuff to it
	 *
	 * @param words
	 * @param base
	 * @return encryption
	 */
	public static String encryptHexOver( String words, int base ) {
		if ( base < 16 && base > 36 ) return "Needs to be above base 16 and less than or equal to 36";
		String result = "";

		for ( int i = 0; i < words.length(); i++ ) {
			String baseOver = Numbers.getDecimalToBaseN( (int)words.charAt( i ), base );
			if ( baseOver.length() < 2 ) {
				int zneeded = 2 - baseOver.length();
				for ( int j = 0; j < zneeded; j++ ) {
					baseOver = "0" + baseOver;
				}
			}
			char a = baseOver.charAt( 0 );
			char b = baseOver.charAt( 1 );

			a ^= b;
			b ^= a;
			a ^= b;

			baseOver = a + "" + b;

			result += baseOver;
		}

		return result;
	}

	/**
	 * decrypt hex to words
	 *
	 * @param baseOver
	 * @param base
	 * @return words
	 */
	public static String decryptHexOver( String baseOver, int base ) {
		if ( base < 16 && base > 36 ) return "Needs to be above base 16 and less than or equal to 36";
		String result = "";
		String si = "";
		for ( int i = 1; i <= baseOver.length(); i++ ) {
			si += baseOver.charAt( i - 1 );
			if ( i % 2 == 0 ) {
				char a = si.charAt( 0 );
				char b = si.charAt( 1 );

				a ^= b;
				b ^= a;
				a ^= b;

				si = a + "" + b;
				char letter = (char)Integer.parseInt( Numbers.getBaseNToDecimal( si, base ) );
				result += letter;
				si = "";
			}
		}
		return result;
	}

	/**
	 * Swaps first with last and moves inward
	 *
	 * @param e Input String
	 * @return swapped string
	 */
	public static String swapCharacters( String e ) {
		char[] r = new char[e.length()];
		String result = "";
		for ( int i = 0; i < e.length(); i += 2 ) {
			int j = i + 1;
			char sc1 = e.charAt( i );
			char sc2 = e.charAt( e.length() - j );

			sc1 ^= sc2;
			sc2 ^= sc1;
			sc1 ^= sc2;

			r[i] = sc1;
			r[e.length() - j] = sc2;
		}

		for ( int i = 0; i < r.length; i++ ) {
			result += r[i];
		}
		return result;
	}
}