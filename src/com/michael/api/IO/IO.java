package com.michael.api.IO;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created By: Michael Risher
 * Date: 2/25/15
 * Time: 4:54 PM
 */
public class IO {
	/**
	 * Used to write a plain .txt file
	 * @param fileName
	 * @param content
	 */
	public static void writeFile(String fileName, String content){
		BufferedWriter writer = null;
		try{
			writer = new BufferedWriter(new FileWriter(fileName));
			writer.write(content);
			writer.newLine();
			writer.flush();
			writer.close();
			Logger.getLogger(IO.class.getName()).log(Level.INFO, "File created succesfully");
		}
		catch(IOException e){
			Logger.getLogger(IO.class.getName()).log(Level.SEVERE, "Could not write the file", e);
		}
	}

	/**
	 * get an inputstream from a file
	 * @param path file path
	 * @return inputstream or null if doenst exist
	 */
	public static InputStream readFile( String path ){
		InputStream in;
		try{
			in = new FileInputStream( path );
			return in;
		} catch ( IOException e ){
			Logger.getLogger(IO.class.getName()).log(Level.SEVERE, "Could not read the File", e);
		}
		return null;
	}

	/**
	 * reads the content of a text file
	 * @param fileName
	 * @return content by line
	 */
	public static ArrayList<String> readFromFileIntoArray( String fileName ){
		ArrayList<String> contents = new ArrayList<String>();
		BufferedReader br = null;
		try{
			br = new BufferedReader(new FileReader(fileName));
			String newLine = null;
			while((newLine = br.readLine()) != null){
				contents.add(newLine);
			}
			br.close();
		}
		catch(IOException e){
			Logger.getLogger(IO.class.getName()).log(Level.SEVERE, "Could not read the File", e);
		}
		return contents;
	}

	/**
	 * Writes a yml file use ~ for tabs
	 * @param fileName
	 * @param content
	 */
	public static void writeYML(String fileName, ArrayList<String> content){
		BufferedWriter writer = null;
		try{
			writer = new BufferedWriter(new FileWriter(fileName));

			int i = 0;
			while(i < content.size()){
				writer.write(checkSpaces(content.get(i++)));
				writer.newLine();
			}
			writer.flush();
			writer.close();
			Logger.getLogger(IO.class.getName()).log(Level.INFO, "File created succesfully");
		}
		catch(IOException e){
			Logger.getLogger(IO.class.getName()).log(Level.SEVERE, "Could not write the file", e);
		}
	}

	/**
	 * Fills the correct amount of spaces per ~
	 * @param content
	 * @return
	 */
	private static String checkSpaces(String content){
		String result = "";
		int spaceCount = 0;
		//count the ~
		for (int i = 0; i < content.length(); i++) {
			if(content.charAt(i) == '~'){
				spaceCount++;
			}
		}
		//remove the ~
		for (int i = 0; i < content.length(); i++) {
			if(content.charAt(i) != '~'){
				result += content.charAt(i);
			}
		}
		//add spaces
		spaceCount*= 4;
		String spaces = "";
		for (int i = 0; i < spaceCount; i++) {
			spaces += " ";
		}
		result = spaces + result;
		return result;
	}

	/**
	 * Copies one file to another
	 * @param source
	 * @param dest
	 */
	public static void copyFile( File source, File dest ) {
		InputStream input = null;
		OutputStream output = null;
		try {
			input = new FileInputStream(source);
			output = new FileOutputStream(dest);
			byte[] buf = new byte[1024];
			int bytesRead;
			while ( ( bytesRead = input.read(buf) ) > 0 ) {
				output.write(buf, 0, bytesRead);
			}

			input.close();
			output.close();
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}

	/**
	 * Lists all files in a sorted array
	 * @param path
	 * @return String[] of files
	 */
	public static String[] getFileListing( String path ) {
		File folder = new File( path );
		File[] listOfFiles = folder.listFiles();
		ArrayList<String> files = new ArrayList<String>();
		for ( int i = 0; i < listOfFiles.length; i++ ) {
			if ( listOfFiles[i].isFile() ) {
				files.add(listOfFiles[i].getName());
			}
		}
		Collections.sort( files );
		return files.toArray( new String[0] );
	}

	/**
	 * print array
	 */
	public static <T> void arrayPrinter( T[] t ){
		for (int i = 0; i < t.length; i++) {
			System.out.println(t[i]);
		}
	}

	public static <T> void println( T out ){
		System.out.println( out );
	}

	public static <T> void print( T out ){
		System.out.print( out );
	}

	public static <T> void printlnErr( T out ){
		System.err.println( out );
	}

	public static <T> void printErr( T out ){
		System.err.print( out );
	}
}
