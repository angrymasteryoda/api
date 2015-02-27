package com.michael.api.games.runescape;

import com.michael.api.Numbers;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by: Michael Risher
 * Date: 2/26/15
 * Time: 7:42 PM
 */
public class Skill {
	private String skillName;
	private int level;
	private int xp;
	private int rank;

	public Skill( String s, int l, int x, int r ) {
		String[] xSpaces = s.split( "  " );
		skillName = xSpaces[0];
		setLevel( l );
		setXp( x );
		setRank( r );
	}

	public Skill( String s, String l, String x, String r ) {
		String[] xSpaces = s.split( "  " );
		skillName = xSpaces[0];
		try {
			setLevel( Integer.parseInt( l ) );
			setXp( Integer.parseInt( x ) );
			setRank( Integer.parseInt( r ) );
		} catch ( NumberFormatException e ) {
			Logger.getLogger( Skill.class.getName() ).log( Level.SEVERE, "Could not parse to integers" );
		}
	}

	public String getSkillName() {
		return skillName;
	}

	public void setSkillName( String skillName ) {
		this.skillName = skillName;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel( int level ) {
		if ( level == -1 ) {
			this.level = 0;
		} else {
			this.level = level;
		}
	}

	public int getXp() {
		return xp;
	}

	public void setXp( int xp ) {
		if ( xp == -1 ) {
			this.xp = 0;
		} else {
			this.xp = xp;
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
		return String.format( "%1s  %7s   %7s   %4s\n", skillName, Numbers.commaFormat( "" + level ), Numbers.commaFormat( "" + xp ), Numbers.commaFormat( "" + rank ) );
	}
}
