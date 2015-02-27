package com.michael.api.games.runescape;

import com.michael.api.Numbers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by: Michael Risher
 * Date: 2/26/15
 * Time: 7:40 PM
 */
public class Highscores {
	public final int PRINT_COMPARISON_LEVEL = 0;
	public final int PRINT_COMPARISON_XP = 1;
	public final int PRINT_COMPARISON_RANK = 2;

	private final int NUMBER_OF_SKILLS = 26;
	private final int NUMBER_OF_MINIGAMES = 14;

	public final int COMPARE_ARRAY_SIZE = NUMBER_OF_SKILLS + NUMBER_OF_MINIGAMES;


	private String name;
	private String name2;

	private final String skillNames[] = {
			"Overall      ",
			"Attack       ",
			"Defence      ",
			"Strength     ",
			"Constitution ",
			"Ranged       ",
			"Prayer       ",
			"Magic        ",
			"Cooking      ",
			"Woodcutting  ",
			"Fletching    ",
			"Fishing      ",
			"Firemaking   ",
			"Crafting     ",
			"Smithing     ",
			"Mining       ",
			"Herblore     ",
			"Agility      ",
			"Theiving     ",
			"Slayer       ",
			"Farming      ",
			"Runecrafting ",
			"Hunter       ",
			"Construction ",
			"Summoning    ",
			"Dungeoneering",
			"Bounty Hunters      ",
			"Bounty Hunter Rogues",
			"Dominion Tower      ",
			"The Crucible        ",
			"Castle Wars Games   ",
			"B.A Attackers       ",
			"B.A Defenders       ",
			"B.A Collectors      ",
			"B.A Healers         ",
			"Duel Tournament     ",
			"Mobilising Armies   ",
			"Conquest            ",
			"Fist of Guthix      ",
			"GG: Athletics       ",
			"GG: Resource Race   " };

	public EnumCompare[] user1Vuser2 = new EnumCompare[NUMBER_OF_SKILLS + NUMBER_OF_MINIGAMES];
	public Skill[] user1 = new Skill[NUMBER_OF_SKILLS];
	public Skill[] user2 = new Skill[NUMBER_OF_SKILLS];

	public Minigame[] user1Minigame = new Minigame[NUMBER_OF_MINIGAMES];
	public Minigame[] user2Minigame = new Minigame[NUMBER_OF_MINIGAMES];

	public Highscores() {

	}

	public Highscores( String name ) {
		this.name = name;
	}

	public Highscores( String name, String compare ) {
		this.name = name;
		this.name2 = compare;
	}

	/**
	 * Gathers the users highscores from the runescape site
	 *
	 * @param print
	 * @param printMinigames
	 * @param compare
	 */
	public void getScoresFromWeb( boolean print, boolean printMinigames, boolean compare ) {
		if ( name == null ) {
			Logger.getLogger( Highscores.class.getName() ).log( Level.SEVERE, "No name to check" );
			return;
		}
		String user;
		//if comparing use other name
		user = ( compare ? name2 : name );
		String parsedName = "";
		String baseUrl = "http://hiscore.runescape.com/index_lite.ws?player=";
		for ( int i = 0; i < user.length(); i++ ) {
			if ( user.charAt( i ) == ' ' ) {
				parsedName += "+";
			} else {
				parsedName += user.charAt( i );
			}
		}

		URL fullUrl = null;
		try {
			fullUrl = new URL( baseUrl + parsedName );
			BufferedReader br = new BufferedReader( new InputStreamReader( fullUrl.openStream() ) );
			if ( print ) System.out.println( "Skills       " + "  Level   Xp\tRank" );
			Skill c;
			for ( int i = 0; i < NUMBER_OF_SKILLS; i++ ) {
				String line = br.readLine();
				String split[] = line.split( "," );
				//System.out.println(split[2]);
				if ( !compare ) {
					user1[i] = new Skill( skillNames[i], split[1], split[2], split[0] );
				} else if ( compare ) {
					user2[i] = new Skill( skillNames[i], split[1], split[2], split[0] );
				}

				c = ( !compare ? user1[i] : user2[i] );
				if ( print ) {
					if ( i == 0 ) {
						System.out.println( skillNames[i] + "  " + Numbers.commaFormat( c.getLevel() ) + "  "
								+ Numbers.commaFormat( c.getXp() ) + "  " + Numbers.commaFormat( c.getRank() ) );
					} else {
						System.out.println( skillNames[i] + "  " + Numbers.commaFormat( c.getLevel() ) + "     "
								+ Numbers.commaFormat( c.getXp() ) + "  " + Numbers.commaFormat( c.getRank() ) );
					}
				}
			}

			if ( printMinigames ) System.out.println( "\nMinigames" );
			Minigame m;
			int j = 0;
			for ( int i = NUMBER_OF_SKILLS; i < user1Vuser2.length; i++ ) {
				String line = br.readLine();
				String split[] = line.split( "," );
				if ( !compare ) {
					user1Minigame[j] = new Minigame( skillNames[i], split[1], split[0] );
					m = user1Minigame[j];
				} else {
					user2Minigame[j] = new Minigame( skillNames[i], split[1], split[0] );
					m = user2Minigame[j];
				}
				if ( printMinigames ) {
					System.out.println( skillNames[i] + "  " + Numbers.commaFormat( m.getScore() ) + "   "
							+ Numbers.commaFormat( m.getRank() ) );
				}
				j++;
			}

			br.close();
		} catch ( MalformedURLException e ) {
			e.printStackTrace();
		} catch ( IOException e ) {
			Logger.getLogger( Highscores.class.getName() ).log( Level.WARNING, "User is not on the highscores. Are they free to play. Are you connected to the internet" );
		}
	}

