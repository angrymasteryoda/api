package com.michael.api;

import com.michael.api.IO.IO;
import com.michael.api.db.MongoDatabase;
import com.michael.api.db.MySqlDatabase;
import com.michael.api.json.JSONArray;
import com.michael.api.json.JSONObject;
import com.michael.api.json.JSONReader;
import com.michael.api.security.AES;
import com.michael.api.swing.BackForeColor;
import com.michael.api.swing.MSplashScreen;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.sun.org.apache.bcel.internal.generic.DMUL;

import java.awt.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

/**
 * Created By: Michael Risher
 * Date: 2/25/15
 * Time: 4:36 PM
 */
public class Tester {
	public static void main( String[] args ) throws Exception {
//		JSONObject jr = new JSONObject( new JSONReader( "{'test':[1,2,3,4]}" ) );
//		IO.println( jr.get( "test" ) );

		HashMap<String, Object> map = new HashMap<>();
		map.put( "port", "3306" );
		map.put( "username", "root" );
		map.put( "host", "localhost" );
		map.put( "dbName", "" );
		map.put( "name", "Localhost" );
		map.put( "password", "uTzjKyYc42+AJteqT98VxA==" );
		JSONArray array = new JSONArray(  );
		array.put( map );
		IO.println( array );

	}
}
