package com.michael.api.db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by: Michael Risher
 * Date: 2/26/15
 * Time: 8:02 PM
 */
public class MySqlDatabase {
	private String MODE;
	private String url;
	private String user;
	private String password;

	/**
	 * Create Database by prop file
	 *
	 * @param fileIn
	 * @param mode
	 */
	public MySqlDatabase( String fileIn, String mode ) {
		try {
			Properties props = readProps( fileIn );
			this.MODE = mode;
			this.url = props.getProperty( MODE + ".url" );
			this.user = props.getProperty( MODE + ".user" );
			this.password = props.getProperty( MODE + ".password" );
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}

	public MySqlDatabase( String url, String user, String password, String mode ) {
		this.MODE = mode;
		this.url = url;
		this.user = user;
		this.password = password;
	}

	/**
	 * Read database credentials from file
	 *
	 * @param file input file
	 * @return Properties
	 * @throws IOException
	 */
	public Properties readProps( String file ) throws IOException {
		Properties props = new Properties();
		FileInputStream in = new FileInputStream( file );
		props.load( in );
		in.close();
		return props;
	}

	/**
	 * get database connection
	 *
	 * @return Connection
	 */
	public Connection getConnection() {
		try {
			return DriverManager.getConnection( url, user, password );
		} catch ( SQLException e ) {
			Logger log = Logger.getLogger( MySqlDatabase.class.getName() );
			log.log( Level.SEVERE, e.getMessage(), e );
			System.exit( 2 );
			return null;
		}
	}

	/**
	 * Check if a tables exists
	 *
	 * @param table name of the table to check
	 * @return boolean
	 */
	public boolean checkTableExists( String table ) {
		Connection connection = getConnection();
		ResultSet result = null;
		try {
			DatabaseMetaData meta = connection.getMetaData();
			ResultSet res = meta.getTables( null, null, table, null );
			if ( res.next() ) {
				res.close();
				connection.close();
				return true;
			} else {
				res.close();
				connection.close();
				return false;
			}
		} catch ( SQLException e ) {
			return false;
		}
	}

	public void createTable( String query ) {
		createTable( query, false, "" );
	}

	public void createTable( String query, boolean checkFirst, String table ) {
		if ( query.equals( "" ) || ( checkFirst && table.equals( "" ) ) ) {
			return;
		}

		if ( checkFirst ) {
			if ( checkTableExists( table ) ) {
//				Main.Pl( "Table already exists" );
				return;
			}
		}
		Connection connection = getConnection();
		try {
			Statement state = connection.createStatement();
			state.executeUpdate( query );
			state.executeUpdate( "FLUSH TABLES" );
			state.close();
			connection.close();
		} catch ( SQLException e ) {

		}


	}

	/**
	 * Escapes quotes for mysql queries
	 *
	 * @param in input string
	 * @return String
	 */
	public static String escape( String in ) {
		String out = in;
		out = out.replaceAll( "\'", "\\\"" );
		out = out.replaceAll( "\"", "\\\'" );

		return out;
	}

	public static String unescape( String in ) {
		String out = in;
		out = out.replaceAll( "\\\"", "\'" );
		out = out.replaceAll( "\\\'", "\"" );

		return out;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl( String url ) {
		this.url = url;
	}

	public String getUser() {
		return user;
	}

	public void setUser( String user ) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword( String password ) {
		this.password = password;
	}
}
