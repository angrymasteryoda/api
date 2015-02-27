package com.michael.api.json;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import javax.swing.*;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.*;
import java.util.Map.Entry;

/**
 * Created By: Michael Risher
 * Date: 2/25/15
 * Time: 5:05 PM
 */
public class JSONObject {
	private final HashMap<String, Object> map;
	public static final Object NULL = new Null();

	public JSONObject() {
		this.map = new HashMap<String, Object>();
	}

	public JSONObject( Map<String, Object> map ) {
		this.map = new HashMap<String, Object>();
		if ( map != null ) {
			Iterator<Entry<String, Object>> itr = map.entrySet().iterator();
			while ( itr.hasNext() ) {
				Entry<String, Object> entry = itr.next();
				Object value = entry.getValue();
				if ( value != null ) {
					this.map.put( entry.getKey(), wrap( value ) );
				}
			}
		}
	}

	/**
	 * Wrap an object, if necessary.
	 *
	 * @param object object to be wrapped
	 * @return Object wrapped
	 */
	public static Object wrap( Object object ) {
		try {
			if ( object == null ) {
				return NULL;
			}
			if ( object instanceof JSONObject || object instanceof JSONArray ||
					object instanceof Double || NULL.equals( object ) ||
					object instanceof Byte || object instanceof Character ||
					object instanceof Short || object instanceof Integer ||
					object instanceof Float || object instanceof Long ||
					object instanceof Boolean || object instanceof String ) {
				return object;
			}
			if ( object instanceof Collection ) {
				return new JSONArray( (Collection<Object>)object );
			}
			if ( object.getClass().isArray() ) {
				return new JSONArray( object );
			}
			if ( object instanceof Map ) {
				return new JSONObject( (Map<String, Object>)object );
			}
			return null;
		} catch ( Exception ex ) {
			return null;
		}
	}

	/**
	 * get set of keys in json object
	 *
	 * @return Iterator
	 */
	public Iterator<String> keys() {
		return this.keySet().iterator();
	}

	/**
	 * get set of keys in json object
	 *
	 * @return Set
	 */
	public Set<String> keySet() {
		return this.map.keySet();
	}

	/**
	 * check if the json object has a specified key
	 *
	 * @param key key to search for
	 * @return boolean
	 */
	public boolean has( String key ) {
		return this.map.containsKey( key );
	}

	/**
	 * get length of json object
	 *
	 * @return length
	 */
	public int length() {
		return this.map.size();
	}

	public JSONObject put( String key, Collection<Object> value ) throws JSONException {
		return this.put( key, new JSONArray( value ) );
	}

	public JSONObject put( String key, boolean value ) throws JSONException {
		return this.put( key, value ? Boolean.TRUE : Boolean.FALSE );
	}

	public JSONObject put( String key, double value ) throws JSONException {
		return this.put( key, new Double( value ) );
	}

	public JSONObject put( String key, float value ) throws JSONException {
		return this.put( key, new Float( value ) );
	}

	public JSONObject put( String key, long value ) throws JSONException {
		return this.put( key, new Long( value ) );
	}

	public JSONObject put( String key, int value ) throws JSONException {
		return this.put( key, new Integer( value ) );
	}

	public JSONObject put( String key, Object value ) throws JSONException {
		if ( key == null ) {
			throw new NullPointerException( "Null key." );
		}
		if ( value != null ) {
			JSONWriter.validate( value );
			this.map.put( key, value );
		} else {
			this.remove( key );
		}
		return this;
	}

	public Object get( String key ) throws JSONException {
		if ( key == null ) {
			throw new JSONException( "null key" );
		}

		Object obj = this.optional( key );
		if ( obj == null ) {
			throw new JSONException( "JSONObject[" + JSONWriter.quote( key ) + "] not found" );
		}
		return obj;
	}

	public boolean getBoolean( String key ) throws JSONException {
		Object object = this.get( key );

		if ( object.equals( Boolean.FALSE ) ) {
			return false;
		} else if ( object.equals( Boolean.TRUE ) ) {
			return true;
		}
		throw new JSONException( "JSONObject[" + JSONWriter.quote( key ) + "] not boolean" );
	}

	public double getDouble( String key ) throws JSONException {
		Object obj = this.get( key );
		try {
			return ( obj instanceof Number ? ( (Number)obj ).doubleValue() : Double.parseDouble( (String)obj ) );
		} catch ( Exception e ) {
			throw new JSONException( "JSONObject[" + JSONWriter.quote( key ) + "] not double" );
		}
	}

	public float getFloat( String key ) throws JSONException {
		Object obj = this.get( key );
		try {
			return ( obj instanceof Number ? ( (Number)obj ).floatValue() : Float.parseFloat( (String)obj ) );
		} catch ( Exception e ) {
			throw new JSONException( "JSONObject[" + JSONWriter.quote( key ) + "] not float" );
		}
	}

	public int getInt( String key ) throws JSONException {
		Object obj = this.get( key );
		try {
			return ( obj instanceof Number ? ( (Number)obj ).intValue() : Integer.parseInt( (String)obj ) );
		} catch ( Exception e ) {
			throw new JSONException( "JSONObject[" + JSONWriter.quote( key ) + "] not int" );
		}
	}

	public long getLong( String key ) throws JSONException {
		Object obj = this.get( key );
		try {
			return ( obj instanceof Number ? ( (Number)obj ).longValue() : Long.parseLong( (String)obj ) );
		} catch ( Exception e ) {
			throw new JSONException( "JSONObject[" + JSONWriter.quote( key ) + "] not long" );
		}
	}

	public String getString( String key ) throws JSONException {
		Object obj = this.get( key );
		if ( obj instanceof String ) {
			return (String)obj;
		}
		throw new JSONException( "JSONObject[" + JSONWriter.quote( key ) + "] not String" );
	}

	public JSONArray getJSONArray( String key ) throws JSONException {
		Object obj = this.get( key );
		if ( obj instanceof JSONArray ) {
			return (JSONArray)obj;
		}
		throw new JSONException( "JSONObject[" + JSONWriter.quote( key ) + "] not JSONArray" );
	}

	public JSONObject getJSONObject( String key ) throws JSONException {
		Object obj = this.get( key );
		if ( obj instanceof JSONObject ) {
			return (JSONObject)obj;
		}
		throw new JSONException( "JSONObject[" + JSONWriter.quote( key ) + "] not JSONObject" );
	}


	public Object optional( String key ) {
		return ( key == null ? null : this.map.get( key ) );
	}


	/**
	 * remove a value from the map
	 *
	 * @param key key to look for
	 * @return
	 */
	public Object remove( String key ) {
		return this.map.remove( key );
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
		Iterator<String> keys = this.keys();
		boolean comma = false;
		sb.append( '{' );
		if ( length == 1 ) {
			Object key = keys.next();
			sb.append( JSONWriter.quote( key.toString() ) );
			sb.append( ':' );
			sb.append( JSONWriter.writeValue( this.map.get( key ) ) );
		} else {
			while ( keys.hasNext() ) {
				Object key = keys.next();
				if ( comma ) {
					sb.append( ',' );
				}

				sb.append( JSONWriter.quote( key.toString() ) );
				sb.append( ':' );
				sb.append( JSONWriter.writeValue( this.map.get( key ) ) );
				comma = true;
			}
		}
		sb.append( '}' );
		return sb.toString();
	}
}
