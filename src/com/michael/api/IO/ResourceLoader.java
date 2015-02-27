package com.michael.api.IO;

import java.io.InputStream;

/**
 * Created by: Michael Risher
 * Date: 2/26/15
 * Time: 7:27 PM
 */
public class ResourceLoader {

	public InputStream load( String path ) {
		InputStream input = ResourceLoader.class.getResourceAsStream( path );
		if ( input == null ) {
			input = ResourceLoader.class.getResourceAsStream( "/" + path );
		}
		return input;
	}

	public static InputStream staticLoad( String path ) {
		InputStream input = ResourceLoader.class.getResourceAsStream( path );
		if ( input == null ) {
			input = ResourceLoader.class.getResourceAsStream( "/" + path );
		}
		return input;
	}
}
