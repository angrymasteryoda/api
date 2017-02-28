package com.michael.api;

import com.michael.api.IO.IO;
import com.michael.api.email.Emailer;
import com.michael.api.json.JSONObject;
import com.michael.api.math.ApiMath;
import com.michael.api.math.Random;
import com.michael.api.security.AES;
import com.michael.api.security.Base64;

/**
 * Created By: Michael Risher
 * Date: 2/25/15
 * Time: 4:36 PM
 */
public class Tester {
	/*
	 * final String gmail, final String pass, String[] to, String[] cc, String[] bcc, String subject, String msg, String filename
	 */
	public static void main( String[] args ) throws Exception {
		String s = "KLUYIUFTYYT876retdyghvjbk";
//		IO.println( Base64.encode( s ) );
//		byte[] t = Base64.decode( Base64.encode( s ) );
//		String out = new String(t, "UTF-8");
//		IO.println( out );

		IO.println( AES.encrypt( s ) );

//		int n = 8;
//		IO.println( Math.sqrt( n ) );
//		Random rand = new Random();
//		IO.println( rand.nextInt( 2 ) );
	}

}
/*
i,value,bin,hex

*/