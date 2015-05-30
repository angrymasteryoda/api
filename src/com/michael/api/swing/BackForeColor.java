package com.michael.api.swing;

import java.awt.*;

/**
 * Created By: Michael Risher
 * Date: 5/30/15
 * Time: 4:09 AM
 */
public class BackForeColor {
	private Color background;
	private Color foreground;

	public BackForeColor() {
		this.background = null;
		this.foreground = null;
	}

	public BackForeColor( Color background, Color foreground ) {
		this.background = background;
		this.foreground = foreground;
	}

	public BackForeColor( int background, int foreground ) {
		this.background = new Color( background );
		this.foreground = new Color( foreground );
	}

	public Color getBackground() {
		return background;
	}

	public void setBackground( Color background ) {
		this.background = background;
	}

	public Color getForeground() {
		return foreground;
	}

	public void setForeground( Color foreground ) {
		this.foreground = foreground;
	}
}
