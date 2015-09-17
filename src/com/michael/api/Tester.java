package com.michael.api;

import com.michael.api.IO.Csv;
import com.michael.api.IO.IO;

import java.util.Random;

/**
 * Created By: Michael Risher
 * Date: 2/25/15
 * Time: 4:36 PM
 */
public class Tester {
	public static void main( String[] args ) throws Exception {
		Csv csv = new Csv( "test.csv" );
		Random rand = new Random();
		for ( int i = 0; i < 50; i++ ) {
			Object[] arr = new Object[4];
			long val = rand.nextInt( ( 2147483600 - 10 ) + 1 ) + 10;
			BaseConversions.setPrettyPrint( true );
			arr[0] = i;
			arr[1] = val;
			arr[2] = BaseConversions.decimalToBinary( val );
			arr[3] = BaseConversions.decimalToBaseN( val, 16 );
			csv.put( i, arr );
		}
		csv.write();
//		IO.println( csv.join( new Object[]{ "test", 1, 1.0, true, 'a' }, ',', '"' ) );
//		csv.put( new Object[]{ "e\"t\"\"d\"", 1, 1.0, true, 'a' } );
//		csv.write();
//		csv.read();

//		IO.println( "0000000100111011" );
//		IO.println( BaseConversions.prettyPrint( BaseConversions.decimalToBinary( 315 ) ) );
//		IO.println( BaseConversions.prettyPrint( BaseConversions.decimalToBinary( -315 ) ) );
//		IO.println( BaseConversions.prettyPrint( BaseConversions.decimalToBaseN( -315, 16 ) ));
//		IO.println( BaseConversions.decimalToBinary( -39363  ) );

//		IO.println("");
//		IO.println( Numbers.getBaseNToDecimal( "0101", 2 ) );
//		IO.println( Numbers.getBaseNToDecimal( "1111", 2 ) );
	}
}
/*
i,value,bin,hex

*/