package com.michael.api.YAML;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Created by Michael Risher on 2/28/2017.
 */
public class YamlList {
	private LinkedList<Object> array;

	public YamlList() {
		this.array = new LinkedList<>( );
	}

	public YamlList( Collection<Object> collection ) {
		this.array = new LinkedList<Object>( );
		if ( collection != null ) {
			Iterator<Object> itr = collection.iterator();
			while ( itr.hasNext() ) {
				this.array.add( Yaml.wrap( itr.next() ) );
			}
		}
	}

	public YamlList( Object array ) throws Exception{
		this.array = new LinkedList<Object>( );
		if ( array.getClass().isArray() ) {
			int length = Array.getLength( array );
			for ( int i = 0; i < length; i++ ) {
				this.put( Yaml.wrap( Array.get( array, i ) ) );
			}
		} else {
			throw new Exception( "YamlList initial value should be a string, collection or array." );
		}
	}

	public YamlList put( Object value ){
		this.array.push( value );
		return this;
	}

	public Object get( int i ){
		return this.array.get( i );
	}

	public int getSize(){
		return this.array.size();
	}

	public Object[] toArray(){
		return array.toArray();
	}
}
