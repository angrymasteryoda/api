package com.michael.api.db;

import com.mongodb.DB;
import com.mongodb.MongoClient;

import java.net.UnknownHostException;

/**
 * Created by: Michael Risher
 * Date: 3/3/15
 * Time: 7:59 PM
 */
public class MongoDatabase {
	private String host;
	private String db;
	private int port;
	private String user;
	private String pass;

	public MongoDatabase( String host, String db, int port, String user, String pass ) {
		this.host = host;
		this.db = db;
		this.port = port;
		this.user = user;
		this.pass = pass;
	}

	public MongoDatabase( String host, String db ) {
		this.host = host;
		this.db = db;
	}
//todo finish this
	public DB initConnection(){
		try {
			MongoClient mongo =  new MongoClient( host );
			return mongo.getDB( db );
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getHost() {
		return host;
	}

	public void setHost( String host ) {
		this.host = host;
	}

	public String getDb() {
		return db;
	}

	public void setDb( String db ) {
		this.db = db;
	}

	public String getUser() {
		return user;
	}

	public void setUser( String user ) {
		this.user = user;
	}

	public String getPass() {
		return pass;
	}

	public void setPass( String pass ) {
		this.pass = pass;
	}
}
