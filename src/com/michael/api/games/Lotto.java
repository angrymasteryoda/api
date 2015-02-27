package com.michael.api.games;

import java.util.Random;

/**
 * Created by: Michael Risher
 * Date: 2/26/15
 * Time: 7:36 PM
 */
public class Lotto {
	private Random rand = new Random();

	/**
	 * will give the
	 *
	 * @param choices               users choices
	 * @param numberOfWiningNumbers
	 * @param range                 max number to be picked by the random number gen
	 * @param runLogs               play game with logs
	 * @return winningGuesses in a format that includes all numbers broken up with "~"
	 */
	public String playLotto( int[] choices, int numberOfWiningNumbers, int range, boolean runLogs ) {
		int[] winningNums = new int[numberOfWiningNumbers];
		if ( runLogs ) System.out.println( "Wining Numbers" );
		for ( int i = 0; i < winningNums.length; i++ ) {
			winningNums[i] = getRandNumber( range );
			if ( runLogs ) System.out.print( winningNums[i] + " " );
		}
		String winningGuesses = "";
		for ( int i = 0; i < choices.length; i++ ) {
			for ( int j = 0; j < winningNums.length; j++ ) {
				if ( choices[i] == winningNums[j] ) {
					winningGuesses += "" + choices[i];
					winningGuesses += "~";
				}
			}
		}
		if ( runLogs ) System.out.println( "\nYour numbers that were correct" );
		if ( winningGuesses.equals( "" ) ) {
			return "none";
		}
		return winningGuesses;
	}

	public void playLotto( int choices, int numberOfWiningNumbers, int range, boolean runLogs ) {
		int[] array = { choices };
		playLotto( array, numberOfWiningNumbers, range, runLogs );
	}

	private int getRandNumber( int range ) {
		return rand.nextInt( range ) + 1;
	}
}