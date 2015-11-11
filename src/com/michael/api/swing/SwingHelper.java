package com.michael.api.swing;

import javax.swing.*;

/**
 * Created By: Michael Risher
 * Date: 11/11/15
 * Time: 9:02 AM
 */
public class SwingHelper {
	public static void setLookAndFeel( String name ) throws Exception{
		for( UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels() ){
			if( name.equals( info.getName() ) ) {
				UIManager.setLookAndFeel( info.getClassName() );
				break;
			}
		}
	}
}
