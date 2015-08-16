package com.michael.api.security;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Michael Risher
 * Date: 8/14/15
 * Time: 10:19 PM
 */
public class TextImage {

	/**
	 * read string stored in image
	 * @param file location of file
	 * @return string read from image
	 */
	public static String readString( String file ){
		File f = new File( file );
		BufferedImage img = null;
		try {
			img = ImageIO.read( f );
			return read( img );
		} catch ( IOException e ) {
			e.printStackTrace();
		}
		return null;
	}

	private static String read( BufferedImage img ){
		int width = img.getWidth();
		int height = img.getHeight();

		StringBuilder sb = new StringBuilder(  );

		for ( int x = 0; x < width; x++ ) {
			for ( int y = 0; y < height; y++ ) {
				int clr = img.getRGB( x, y );
				char chars[] = new char[3];
				chars[0] = (char)( ( clr & 0x00ff0000 ) >> 16 );
				chars[1] = (char)( ( clr & 0x0000ff00 ) >> 8 );
				chars[2] = (char)( clr & 0x000000ff );
				for ( int i = 0; i < chars.length; i++ ) {
					if ( chars[ i ] != 0 ) {
						sb.append( chars[i] );
					}
				}
			}
		}

		return sb.toString();
	}

	/**
	 * writes a string into an image
	 * @param str string to be written
	 * @param file file location to save
	 */
	public static void writeString( String str, String file ){
		int strLen = str.length();
//		IO.println( strLen );
		strLen /= 3;
//		IO.println( strLen );
		int rows = nearestSquare( strLen );
		int rowsLen = rows;
//		IO.println( rows );
		if ( !perfectSquare( strLen ) ) {
			int remaining = ( strLen ) - ( rows * rows );
//			IO.println( remaining );
			while( true ){
				if ( remaining - rows > 0 ) {
					rows++;
					remaining -= rowsLen;
				} else if ( remaining - rows < 0 ) {
					rows++;
					break;
				}
			}
//			IO.println( rows );
//			rows += ( remaining % rows ) + 1;
		}

		BufferedImage img = new BufferedImage( rowsLen, rows, BufferedImage.TYPE_INT_RGB );
//		System.out.println();
//		System.out.println();
		int i = 0;
		for ( int r = 0; r < rows; r++ ) {
			for ( int c = 0; c < rows; c++ ) {
				try {
					char[] chars = new char[3];
					chars[0] = str.charAt( i++ );
					chars[1] = str.charAt( i++ );
					chars[2] = str.charAt( i++ );
					int color = ( (int)chars[0] << 16 ) | ( (int)chars[1] << 8 ) | (int)chars[2];
					img.setRGB( r, c, color );
				} catch ( Exception ignore ) {}
			}
//			System.out.println();
		}

		File f = new File( "text.png" );
		try {
			ImageIO.write( img, "PNG", f );
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}

	private static int nearestSquare( int a ) {
		float b = (float) Math.sqrt( a );
		int c;
		if ( b - Math.floor( b ) != 0 ) {
			return nearestSquare( a - 1 );
		} else {
			c = (int) b;
			return c;
		}
	}

	private static boolean perfectSquare( int a ) {
		float b = (float) Math.sqrt( a );
		if ( b - (int) b != 0 ) {
			return false;
		}
		return true;
	}
}
