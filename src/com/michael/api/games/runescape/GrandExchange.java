package com.michael.api.games.runescape;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by: Michael Risher
 * Date: 2/26/15
 * Time: 7:40 PM
 */
public class GrandExchange {
	/**
	 * if id is known will look up price on ge -1 means a non-tradable
	 *
	 * @param id
	 * @return price
	 * @throws IOException
	 */
	public int getPriceOfItem( int id ) throws IOException {
		String price;
		URL url = new URL( "http://services.runescape.com/m=itemdb_rs/viewitem.ws?obj=" + id );
		URLConnection con = url.openConnection();
		BufferedReader in = new BufferedReader( new InputStreamReader( con.getInputStream() ) );
		String line;
		while ( ( line = in.readLine() ) != null ) {
			if ( line.contains( "<td>" ) ) {
				price = line.substring( line.indexOf( ">" ) + 1,
						line.indexOf( "/" ) - 1 );
				price = price.replace( ",", "" );
				try {
					return Integer.parseInt( price );
				} catch ( NumberFormatException e ) {
					System.err.println( e );
					return 0;
				}
			}
		}
		in.close();
		return -1;
	}

	/**
	 * if item id is unknown will try to look up id if fails return -1,
	 * id is known will look up price on ge -1 means a non-tradable
	 *
	 * @param name
	 * @return price
	 * @throws IOException
	 */
	public int getPriceOfItem( String name ) throws IOException {
		int id;
		URL url = new URL( "http://www.cache.fatalanarchy.net/634ItemIDList.txt" );
		URLConnection con = url.openConnection();
		BufferedReader in = new BufferedReader( new InputStreamReader( con.getInputStream() ) );
		String line;
		while ( ( line = in.readLine() ) != null ) {
			if ( line.contains( name ) ) {
				String[] cutline = line.split( "		" );
				id = Integer.parseInt( cutline[0] );
				return getPriceOfItem( id );
			}
		}
		in.close();
		return -1;
	}

	/**
	 * if item id is unknown will try to look up id if fails return -1,
	 * id is known will look up price on ge -1 means a non-tradable
	 *
	 * @param name
	 * @param showID
	 * @return price
	 * @throws IOException
	 */
	public int getPriceOfItem( String name, boolean showID ) throws IOException {
		int id;
		URL url = new URL( "http://www.cache.fatalanarchy.net/634ItemIDList.txt" );
		URLConnection con = url.openConnection();
		BufferedReader in = new BufferedReader( new InputStreamReader( con.getInputStream() ) );
		String line;
		while ( ( line = in.readLine() ) != null ) {
			if ( line.contains( name ) ) {
				String[] cutline = line.split( "		" );
				id = Integer.parseInt( cutline[0] );
				if ( showID ) System.out.println( "Id for: " + name + " is: " + id );
				return getPriceOfItem( id );
			}
		}
		in.close();
		return -1;
	}
}