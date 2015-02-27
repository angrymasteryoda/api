package com.michael.api;

import com.michael.api.IO.IO;
import com.michael.api.db.MySqlDatabase;
import com.michael.api.json.JSONArray;
import com.michael.api.json.JSONObject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created By: Michael Risher
 * Date: 2/25/15
 * Time: 4:36 PM
 */
public class Tester {
	public static void main( String[] args ) throws Exception{
		MySqlDatabase db = new MySqlDatabase( "jdbc:mysql://localhost:3306/rcc", "michael", "mrisher", "local" );
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


	}
}
