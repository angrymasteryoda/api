package com.michael.api.IO;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created By: Michael Risher
 * Date: 9/30/15
 * Time: 6:14 PM
 */
@SuppressWarnings("unchecked")
public class CsvRecord {
	private List<Object> list;
	private ResultSet resultSet = null;

	public CsvRecord( final ArrayList list ) {
		this.list = (ArrayList) list.clone();
	}

	public CsvRecord() {
		this.list = new ArrayList();
	}

	public CsvRecord( final Object[] in ) {
		this.list = new ArrayList();
		for ( int i = 0; i < in.length; i++ ) {
			list.add( i, in[i] );
		}
	}

	public CsvRecord( ResultSet res ) {
		this.list = new ArrayList();
		this.resultSet = res;
	}

	public Object get( int index ) {
		return list.get( index );
	}

	public int getInt( int index ) {
		return (int) list.get( index );
	}

	public float getFloat( int index ) {
		return (float) list.get( index );
	}

	public double getDouble( int index ) {
		return (double) list.get( index );
	}

	public boolean getBoolean( int index ) {
		return (boolean) list.get( index );
	}

	public String getString( int index ) {
		return (String) list.get( index );
	}

	public void put( Object in ) {
		list.add( in );
	}

	public void put( int index, Object in ) {
		list.add( index, in );
	}

	public void fetchResultSetRow()  throws SQLException{
		if ( resultSet != null ) {
			ResultSetMetaData rsmd = resultSet.getMetaData();
			int len = rsmd.getColumnCount();
			for ( int i = 1; i <= len; i++ ) {
				list.add( resultSet.getObject( i ) );
			}
		}
	}

	public void fetchResultSetRow( String...columns) throws SQLException {
		if ( resultSet != null ) {
			for( String str : columns ) {
				list.add( resultSet.getObject( str ) );
			}
		}
	}

	public String printString( char delimiter, char enclosure ){
		String s = "";
		for ( int i = 0; i < list.size(); i++ ) {
			Object obj = list.get( i );
			if ( obj instanceof Number || obj instanceof Boolean ) {
				s += obj.toString();
			} else {
				String temp = obj.toString();
				temp = escape( temp, enclosure );
				s += enclosure + temp + enclosure;
			}
			if ( i != list.size() - 1 ) {
				s += delimiter;
			}
		}
		return s;
	}

	private String escape( final String in, char enclosure ) {
		String str = "";
		for ( int i = 0; i < in.length(); i++ ) {
			char current = in.charAt( i );
			if ( current == enclosure ) {
				str += "" + current + current;
			} else {
				str += current;
			}
		}
		return str;
	}

	/**
	 * Load a string csv row into a CsvRecord
	 * @param in input string
	 * @param delimiter delimiter character
	 * @param enclosure enclosure character
	 * @return created CsvRecord
	 */
	public static CsvRecord loadString( final String in, char delimiter, char enclosure){
		Object[] objects = split( in, delimiter, enclosure );
		return new CsvRecord( objects );
	}

	/**
	 * split the string read from file into the java data
	 * @param in
	 * @return
	 */
	private static Object[] split( final String in, char delimiter, char enclosure ) {
		String strIn = in;
		int commas = 0;
		for ( int i = 0; i < strIn.length(); i++ ) {
			if ( strIn.charAt( i ) == delimiter ) {
				commas++;
			}
		}
		int type = -1;
		Object[] objs = new Object[ commas + 1 ];
		String temp = "";
		int index = 0;
		boolean doubleEnclosure = false;
		for ( int i = 0; i < strIn.length(); i++ ) {
			boolean skip = false;
			char current = strIn.charAt( i );
			if ( current == enclosure ) {
				//its a string
				boolean noLookAhead = false;
				if ( i == strIn.length() - 1 ) {
					noLookAhead = true;
				}
				type = 1;
				if ( !noLookAhead ) {
					if ( strIn.charAt( i + 1 ) == enclosure || doubleEnclosure ) {
						//assume its a enclosure meant to be there
						if ( doubleEnclosure ) {
							skip = true;
							doubleEnclosure = false;
						} else {
							doubleEnclosure = true;
						}

					}
				}
			} else if ( ( "" + current ).matches( "[0-9\\.\\-]" ) ) {
				//its a number type
				type = 2;
			} else {
				if ( type == -1 ) {
					//probably a boolean
					type = 3;
				} else {
					//if its a letter in a string
					doubleEnclosure = false;
				}
			}

			if ( current == delimiter || i == strIn.length() - 1 ) {
				if ( i == strIn.length() - 1) {
					//get the last char
					temp += current;
				}
				skip = true;
				switch ( type ) {
					case 1:
						//take off the quotes in front and end
						temp = temp.substring( 1, temp.length() - 1 );
						objs[ index++ ] = temp;
						break;
					case 2:
						if ( temp.matches( "[\\-\\d]*\\.[\\d]+" ) ) {
							//going to do double so i don't have to check size
							objs[ index++ ] = Double.parseDouble( temp );
						} else {
							objs[ index++ ] = Integer.parseInt( temp );
						}
						break;
					case 3:
						if ( temp.matches( "(?i)(true)|(false)" ) ) {
							objs[ index++ ] = Boolean.parseBoolean( temp );
						}
						break;
				}
				type = -1;
				temp = "";
			}

			if ( !skip ) {
				temp += current;
			}

		}
		return objs;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		int i = 0;
		for ( Object o : list ) {
			sb.append( o.toString() );
			if ( ++i < list.size() ) {
				sb.append( ", " );
			}
		}
		return sb.toString();
	}
}


