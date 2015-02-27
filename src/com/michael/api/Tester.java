package com.michael.api;

import com.michael.api.json.JSONObject;

import java.util.Iterator;

/**
 * Created By: Michael Risher
 * Date: 2/25/15
 * Time: 4:36 PM
 */
public class Tester {
	public static void main( String[] args ){
		JSONObject json = new JSONObject();
		json.put( "test", "true" );
		json.put( "test2", "true" );
		json.put( "test3", "true" );

		Iterator<String> itr = json.keys();
		while( itr.hasNext() ){
			IO.println( itr.next() );
		}

	}
}
