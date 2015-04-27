package com.michael.api.security;

import java.security.MessageDigest;

/**
 * Created By: Michael Risher
 * Date: 4/27/15
 * Time: 3:09 AM
 */
public class Hashing {
	private static String convertToHex(byte[] data) {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < data.length; i++) {
			int halfbyte = (data[i] >>> 4) & 0x0F;
			int two_halfs = 0;
			do {
				if ((0 <= halfbyte) && (halfbyte <= 9))
					buf.append((char) ('0' + halfbyte));
				else
					buf.append((char) ('a' + (halfbyte - 10)));
				halfbyte = data[i] & 0x0F;
			} while(two_halfs++ < 1);
		}
		return buf.toString();
	}

	/**
	 * Get sha1 of text
	 * @param inputString tex to hash
	 * @return hashed text
	 * @throws Exception
	 */
	public static String sha1( String inputString ) throws Exception {
		MessageDigest md;
		md = MessageDigest.getInstance( "SHA-1" );
		byte[] hash = new byte[40];
		md.update( inputString.getBytes( "iso-8859-1" ), 0, inputString.length() );
		hash = md.digest();
		return convertToHex( hash );
	}

	/**
	 * Get sha256 of text
	 * @param inputString tex to hash
	 * @return hashed text
	 * @throws Exception
	 */
	public static String sha256( String inputString ) throws Exception {
		MessageDigest md;
		md = MessageDigest.getInstance( "SHA-256" );
		byte[] hash = new byte[64];
		md.update( inputString.getBytes( "iso-8859-1" ), 0, inputString.length() );
		hash = md.digest();
		return convertToHex( hash );
	}

	/**
	 * Get sha384 of text
	 * @param inputString tex to hash
	 * @return hashed text
	 * @throws Exception
	 */
	public static String sha384( String inputString ) throws Exception {
		MessageDigest md;
		md = MessageDigest.getInstance( "SHA-384" );
		byte[] hash = new byte[96];
		md.update( inputString.getBytes( "iso-8859-1" ), 0, inputString.length() );
		hash = md.digest();
		return convertToHex( hash );
	}

	/**
	 * Get sha512 of text
	 * @param inputString tex to hash
	 * @return hashed text
	 * @throws Exception
	 */
	public static String sha512( String inputString ) throws Exception {
		MessageDigest md;
		md = MessageDigest.getInstance( "SHA-512" );
		byte[] hash = new byte[128];
		md.update( inputString.getBytes( "iso-8859-1" ), 0, inputString.length() );
		hash = md.digest();
		return convertToHex( hash );
	}

	/**
	 * Get md5 of text
	 * @param inputString tex to hash
	 * @return hashed text
	 * @throws Exception
	 */
	public static String md5( String inputString ) throws Exception {
		MessageDigest md;
		md = MessageDigest.getInstance( "MD5" );
		byte[] hash = new byte[32];
		md.update( inputString.getBytes( "iso-8859-1" ), 0, inputString.length() );
		hash = md.digest();
		return convertToHex( hash );
	}
}



/*
String hashword = null;
		try {
			MessageDigest md5 = MessageDigest.getInstance( "MD5" );
			md5.update( password.getBytes() );
			BigInteger hash = new BigInteger( 1, md5.digest() );
			hashword = hash.toString( 16 );
		} catch ( NoSuchAlgorithmException nsae ) {
			// ignore
		}
 */
