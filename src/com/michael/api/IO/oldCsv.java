package com.michael.api.IO;

import java.io.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Michael Risher
 * Date: 8/15/15
 * Time: 10:12 AM
 */
public class oldCsv {
	private String fileName;
	private List list;
	private int index = 0;

	private char delimiter;
	private char enclosure;
	private char escape;

	//TODO doc this class

	/**
	 * Create a csv file
	 */
	public oldCsv() {
		this( null, ',', '"', '\\' );
	}

	/**
	 * Create a csv file
	 * @param fileName filename to save with
	 */
	public oldCsv( String fileName ) {
		this( fileName, ',', '"', '\\' );
	}

	/**
	 * Create a csv file
	 * @param fileName filename to save with
	 * @param delimiter char used as delimiter
	 */
	public oldCsv( String fileName, char delimiter ) {
		this( fileName, delimiter, '"', '\\' );
	}

	/**
	 * Create a csv file
	 * @param fileName filename to save with
	 * @param delimiter char used as delimiter
	 * @param enclosure char that encloses strings
	 */
	public oldCsv( String fileName, char delimiter, char enclosure ) {
		this( fileName, delimiter, enclosure, '\\' );
	}

	/**
	 * Create a csv file
	 * @param fileName filename to save with
	 * @param delimiter char used as delimiter
	 * @param enclosure char that encloses strings
	 * @param escape char to escape characters
	 */
	public oldCsv( String fileName, char delimiter, char enclosure, char escape ) {
		this.fileName = fileName;
		this.list = new ArrayList();
		this.delimiter = delimiter;
		this.enclosure = enclosure;
		this.escape = escape;
	}


	/**
	 * Insert an array of data into a line of the csv
	 * @param in data to input
	 */
	public void put( final Object[] in ) {
		list.add( join( in ) );
	}

	/**
	 * Insert an array of data into a line of the csv
	 * @param i index to insert to
	 * @param in data to input
	 */
	public void put( int i, final Object[] in ) {
		list.add( i, join( in ) );
	}

	/**
	 * remove all data from csv
	 */
	public void clear() {
		list.clear();
	}

	/**
	 * get a line from the csv
	 * @param index line
	 * @return line from csv in array
	 */
	public Object[] get( int index ) {
		return split( list.get( index ) );
	}

	/**
	 * get a specific item in a line from the csv
	 * @param col line
	 * @param index index of  the object
	 * @return fetched object
	 */
	public Object getIndex( int col, int index ){
		Object[] objs = split( list.get( col ) );
		return objs[index];
	}

	/**
	 * write to file
	 * @throws IOException
	 */
	public void write() throws IOException {
		if ( fileName == null ) {
			IOException e = new IOException( "No file inputted" );
			throw e;
		}
		File file = new File( fileName );
		if ( file.getParentFile() != null ) {
			if ( !file.getParentFile().exists() ) {
				file.mkdirs();
			}
		}

		if ( !file.exists() ) {
			file.createNewFile();
		}

		BufferedWriter bw = new BufferedWriter( new FileWriter( file.getAbsolutePath() ) );
		for ( int i = 0; i < list.size(); i++ ) {
			bw.write( list.get( i ).toString() );
			bw.newLine();
		}
		bw.flush();
		bw.close();
	}

	/**
	 * read from file
	 * @throws IOException
	 */
	public void read() throws IOException{
		File file = new File( fileName );

		BufferedReader br = new BufferedReader( new FileReader( file.getAbsolutePath() ) );
		String line = "";
		//clear the list new stuff incoming
		clear();
		int i = 0;
		while( ( line = br.readLine() ) != null ){
			list.add( i++, line );
		}
		br.close();
	}

	/**
	 * join the data into a string for printing
	 * @param in
	 * @return
	 */
	private String join( final Object[] in ) {
		String s = "";
		for ( int i = 0; i < in.length; i++ ) {
			if ( in[ i ] instanceof Number || in[ i ] instanceof Boolean ) {
				s += in[ i ].toString();
			} else {
				String temp = in[ i ].toString();
				temp = escape( temp );
				s += enclosure + temp + enclosure;
			}
			if ( i != in.length - 1 ) {
				s += delimiter;
			}
		}
		return s;
	}

