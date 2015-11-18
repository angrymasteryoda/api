package com.michael.api;

import com.michael.api.IO.IO;
import com.michael.api.math.Random;

/**
 * Created By: Michael Risher
 * Date: 2/25/15
 * Time: 4:36 PM
 */
public class Tester {
	public static void main( String[] args ) throws Exception {
		IO.println( Random.randomLowercaseLetters( 200 ) );
//		MySqlDatabase db = new MySqlDatabase( "jdbc:mysql://localhost:3306/csgo", "root", "", "local" );
//		Connection conn = db.getConnection();
//		Statement state = conn.createStatement();
//		ResultSet res = null;
//		res = state.executeQuery( "SELECT * FROM guns" );
//
//		Csv csv = new Csv( "test.csv");
//		csv.setHeader( res );
//		csv.fetchResultSet( res );
//		csv.write();
//		res.close();
//		state.close();
//		conn.close();

//		TextImage.writeString( "asd", "test.png");
//		BaseConversions.setPrettyPrint( true );
//		IO.println( BaseConversions.decimalToBinary( -12341 ) );
//		IO.println( BaseConversions.decimalToBinary( 12341 ) );
//		IO.println( BaseConversions.decimalToBaseN( 1.8, 2 ) );
//		Csv csv = new Csv( "test.csv" );
//		csv.setHeader( new Object[]{ "string", "number", "bool" } );
//		csv.put( new Object[]{ "string1", 1, true } );
//		csv.put( new Object[]{ "string2", 2, false } );
//		csv.put( new Object[]{ "string3", 3, true } );
//		CsvRecord record = new CsvRecord();
//		record.put( "string4" );
//		record.put( 4);
//		record.put( false );
//		csv.put( record );
//		IO.println( csv.getColumn( 0 ) );
//		csv.write();
	}
}
/*
i,value,bin,hex

*/