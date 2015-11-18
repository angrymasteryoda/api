package com.michael.api.swing;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Michael Risher
 * Date: 7/6/15
 * Time: 5:36 PM
 */
public class JLabels {
	private ArrayList<JLabel> labels;

	public JLabels( ArrayList<JLabel> labels ) {
		this.labels = labels;
	}

	public JLabels() {
		this.labels = new ArrayList<>();
	}


	public JLabel add( int index, JLabel label ){
		labels.add( index, label );
		return label;
	}

	public JLabel add( JLabel label ){
		labels.add( label );
		return label;
	}

	public JLabel add( String text ){
		JLabel label = new JLabel( text );
		labels.add( label );
		return label;
	}

	public JLabel add( int index, String text ){
		JLabel label = new JLabel( text );
		labels.add( index, label );
		return label;
	}

	public JLabel get( int index ){
		return labels.get( index );
	}

	public int size(){
		return labels.size();
	}

	public void clear(){
		labels.clear();
	}

	public ArrayList<JLabel> getLabels() {
		return labels;
	}
}
