package com.michael.api.IO;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

/**
 * Created By: Michael Risher
 * Date: 9/30/15
 * Time: 6:09 PM
 */
@SuppressWarnings("unchecked")
public class Csv {
	private boolean hasHeaders = false;
	private String fileName;
	private List<Object> list;
	private List<Object> header;
	private char delimiter;
	private char enclosure;
	private char escape;

	/**
	 * Create a csv file
	 */
	public Csv() {
		this( null, ',', '"', '\\' );
	}

	/**
	 * Create a csv file
	 *
	 * @param fileName filename to save with
	 */
	public Csv( String fileName ) {
		this( fileName, ',', '"', '\\' );
	}

	/**
	 * Create a csv file
	 *
	 * @param fileName  filename to save with
	 * @param delimiter char used as delimiter
	 */
	public Csv( String fileName, char delimiter ) {
		this( fileName, delimiter, '"', '\\' );
	}

	/**
	 * Create a csv file
	 *
	 * @param fileName  filename to save with
	 * @param delimiter char used as delimiter
	 * @param enclosure char that encloses strings
	 */
	public Csv( String fileName, char delimiter, char enclosure ) {
		this( fileName, delimiter, enclosure, '\\' );
	}

	/**
	 * Create a csv file
	 *
	 * @param fileName  filename to save with
	 * @param delimiter char used as delimiter
	 * @param enclosure char that encloses strings
	 * @param escape    char to escape characters
	 */
	public Csv( String fileName, char delimiter, char enclosure, char escape ) {
		this.fileName = fileName;
		this.list = new ArrayList<Object>();
		this.header = new ArrayList();
		this.delimiter = delimiter;
		this.enclosure = enclosure;
		this.escape = escape;
	}

	/**
	 * Insert an array of data into a line of the csv
	 *
	 * @param in data to input
	 */
	public void put( final Object[] in ) {
		list.add( new CsvRecord( in ) );
	}

	/**
	 * Insert an array of data into a line of the csv
	 *
	 * @param in data to input
	 */
	public void put( final CsvRecord in ) {
		list.add( in );
	}

	/**
	 * Insert an array of data into a line of the csv
	 *
	 * @param in data to input
	 */
	public void put( final ArrayList[] in ) {
		list.add( new CsvRecord( in ) );
	}

	/**
	 * Insert an array of data into a line of the csv
	 *
	 * @param i  index to insert to
	 * @param in data to input
	 */
	public void put( int i, final Object[] in ) {
		list.add( i, new CsvRecord( in ) );
	}

	/**
	 * Insert an array of data into a line of the csv
	 *
	 * @param i  index to insert to
	 * @param in data to input
	 */
	public void put( int i, final ArrayList[] in ) {
		list.add( i, new CsvRecord( in ) );
	}

	/**
	 * Insert an array of data into a line of the csv
	 *
	 * @param i  index to insert to
	 * @param in data to input
	 */
	public void put( int i, final CsvRecord[] in ) {
		list.add( i, in );
	}

	/**
	 * remove all data from csv
	 */
	public void clear() {
		list.clear();
	}

	/**
	 * get a line from the csv
	 *
	 * @param index line
	 * @return line from csv in array
	 */
	public CsvRecord get( int index ) {
		return (CsvRecord) list.get( index );
	}

	/**
	 * Get entire column
	 * @param index column index
	 * @return Column stored in CsvRecord
	 */
	public CsvRecord getColumn( int index ) {
		CsvRecord record = new CsvRecord();
		for ( int i = 0; i < list.size(); i++ ) {
			record.put( ( (CsvRecord) list.get( i ) ).get( 0 ) );
		}
		return record;
	}

	/**
	 * write to file. if headers exist will write them
	 * @throws java.io.IOException
	 */
	public void write() throws IOException {
		write( hasHeaders );
	}

