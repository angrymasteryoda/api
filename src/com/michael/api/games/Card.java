package com.michael.api.games;

/**
 * Created by: Michael Risher
 * Date: 2/26/15
 * Time: 7:33 PM
 */
public class Card {

	private String face;
	private String suit;

	public Card( String cardface, String cardsuit ) {
		face = cardface;
		suit = cardsuit;
	}

	public String toString() {
		return face + " of " + suit;
	}
}
