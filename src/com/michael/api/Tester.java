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
		IO.println( BaseConversions.decimalToBaseN( 113.4, 16 ));
		IO.println( BaseConversions.decimalToBaseN( 71.666, 10 ));
	}


	private static int[] buildArray( int len ){
		Random rand = new Random(  );
		int[] array = new int[ len ];
		for ( int i = 0; i < len; i++ ) {
			array[i] = i;
		}

		for ( int j = 0; j < 6; j++ ) {
			for ( int i = 0; i < len; i++ ) {
				int second = rand.nextInt( len );
				int temp = array[i];
				array[i] = array[second];
				array[second] = temp;
			}
		}

		return array;
	}

	private static void print( int[] a, int nl ){
		for ( int i = 0; i < a.length; i++ ) {
			IO.print( a[i] + "  " );
			if ( i % nl == nl - 1 ) {
				IO.println("");
			}
		}
	}
}
/*
i,value,bin,hex

*/