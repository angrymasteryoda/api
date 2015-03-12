package com.michael.api.json;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;

/**
 * Created by: Michael Risher
 * Date: 3/6/15
 * Time: 5:41 PM
 */
public class JSONReader {

	public JSONReader( Reader reader ) {
		
	}

	public JSONReader( InputStream input ) {
		this( new InputStreamReader( input ) );
	}

	public JSONReader( String input ) {
		this( new StringReader( input ) );
	}
}
