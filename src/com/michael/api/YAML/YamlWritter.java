package com.michael.api.YAML;

import com.michael.api.IO.IO;
import com.michael.api.json.Null;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Michael Risher on 2/28/2017.
 */
public class YamlWritter {
	public int YAML_INDENT_SIZE = 2;

	private String filename;
	private final Yaml topObject;
	private final Object NULL = new Null();

	public YamlWritter( Yaml instance ) {
		this.filename = instance.getFilename();
		this.topObject = instance;
	}

	public void initWrite( boolean newline ){
		BufferedWriter bw = null;
		try{
			bw = new BufferedWriter( new FileWriter( this.filename ) );
			int level = 0;
			write( level, topObject, bw );
			bw.close();
		} catch( IOException e ){
			e.printStackTrace();
		}
	}

	private void write( int level, Object obj, BufferedWriter bw ) throws IOException{
		//different types of write in here
		if( obj instanceof Yaml ){
			Yaml yaml = (Yaml)obj;//so i dont have to keep casting
			//get the keys in this object
			yaml.loadKeyIterator();
			Iterator<String> keys = yaml.getKeyIterator();
			while( keys.hasNext() ){
				String key = keys.next();
				YamlValue value = yaml.get( key );
				if( value.hasComment() ){
					bw.write( "#" + value.getComment() );
					bw.newLine();
				}
				padSpaces( level, bw );
				bw.write( key + ": " );
				if( value.getValue() instanceof Yaml ){
					bw.newLine();
					write( ++level, (Yaml)(value.getValue()), bw );
					--level;
				} else if( value.getValue() instanceof YamlList ){
					bw.newLine();
					write( ++level, (YamlList)(value.getValue()), bw );
					--level;
				} else {
					bw.write( valueToString( value.getValue() ) );
					bw.newLine();
				}
			}
		} else if( obj instanceof YamlList ){
			YamlList list = (YamlList)obj;//so i dont have to keep casting
			int size = list.getSize();
			for( int i = 0; i < size; i++ ){
				padSpaces( level, bw );
				bw.write( "-" + valueToString( list.get( i ) ) );
				bw.newLine();
			}
		}

	}

	private void padSpaces( int level, BufferedWriter bw ) throws IOException{
		String t = "";
		for( int i = 0; i < ( level * YAML_INDENT_SIZE ); i++ ){
			t+=" ";
		}
		bw.write( t );
	}

	private String valueToString( Object o ){
		if( o instanceof YamlValue ){
			Object val = ( (YamlValue) o ).getValue();
			return valueToString( val );
		}
		else if( o instanceof Double || NULL.equals( o ) ||
				o instanceof Byte || o instanceof Character ||
				o instanceof Short || o instanceof Integer ||
				o instanceof Float || o instanceof Long ||
				o instanceof Boolean ){
			return o.toString();
		}
		else if( o instanceof String ){
			if( ( (String) o ).indexOf( ' ' ) != -1 || ( (String) o ).indexOf( ':' ) != -1 ){
				return ( "\"" + o + "\"" );
			}
			return o.toString();
		}
//		else if( o instanceof YamlList ){
//			return ( (YamlList) o ).write();
//		}
//		else if( o instanceof Yaml ){
//			( (Yaml) o ).loadKeyIterator();
//			StringBuilder sb = new StringBuilder( );
//			sb.append( "\n" );
//			while( ( (Yaml) o).hasNext() ) {
//				sb.append( ( (Yaml) o).writeNext() );
//				sb.append( "\n" );
//			}
//			return sb.toString();
//		}
		else{
			return o.toString();
		}
	}

//	public void write( boolean hasNewline ) {
//		BufferedWriter writer = null;
//		try {
//			writer = new BufferedWriter( new FileWriter( this.filename ) );
//			writer.write( "---" );
//			writer.newLine();
//			while( instance.hasNext() ) {
//				writer.write( instance.writeNext() );
//				writer.newLine();
//				if ( hasNewline ) {
//					writer.newLine();
//				}
//			}
//			writer.flush();
//			writer.close();
//		} catch ( IOException e ) {
//			e.printStackTrace();
//		}
//
//	}
}
