package com.michael.api;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: Michael Risher
 * Date: 8/14/15
 * Time: 5:03 PM
 */
public class ColorStatistics {
	private String fileName;
	private BufferedImage image;

	/**
	 * Opens and buffers an image for statistics
	 * @param fileName location of image file
	 */
	public ColorStatistics( String fileName ) {
		this.fileName = fileName;
		File file = new File( fileName );
		BufferedImage img = null;
		try {
			img = ImageIO.read( file );
			this.image = img;
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}

	/**
	 * Sets a bufferedImage to use for statistics
	 * @param image bufferedimage to use
	 */
	public ColorStatistics( BufferedImage image ) {
		this.image = image;
	}

	/**
	 * Find a color usage by percentage
	 * @param color color to look for
	 * @return percentage it was found in image
	 */
	public float specificColorPercent( Color color ) {
		int width = image.getWidth();
		int height = image.getHeight();
		int compare = color.getRGB();
		float count = 0;
		float total = width * height;
		for ( int x = 0; x < width; x++ ) {
			for ( int y = 0; y < height; y++ ) {
				int readColor = image.getRGB( x, y );
				if ( readColor == compare ) {
					count++;
				}
			}
		}

		return ( count / total ) * 100;
	}

	/**
	 * find most common color using scaling method
	 * @return Color found
	 */
	public Color mostCommonColor() {
		Image img = image.getScaledInstance( 1, 1, Image.SCALE_REPLICATE );
		BufferedImage bimg = new BufferedImage( img.getWidth( null ), img.getHeight( null ), BufferedImage.TYPE_INT_ARGB );

		// Draw the image on to the buffered image
		Graphics2D bGr = bimg.createGraphics();
		bGr.drawImage( img, 0, 0, null );
		bGr.dispose();

		Color common = new Color( bimg.getRGB( 0, 0 ) );
		return common;

	}

	/**
	 * Find the exact common color this is memory extensive filters out gray, black and white
	 * @return Color found
	 */
	public Color mostCommonExactColor() {
		return mostCommonExactColor( false );
	}

	/**
	 * Find the exact common color this is memory extensive filters out gray, black and white
	 * @param isGrayScale flag to filter out gray, black and white
	 * @return Color found
	 */
	public Color mostCommonExactColor( boolean isGrayScale ) {
		int height = image.getHeight();
		int width = image.getWidth();

		Map m = new HashMap();
		for ( int i = 0; i < width; i++ ) {
			for ( int j = 0; j < height; j++ ) {
				int rgb = image.getRGB( i, j );
				int[] rgbArr = getRGBArr( rgb );
				// Filter out grays....
				if ( !isGray( rgbArr ) || isGrayScale ) {
						Integer counter = (Integer) m.get( rgb );
						if ( counter == null )
							counter = 0;
						counter++;
						m.put( rgb, counter );
				}
			}
		}
		return getMostCommonColour( m );
	}

	private Color getMostCommonColour( Map map ) {
		List list = new LinkedList( map.entrySet() );
		Collections.sort( list, new Comparator() {
			public int compare( Object o1, Object o2 ) {
				return ( (Comparable) ( (Map.Entry) ( o1 ) ).getValue() ) .compareTo( ( (Map.Entry) ( o2 ) ).getValue() );
			}
		} );
		Map.Entry me = (Map.Entry) list.get( list.size() - 1 );
		int[] rgb = getRGBArr( (Integer) me.getKey() );
		return new Color( rgb[ 0 ], rgb[ 1 ], rgb[ 2 ] );
	}

	private int[] getRGBArr( int pixel ) {
		int alpha = ( pixel >> 24 ) & 0xff;
		int red = ( pixel >> 16 ) & 0xff;
		int green = ( pixel >> 8 ) & 0xff;
		int blue = ( pixel ) & 0xff;
		return new int[]{ red, green, blue };

	}

	private boolean isGray( int[] rgbArr ) {
		int rgDiff = rgbArr[ 0 ] - rgbArr[ 1 ];
		int rbDiff = rgbArr[ 0 ] - rgbArr[ 2 ];
		// Filter out black, white and grays...... (tolerance within 10 pixels)
		int tolerance = 10;
		if ( rgDiff > tolerance || rgDiff < -tolerance )
			if ( rbDiff > tolerance || rbDiff < -tolerance ) {
				return false;
			}
		return true;
	}
}
