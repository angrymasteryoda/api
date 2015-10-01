package com.michael.api;

import com.michael.api.IO.Csv;
import com.michael.api.IO.CsvRecord;
import com.michael.api.IO.IO;

/**
 * Created By: Michael Risher
 * Date: 2/25/15
 * Time: 4:36 PM
 */
public class Tester {
	public static void main( String[] args ) throws Exception {
		Csv csv = new Csv( "test.csv" );
		csv.setHeader( new Object[]{ "string", "number", "bool" } );
		csv.put( new Object[]{ "string1", 1, true } );
		csv.put( new Object[]{ "string2", 2, false } );
		csv.put( new Object[]{ "string3", 3, true } );
		CsvRecord record = new CsvRecord();
		record.put( "string4" );
		record.put( 4);
		record.put( false );
		csv.put( record );
		IO.println( csv.getColumn( 0 ) );
		csv.write();
	}
}
/*
i,value,bin,hex

*/