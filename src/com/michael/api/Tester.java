package com.michael.api;

import com.michael.api.IO.Csv;
import com.michael.api.IO.IO;
import com.michael.api.db.MongoDatabase;
import com.michael.api.db.MySqlDatabase;
import com.michael.api.games.runescape.Highscores;
import com.michael.api.json.JSONArray;
import com.michael.api.json.JSONObject;
import com.michael.api.json.JSONReader;
import com.michael.api.security.AES;
import com.michael.api.swing.BackForeColor;
import com.michael.api.swing.MSplashScreen;
import com.michael.api.swing.console.Console;
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
		Csv csv = new Csv( "test.csv" );
//		IO.println( csv.join( new Object[]{ "test", 1, 1.0, true, 'a' }, ',', '"' ) );
//		csv.put( new Object[]{ "e\"t\"\"d\"", 1, 1.0, true, 'a' } );
//		csv.write();
		csv.read();
	}
}
