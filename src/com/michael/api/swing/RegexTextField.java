package com.michael.api.swing;

import javax.swing.*;
import java.util.regex.Pattern;

/**
 * Created By: Michael Risher
 * Date: 4/23/15
 * Time: 2:47 AM
 */
public class RegexTextField extends JTextField {
	private String regex;
	private boolean disallowEmpty = false;


	public RegexTextField( boolean disallowEmpty ) {
		this.regex = "";
		this.disallowEmpty = disallowEmpty;
	}

	public RegexTextField( String text, boolean disallowEmpty ) {
		super( text );
		this.regex = "";
		this.disallowEmpty = disallowEmpty;
	}

	public RegexTextField( String regex ) {
		this.regex = regex;
	}

	public RegexTextField( String text, String regex ) {
		super( text );
		this.regex = regex;
	}

	public RegexTextField( int columns, String regex ) {
		super( columns );
		this.regex = regex;
	}

	public RegexTextField( String text, int columns, String regex ) {
		super( text, columns );
		this.regex = regex;
	}

	public boolean matches() {
		if ( this.regex.isEmpty() && this.disallowEmpty ) {
			return this.getText().length() > 0;
		} else {
			Pattern pattern = Pattern.compile( this.regex );
			return ( pattern.matcher( this.getText() ).matches() ? true : false );
		}
	}

	public boolean equals( RegexTextField testTo ) {
		if ( this.getText().equals( testTo.getText() ) ) {
			return true;
		}
		return false;
	}

	public boolean equalsIgnoreCase( RegexTextField testTo ) {
		if ( this.getText().equalsIgnoreCase( testTo.getText() ) ) {
			return true;
		}
		return false;
	}
}
