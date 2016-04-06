package com.michael.api.email;

import com.michael.api.json.JSONException;
import com.michael.api.json.JSONObject;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.Message.RecipientType;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Created by: Michael Risher
 * Date: 2/26/15
 * Time: 7:29 PM
 */
public class Emailer {
	private HashMap<String, String> props = new HashMap<>();

	public Emailer() {
		this.props.put( "mail.smtp.auth", "true" );
		this.props.put( "mail.smtp.starttls.enable", "true" );
		this.props.put( "mail.smtp.host", "smtp.gmail.com" );
		this.props.put( "mail.smtp.port", "587" );
	}

	public Emailer( HashMap<String, String> props ) {
		this.props.put( "mail.smtp.auth", "true" );
		this.props.put( "mail.smtp.starttls.enable", "true" );
		this.props.put( "mail.smtp.host", "smtp.gmail.com" );
		this.props.put( "mail.smtp.port", "587" );

		for ( Map.Entry<String, String> entry : props.entrySet() ) {
			String key = entry.getKey();
			String value = entry.getValue();
			this.props.put( key, value );
		}
	}

	/**
	 *
	 * @param values Json object of required details example listed below
	 * @param props Allows user to insert the smtp properties themselves
	 * @throws MessagingException
	 * @throws JSONException
	 * {
	 *		from : "me@host.com",
	 *		password : "mypassword",
	 *		to : [ "them@host.com" ],
	 *		cc : [ "them@host.com" ],
	 *		bcc : [ "them@host.com" ],
	 *		message : "message",
	 *		subject : "subject",
	 *		filename : "filename" | null
	 * }
	 */
	public void send( final JSONObject values, Properties props ) throws MessagingException, JSONException {
		if ( props == null ) {
			props = new Properties();
			props.put( "mail.smtp.auth", this.props.get( "mail.smtp.auth" ) );
			props.put( "mail.smtp.starttls.enable", this.props.get( "mail.smtp.starttls.enable" ) );
			props.put( "mail.smtp.host", this.props.get( "mail.smtp.host" ) );
			props.put( "mail.smtp.port", this.props.get( "mail.smtp.port" ) );
		}

		Session session = Session.getInstance( props,
				new Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication( values.getString( "from" ), values.getString( "password" ) );
					}
				}
		);

		Message message = new MimeMessage( session );
		message.setFrom( new InternetAddress( values.getString( "from" ) ) );
		String[] to = new String[0];
		try{
			to = Arrays.copyOf( values.getJSONArray( "to" ).getArray(), values.getJSONArray( "to" ).getArray().length, String[].class );
		} catch ( JSONException e ){}
		for ( int i = 0; i < to.length; i++ ) {
			if ( to[i] != null ) {
				message.addRecipients( RecipientType.TO, InternetAddress.parse( to[i] ) );
			}
		}

		String[] cc = new String[0];
		try{
			cc = Arrays.copyOf( values.getJSONArray( "cc" ).getArray(), values.getJSONArray( "cc" ).getArray().length, String[].class );
		} catch ( JSONException e ){}
		for ( int i = 0; i < cc.length; i++ ) {
			if ( !cc[i].isEmpty() ) {
				message.setRecipients( Message.RecipientType.CC, InternetAddress.parse( cc[i] ) );
			}
		}

		String[] bcc = new String[0];
		try{
			bcc = Arrays.copyOf( values.getJSONArray( "bcc" ).getArray(), values.getJSONArray( "bcc" ).getArray().length, String[].class );
		} catch ( JSONException e ){}
		for ( int i = 0; i < bcc.length; i++ ) {
			if ( !bcc[i].isEmpty() ) {
				message.setRecipients( Message.RecipientType.BCC, InternetAddress.parse( bcc[i] ) );
			}
		}

		message.setSubject( values.getString( "subject" ) );

		/* following http://www.tutorialspoint.com/javamail_api/javamail_api_send_email_with_attachment.htm
		to make the attachment part
		 */

		BodyPart messageBodyPart = new MimeBodyPart();
		//this is the actual message
		messageBodyPart.setText( values.getString( "message" ) );
		//this part is the attachment
		Multipart multipart = new MimeMultipart();
		//add the message to the email
		multipart.addBodyPart( messageBodyPart );

		String filename = (String) values.opt( "filename" );
		if ( filename != null ) {
			messageBodyPart = new MimeBodyPart();
			DataSource source = new FileDataSource( filename );
			messageBodyPart.setDataHandler( new DataHandler( source ) );
			messageBodyPart.setFileName( new File( filename ).getName() );

			//add the attachment
			multipart.addBodyPart( messageBodyPart );
		}

		//add the multipart to message
		message.setContent( multipart );

		Transport.send( message );
		Logger.getLogger( Emailer.class.getName() ).log( Level.INFO, "Email sent succesfully" );
	}

	/**
	 * @param values Json object of required details example listed below
	 * @throws MessagingException
	 * @throws JSONException
	 * {
	 *		from : "me@host.com",
	 *		password : "mypassword",
	 *		to : [ "them@host.com" ],
	 *		cc : [ "them@host.com" ],
	 *		bcc : [ "them@host.com" ],
	 *		message : "message",
	 *		subject : "subject",
	 *		filename : "filename" | null
	 * }
	 */
	public void send( final JSONObject values ) throws MessagingException, JSONException {
		send( values, null );
	}

	/**
	 * set the host you want to send to. returns false if host you entered was not found
	 * @param host the domain of the provider ( ie gmail, yahoo, outlook, aol, zoho, mail, yandex )
	 * @return true if found host and set settings
	 */
	public boolean setHost( String host ) {
		switch ( host ) {
			case "gmail":
				this.props.put( "mail.smtp.host", "smtp.gmail.com" );
				this.props.put( "mail.smtp.port", "587" );
				return true;
			case "yahoo":
				this.props.put( "mail.smtp.debug", "true" );
				this.props.put( "mail.smtp.host", "smtp.mail.yahoo.com" );
				this.props.put( "mail.smtp.port", "587" );
				return true;
			case "outlook":
				props.put( "mail.smtp.debug", "true" );
				props.put( "mail.smtp.host", "smtp.office365.com" );
				props.put( "mail.smtp.port", "587" );
				return true;
			case "aol":
				props.put( "mail.smtp.host", "smtp.aol.com" );
				props.put( "mail.smtp.port", "25" );
				return true;
			case "zoho":
				props.put( "mail.smtp.host", "smtp.zoho.com" );
				props.put( "mail.smtp.port", "465" );
				return true;
			case "mail":
				props.put( "mail.smtp.host", "smtp.mail.com" );
				props.put( "mail.smtp.port", "587" );
				return true;
			case "yandex":
				props.put( "mail.smtp.host", "smtp.yandex.com" );
				props.put( "mail.smtp.port", "465" );
				return true;
			case "naver" :
				props.put( "mail.smtp.host", "smtp.naver.com" );
				props.put( "mail.smtp.port", "587" );
				return true;
			default : return false;
		}
	}

	public static String supportedProviders(){
		return "Gmail,Yahoo,Outlook,Aol,Zoho,Mail,Yandex,Naver";
	}

	/**
	 * Sends email via gmail
	 * @deprecated  3/2/16
	 * @param gmail   email to send from
	 * @param pass    senders password
	 * @param to
	 * @param subject
	 * @param msg
	 * @throws MessagingException
	 * @paran filename
	 */
	private static void Gmail( final String gmail, final String pass, String[] to, String[] cc, String[] bcc, String subject, String msg, String filename ) throws MessagingException {


		Properties props = new Properties();
		props.put( "mail.smtp.auth", "true" );
		props.put( "mail.smtp.starttls.enable", "true" );
		props.put( "mail.smtp.host", "smtp.gmail.com" );
		props.put( "mail.smtp.port", "587" );

		Session session = Session.getInstance( props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication( gmail, pass );
					}
				}
		);

		Message message = new MimeMessage( session );
		message.setFrom( new InternetAddress( gmail ) );
		for ( int i = 0; i < to.length; i++ ) {
			if ( to[i] != null ) {
				message.addRecipients( Message.RecipientType.TO, InternetAddress.parse( to[i] ) );
			}
		}
		for ( int i = 0; i < cc.length; i++ ) {
			if ( !cc[i].isEmpty() ) {
				message.setRecipients( Message.RecipientType.CC, InternetAddress.parse( cc[i] ) );
			}
		}

		for ( int i = 0; i < bcc.length; i++ ) {
			if ( !bcc[i].isEmpty() ) {
				message.setRecipients( Message.RecipientType.BCC, InternetAddress.parse( bcc[i] ) );
			}
		}


		message.setSubject( subject );

		/* following http://www.tutorialspoint.com/javamail_api/javamail_api_send_email_with_attachment.htm
		to make the attachment part
		 */

		BodyPart messageBodyPart = new MimeBodyPart();
		//this is the actual message
		messageBodyPart.setText( msg );
		//this part is the attachment
		Multipart multipart = new MimeMultipart();
		//add the message to the email
		multipart.addBodyPart( messageBodyPart );

		if ( filename != null ) {
			messageBodyPart = new MimeBodyPart();
			DataSource source = new FileDataSource( filename );
			messageBodyPart.setDataHandler( new DataHandler( source ) );
			messageBodyPart.setFileName( new File( filename ).getName() );

			//add the attachment
			multipart.addBodyPart( messageBodyPart );
		}

		//add the multipart to message
		message.setContent( multipart );

		Transport.send( message );
		Logger.getLogger( Emailer.class.getName() ).log( Level.INFO, "Email sent succesfully" );

	}

	/**
	 * Used to send the email through gmail
	 * @deprecated  3/2/16
	 * @param gmail
	 * @param pass
	 * @param to
	 * @param subject
	 * @param msg
	 * @throws MessagingException
	 */
	public static void sendGmail( final String gmail, final String pass, String[] to, String[] cc, String[] bcc, String subject, String msg ) throws MessagingException {
		Gmail( gmail, pass, to, cc, bcc, subject, msg, null );
	}

	/**
	 * @deprecated  3/2/16
	 */
	public static void sendGmail( final String gmail, final String pass, String[] to, String[] cc, String[] bcc, String subject, String msg, String filename ) throws MessagingException {
		Gmail( gmail, pass, to, cc, bcc, subject, msg, filename );
	}

	/**
	 * @deprecated  3/2/16
	 */
	public static void sendGmail( final String gmail, final String pass, String to, String cc, String bcc, String subject, String msg, String filename ) throws MessagingException {
		String[] toArray = {to};
		String[] ccArray = {cc};
		String[] bccArray = {bcc};
		Gmail( gmail, pass, toArray, ccArray, bccArray, subject, msg, filename );
	}

	/**
	 * @deprecated  3/2/16
	 */
	public static void sendGmail( final String gmail, final String pass, String to, String cc, String bcc, String subject, String msg ) throws MessagingException {
		String[] toArray = {to};
		String[] ccArray = {cc};
		String[] bccArray = {bcc};
		Gmail( gmail, pass, toArray, ccArray, bccArray, subject, msg, null );
	}

	/**
	 * Sends email via yahoo
	 * @deprecated  3/2/16
	 * @param gmail
	 * @param pass
	 * @param to
	 * @param subject
	 * @param msg
	 * @throws MessagingException
	 * @throws AddressException
	 */
	public static void Yahoo( final String gmail, final String pass, String[] to, String subject, String msg, String filename ) throws AddressException, MessagingException {
		//TODO fix the yahoo Emailer so it idk works!

		Properties props = new Properties();
		props.put( "mail.smtp.auth", "true" );
		props.put( "mail.smtp.starttls.enable", "true" );
		props.put( "mail.smtp.debug", "true" );
		props.put( "mail.smtp.host", "smtp.mail.yahoo.com" );
		props.put( "mail.smtp.port", "587" );

		Session session = Session.getInstance( props, new Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication( gmail, pass );
					}
				}
		);

		Message message = new MimeMessage( session );
		message.setFrom( new InternetAddress( gmail ) );
		for ( int i = 0; i < to.length; i++ ) {
			if ( to[i] != null ) {
				message.addRecipients( Message.RecipientType.TO, InternetAddress.parse( to[i] ) );
			}
		}
