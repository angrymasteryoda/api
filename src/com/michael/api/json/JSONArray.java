package com.michael.api.json;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by: Michael Risher
 * Date: 2/26/15
 * Time: 5:22 PM
 */
public class JSONArray {
	private final ArrayList<Object> array;

	public JSONArray() {
		this.array = new ArrayList<Object>();
	}

	public JSONArray( Collection<Object> collection ) {
		this.array = new ArrayList<Object>();
		if ( collection != null ) {
			Iterator<Object> itr = collection.iterator();
			while ( itr.hasNext() ) {
				this.array.add( JSONObject.wrap( itr.next() ) );
			}
		}
	}

	// very vague constructor
	public JSONArray( Object array ) throws JSONException {
		this();
		if ( array instanceof JSONReader ) {
			JSONReader reader = (JSONReader) array;
			if ( reader.nextNoSpace() != '[' ) {
				throw new JSONException( "Must start with a '['" );
			}
			if ( reader.nextNoSpace() != ']' ) {
				reader.back();
				for (; ; ) {
					if ( reader.nextNoSpace() == ',' ) {
						reader.back();
						this.array.add( JSONObject.NULL );
					}
					else{
						reader.back();
						this.array.add( reader.nextValue() );
					}

					switch ( reader.nextNoSpace() ) {
						case ',':
							if ( reader.nextNoSpace() == ']' ) {
								return;
							}
							reader.back();
							break;
						case ']':
							return;
						default:
							throw new JSONException( "Expected a ',' or ']'" );
					}
				}
			}
		}
		if ( array.getClass().isArray() ) {
			int length = Array.getLength( array );
			for ( int i = 0; i < length; i++ ) {
				this.put( JSONObject.wrap( Array.get( array, i ) ) );
			}
		} else {
			throw new JSONException( "JSONArray initial value should be a string, collection or array." );
		}
	}

	public JSONArray( String string ){
		this( new JSONReader( string ) );
	}

	public Object get( int i ) throws JSONException {
		Object object = this.opt( i );
		if ( object == null ) {
			throw new JSONException( "JSONArray[" + i + "] not found" );
		}
		return object;
	}

	public boolean getBoolean( int i ) throws JSONException {
		Object obj = this.get( i );
		if ( obj.equals( Boolean.FALSE ) ) {
			return false;
		} else if ( obj.equals( Boolean.TRUE ) ) {
			return true;
		}
		throw new JSONException( "JSONArray[" + i + "] not boolean" );
	}

	public double getDouble( int i ) throws JSONException {
		Object obj = this.get( i );
		try {
			return ( obj instanceof Number ? ( (Number) obj ).doubleValue() : Double.parseDouble( (String) obj ) );
		} catch ( Exception e ) {
			throw new JSONException( "JSONArray[" + i + "] not double" );
		}
	}

	public float getFloat( int i ) throws JSONException {
		Object obj = this.get( i );
		try {
			return ( obj instanceof Number ? ( (Number) obj ).floatValue() : Float.parseFloat( (String) obj ) );
		} catch ( Exception e ) {
			throw new JSONException( "JSONArray[" + i + "] not float" );
		}
	}

	public int getInt( int i ) throws JSONException {
		Object obj = this.get( i );
		try {
			return ( obj instanceof Number ? ( (Number) obj ).intValue() : Integer.parseInt( (String) obj ) );
		} catch ( Exception e ) {
			throw new JSONException( "JSONArray[" + i + "] not int" );
		}
	}

	public long getLong( int i ) throws JSONException {
		Object obj = this.get( i );
		try {
			return ( obj instanceof Number ? ( (Number) obj ).longValue() : Long.parseLong( (String) obj ) );
		} catch ( Exception e ) {
			throw new JSONException( "JSONArray[" + i + "] not long" );
		}
	}

	public String getString( int i ) throws JSONException {
		Object obj = this.get( i );
		if ( obj instanceof String ) {
			return (String) obj;
		}
		throw new JSONException( "JSONArray[" + i + "] not String" );
	}

	public JSONArray getJSONArray( int i ) throws JSONException {
		Object obj = this.get( i );
		if ( obj instanceof JSONArray ) {
			return (JSONArray) obj;
		}
		throw new JSONException( "JSONArray[" + i + "] not JSONArray" );
	}

	public JSONObject getJSONObject( int i ) throws JSONException {
		Object obj = this.get( i );
		if ( obj instanceof JSONObject ) {
			return (JSONObject) obj;
		}
		throw new JSONException( "JSONArray[" + i + "] not JSONObject" );
	}

	public int length() {
		return this.array.size();
	}

	/**
	 * Get the optional object value associated with an index.
	 *
	 * @param i The index must be between 0 and length() - 1.
	 * @return An object value, or null
	 */
	public Object opt( int i ) {
		return ( i < 0 || i >= this.length() ) ? null : this.array.get( i );
	}

	public JSONArray put( Collection<Object> value ) {
		return this.put( new JSONArray( value ) );
	}

	public JSONArray put( boolean value ) {
		return this.put( value ? Boolean.TRUE : Boolean.FALSE );
	}

	public JSONArray put( float value ) {
		return this.put( new Float( value ) );
	}

	public JSONArray put( double value ) {
		return this.put( new Double( value ) );
	}

	public JSONArray put( int value ) {
		return this.put( new Integer( value ) );
	}

	public JSONArray put( long value ) {
		return this.put( new Long( value ) );
	}

	public JSONArray put( Map<String, Object> value ) {
		return this.put( new JSONObject( value ) );
	}

	public JSONArray put( Object value ) {
		this.array.add( value );
		return this;
	}

	public JSONArray put( int i, Collection<Object> value ) {
		return this.put( i, new JSONArray( value ) );
	}

	public JSONArray put( int i, boolean value ) {
		return this.put( i, value ? Boolean.TRUE : Boolean.FALSE );
	}

	public JSONArray put( int i, float value ) {
		return this.put( i, new Float( value ) );
	}

	public JSONArray put( int i, double value ) {
		return this.put( i, new Double( value ) );
	}

	public JSONArray put( int i, int value ) {
		return this.put( i, new Integer( value ) );
	}

	public JSONArray put( int i, long value ) {
		return this.put( i, new Long( value ) );
	}

	public JSONArray put( int i, Map<String, Object> value ) {
		return this.put( i, new JSONObject( value ) );
	}

	public JSONArray put( int i, Object value ) {
		if ( i < 0 ) {
			throw new JSONException( "JSONArray[" + i + "] not found." );
		}
		if ( i < this.length() ) {
			this.array.set( i, value );
		} else {
			while ( i != this.length() ) {
				this.put( JSONObject.NULL );
			}
			this.put( value );
		}
		return this;
	}

	public Object remove( int i ) {
		return i >= 0 && i < this.length() ? this.array.remove( i ) : null;
	}

	public String toString() {
		try {
			return write();
		} catch ( Exception e ) {
			return null;
		}
	}

	public String write() throws JSONException {
		StringBuilder sb = new StringBuilder();
		final int length = this.length();
		boolean comma = false;
		sb.append( '[' );

		if ( length == 1 ) {
			sb.append( JSONWriter.writeValue( this.array.get( 0 ) ) );
		} else {
			for ( int i = 0; i < length; i++ ) {
				if ( comma ) {
					sb.append( ',' );
				}

				sb.append( JSONWriter.writeValue( this.array.get( i ) ) );
				comma = true;
			}
		}
		sb.append( ']' );
		return sb.toString();
	}


}
