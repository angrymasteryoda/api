package com.michael.api.games;

import java.util.Random;
import java.util.Scanner;

/**
 * Created by: Michael Risher
 * Date: 2/26/15
 * Time: 7:38 PM
 */
public class Yahtzee {
	private Random rand = new Random();

	public void playYahtzee( boolean runLogs ) {
		int[] rolledDie = new int[5];
		String userIn = "";
		@SuppressWarnings( "resource" )
		Scanner in = new Scanner( System.in );
		do {
			showOptions();
			userIn = in.nextLine();
			if ( userIn.equals( "roll" ) ) {
				roll5Die( rolledDie );
				showCurrentDie( rolledDie );
				rollMenu( rolledDie );
			}
			if ( userIn.equals( "score" ) ) {
				showUpperSectionMenu();
			}


		} while ( !userIn.equals( "END" ) );


	}

	private void roll5Die( int[] a ) {
		for ( int i = 0; i < a.length; i++ ) {
			a[i] = rand.nextInt( 6 ) + 1;
		}
	}

	private void showCurrentDie( int[] a ) {
		System.out.println( "Dice" );
		System.out.println( "1\t2\t3\t4\t5\t" );
		for ( int i = 0; i < a.length; i++ ) {
			System.out.print( a[i] + "\t" );
		}
		System.out.println();
	}

	private void rollMenu( int[] a ) {
		@SuppressWarnings( "resource" )
		Scanner in = new Scanner( System.in );
		System.out.println( "Want to keep this roll enter yes" );
		System.out.println( "Want to reroll some dice enter reroll" );
		String userIn = "";
		userIn = in.nextLine();
		if ( userIn.equalsIgnoreCase( "reroll" ) ) {
			int trys = 0;
			do {
				System.out.println( "what dice do you want to reroll (enter dice # in comma seperated list)" );
				userIn = in.nextLine();
				String[] sp = userIn.split( "," );
				int[] reRollThese = new int[sp.length];
				for ( int i = 0; i < sp.length; i++ ) {
					reRollThese[i] = Integer.parseInt( sp[i] );
				}
				reRollDie( reRollThese, a );
				showCurrentDie( a );
				trys++;
			}
			while ( trys != 2 );
		}

	}

	private void reRollDie( int[] aIndex, int[] a ) {
		for ( int i = 0; i < aIndex.length; i++ ) {
			a[( aIndex[i] - 1 )] = rand.nextInt( 6 ) + 1;
		}

	}

	private void showOptions() {
		System.out.println( "Enter a command" );
		System.out.println( "roll - to roll the dice" );
		System.out.println( "score" );
		System.out.println( "END - to end the game" );
	}

	private void showUpperSectionMenu() {
		for ( int i = 1; i <= 6; i++ ) {
			System.out.println( "Add only " + i + "'s" );
		}
		System.out.println();
	}
}