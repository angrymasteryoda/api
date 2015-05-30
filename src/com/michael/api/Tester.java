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

/**
 * Created By: Michael Risher
 * Date: 2/25/15
 * Time: 4:36 PM
 */
public class Tester {
	public static void main( String[] args ) throws Exception{
		MSplashScreen splash = new MSplashScreen( new URL( "http://www.lessons4living.com/images/penclchk.gif" ),
			true,
			true,
			false,
			"version",
			null,
			Color.red,
			new BackForeColor( 0xff0000, 0x00ff00 )
		);
		splash.setIndeterminate( true );
		splash.on();

		Thread.sleep( 1000l );
		splash.setProgress( "test" );
		Thread.sleep( 1000l );
		splash.setProgress( "testas" );
		Thread.sleep( 3000l );
		splash.off();
//		JSONReader jr = new JSONReader( "{'test':2}");
//		MongoDatabase mongo = new MongoDatabase( "localhost", "test" );
//		mongo.initConnection();
	}
}