	/**
	 * Gathers the users highscores from the runescape site
	 *
	 * @param print
	 * @param printMinigames
	 */
	public void getScoresFromWeb( boolean print, boolean printMinigames ) {
		this.getScoresFromWeb( print, printMinigames, false );
	}

	/**
	 * Gathers the users highscores from the runescape site
	 *
	 * @param print
	 */
	public void getScoresFromWeb( boolean print ) {
		this.getScoresFromWeb( print, false, false );
	}

	/**
	 * Gathers the users highscores from the runescape site
	 */
	public void getScoresFromWeb() {
		this.getScoresFromWeb( false, false, false );
	}

	/**
	 * Compares a second player to the first player
	 *
	 * @param name
	 */
	public void comparePlayers( String name ) {
		name2 = name;
		if ( name2 == null ) {
			Logger.getLogger( Highscores.class.getName() ).log( Level.SEVERE, "No user to compare to " + this.name );
		} else {
			this.getScoresFromWeb( false, false, false );
			this.getScoresFromWeb( false, false, true );
			compareXp();
		}
	}

	/**
	 * Compares a second player to the first player
	 */
	public void comparePlayers() {
		this.comparePlayers( ( ( name2 == null ) ? null : name2 ) );
	}

	private void compareXp() {
		for ( int i = 0; i < user1Vuser2.length - NUMBER_OF_MINIGAMES; i++ ) {
			if ( user1[i].getXp() == user2[i].getXp() ) {
				user1Vuser2[i] = EnumCompare.EQUAL;
			} else if ( user1[i].getXp() > user2[i].getXp() ) {
				user1Vuser2[i] = EnumCompare.MORE;
			} else if ( user1[i].getXp() < user2[i].getXp() ) {
				user1Vuser2[i] = EnumCompare.LESS;
			}
		}
		int j = NUMBER_OF_SKILLS;
		for ( int i = 0; i < NUMBER_OF_MINIGAMES; i++ ) {
			if ( user1Minigame[i].getScore() == user2Minigame[i].getScore() ) {
				user1Vuser2[j] = EnumCompare.EQUAL;
			} else if ( user1Minigame[i].getScore() > user2Minigame[i].getScore() ) {
				user1Vuser2[j] = EnumCompare.MORE;
			} else if ( user1Minigame[i].getScore() < user2Minigame[i].getScore() ) {
				user1Vuser2[j] = EnumCompare.LESS;
			}
			j++;
		}
	}

