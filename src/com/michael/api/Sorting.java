package com.michael.api;

/**
 * Created by: Michael Risher
 * Date: 2/26/15
 * Time: 7:26 PM
 */
public class Sorting {

	private static int heapN;
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

	public static <T extends Comparable<? super T > > void quickSort( T[] a, int left, int right, boolean isAscending ){
		int leftI = left; // left scan index
		int rightI = right; //right scan index
//		Random rand = new Random( );
		T pivot = a[left]; //using the leftmost as the pivot
		while ( leftI < rightI ) { //if we havent scanned the left side all the way through yet
			if( isAscending ){
				while ( compare( a[leftI], pivot ) < 0 ) { //scan left side looking for index where larger than pivot
					leftI++;
				}

				while ( compare( a[rightI], pivot ) > 0 ) { //scan right side looking for index where smaller than pivot
					rightI--;
				}
			} else {
				while ( compare( a[leftI], pivot ) > 0 ) { //scan left side looking for index where larger than pivot
					leftI++;
				}

				while ( compare( a[rightI], pivot ) < 0 ) { //scan right side looking for index where smaller than pivot
					rightI--;
				}
			}


			if ( leftI <= rightI ) { //if the left index is smaller than we need to swap
				swap( a, leftI, rightI );
				leftI++;
				rightI--;
			}
		}
		if ( left < rightI ) {
			quickSort( a, left, rightI, isAscending );
		}
		if ( leftI < right ) {
			quickSort( a, leftI, right, isAscending );
		}
	}

	private static <T extends Comparable< ? super T > > int compare( T a, T b ){
		if ( a instanceof String ) {
			return ( (String) a ).compareToIgnoreCase( (String) b );
		} else{
			return a.compareTo( b );
		}
	}

	/*
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
*/
	public static void bubbleSort( int[] array ){
		boolean swap = false;
		int len = array.length - 1;
		do{
			swap = false;
			for ( int i = 0; i < len; i++ ) {
				if ( array[i] > array[i+1] ) {
					swap( array, i, i+1 );
					swap = true;
				}
			}
			len--;
		}
		while( swap );

	}

	public static void heapSort( int[] array ){
		//make the heap
		heapify( array );
		for ( int i = heapN; i > 0; i-- ) {
			swap( array, 0, i );
			heapN--;
			maxHeap( array, 0 );
		}
	}

	private static void heapify( int[] array ){
		heapN = array.length - 1;
		for ( int i = heapN / 2; i >= 0; i-- ) {
			maxHeap( array, i );
		}
	}

	//swap the largest element in heap
	private static void maxHeap( int[] array, int i ){
		int left = 2 * i;
		int right = 2 * i + 1;
		int max = i;
		if ( left <= heapN && array[left] > array[right] ) {
			max = left;
		}
		if ( right <= heapN && array[right] > array[max] ) {
			max = right;
		}

		if ( max != i ) {
			swap( array, i, max );
			maxHeap( array, max );
		}
	}

	private static void swap( int array[], int index1, int index2 ) {
		int temp = array[index1];
		array[index1] = array[index2];
		array[index2] = temp;
	}

	private static <T> void swap( T array[], int index1, int index2 ) {
		T temp = array[index1];
		array[index1] = array[index2];
		array[index2] = temp;
	}
}
