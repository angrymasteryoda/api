package com.michael.api.json;

import java.io.*;

/**
 * Created by: Michael Risher
 * Date: 3/6/15
 * Time: 5:41 PM
 */
public class JSONReader {

	private long character;
	private long index;
	private long line;
	private char previous;
	private boolean usePrevious;
	private Reader reader;
	private boolean eof;

	public JSONReader( Reader reader ) {
		if ( reader.markSupported() ) {
			this.reader = reader;
		} else {
			this.reader = new BufferedReader( reader );
		}
		eof = false;
		usePrevious = false;
		previous = 0;
		index = 0;
		character = 1;
		line = 1;
	}

	public JSONReader( InputStream input ) {
		this( new InputStreamReader( input ) );
	}

	public JSONReader( String input ) {
		this( new StringReader( input ) );
	}

	/**
	 * Get the next character
	 *
	 * @return next character or 0 if end of string
	 * @throws JSONException
	 */
	public char next() throws JSONException {
		int c;
		if ( usePrevious ) {
			usePrevious = false;
			c = previous;
		} else {
			try {
				c = reader.read();
			} catch ( IOException ex ) {
				throw new JSONException( ex );
			}

			if ( c <= 0 ) {
				eof = true;
				c = 0;
			}
		}

		index++;
		if ( previous == '\n' || previous == '\r' ) { //unix check for newline
			line++;
			character = 0;
		} else {
			character++;
		}
		previous = (char) c;
		return previous;
	}

	/**
	 * get next n characters
	 * @param n number of characters to get
	 * @return string of characters
	 * @throws JSONException
	 */
	public String nextN( int n ) throws JSONException {
		if ( n == 0 ) {
			return "";
		}

		StringBuilder sb = new StringBuilder(  );
		int counter = 0;

		while ( counter < n ){
			sb.append( next() );
			if ( end() ) {
				throw new JSONException( "Substring bound error" );
			}
			counter++;
		}

		return sb.toString();
	}

	/**
	 * checks if the next character matches
	 *
	 * @param c Character to match
	 * @return true if matched, false otherwise
	 * @throws JSONException
	 */
	public boolean nextMatch( char c ) throws JSONException {
		char n = next();
		if ( n != c ) {
			return false;
		}
		return true;
	}

	/**
	 * gets next character skipping any whitespace
	 *
	 * @return
	 * @throws JSONException
	 */
	public char nextNoSpace() throws JSONException {
		for (; ; ) {
			char c = next();
			if ( c == 0 || c > ' ' ) {
				return c;
			}
		}
	}

	/**
	 * gets the next value. Possible value, boolean, double, integer, jsonarray, jsonobject, long, string
	 *
	 * @return Object
	 * @throws JSONException
	 */
	public Object nextValue() throws JSONException {
		char c = nextNoSpace();
		String str;

		switch ( c ) {
			case '"':
			case '\'':
				return nextString( c );
			case '{':
				back();
				return new JSONObject( this );
			case '[':
				back();
				return new JSONArray( this );
		}

		StringBuilder sb = new StringBuilder();
		while ( c >= ' ' && notSpecialChar( c ) < 0 ) {
			sb.append( c );
			c = next();
		}

		back();

		str = sb.toString().trim();
		if ( str.isEmpty() ) {
			throw new JSONException( "Missing value" );
		}
		return stringToValue( str );
	}

	/**
	 * Returns characters to close quote
	 * @param quote opening quote type
	 * @return string
	 * @throws JSONException
	 */
	public String nextString( char quote ) throws JSONException {
		char c;
		StringBuilder sb = new StringBuilder();
		for (; ; ) {
			c = next();
			switch ( c ) {
				case 0:
				case '\n':
				case '\r':
					throw new JSONException( "unterminated string" );
				case '\\':
					c = this.next();
					switch ( c ) {
						case 'b':
							sb.append( '\b' );
							break;
						case 't':
							sb.append( '\t' );
							break;
						case 'n':
							sb.append( '\n' );
							break;
						case 'f':
							sb.append( '\f' );
							break;
						case 'r':
							sb.append( '\r' );
							break;
						case 'u':
							sb.append( (char) Integer.parseInt( nextN( 4 ), 16 ) ); //catches unicorns like \u2109
							break;
						case '"':
						case '\'':
						case '\\':
						case '/':
							sb.append( c );
							break;
						default:
							throw new JSONException( "Illegal escape." );
					}
					break;
				default:
					if (c == quote) {
						return sb.toString();
					}
					sb.append(c);
			}
		}
	}

	/**
	 * backs up one character
	 *
	 * @throws JSONException
	 */
	public void back() throws JSONException {
		if ( usePrevious || index <= 0 ) {
			throw new JSONException( "Cannot step back twice" );
		}
		index--;
		character--;
		usePrevious = true;
		eof = false;
	}

	/**
	 * get if reached end of file
	 * @return true is end
	 */
	public boolean end() {
		return this.eof && !this.usePrevious;
	}

	private Object stringToValue( String str ) {
		Double d;
		if ( str.equals( "" ) ) {
			return str;
		}
		if ( str.equalsIgnoreCase( "true" ) ) {
			return Boolean.TRUE;
		}
		if ( str.equalsIgnoreCase( "false" ) ) {
			return Boolean.FALSE;
		}
		if ( str.equalsIgnoreCase( "null" ) ) {
			return JSONObject.NULL;
		}

		char b = str.charAt( 0 );
		if ( ( b >= '0' && b <= '9' ) || b == '-' ) {
			try {
				if ( str.indexOf( '.' ) > -1 || str.indexOf( 'e' ) > -1 || str.indexOf( 'E' ) > -1 ) {
					d = Double.valueOf( str );
					if ( !d.isInfinite() && !d.isNaN() ) {
						return d;
					}
				} else {
					Long myLong = new Long( str );
					if ( str.equals( myLong.toString() ) ) {
						if ( myLong == myLong.intValue() ) {
							return myLong.intValue();
						} else {
							return myLong;
						}
					}
				}
			} catch ( Exception ignored ) {
			}
		}
		return str;
	}

	private int notSpecialChar( char c ) {
		return ",:]}/\\\"[{;=#".indexOf( c );
	}
}
