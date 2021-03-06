package com.michael.api.db;

import com.michael.api.IO.IO;
import com.michael.api.security.AES;

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
	protected String MODE;
	protected String url;
	protected String user;
	protected String password;

	/**
	 * Create Database by prop file
	 *
	 * @param fileIn
	 * @param mode
	 */
	public MySqlDatabase( String fileIn, String mode ) throws IOException {
		Properties props = readProps( fileIn );
		this.MODE = mode;
		this.url = props.getProperty( MODE + ".url" );
		this.user = props.getProperty( MODE + ".user" );
		this.password = props.getProperty( MODE + ".password" );
	}

	public MySqlDatabase( String fileIn ) throws IOException{
		Properties props = readProps( fileIn );
		this.MODE = props.getProperty( "mode" );
		this.url = props.getProperty( MODE + ".url" );
		this.user = props.getProperty( MODE + ".user" );
		this.password = props.getProperty( MODE + ".password" );
	}

	public MySqlDatabase( String fileIn, String mode, boolean isAes ) throws Exception {
		Properties props = readProps( fileIn );
		this.MODE = mode;
		this.url = props.getProperty( MODE + ".url" );
		this.user = props.getProperty( MODE + ".user" );
		this.password = AES.decrypt( props.getProperty( MODE + ".password" ) );
	}

	public MySqlDatabase( String fileIn, boolean isAes ) throws Exception {
		Properties props = readProps( fileIn );
		this.MODE = props.getProperty( "mode" );
		this.url = props.getProperty( MODE + ".url" );
		this.user = props.getProperty( MODE + ".user" );
		this.password = AES.decrypt( props.getProperty( MODE + ".password" ) );
	}

	public MySqlDatabase( String url, String user, String password, String mode ) {
		this.MODE = mode;
		this.url = url;
		this.user = user;
		this.password = password;
	}

	public MySqlDatabase( String url, String user, String password, String mode, boolean isAes ) throws Exception{
		this.MODE = mode;
		this.url = url;
		this.user = user;
		this.password = AES.decrypt( password );
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
	public Connection getConnection() throws SQLException{
		return DriverManager.getConnection( url, user, password );
	}

	/**
	 * Check if a tables exists
	 *
	 * @param table name of the table to check
	 * @return boolean
	 */
	public boolean checkTableExists( String table ) throws SQLException{
		Connection connection = getConnection();
		ResultSet result = null;
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
	}

	/**
	 * Create a table with a query
	 * @param query string of table create query
	 * @return true if created
	 */
	public boolean createTable( String query ) throws SQLException{
		return createTable( query, false, "" );
	}

	/**
	 * Create a table with a query
	 * @param query string of table create query
	 * @param checkFirst check if table exists first
	 * @param table table name
	 * @return
	 */
	public boolean createTable( String query, boolean checkFirst, String table ) throws SQLException {
		if ( query.equals( "" ) || ( checkFirst && table.equals( "" ) ) ) {
			return false;
		}

		if ( checkFirst ) {
			if ( checkTableExists( table ) ) {
				return false;
			}
		}
		Connection connection = getConnection();
		try {
			Statement state = connection.createStatement();
			state.executeUpdate( query );
			state.executeUpdate( "FLUSH TABLES" );
			state.close();
			connection.close();
			return true;
		} catch ( SQLException e ) {
			e.printStackTrace();
			return false;
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

	/* example
	MySqlDatabase db = new MySqlDatabase( "jdbc:mysql://localhost:3306/rcc", "michael", "password", "local" );
		Connection conn = db.getConnection();
		Statement state = conn.createStatement();
		ResultSet res = null;
		res = state.executeQuery( "SELECT * FROM news_header" );

		while ( res.next() ){
			IO.println( res.getInt( "id" ) );
		}

		res.close();
		state.close();
		conn.close();
	 */
}
