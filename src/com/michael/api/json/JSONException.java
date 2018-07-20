package com.michael.api.json;

import java.io.Serializable;

/**
 * Created By: Michael Risher
 * Date: 2/25/15
 * Time: 5:18 PM
 */
public class JSONException extends RuntimeException implements Serializable{
	private Throwable cause;

	public JSONException( String message ) {
		super( message );
	}

	public JSONException( Throwable cause ) {
		super( cause.getMessage() );
		this.cause = cause;
	}

	@Override
	public Throwable getCause() {
		return this.cause;
	}
}
