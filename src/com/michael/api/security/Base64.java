package com.michael.api.security;

import com.michael.api.IO.IO;

/**
 * Created by Michael Risher on 2/21/2017.
 */
public class Base64 {
	private static String TABLE = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";

	public static String encode( String input ){
		byte[] bytes = input.getBytes();
		return encode( bytes );
	}

	public static String encode( byte[] bytes ){
		StringBuilder sb = new StringBuilder();
		int temp;
		for( int i = 0; i < bytes.length; i+=3 ){
			temp = ( bytes[i] & 0xfc ) >> 2;
			sb.append( TABLE.charAt( temp ) );
			temp = ( bytes[i] & 0x03 ) << 4;
			if( i + 1 < bytes.length ){
				temp |= ( bytes[i + 1] & 0xf0 ) >> 4;
				sb.append( TABLE.charAt( temp ) );
				temp = ( bytes[i + 1] & 0x0f ) << 2;
				if( i + 2 < bytes.length ){
					temp |= ( bytes[i + 2] & 0xc0 ) >> 6;
					sb.append( TABLE.charAt( temp ) );
					temp = ( bytes[i + 2] & 0x3f );
					sb.append( TABLE.charAt( temp ) );
				} else {
					sb.append( TABLE.charAt( temp ) );
					sb.append( "=" );
				}
			} else {
				sb.append( TABLE.charAt( temp ) );
				sb.append( "==" );
			}
		}

		return sb.toString();
	}

	public static byte[] decode( String input ){
		if (input.length() % 4 != 0)    {
			throw new IllegalArgumentException("Invalid base64 input");
		}
		byte[] output = new byte[( input.length() * 3 ) / 4 - (input.indexOf( '=' ) > 0 ? (input.length() - input.indexOf( '=' ) ) : 0 )];
		int j = 0;
		int[] temp = new int[4];
		for( int i = 0; i < input.length(); i+=4 ){
			temp[0] = TABLE.indexOf( input.charAt( i ) );
			temp[1] = TABLE.indexOf( input.charAt( i + 1 ) );
			temp[2] = TABLE.indexOf( input.charAt( i + 2 ) );
			temp[3] = TABLE.indexOf( input.charAt( i + 3 ) );
			output[j++] = (byte) ( temp[0] << 2 | temp[1] >> 4 );
			if( temp[2] < 64 ){
				output[j++] = (byte) ( temp[1] << 4 | temp[2] >> 2 );
				if( temp[3] < 64 ){
					output[j++] = (byte) ( temp[2] << 6 | temp[3] );
				}
			}
		}
		return output;
	}
	/* manual logic behind it
	--------|
123 to base64
00110001|
&&
11111100| 0xfc
00110000|
>>2
00001100| == 12

00110001|
&
00000011| 0x03
00000001|
<<4
00010000| == 16		var = A
if length < 1
	--------|
	00110010| ==50
	&
	11110000| 0xf0
	00110000|
	>>4
	00000011|
	|
	00010000| ==16
	00010011| ==19

	00110010| ==50
	&
	00001111|0x0f
	00000010|
	<<2
	00001000| ==8	var = B
	if length < 2
		--------|
		00110011| ==51
		&
		11000000| 0xc0
		00000000|
		>>6
		00000000|
		|
		00001000| ==8

		00110011| ==51
		&
		00111111| 0x3f
		00110011| ==51





	 */
}
