package com.michael.api.IO;

import java.io.UnsupportedEncodingException;

/**
 * Created by Michael Risher on 2/28/2017.
 */
public class ByteHelper {

	/**
	 * Converts byte array into string
	 * @param input byte array
	 * @param encoding output encoding type eg 'UTF-8'
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String byteToString( byte[] input, String encoding ) throws UnsupportedEncodingException{
		String out = new String( input, encoding );
		return out;
	}

	/**
	 * Converts byte array into string
	 * @param input byte Array
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String byteToString( byte[] input ) throws UnsupportedEncodingException{
		String out = new String( input, "UTF-8");
		return out;
	}
}
