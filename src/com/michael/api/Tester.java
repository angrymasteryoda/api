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
		int[] array = buildArray( 10 );
		//print( array, 10 );
		IO.println( "" );
		//print( array, 10 );
		long beg = System.nanoTime();
		Sorting.heapSort( array );
		long end = System.nanoTime();
		IO.println( "took: " + ( end - beg ) + "u");
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