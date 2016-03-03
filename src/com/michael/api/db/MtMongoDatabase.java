package com.michael.api.db;

/**
 * Created by: Michael Risher
 * Date: 3/3/15
 * Time: 7:59 PM
 */
public class MtMongoDatabase {
	private String host;
	private String database;
	private int port;
	private String user;
	private String pass;
//	private DB db;

	public MtMongoDatabase( String host, String database, int port, String user, String pass ) {
		this.host = host;
		this.database = database;
		this.port = port;
		this.user = user;
		this.pass = pass;
	}

	public MtMongoDatabase( String host, String database ) {
		this( host, database, 27017, null, null );
	}
//todo finish this
	public void initConnection(){

		/*
		try {
			MongoClient mongo = null;
			if ( user == null && pass == null ) {
				mongo =  new MongoClient( new ServerAddress( host, port ) );
			} else {
				MongoCredential credential = MongoCredential.createCredential( user, database, pass.toCharArray() );
				mongo = new MongoClient( new ServerAddress( host, port ), Arrays.asList( credential ) );
			}
			this.db = mongo.getDB( database );
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		*/
	}

//	public DBCollection getCollection( String collection ){
//		return db.getCollection( collection );
//	}

	public String getHost() {
		return host;
	}

	public void setHost( String host ) {
		this.host = host;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase( String database ) {
		this.database = database;
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

//	public DB db() {
//		return db;
//	}

	public int getPort() {
		return port;
	}

	public void setPort( int port ) {
		this.port = port;
	}
}
