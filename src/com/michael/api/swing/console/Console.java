package com.michael.api.swing.console;

import com.michael.api.Time;
import com.michael.api.swing.BasicFrameObject;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.text.SimpleDateFormat;

/**
 * Created with IntelliJ IDEA.
 * User: Michael Risher
 * Date: 7/20/15
 * Time: 5:35 PM
 */
public class Console extends BasicFrameObject {

	private JTextPane textarea;
	private StyledDocument document;

	private boolean showTimestamp;
	private String timestampFormat = "HH:mm:ss";
	private SimpleDateFormat dateFormat = new SimpleDateFormat( timestampFormat );
	private Dimension size;

	public Console( String title ) {
		this( title, new Dimension( 400, 200 ) );
	}

	public Console( String title, Dimension dimension ) {
		super( title );
		showTimestamp = true;
		size = dimension;
		init();
	}

	@Override
	protected void init() {
		initMenu();
		JPanel panel = new JPanel( new MigLayout( "w " + size.getWidth() + "px, h " + size.getHeight() + "px" ) );

		textarea = new JTextPane();
		document = textarea.getStyledDocument();

		panel.add( new JScrollPane( textarea ), "push, grow" );//w 200px, h 200px

		frame.setContentPane( panel );
		frame.setLocationRelativeTo( null );
		frame.setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE );
		frame.pack();
	}

	@Override
	protected void initMenu() {
		//TODO implement method
	}

	public void append( final String str, Color color ) {
		SimpleAttributeSet keyAttribute = new SimpleAttributeSet();
		StyleConstants.setForeground( keyAttribute, color );

		String insert;
		if ( showTimestamp ) {
			insert = makeTimestamp() + str;
		} else{
			insert = str;
		}

		try {
			document.insertString( document.getLength(), insert + "\n", keyAttribute );
		} catch ( BadLocationException e ) {
			e.printStackTrace();
		}
	}

	public void append( String str ) {
		append( str, Color.black );
	}

	private String makeTimestamp(){
		String time = dateFormat.format( System.currentTimeMillis() );
		return "[" + time + "] ";
	}

	public boolean isShowTimestamp() {
		return showTimestamp;
	}

	public void setShowTimestamp( boolean showTimestamp ) {
		this.showTimestamp = showTimestamp;
	}

	public String getTimestampFormat() {
		return timestampFormat;
	}

	public void setTimestampFormat( String timestampFormat ) {
		this.timestampFormat = timestampFormat;
		dateFormat = new SimpleDateFormat( timestampFormat );
	}

	public void setDefaultCloseOperation( int pass ){
		frame.setDefaultCloseOperation( pass );
	}
}
