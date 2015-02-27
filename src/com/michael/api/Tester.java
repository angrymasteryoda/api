package com.michael.api;

import com.michael.api.IO.IO;
import com.michael.api.json.JSONArray;
import com.michael.api.json.JSONObject;

/**
 * Created By: Michael Risher
 * Date: 2/25/15
 * Time: 4:36 PM
 */
public class Tester {
	public static void main( String[] args ){
		JSONObject json = new JSONObject();


		int[] ints = {1,2,3,4,5};
		JSONArray jsonarr = new JSONArray( ints );

//		jsonarr.put( 1 );
//		jsonarr.put( 2.012 );
//		jsonarr.put( true );
//		jsonarr.put( "test" );

		json.put( "arr", jsonarr );
		json.put( "test", "true" );
		json.put( "test2", 1 );
		json.put( "test3", 2.12 );
		json.put( "test4", true );

		IO.println( json );

	}
}