	/**
	 * write to file.
	 * @param writeHeaders flag to print the headers
	 * @throws IOException
	 */
	public void write( boolean writeHeaders ) throws IOException {
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

		if ( writeHeaders && hasHeaders ) {
			bw.write( ( ( CsvRecord ) header.get( 0 ) ).printString( delimiter, enclosure ) );
			bw.newLine();
		}
		for ( int i = 0; i < list.size(); i++ ) {
			bw.write( ( (CsvRecord) list.get( i ) ).printString( delimiter, enclosure ) );
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
		read( false );
	}

	/**
	 * read from file
	 * @param hasHeaders flag if has headers will exclude them in the data
	 * @throws IOException
	 */
	public void read( boolean hasHeaders ) throws IOException{
		File file = new File( fileName );

		BufferedReader br = new BufferedReader( new FileReader( file.getAbsolutePath() ) );
		String line = "";
		//clear the list new stuff incoming
		clear();
		int i = 0;
		while( ( line = br.readLine() ) != null ){
			if ( hasHeaders && i ==0 ) {
				header.add( i++, CsvRecord.loadString( line, delimiter, enclosure ) );
			} else{
				list.add( i++, CsvRecord.loadString( line, delimiter, enclosure ) );
			}
		}
		br.close();
	}

	/**
	 * gets the row count of the data
	 * @return count of rows
	 */
	public int size(){
		return list.size();
	}

	/**
	 * Returns true if this list contains the specified element.
	 * @param obj element whose presence in this list is to be tested
	 * @return true if this list contains the specified element
	 */
	public boolean contains( Object obj ){
		return list.contains( obj );
	}

	/**
	 * Returns true if this list contains all of the elements of the specified collection.
	 * @param  collections collection to be checked for containment in this list
	 * @return true if this list contains all of the elements of the specified collection
	 */
	public boolean containsAll( Collection<?> collections ){
		return list.containsAll( collections );
	}

	/**
	 * Returns the index of the first occurrence of the specified element
	 * in this list, or -1 if this list does not contain the element.
	 * @param o element to search for
	 * @return the index of the first occurrence of the specified element in this list, or -1 if this list does not contain the element
	 */
	public int indexOf( Object o ){
		return list.indexOf( o );
	}

	/**
	 * Returns the index of the last occurrence of the specified element in this list, or -1 if this list does not contain the element.
	 *
	 * @param o element to search for
	 * @return the index of the last occurrence of the specified element in this list, or -1 if this list does not contain the element
	 */
	public int lastIndexOf( Object o){
		return list.lastIndexOf( o );
	}

	/**
	 * Removes the element at the specified position in this list (optional operation).  Shifts any subsequent elements to the left (subtracts one
	 * from their indices).  Returns the element that was removed from the list.
	 *
	 * @param index the index of the element to be removed
	 * @return the element previously at the specified position
	 */
	public Object remove( int index ){
		return list.remove( index );
	}

	/**
	 * Replaces the element at the specified position in this list with the specified element (optional operation).
	 *
	 * @param index index of the element to replace
	 * @param element element to be stored at the specified position
	 * @return the element previously at the specified position
	 */
	public Object set( int index, Object element ){
		return list.set( index, element );
	}

	/**
	 * Returns a list iterator over the elements in this list (in proper sequence).
	 *
	 * @return a list iterator over the elements in this list (in proper sequence)
	 */
	public ListIterator listIterator(){
		return list.listIterator();
	}

	/**
	 * Returns a list iterator over the elements in this list (in proper sequence), starting at the specified position in the list.
	 * The specified index indicates the first element that would be returned by an initial call to {@link ListIterator#next next}.
	 * An initial call to {@link ListIterator#previous previous} would return the element with the specified index minus one.
	 *
	 * @param index index of the first element to be returned from the list iterator (by a call to {@link ListIterator#next next})
	 * @return a list iterator over the elements in this list (in proper sequence), starting at the specified position in the list
	 */
	public ListIterator listIterator( int index ){
		return list.listIterator( index );
	}

	/**
	 * Returns the filename
	 * @return the filename string
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * set the filename
	 * @param fileName string to set the filename to
	 */
	public void setFileName( String fileName ) {
		this.fileName = fileName;
	}

	/**
	 * returns the delimiter
	 * @return returns the delimiter
	 */
	public char getDelimiter() {
		return delimiter;
	}

	/**
	 * set the delimiter
	 * @param delimiter char to set the delimiter
	 */
	public void setDelimiter( char delimiter ) {
		this.delimiter = delimiter;
	}

	/**
	 * returns the enclosure
	 * @return returns the enclosure
	 */
	public char getEnclosure() {
		return enclosure;
	}

	/**
	 * set the enclosure
	 * @param enclosure char to set the enclurse
	 */
	public void setEnclosure( char enclosure ) {
		this.enclosure = enclosure;
	}

	/**
	 * get the escape character
	 * @deprecated 9/30/15
	 * @return returns the escape character
	 */
	public char getEscape() {
		return escape;
	}

	/**
	 * set the escape character
	 * @deprecated 9/30/15
	 * @param escape char to set the escape character
	 */
	public void setEscape( char escape ) {
		this.escape = escape;
	}

	/**
	 * get the header as a CsvRecord
	 * @return returns the header as a CsvRecord
	 */
	public CsvRecord getHeader() {
		return (CsvRecord) header.get( 0 );
	}

	/**
	 * set the header with an Object[]
	 * @param in Object[] array to input
	 */
	public void setHeader( Object[] in ) {
		hasHeaders = true;
		header.add( 0, new CsvRecord( in ) );
	}

	/**
	 * set the header with a ArrayList
	 * @param in ArrayList to input
	 */
	public void setHeader( ArrayList in ) {
		hasHeaders = true;
		header.add( 0, new CsvRecord( in ) );
	}

	/**
	 * set the header with a CsvRecord
	 * @param in CsvRecord to input
	 */
	public void setHeader( CsvRecord in ) {
		hasHeaders = true;
		header.add( 0, in );
	}

	/**
	 * delete the headers
	 */
	public void removeHeader(){
		hasHeaders = false;
		header = new ArrayList();
	}
}