	/**
	 * Prints the comparison
	 *
	 * @param mode 0 for levels, 1 for xp, 2 for rank
	 */
	public void printComparison( int mode ) {
		if ( name == null || name2 == null ) return;
		String[] s = new String[user1Vuser2.length];

		for ( int i = 0; i < user1Vuser2.length; i++ ) {
			if ( user1Vuser2[i] == EnumCompare.MORE ) {
				s[i] = ">";
			} else if ( user1Vuser2[i] == EnumCompare.LESS ) {
				s[i] = "<";
			} else if ( user1Vuser2[i] == EnumCompare.EQUAL ) {
				s[i] = "=";
			}
		}
		System.out.printf( "Skills     %s %s %s\n", name, " ", name2 );
		for ( int i = 0; i < s.length - NUMBER_OF_MINIGAMES; i++ ) {
			if ( mode == 0 )
				System.out.printf( "%s  %s      %s      %s\n", skillNames[i], Numbers.commaFormat( user1[i].getLevel() ), s[i], Numbers.commaFormat( user2[i].getLevel() ) );
			else if ( mode == 1 )
				System.out.printf( "%s  %s      %s      %s\n", skillNames[i], Numbers.commaFormat( user1[i].getXp() ), s[i], Numbers.commaFormat( user2[i].getXp() ) );
			else if ( mode == 2 )
				System.out.printf( "%s  %s      %s      %s\n", skillNames[i], Numbers.commaFormat( user1[i].getRank() ), s[i], Numbers.commaFormat( user2[i].getRank() ) );
		}
		int j = 0;
		for ( int i = NUMBER_OF_SKILLS; i < s.length; i++ ) {
			if ( mode == 0 || mode == 1 )
				System.out.printf( "%s  %s      %s      %s\n", skillNames[i], Numbers.commaFormat( user1Minigame[j].getScore() ), s[i], Numbers.commaFormat( user2Minigame[j].getScore() ) );
			else if ( mode == 2 )
				System.out.printf( "%s  %s      %s      %s\n", skillNames[i], Numbers.commaFormat( user1Minigame[j].getRank() ), s[i], Numbers.commaFormat( user2Minigame[j].getRank() ) );
			j++;
		}
	}

	/**
	 * Prints the level comparison
	 */
	public void printComparison() {
		printComparison( 0 );
	}

	/**
	 * Gives one line of a comparison for a for loop or only 1 skill/minigame
	 *
	 * @param i    range 0 - 40
	 * @param mode 0 for levels, 1 for xp, 2 for rank
	 * @return one level/minigame comparison
	 */
	public String printForLoopComparison( int i, int mode ) {
		if ( i < COMPARE_ARRAY_SIZE ) {
			Logger.getLogger( Highscores.class.getName() ).log( Level.SEVERE, i + " is out of the range use 0 - " + COMPARE_ARRAY_SIZE );
		}
		this.getScoresFromWeb( false, false, true );
		String[] s = new String[user1Vuser2.length];
		if ( user1Vuser2[i] == EnumCompare.MORE ) {
			s[i] = ">";
		} else if ( user1Vuser2[i] == EnumCompare.LESS ) {
			s[i] = "<";
		} else if ( user1Vuser2[i] == EnumCompare.EQUAL ) {
			s[i] = "=";
		}

		if ( mode == 0 ) {
			if ( i < NUMBER_OF_SKILLS ) {
				return String.format( "%s  %s      %s      %s\n", skillNames[i], Numbers.commaFormat( user1[i].getLevel() ), s[i], Numbers.commaFormat( user2[i].getLevel() ) );
			} else {
				return String.format( "%s  %s      %s      %s\n", skillNames[i], Numbers.commaFormat( user1Minigame[i - NUMBER_OF_SKILLS].getScore() ), s[i], Numbers.commaFormat( user2Minigame[i - NUMBER_OF_SKILLS].getScore() ) );
			}
		} else if ( mode == 1 ) {
			if ( i < NUMBER_OF_SKILLS ) {
				return String.format( "%s  %s      %s      %s\n", skillNames[i], Numbers.commaFormat( user1[i].getXp() ), s[i], Numbers.commaFormat( user2[i].getXp() ) );
			} else {
				return String.format( "%s  %s      %s      %s\n", skillNames[i], Numbers.commaFormat( user1Minigame[i - NUMBER_OF_SKILLS].getScore() ), s[i], Numbers.commaFormat( user2Minigame[i - NUMBER_OF_SKILLS].getScore() ) );
			}
		} else if ( mode == 2 ) {
			if ( i < NUMBER_OF_SKILLS ) {
				return String.format( "%s  %s      %s      %s\n", skillNames[i], Numbers.commaFormat( user1[i].getRank() ), s[i], Numbers.commaFormat( user2[i].getRank() ) );
			} else {
				return String.format( "%s  %s      %s      %s\n", skillNames[i], Numbers.commaFormat( user1Minigame[NUMBER_OF_SKILLS - i].getRank() ), s[i], Numbers.commaFormat( user2Minigame[NUMBER_OF_SKILLS - i].getRank() ) );
			}
		} else
			Logger.getLogger( Highscores.class.getName() ).log( Level.SEVERE, "Mode entered incorrect use PRINT_COMPARISON consants" );
		return null;
	}

	public String getName() {
		return name;
	}

	public void setName( String name ) {
		this.name = name;
	}

	public String getName2() {
		return name2;
	}

	public void setName2( String name2 ) {
		this.name2 = name2;
	}

	/**
	 * @return returns formated skill names
	 */
	public String[] getSkillNames() {
		return skillNames;
	}
}