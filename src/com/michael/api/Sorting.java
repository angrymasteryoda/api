package com.michael.api;

/**
 * Created by: Michael Risher
 * Date: 2/26/15
 * Time: 7:26 PM
 */
public class Sorting {

	public void sort( char[][] a ) {
		for ( int t = 0; t < a.length; t++ ) {

			//the sorting
			for ( int i = 0; i < a.length - 1; i++ ) {
				for ( int j = 0; j < a[i].length - 1; j++ ) {
					if ( a[i][j] > a[i + 1][j + 1] ) {
						char temp = a[i][j];
						a[i][j] = a[i + 1][j + 1];
						a[i + 1][j + 1] = temp;
					}
				}
			}

		}
	}
}
