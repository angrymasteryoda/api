package com.michael.api.json;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by: Michael Risher
 * Date: 2/26/15
 * Time: 7:09 PM
 */
public class JSONWriter {
	/**
	 * encapsulates values in quotes
	 * @param str string to be quoted
	 * @return quoted string
	 */
	public static String quote( String str ){
		if ( str == null || str.length() == 0 ) {
			return "\"\"";
		}

		StringBuilder sb = new StringBuilder();
		int len = str.length();
		char a;
		char b = 0;

		sb.append( '"' );
		for ( int i = 0; i < len; i++ ) {
			a = b; //previous char
			b = str.charAt( i ); //current

			switch ( b ) {
				case '\\':
				case '"':
					sb.append( '\\' );
					sb.append( b );
					break;
				case '/':
					if ( a == '<' ) {
						sb.append( '\\' );
					}
					sb.append( b );
					break;
				case '\b':
					sb.append( "\\b" );
					break;
				case '\t':
					sb.append( "\\t" );
					break;
				case '\n':
					sb.append( "\\n" );
					break;
				case '\f':
					sb.append( "\\f" );
					break;
				case '\r':
					sb.append( "\\r" );
					break;
				default:
					sb.append( b );
			}
		}
		sb.append( '"' );
		return sb.toString();
	}

	/**
	 * Write the value of th object passed in
	 * @param value the object to be written
	 * @return String of the object
	 * @throws JSONException
	 */
	@SuppressWarnings( "unchecked" )
	public static String writeValue( Object value ) throws JSONException{
		StringBuilder sb = new StringBuilder();
		if ( value == null || value.equals( null ) ) {
			sb.append( "null" );
		} else if ( value instanceof JSONObject ) {
			sb.append( ( (JSONObject)value ).write() );
		} else if ( value instanceof Map ) {
			sb.append( new JSONObject( (Map<String, Object>)value ).write() );
		} else if ( value instanceof JSONArray ) {
			sb.append( ( (JSONArray)value ).write() );
		} else if ( value instanceof Collection ) {
			sb.append( new JSONArray( (Collection<Object>)value ).write() );
		} else if ( value.getClass().isArray() ) {
			sb.append( new JSONArray( value ).write() );
		} else if ( value instanceof Number ) {
			sb.append( numberToString( (Number)value ) );
		} else if ( value instanceof Boolean ) {
			sb.append( value.toString() );
		} else {
			sb.append( quote( value.toString() ) );
		}

		return sb.toString();
	}

	/**
	 * formats numbers trimming off dumb 0's
	 * @param number number to be formatted
	 * @return formatted number
	 * @throws JSONException
	 */
	public static String numberToString( Number number ) throws JSONException {
		if ( number == null ) {
			throw new JSONException( "Null pointer" );
		}

		validate( number );

		String string = number.toString();
		if ( string.indexOf( '.' ) > 0 && string.indexOf( 'e' ) < 0 && string.indexOf( 'E' ) < 0 ) {
			while ( string.endsWith( "0" ) ) {
				string = string.substring( 0, string.length() - 1 );
			}
			if ( string.endsWith( "." ) ) {
				string = string.substring( 0, string.length() - 1 );
			}
		}
		return string;
	}

	/**
	 * validate the objects for json
	 *
	 * @param object to be validated
	 * @throws JSONException
	 */
	public static void validate( Object object ) throws JSONException {
		if ( object != null ) {
			if ( object instanceof Double ) {
				if ( ( (Double)object ).isInfinite() || ( (Double)object ).isNaN() ) {
					throw new JSONException( "Doubles must be finite numbers" );
				}
			} else if ( object instanceof Float ) {
				if ( ( (Float)object ).isInfinite() || ( (Float)object ).isNaN() ) {
					throw new JSONException( "Floats must be finite numbers" );
				}
			}
		}
	}
}
