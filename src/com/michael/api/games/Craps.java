package com.michael.api.games;

import java.util.Random;

/**
 * Created by: Michael Risher
 * Date: 2/26/15
 * Time: 7:34 PM
 */
public class Craps {
	private enum status {CONTINUE, WIN, LOST}

	private Random rand = new Random();


	/**
	 * Play a game of craps
	 *
	 * @param runLogs display misc info like rolls
	 */
	public String playCraps( boolean runLogs ) {
		int myPoint = 0;//point if no win or loss on first roll
		status gameStatus;

		int sumOfDice = rollDice();
		if ( runLogs ) System.out.println( "Rolled a " + sumOfDice );

		switch ( sumOfDice ) {
			case 7:
			case 11:
				gameStatus = status.WIN;
				break;
			case 2:
			case 3:
			case 12:
				gameStatus = status.LOST;
				break;
			default://did not win or lose
				gameStatus = status.CONTINUE;
				myPoint = sumOfDice;
				if ( runLogs ) System.out.println( "Point is " + myPoint );
				break;
		}

		while ( gameStatus == status.CONTINUE ) {
			sumOfDice = rollDice();
			if ( runLogs ) System.out.println( "Rolled a " + sumOfDice );
			if ( sumOfDice == myPoint ) {
				gameStatus = status.WIN;
			} else if ( sumOfDice == 7 ) {
				gameStatus = status.LOST;
			}
		}

		if ( gameStatus == status.WIN ) {
			if ( runLogs ) System.out.println( "You won!" );
			return "won";
		} else {
			if ( runLogs ) System.out.println( "You lost" );
			return "lost";
		}
	}

	private int rollDice() {
		int die1 = rand.nextInt( 6 ) + 1;
		int die2 = rand.nextInt( 6 ) + 1;
		return die1 + die2;
	}
}