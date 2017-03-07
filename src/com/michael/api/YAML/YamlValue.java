package com.michael.api.YAML;

import java.util.Vector;

/**
 * Created by Michael Risher on 2/28/2017.
 */
public class YamlValue {
	private Object value;
	private String comment;

	/**
	 * Create a yaml value that has a comment associated with it
	 * @param value value
	 * @param comment comment for that value
	 */
	public YamlValue( Object value, String comment ) {
		this.value = value;
		this.comment = comment;
	}

	/**
	 * create a yaml value. could just use Yaml.put( key, value )
	 * @param value
	 */
	public YamlValue( Object value ) {
		this.value = value;
	}

	public boolean hasComment(){
		return ( ( comment == null ) ? false : !comment.isEmpty() );
	}

	public Object getValue() {
		return value;
	}

	public void setValue( Object value ) {
		this.value = value;
	}

	public String getComment() {
		return comment;
	}

	public void setComment( String comment ) {
		this.comment = comment;
	}
}