	/**
	 * split the string read from file into the java data
	 * @param in
	 * @return
	 */
	private Object[] split( final Object in ) {
		String strIn = in.toString();
		int commas = 0;
		for ( int i = 0; i < strIn.length(); i++ ) {
			if ( strIn.charAt( i ) == delimiter ) {
				commas++;
			}
		}
		int type = -1;
		Object[] objs = new Object[ commas + 1 ];
		String temp = "";
		int index = 0;
		boolean doubleEnclosure = false;
		for ( int i = 0; i < strIn.length(); i++ ) {
			boolean skip = false;
			char current = strIn.charAt( i );
			if ( current == enclosure ) {
				//its a string
				boolean noLookAhead = false;
				if ( i == strIn.length() - 1 ) {
					noLookAhead = true;
				}
				type = 1;
				if ( !noLookAhead ) {
					if ( strIn.charAt( i + 1 ) == enclosure || doubleEnclosure ) {
						//assume its a enclosure meant to be there
						if ( doubleEnclosure ) {
							skip = true;
							doubleEnclosure = false;
						} else {
							doubleEnclosure = true;
						}

					}
				}
			} else if ( ( "" + current ).matches( "[0-9\\.\\-]" ) ) {
				//its a number type
				type = 2;
			} else {
				if ( type == -1 ) {
					//probably a boolean
					type = 3;
				} else {
					//if its a letter in a string
					doubleEnclosure = false;
				}
			}

			if ( current == delimiter || i == strIn.length() - 1 ) {
				if ( i == strIn.length() - 1) {
					//get the last char
					temp += current;
				}
				skip = true;
				switch ( type ) {
					case 1:
						//take off the quotes in front and end
						temp = temp.substring( 1, temp.length() - 1 );
						objs[ index++ ] = temp;
						break;
					case 2:
						if ( temp.matches( "[\\-\\d]*\\.[\\d]+" ) ) {
							//going to do double so i don't have to check size
							objs[ index++ ] = Double.parseDouble( temp );
						} else {
							objs[ index++ ] = Integer.parseInt( temp );
						}
						break;
					case 3:
						if ( temp.matches( "(?i)(true)|(false)" ) ) {
							objs[ index++ ] = Boolean.parseBoolean( temp );
						}
						break;
				}
				type = -1;
				temp = "";
			}

			if ( !skip ) {
				temp += current;
			}

		}
		return objs;
	}

	public int size(){
		return list.size();
	}

	public boolean contains( Object obj ){
		return list.contains( obj );
	}

	public boolean containsAll( Collection<?> collections ){
		return list.containsAll( collections );
	}

	public int indexOf( Object o ){
		return list.indexOf( o );
	}

	public int lastIndexOf( Object o){
		return list.lastIndexOf( o );
	}

	public Object remove( int i ){
		return list.remove( i );
	}

	public Object set( int i, Object o ){
		return list.set( i, o );
	}

	public ListIterator listIterator(){
		return list.listIterator();
	}

	public ListIterator listIterator( int i ){
		return list.listIterator( i );
	}

	private String escape( final String in ) {
		String str = "";
		for ( int i = 0; i < in.length(); i++ ) {
			char current = in.charAt( i );
			if ( current == enclosure ) {
				str += "" + current + current;
			} else {
				str += current;
			}
		}
		return str;
	}


	private void test() {
		Object[] a = split( list.get( 0 ) );
		for ( int i = 0; i < a.length; i++ ) {
			System.out.println( a[ i ] );
		}
	}


	public String getFileName() {
		return fileName;
	}

	public void setFileName( String fileName ) {
		this.fileName = fileName;
	}

	public char getDelimiter() {
		return delimiter;
	}

	public void setDelimiter( char delimiter ) {
		this.delimiter = delimiter;
	}

	public char getEnclosure() {
		return enclosure;
	}

	public void setEnclosure( char enclosure ) {
		this.enclosure = enclosure;
	}

	public char getEscape() {
		return escape;
	}

	public void setEscape( char escape ) {
		this.escape = escape;
	}
}
