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
			empty.setFilename( filename );
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
		String lastComment = "";
		while( ( current = br.readLine() ) != null ){
			//do we can do a scan ahead if needed


			//check for level change which is my exit case
			if( getLevel( current ) < level ) break;
			//ignore comments
			if( current.contains( "#" ) ){
				lastComment = current.split( "#" )[1];
				current = current.split("#")[0];
				if( current.isEmpty() ) continue;
			}
			//split data on the colon if it has
			if ( current.contains( ":" ) ) {
				temp = current.split( ":", 2 );
				if( temp[1].isEmpty() ){ //this is a new Object here or could be a list
					//check if a list
					int BUFFER_SIZE = 1000;//todo this breaks my yaml code
					br.mark(BUFFER_SIZE);
					String next = br.readLine();
					if( next.trim().startsWith( "-" ) ){
						YamlList list = new YamlList();
						list.put( next.trim().split( "-" )[1].trim() );
						while( ( next = br.readLine() ) != null ){
							next = next.trim();
							if( next.startsWith( "-" ) ) {
								next = next.split( "-", 2 )[1];
								if( !next.isEmpty() ) {
									list.put( removeQuotes( next.trim() ) );
								}
							} else break;
						}
						obj.put( temp[0].trim(), list, lastComment );
						lastComment = "";
						br.reset();
					} else { //for an object
						br.reset();
						Yaml nested = recursiveRead( br, null, ++level );
						obj.put( temp[0].trim(), nested );
						level--;
					}
				} else {
//					IO.print( temp[0] );
					temp[1] = temp[1].trim();
					if( temp[1].startsWith( ">" ) || isBlockText ){ //this is block text
						isBlockText = true;
						blockKey = temp[0].trim();
					} else {
//						IO.println( " : " + temp[1]);
//						IO.println( lastComment );
						obj.put( temp[0].trim(), removeQuotes( temp[1] ), lastComment );
						lastComment = "";
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

	protected boolean isSpace( char l ){
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
