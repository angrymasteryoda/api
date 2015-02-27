package com.michael.api.games.runescape;

import com.michael.api.Numbers;

/**
 * Created by: Michael Risher
 * Date: 2/26/15
 * Time: 7:41 PM
 */
public class Minigame {
	private String name;
	private int score;
	private int rank;

	public Minigame( String name, int score, int rank ) {
		this.name = name;
		setScore( score );
		setRank( rank );
	}

	public Minigame( String name, String score, String rank ) {
		this.name = name;
		setScore( Integer.parseInt( score ) );
		setRank( Integer.parseInt( rank ) );
	}

	public String getName() {
		return name;
	}

	public void setName( String name ) {
		this.name = name;
	}

	public int getScore() {
		return score;
	}

	public void setScore( int score ) {
		if ( score == -1 ) {
			this.score = 0;
		} else {
			this.score = score;
		}
	}

	public int getRank() {
		return rank;
	}

	public void setRank( int rank ) {
		if ( rank == -1 ) {
			this.rank = 0;
		} else {
			this.rank = rank;
		}
	}

	public String toString() {
		return String.format( "%s  %7s   %7s\n", name, Numbers.commaFormat( "" + score ), Numbers.commaFormat( "" + rank ) );
	}

}