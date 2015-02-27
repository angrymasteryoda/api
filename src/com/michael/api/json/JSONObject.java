package com.michael.api.json;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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
	public Object wrap( Object object ) {
		try {
			if ( object == null ) {
				return NULL;
			}
			if ( object instanceof JSONObject || object instanceof Double ||
				object instanceof Byte || object instanceof Character ||
				object instanceof Short || object instanceof Integer ||
				object instanceof Float || object instanceof Long ||
				object instanceof Boolean || object instanceof String ) {
				return object;
			}
			if ( object instanceof Map ) {
				return new JSONObject( (Map<String, Object>) object );
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

	public JSONObject put( String key, boolean value ) throws JSONException {
		this.put( key, value ? Boolean.TRUE : Boolean.FALSE );
		return this;
	}

	public JSONObject put( String key, double value ) throws JSONException {
		this.put( key, new Double( value ) );
		return this;
	}

	public JSONObject put( String key, float value ) throws JSONException {
		this.put( key, new Float( value ) );
		return this;
	}

	public JSONObject put( String key, long value ) throws JSONException {
		this.put( key, new Long( value ) );
		return this;
	}

	public JSONObject put( String key, int value ) throws JSONException {
		this.put( key, new Integer( value ) );
		return this;
	}

	public JSONObject put( String key, Object value ) throws JSONException {
		if ( key == null ) {
			throw new NullPointerException( "Null key." );
		}
		if ( value != null ) {
			validate( value );
			this.map.put( key, value );
		} else {
			this.remove( key );
		}
		return this;
	}

	/**
	 * remove a value from the map
	 * @param key key to look for
	 * @return
	 */
	public Object remove( String key ) {
		return this.map.remove( key );
	}

	/**
	 * validate the objects for json
	 *
	 * @param object to be validated
	 * @throws JSONException
	 */
	private void validate( Object object ) throws JSONException {
		if ( object != null ) {
			if ( object instanceof Double ) {
				if ( ( (Double) object ).isInfinite() || ( (Double) object ).isNaN() ) {
					throw new JSONException( "Doubles must be finite numbers" );
				}
			} else if ( object instanceof Float ) {
				if ( ( (Float) object ).isInfinite() || ( (Float) object ).isNaN() ) {
					throw new JSONException( "Floats must be finite numbers" );
				}
			}
		}
	}
}
