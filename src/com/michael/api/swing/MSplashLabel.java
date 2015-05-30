package com.michael.api.swing;

import com.michael.api.IO.IO;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * Created By: Michael Risher
 * Date: 5/30/15
 * Time: 3:21 AM
 */
public class MSplashLabel extends JLabel {

	private String text;
	private Color color = null;
	private Font font = null;

	public MSplashLabel( URL url, String text ){
		this( url, text, null, null );
	}

	public MSplashLabel( URL url, String text, Font font, Color color ) {
		ImageIcon icon = new ImageIcon( url );
		if ( icon.getImageLoadStatus() != 8 ) {
			IO.printlnErr( "Cannot load splash screen: " + url );
			setText( "Cannot load splash screen: " + url );
		} else {
			setIcon( icon );
			this.text = text;
			this.color = color;
			this.font = font;

			if ( this.font != null ){
				setFont( font );
			}
		}
	}

	public void paint( Graphics g ) {
		super.paint( g );

		if( this.text != null ) {
			if ( this.color != null ) {
				g.setColor( this.color );
			}

			FontMetrics fm = g.getFontMetrics();
			int width = fm.stringWidth( this.text ) + 20;
			int height = fm.getHeight();

			g.drawString( this.text, getWidth() - width, getHeight() - height );
		}
	}

	public Color getColor() {
		return color;
	}
}
