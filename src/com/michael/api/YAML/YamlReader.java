package com.michael.api.YAML;

import com.michael.api.IO.IO;

import java.io.*;

/**
 * Created by Michael Risher on 3/7/2017.
 */
public class YamlReader {

	public String filename;
	private int YAML_INDENT_SIZE = 2;

	public YamlReader( String filename ) {
		this.filename = filename;
	}

	public void read(){
		BufferedReader br = null;
		Yaml empty = null; //= new Yaml( "result.yml" );
		try{
			br = new BufferedReader( new FileReader( filename ) );
			empty = recursiveRead( br, empty, 0 );
			br.close();
		} catch ( Exception e ){
			e.printStackTrace();
		}
		empty.setFilename( "result.yml" );
		empty.write();
	}

	private Yaml recursiveRead( BufferedReader br, Yaml obj,  int level ) throws Exception{
		if( obj == null ) obj = new Yaml(  );
		String current;
		String[] temp;
		boolean isBlockText = false;
		String blockKey = "";
		String blockText = "";
		while( ( current = br.readLine() ) != null ){
			//check for level change which is my exit case
			if( getLevel( current ) < level ) break;
			//ignore comments
			if( current.contains( "#" ) ){
				current = current.split("#")[0];
				if( current.isEmpty() ) continue;
			}
			//split data on the colon if it has
			if ( current.contains( ":" ) ) {
				temp = current.split( ":", 2 );
				if( temp[1].isEmpty() ){ //this is a new Object here
					Yaml nested = recursiveRead( br, null, ++level );
					obj.put( temp[0].trim(), nested );
					level--;
				} else {
					IO.print( temp[0] );
					temp[1] = temp[1].trim();
					if( temp[1].startsWith( ">" ) || isBlockText ){ //this is block text
						isBlockText = true;
						blockKey = temp[0].trim();
					} else {
						IO.println( " : " + temp[1]);
						obj.put( temp[0].trim(), removeQuotes( temp[1] ) );
					}
				}
			} else if ( isBlockText && !blockKey.isEmpty() ){
				if( getLevel( current ) == level + 1 ){
					blockText += current.trim() + " ";

					int BUFFER_SIZE = 1000;
					br.mark(BUFFER_SIZE);
					String next = br.readLine();  // returns the GET
					if( next == null || getLevel( next ) == level ){
						//stop the blockText
						isBlockText = false;
						obj.put( blockKey, blockText.trim() );
						blockText = "";
						blockKey = "";
					}
					br.reset();     // rewinds the stream back to the mark
				}
			}
		}
		return obj;

	}
	public void readv1() {
		//how can we check if 2 space or 4 spaced
		BufferedReader br = null;
		try {
			Yaml topObj = new Yaml();
			br = new BufferedReader( new FileReader( filename ) );
			String currentLine, prevLine;
			while ( ( currentLine = br.readLine() ) != null ) {
				//ignore comments
				if( currentLine.trim().contains( "#" ) ){
					currentLine = currentLine.split( "#" )[0];
				}
				if( !currentLine.isEmpty() ) {
					//IO.println( currentLine );
					int spaces = countSpaces( currentLine );
					//got the depth level trim it now
					currentLine = currentLine.trim();
					if( currentLine.contains( ":" ) ) { //assume it is a key
						String key = "";
						String value = "";
						if( currentLine.contains( "\"" ) ){
							boolean inQuotes = false;
							int indexColon = -1;
							for( int i = 0; i < currentLine.length(); i++ ){
								if( currentLine.charAt( i ) == '"' ){
									inQuotes = !inQuotes;
								}
								else if( currentLine.charAt( i ) == ':' && inQuotes == false){
									indexColon = i;
									break; //if there is more that 2 : that are not in quotes not my fault
								}
							}

							key = currentLine.substring( 0, indexColon );
							value = currentLine.substring( indexColon + 1 ).trim();
						} else {
							String[] t = currentLine.split( ":" );
							key = t[0];
							if( t.length == 2) {
								value = t[1].trim();
							}
							t = null; //delete t for space reasons
						}
						IO.println( key + ":" + value );
						//strip quote out of string value
						if( value.contains( "\"" ) ){
							if( value.indexOf( '"' ) == 0 && value.lastIndexOf( '"' ) == value.length() - 1 ){
								value = value.substring( 1, value.length() - 1 );
							}
						}
						if( value.isEmpty() ){
							//assume its an object
						} else{
							//probably a key value pair
							topObj.put( key, value );
						}
					}
				}
				prevLine = currentLine;
			}
			br.close();
			topObj.setFilename( "result.yml" );
			topObj.write();
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	private int countSpaces( String line ){
		int i = 0;
		while( isSpace( line.charAt( i ) ) ){
			i++;
		}
		return i;
	}

	private boolean isSpace( char l ){
		return ( l == 32 ) ? true : false;
	}


	private int getLevel( String line ) {
		int i = 0;
		while ( isSpace( line.charAt( i ) ) ) {
			i++;
		}
		return i / YAML_INDENT_SIZE;
	}

	private String removeQuotes( String line ){
		if( line.startsWith( "\"" ) && line.endsWith( "\"" ) ){
			line = line.substring( 1, line.length()-1 );
		}
		return line;
	}

}
