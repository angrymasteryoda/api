package com.michael.api.games;

import com.michael.api.IO.IO;

import java.util.ArrayList;

/**
 * Created by: Michael Risher
 * Date: 2/26/15
 * Time: 7:35 PM
 */
public class GameConfigReader {
	/**
	 * Reads data from config file excluded line that start with a #
	 *
	 * @param fileName
	 * @param getValuesOnly will only get the value after the colon
	 */
	public String[] readConfig( String fileName, boolean getValuesOnly ) {
		ArrayList<String> fileContents = IO.readFromFileIntoArray( fileName );
		int lines = 0;
		for ( int i = 0; i < fileContents.size(); i++ ) {
			if ( !fileContents.get( i ).startsWith( "#" ) ) {
				lines++;
			}
		}
		//get configs with values and info about
		String[] configs = new String[lines];
		int j = 0;
		for ( int i = 0; i < fileContents.size(); i++ ) {
			if ( !fileContents.get( i ).startsWith( "#" ) ) {
				configs[j++] = fileContents.get( i );
			}
		}
		String[] values = new String[configs.length];
		//get values
		for ( int i = 0; i < configs.length; i++ ) {
			String[] a = configs[i].split( ": " );
			values[i] = a[1];
		}

		if ( getValuesOnly ) {
			return values;
		} else {
			return configs;
		}
	}

	public void printExampleConfigs() {
		System.out.println( "#Configs\n" +
				"#this is a comment\n" +
				"info: value\n" +
				"#run in fullscreen (0 = false 1 = true)\n" +
				"fullscreen: 1" );
	}
}
