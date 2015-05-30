package com.michael.api.swing;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * Created By: Michael Risher
 * Date: 5/30/15
 * Time: 3:14 AM
 */
public class MSplashScreen extends JWindow {
	private final JProgressBar progressBar;
	private boolean showProgress = false;
	private boolean showMessages = false;
	private boolean showPercent = false;

	public MSplashScreen( URL url, boolean progress, boolean messages, boolean percent, String version ) {
		this( url, progress, messages, percent, version, null, null, new BackForeColor( null, null ) );
	}

	public MSplashScreen( URL url, boolean progress, boolean messages, boolean percent, String version, Color progressColor ) {
		this( url, progress, messages, percent, version, null, null, new BackForeColor( null, progressColor ) );
	}

	public MSplashScreen( URL url, boolean progress, boolean messages, boolean percent, String version, BackForeColor progressColor ) {
		this( url, progress, messages, percent, version, null, null, progressColor );
	}

	public MSplashScreen( URL url, boolean progress, boolean messages, boolean percent, String version, Font versionFont, Color versionColor, Color progressColor ) {
		this( url, progress, messages, percent, version, versionFont, versionColor, new BackForeColor( null, progressColor ) );
	}

	public MSplashScreen( URL url, boolean progress, boolean messages, boolean percent, String version, Font versionFont, Color versionColor, BackForeColor progressColor ) {
		this.showProgress = progress;
		this.showMessages = messages;
		this.showPercent = percent;

		this.progressBar = new JProgressBar();
		if ( progressColor != null ) {
			if ( progressColor.getForeground() != null ) {
				this.progressBar.setForeground( progressColor.getForeground() );
			}
			if ( progressColor.getBackground() != null ) {
				this.progressBar.setForeground( progressColor.getBackground() );
			}
		}

		JPanel panel = new JPanel();
		panel.setLayout( new BorderLayout() );
		panel.setBorder( BorderFactory.createLineBorder( Color.black ) );
		setContentPane( panel );

		MSplashLabel splashLabel = new MSplashLabel( url, version, versionFont, versionColor );

		if ( showProgress ) {
			if ( ( showMessages ) || ( showPercent ) )
				progressBar.setStringPainted( true );
			else {
				progressBar.setStringPainted( false );
			}

			if ( ( showMessages ) && ( !showPercent ) ) {
				this.progressBar.setString( "" );
			}

			progressBar.setMaximum( 100 );
			progressBar.setMinimum( 0 );
			progressBar.setValue( 0 );
		}

		getContentPane().add( splashLabel, "Center" );

		if ( showProgress ) {
			getContentPane().add( progressBar, "South" );
		}

		pack();
		centerOnScreen();
		setVisible( false );
	}

	public void on() {
		setVisible( true );
	}


	public void off() {
		setVisible( false );
		dispose();
	}

	public void setIndeterminate( boolean flag ) {
		progressBar.setIndeterminate( flag );
	}

	public void setProgress( int value ) {
		if ( ( showProgress ) && ( value >= 0 ) && ( value <= 100 ) )
			progressBar.setValue( value );
	}

	public void setProgress( int value, String msg ) {
		setProgress( value );

		if ( ( showMessages ) && ( !showPercent ) && ( msg != null ) )
			progressBar.setString( msg );
	}

	public void setProgress( String msg ) {
		if ( ( showMessages ) && ( !showPercent ) && ( msg != null ) )
			progressBar.setString( msg );
	}

	public final JProgressBar getProgressBar() {
		return progressBar;
	}

	private void centerOnScreen() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension splashSize = getPreferredSize();
		setLocation( screenSize.width / 2 - splashSize.width / 2,
			screenSize.height / 2 - splashSize.height / 2 );
	}
}
