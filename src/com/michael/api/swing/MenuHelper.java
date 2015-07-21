package com.michael.api.swing;


import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * Created By: Michael Risher
 * Date: 4/22/15
 * Time: 5:28 PM
 */
public class MenuHelper {

	public static final int PLAIN = 0;
	public static final int RADIO = 1;
	public static final int CHECK = 2;

	public MenuHelper() {
	}

	public static JMenuItem createMenuItem( JMenu menu, int type, String text, String command, int acceleratorKey, ActionListener action ) {
		return createMenuItem( menu, type, text, command, null, acceleratorKey, null, action );
	}

	public static JMenuItem createMenuItem( JMenu menu, int type, String text, String command, int acceleratorKey, String toolTip, ActionListener action ) {
		return createMenuItem( menu, type, text, command, null, acceleratorKey, toolTip, action );
	}

	/**
	 * Create a menu item and add it to menu
	 * @param menu menu to add item to
	 * @param type type of menu
	 * @param text displayed text
	 * @param command the action command string
	 * @param image icon
	 * @param acceleratorKey shortcut key
	 * @param toolTip string for tooltip
	 * @param action action listener
	 * @return menu item created
	 */
	public static JMenuItem createMenuItem( JMenu menu, int type, String text, String command, ImageIcon image, int acceleratorKey, String toolTip, ActionListener action ) {
		JMenuItem item;

		switch ( type ) {
			case RADIO:
				item = new JRadioButtonMenuItem();
				break;
			case CHECK:
				item = new JCheckBoxMenuItem();
				break;
			default:
				item = new JMenuItem();
		}

		item.setText( text );

		if ( image != null ) {
			item.setIcon( image );
		}

		if ( acceleratorKey > 0 ) {
			item.setMnemonic( acceleratorKey );
		}

		if ( toolTip != null ) {
			item.setToolTipText( toolTip );
		}

		item.setActionCommand( command );
		item.addActionListener( action );

		menu.add( item );
		return item;
	}

	/**
	 * Create a separator in the menu
	 * @param menu menu to put separator into
	 */
	public static void createMenuSeparator( JMenu menu ){
		menu.addSeparator();
	}
}
