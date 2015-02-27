package com.michael.api.IO;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by: Michael Risher
 * Date: 2/26/15
 * Time: 7:28 PM
 */
public class Downloadable {
	/* how to call
	Downloadable downloadFile = new Downloadable(
		"https://dl.dropbox.com/u/53983608/ClientUpdater.jar",
		"https://dl.dropbox.com/u/53983608/filesize.dat",
		"test.jar",
		"C:/Users/michael/Documents/Eclipse/Michaeltechapi/");
	new Thread(new ThreadedDownloader(downloadFile)).start();
	*/

	private String downloadFileUrl;
	private String fileSize;
	private long totalBytes = 0;
	private int percentDone = 0;
	private String directory;
	private String fileName;

	/**
	 * will commence the threaded download after taking required info
	 *
	 * @param downloadUrl Url to the file to download
	 * @param sizeUrl     Url to file containing download file size, or integer of the size
	 * @param filename    Filename with extension
	 * @param dir         The directory the file will be downloaded to (use / instead of \)
	 */
	public Downloadable( String downloadUrl, String sizeUrl, String filename, String dir ) {
		downloadFileUrl = downloadUrl;
		fileSize = sizeUrl;
		totalBytes = getFileSize();
		fileName = filename;
		directory = dir;
	}

	private long getFileSize() {
		if ( fileSize.contains( "http://" ) || fileSize.contains( "https://" ) ) {
			URL size = null;
			try {
				size = new URL( fileSize );
				BufferedReader br = new BufferedReader( new InputStreamReader( size.openStream() ) );
				return Long.parseLong( br.readLine() );

			} catch ( MalformedURLException e ) {
				Logger.getLogger( this.getClass().getName() ).log( Level.SEVERE, "Could not connect" );
				return 0L;
			} catch ( IOException e ) {
				Logger.getLogger( this.getClass().getName() ).log( Level.SEVERE, "Could not connect" );
				return 0L;
			}

		} else {
			try {
				return Long.parseLong( fileSize );
			} catch ( NumberFormatException e ) {
				Logger.getLogger( this.getClass().getName() ).log( Level.WARNING, "Could not convert file size to a url or a number" );
				return 0L;
			}
		}

	}

	public String getDownloadFileUrl() {
		return downloadFileUrl;
	}

	public void setDownloadFileUrl( String downloadFileUrl ) {
		this.downloadFileUrl = downloadFileUrl;
	}

	public long getTotalBytes() {
		return totalBytes;
	}

	public void setTotalBytes( long totalBytes ) {
		this.totalBytes = totalBytes;
	}

	public int getPercentDone() {
		return percentDone;
	}

	public void setPercentDone( int percentDone ) {
		this.percentDone = percentDone;
	}

	public String getDirectory() {
		return directory;
	}

	public void setDirectory( String directory ) {
		this.directory = directory;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName( String fileName ) {
		this.fileName = fileName;
	}

	public void setFileSize( String fileSize ) {
		this.fileSize = fileSize;
	}

	public String toString() {
		String s = new StringBuilder().append( downloadFileUrl ).append( "\n" ).append( fileName ).append( "\n" ).append( fileSize ).append( "\n" ).append( directory ).toString();
		return s;

	}
}
