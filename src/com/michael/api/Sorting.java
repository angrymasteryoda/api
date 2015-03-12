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

	public static void quickSort( int[] array, int start, int end ){
		int i = start; //left -right scan index
		int k = end; // right -left scan index

		if ( end - start >= 1 ) { //make sure there is 2 or more elements
			int pivot = array[start];

			while ( k > i ){ //while the scanning indexs have not meet
				while ( array[i] <= pivot && i <+ end && k > i ) { // form the left look for first element greater than pivot
					i++;
				}
				while ( array[k] > pivot && k >= start && k >= i ){ //from the right look for the first elem not greater than pivot
					k--;
				}
				if ( k > i ) { // if the left scan index is still smaller than right index, swap the elems
					swap( array, i, k );
				}
			}
			swap( array, start, k ); //after indices have crossed swap the last elem in the left partition with pivot
			quickSort( array, start, k - 1 ); // quicksort the left part
			quickSort( array, k + 1, end ); //quicksort the right part
		} else {
			return; //its sorted
		}
	}

	private static void swap( int array[], int index1, int index2 ) {
		int temp = array[index1];
		array[index1] = array[index2];
		array[index2] = temp;
	}
}