//		for ( int i = 0; i < cc.length; i++ ) {
//			if ( !cc[i].isEmpty() ) {
//				message.setRecipients( Message.RecipientType.CC, InternetAddress.parse( cc[i] ) );
//			}
//		}
//
//		for ( int i = 0; i < bcc.length; i++ ) {
//			if ( !bcc[i].isEmpty() ) {
//				message.setRecipients( Message.RecipientType.BCC, InternetAddress.parse( bcc[i] ) );
//			}
//		}


		message.setSubject( subject );

		/* following http://www.tutorialspoint.com/javamail_api/javamail_api_send_email_with_attachment.htm
		to make the attachment part
		 */

		BodyPart messageBodyPart = new MimeBodyPart();
		//this is the actual message
		messageBodyPart.setText( msg );
		//this part is the attachment
		Multipart multipart = new MimeMultipart();
		//add the message to the email
		multipart.addBodyPart( messageBodyPart );

		if ( filename != null ) {
			messageBodyPart = new MimeBodyPart();
			DataSource source = new FileDataSource( filename );
			messageBodyPart.setDataHandler( new DataHandler( source ) );
			messageBodyPart.setFileName( new File( filename ).getName() );

			//add the attachment
			multipart.addBodyPart( messageBodyPart );
		}

		//add the multipart to message
		message.setContent( multipart );

		Transport.send( message );
		Logger.getLogger( Emailer.class.getName() ).log( Level.INFO, "Email sent succesfully" );
	}
}