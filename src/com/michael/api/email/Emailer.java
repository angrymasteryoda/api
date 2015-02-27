package com.michael.api.email;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
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


	/**
	 * Sends email via gmail
	 *
	 * @param gmail
	 * @param pass
	 * @param to
	 * @param subject
	 * @param msg
	 * @throws MessagingException
	 */
	private static void Gmail( final String gmail, final String pass, String[] to, String[] cc, String[] bcc, String subject, String msg ) throws MessagingException {


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
				} );


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

		message.setText( msg );

		Transport.send( message );
		Logger.getLogger( Emailer.class.getName() ).log( Level.INFO, "Email sent succesfully" );

	}

	/**
	 * Used to send the email through gmail
	 *
	 * @param gmail
	 * @param pass
	 * @param to
	 * @param subject
	 * @param msg
	 * @throws MessagingException
	 */
	public static void sendGmail( final String gmail, final String pass, String[] to, String[] cc, String[] bcc, String subject, String msg ) throws MessagingException {
		Gmail( gmail, pass, to, cc, bcc, subject, msg );
	}

	public static void sendGmail( final String gmail, final String pass, String to, String cc, String bcc, String subject, String msg ) throws MessagingException {
		String[] toArray = { to };
		String[] ccArray = { cc };
		String[] bccArray = { bcc };
		Gmail( gmail, pass, toArray, ccArray, bccArray, subject, msg );
	}

	/**
	 * Sends email via yahoo
	 *
	 * @param user
	 * @param pass
	 * @param to
	 * @param subject
	 * @param msg
	 * @throws MessagingException
	 * @throws AddressException
	 */
	private void Yahoo( final String user, final String pass, String[] to, String subject, String msg ) throws AddressException, MessagingException {
		//TODO fix the yahoo Emailer so it idk works!

		Properties props = new Properties();
		props.put( "mail.transport.protocol", "smtps" );
		props.put( "mail.smtp.auth", "true" );
		props.put( "mail.smtp.host", "smtp.mail.yahoo.com" );
		props.put( "mail.smtp.port", "465" );

		Session session = Session.getInstance( props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication( user, pass );
					}
				} );
		Message message = new MimeMessage( session );
		message.setFrom( new InternetAddress( user ) );
		for ( int i = 0; i < to.length; i++ ) {
			if ( to[i] != null ) {
				message.addRecipients( Message.RecipientType.TO, InternetAddress.parse( to[i] ) );
			}
		}

		message.setSubject( subject );

		message.setText( msg );

		Transport.send( message );
		Logger.getLogger( Emailer.class.getName() ).log( Level.INFO, "Email sent succesfully" );

	}

	/**
	 * Used to send the email through gmail
	 *
	 * @param user
	 * @param pass
	 * @param to
	 * @param subject
	 * @param msg
	 * @throws MessagingException
	 * @throws AddressException
	 */
	public void sendYahoo( final String user, final String pass, String[] to, String subject, String msg ) throws AddressException, MessagingException {
		Yahoo( user, pass, to, subject, msg );
	}

	public static void test() throws MessagingException {
		// Email message
		String toAddress = "rishermichael@gmail.com";
		String fromAddress = "rishermichael@yahoo.com";
		String subject = "Hello Yahoo!";
		String message = "From Java!";

		// Auth.
		String host = "smtp.mail.yahoo.com";
		String port = "465";
		String username = "rishermichael";
		String password = "Mbr3363329";

		// Configure your JavaMail.
		Properties props = new Properties();
		props.setProperty( "mail.transport.protocol", "smtps" );
		props.setProperty( "mail.smtps.auth", "true" );
		props.setProperty( "mail.host", host );
		props.setProperty( "mail.port", port );
		props.setProperty( "mail.user", username );
		props.setProperty( "mail.password", password );

		// Start an email session.
		Session session = Session.getDefaultInstance( props, null );
		Transport transport = session.getTransport( "smtp" );
		MimeMessage mimeMessage = new MimeMessage( session );
		Multipart multiPart = new MimeMultipart();

		mimeMessage.setSubject( subject );
		mimeMessage.addRecipient( RecipientType.TO, new InternetAddress( toAddress ) );
		MimeBodyPart textBodyPart = new MimeBodyPart();
		textBodyPart.setContent( message, "text/plain" );
		multiPart.addBodyPart( textBodyPart );
		mimeMessage.setContent( multiPart );
		mimeMessage.setFrom( new InternetAddress( fromAddress ) );

		// Send email.
		transport.connect();
		transport.sendMessage( mimeMessage, mimeMessage.getAllRecipients() );
		transport.close();
	}


}