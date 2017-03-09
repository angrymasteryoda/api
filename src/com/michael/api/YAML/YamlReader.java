package com.michael.api.YAML;

import com.michael.api.IO.IO;

import java.io.*;

/**
 * Created by Michael Risher on 3/7/2017.
 */
public class YamlReader {

	public String filename;

	public YamlReader( String filename ) {
		this.filename = filename;
	}

	public void read() {
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
		while( !isLetter( line.charAt( i ) ) ){
			i++;
		}
		return i;
	}

	private boolean isLetter( char l ){
		return ( l == 32 ) ? false : true;
	}
}
