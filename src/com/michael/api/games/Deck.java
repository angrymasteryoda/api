package com.michael.api.games;

import java.util.Random;

/**
 * Created by: Michael Risher
 * Date: 2/26/15
 * Time: 7:34 PM
 */
public class Deck {
	private Card[] fulldeck;
	private final int NumberOfCards = 52;
	private int currentCard = 0;
	private Random rand = new Random();

	public Deck() {
		String faces[] = { "Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King" };
		String suit[] = { "Hearts", "Diamond", "Club", "Spades" };

		fulldeck = new Card[NumberOfCards];

		for ( int i = 0; i < fulldeck.length; i++ ) {
			fulldeck[i] = new Card( faces[i % 13], suit[i / 13] );
		}
	}

	/**
	 * used to sort a shuffled deck
	 */

	public void sort() {
		String faces[] = { "Ace", "2", "3", "4", "5", "6", "7", "8", "9", "20", "Jack", "Queen", "King" };
		String suit[] = { "Hearts", "Diamond", "Club", "Spades" };

		fulldeck = new Card[NumberOfCards];

		for ( int i = 0; i < fulldeck.length; i++ ) {
			fulldeck[i] = new Card( faces[i % 13], suit[i / 13] );
		}
	}

	/**
	 * same as shuffling the deck 6 times
	 */
	public void shuffle() {
		currentCard = 0;
		for ( int j = 0; j < 6; j++ ) {
			for ( int i = 0; i < fulldeck.length; i++ ) {
				int second = rand.nextInt( NumberOfCards );
				Card temp = fulldeck[i];
				fulldeck[i] = fulldeck[second];
				fulldeck[second] = temp;
			}
		}
	}

	/**
	 * shuffle the deck X amount of times
	 *
	 * @param times
	 */
	public void shuffle( int times ) {
		currentCard = 0;
		for ( int j = 0; j < times; j++ ) {
			for ( int i = 0; i < fulldeck.length; i++ ) {
				int second = rand.nextInt( NumberOfCards );
				Card temp = fulldeck[i];
				fulldeck[i] = fulldeck[second];
				fulldeck[second] = temp;
			}
		}
	}

	/**
	 * deal the current card
	 *
	 * @return Card
	 */
	public Card dealCard() {
		if ( currentCard < fulldeck.length ) {
			return fulldeck[currentCard++];
		} else {
			return null;
		}
	}
}