package com.michael.api.YAML;

import com.michael.api.json.JSONWriter;
import com.michael.api.json.Null;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * Created by Michael Risher on 2/28/2017.
 */
public class Yaml {
	private boolean hasOptionalHeaders = true;
	private LinkedHashMap<String, YamlValue> data;
	public static final Object NULL = new Null();
	private Iterator<String> keyIterator;
	private String filename;

	/**
	 * create blank yaml file
	 */
	public Yaml() {
		this.data = new LinkedHashMap<>(  );
	}

	/**
	 * create blank yaml file
	 * @param filename name of file
	 */
	public Yaml( String filename ) {
		this.filename = filename;
		this.data = new LinkedHashMap<>();
	}

	/**
	 * create yaml file with data
	 * @param filename name of file
	 * @param data linked hash map of data
	 */
	public Yaml( String filename, LinkedHashMap<String, YamlValue> data ) {
		this.filename = filename;
		this.data = data;
	}

	/**
	 * should be used for n-depth maps
	 * @param data
	 */
	//TODO fix this
//	public Yaml( Map<String, Object> data ) {
//		this.data = new LinkedHashMap<>(  );
//		if ( data != null ) {
//			Iterator<Map.Entry<String, Object>> itr = data.entrySet().iterator();
//			while ( itr.hasNext() ) {
//				Map.Entry<String, Object> entry = itr.next();
//				Object value = entry.getValue();
//				if ( value != null ) {
//					this.data.put( entry.getKey(), wrap( value ) );
//				}
//			}
//		}
//	}//just a test to see if ide can push now

	/**
	 * Insert a key pair into list without comment
	 * @param key key
	 * @param value value
	 * @return the yaml for chaining
	 * @throws Exception
	 */
	public Yaml put( String key, Object value ) throws Exception{
		if( key == null ){
			throw new Exception( "No key value" );
		}
		if( value != null ){
			JSONWriter.validate( value );//TODO look at if i should move this
			//check if putting a new
			this.data.put( key, new YamlValue( value ) );
		}
		return this;
	}

	/**
	 * Insert a key pair into list with comment
	 * @param key key
	 * @param value value
	 * @param comment comment for the key value pair
	 * @return the yaml for chaining
	 * @throws Exception
	 */
	public Yaml put( String key, Object value, String comment ) throws Exception{
		if( key == null ){
			throw new Exception( "No key value" );
		}
		if( value != null ){
			JSONWriter.validate( value );//TODO look at if i should move this
			this.data.put( key, new YamlValue( value, comment ) );
		}
		return this;
	}

	public Yaml put( String key, Collection<Object> value ) throws Exception {
		return this.put( key, new YamlList( value ) );
	}

	public YamlValue get( String key ){
		if( data.containsKey( key ) ){
			return data.get( key );
		}
		return null;
	}

	public Object getValue( String key ){
		if( data.containsKey( key ) ){
			return data.get( key ).getValue();
		}
		return null;
	}

	/**
	 * Wrap an object, if necessary.
	 *
	 * @param object object to be wrapped
	 * @return Object wrapped
	 */
	//TODO decide if i need it or not
	public static Object wrap( Object object ) {
		try {
			if ( object == null ) {
				return NULL;
			}
			if ( object instanceof YamlValue || object instanceof YamlList ||
					object instanceof Double || NULL.equals( object ) ||
					object instanceof Byte || object instanceof Character ||
					object instanceof Short || object instanceof Integer ||
					object instanceof Float || object instanceof Long ||
					object instanceof Boolean || object instanceof String ) {
				return object;
			}
			if ( object instanceof Collection ) {
				return new YamlList( (Collection<Object>) object );
			}
			if ( object.getClass().isArray() ) {
				return new YamlList( object );
			}
			//TODO look at this again
//			if ( object instanceof Map ) {
//				return new Yaml( (Map<String, YamlValue>) object );
//			}
			return null;
		} catch ( Exception ex ) {
			return null;
		}
	}

	public void read() {
		YamlReader reader = new YamlReader( this.filename );
		Yaml temp = reader.read();
		if( temp != null ){
			data = temp.getData();
		}

	}
	public void write(){
		YamlWritter yw = new YamlWritter( this );
		yw.initWrite( false );
	}

	public void writeJson( String filename ){
		YamlWritter yw = new YamlWritter( this );
	}

	public boolean hasNext(){
		return ( keyIterator != null && keyIterator.hasNext() );
	}

	public void loadKeyIterator(){
		keyIterator = data.keySet().iterator();
	}
	//getters / setters

	public String getFilename() {
		return filename;
	}

	public void setFilename( String filename ) {
		this.filename = filename;
	}

	public LinkedHashMap<String, YamlValue> getData() {
		return data;
	}

	public Iterator<String> getKeyIterator() {
		return keyIterator;
	}
}
