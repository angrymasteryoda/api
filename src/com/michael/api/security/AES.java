package com.michael.api.security;

import java.security.*;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.*;

/**
 * Created By: Michael Risher
 * Date: 4/27/15
 * Time: 2:40 AM
 */
public class AES {
	private static final String ALGORITHM = "AES";

	private static byte[] keyValue = "wint3rW00ds@Dusk".getBytes();

	/**
	 * Encrypt text using AES algorithm
	 * @param plainText text to encrypt
	 * @return encrypted text
	 * @throws Exception
	 */
	public static String encrypt( String plainText ) throws Exception {
		return encrypt( plainText, true );
	}

	/**
	 * Encrypt text using AES algorithm
	 * @param plainText text to encrypt
	 * @param base64 encode into base 64
	 * @return encrypted text
	 * @throws Exception
	 */
	public static String encrypt( String plainText, boolean base64 ) throws Exception {
		Key key = generateKey();
		Cipher c = Cipher.getInstance( ALGORITHM );
		c.init( Cipher.ENCRYPT_MODE, key );
		byte[] encValue = c.doFinal( plainText.getBytes() );
		String encryptedValue = new BASE64Encoder().encode( encValue );
		if ( base64 ) {
			encryptedValue = new BASE64Encoder().encode( encValue );
		} else {
			encryptedValue = Arrays.toString( encValue );
		}
		return encryptedValue;
	}


	/**
	 * Decrypt text using AES algorithm
	 * @param encryptedText text to decrypt
	 * @return decrypted text
	 * @throws Exception
	 */
	public static String decrypt( String encryptedText ) throws Exception {
		return decrypt( encryptedText, true );
	}
	/**
	 * Decrypt text using AES algorithm
	 * @param encryptedText text to decrypt
	 * @param base64 decode from base 64
	 * @return decrypted text
	 * @throws Exception
	 */
	public static String decrypt( String encryptedText, boolean base64 ) throws Exception {
		Key key = generateKey();
		Cipher c = Cipher.getInstance( ALGORITHM );
		c.init( Cipher.DECRYPT_MODE, key );
		byte[] decordedValue;
		if ( base64 ) {
			decordedValue = new BASE64Decoder().decodeBuffer( encryptedText );
		} else {
			decordedValue = encryptedText.getBytes();
		}
		byte[] decValue = c.doFinal( decordedValue );
		String decryptedValue = new String( decValue );
		return decryptedValue;
	}

	/**
	 * Set the AES salt
	 * @param str string that will be set as the AES salt
	 */
	public static void setSalt( String str ){
		keyValue = str.getBytes();
	}

	private static Key generateKey() throws Exception {
		Key key = new SecretKeySpec( keyValue, ALGORITHM );
		return key;
	}
}
