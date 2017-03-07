package com.michael.api;

import com.michael.api.IO.IO;
import com.michael.api.YAML.Yaml;
import com.michael.api.YAML.YamlList;
import com.michael.api.json.JSONArray;
import com.michael.api.json.JSONObject;

import java.util.HashMap;

/**
 * Created By: Michael Risher
 * Date: 2/25/15
 * Time: 4:36 PM
 */
public class Tester {
	/*
	 * final String gmail, final String pass, String[] to, String[] cc, String[] bcc, String subject, String msg, String filename
	 */
	public static void main( String[] args ) throws Exception {
		Yaml yaml = new Yaml( "tester.yml" );
		yaml.put( "prop1", true, "this is a comment" );
		yaml.put( "prop2", 2 );
		yaml.put( "prop3", "hello" );
		yaml.put( "prop4", "hello world" );
		yaml.put( "prop5", "c:/windows" );
//		Object[] arr = {1,"test er",false};
//		yaml.put( "list", new YamlList(arr), "list comment" );
		YamlList yl = new YamlList();
		yl.put( 1 );
		yl.put( "test er" );
		yl.put( false );
		yaml.put( "list", yl, "list comment" );

		Yaml nestGroup = new Yaml(  );
		nestGroup.put( "g1", 1 );

		Yaml nesterGroup = new Yaml(  );
		nesterGroup.put( "g5", 1 );

		nestGroup.put( "nested1", nesterGroup );

		yaml.put( "nested", nestGroup );
		yaml.write();


//		HashMap<String, Object> map = new HashMap<>();
//		map.put( "port", "3306" );
//		map.put( "username", "root" );
//		map.put( "host", "localhost" );
//		map.put( "dbName", "" );
//		map.put( "name", "Localhost" );
//		map.put( "password", "uTzjKyYc42+AJteqT98VxA==" );
//		JSONObject array = new JSONObject( map );
//		JSONObject ne = new JSONObject(  );
//		ne.put( "test", 't' );
//		int[] t = {1,2,3,4,5};
//		ne.put( "arr", t );
//		array.put( "nest", ne );
////		array.put(  );
//		IO.println( array );

	}

}
/*
i,value,bin,hex

*/