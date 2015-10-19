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
		Key key = generateKey();
		Cipher c = Cipher.getInstance( ALGORITHM );
		c.init( Cipher.ENCRYPT_MODE, key );
		byte[] encValue = encValue = c.doFinal( plainText.getBytes() );
		String encryptedValue = encryptedValue = new BASE64Encoder().encode( encValue );
		return encryptedValue;
	}

	/**
	 * Decrypt text using AES algorithm
	 * @param encryptedText text to decrypt
	 * @return decrypted text
	 * @throws Exception
	 */
	public static String decrypt( String encryptedText ) throws Exception {
		Key key = generateKey();
		Cipher c = Cipher.getInstance( ALGORITHM );
		c.init( Cipher.DECRYPT_MODE, key );
		byte[] decordedValue = new BASE64Decoder().decodeBuffer( encryptedText );
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
