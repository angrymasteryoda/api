package com.michael.api.IO;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by: Michael Risher
 * Date: 2/26/15
 * Time: 7:28 PM
 */
public class ThreadedDownloader implements Runnable {
	private Downloadable downFile;
	private long totalBytes = 0;
	private int percentDone = 0;

	public ThreadedDownloader( Downloadable df ) {
		downFile = df;
	}

	public void run() {
		totalBytes = downFile.getTotalBytes();
		try {
			System.out.println( downFile.getDownloadFileUrl() );
			URL url = new URL( downFile.getDownloadFileUrl() );
			url.openConnection();
			InputStream reader = url.openStream();

			//setup a buffered file writer to write out what we read from the website.
			FileOutputStream writer = null;
			if ( downFile.getDirectory().endsWith( "/" ) ) {
				writer = new FileOutputStream( downFile.getDirectory() + downFile.getFileName() );
			} else {
				writer = new FileOutputStream( downFile.getDirectory() + "/" + downFile.getFileName() );
			}
			byte[] buffer = new byte[153600];

			double totalBytesRead = 0;
			int bytesRead = 0;

			//System.out.println("reading txt file 150k blocks at a time.");
			int lastPercent = 0;
			while ( ( bytesRead = reader.read( buffer ) ) > 0 ) {
				writer.write( buffer, 0, bytesRead );
				buffer = new byte[153600];
				totalBytesRead += bytesRead;
				//System.out.printf("percent: %f\n", (totalBytesRead/size)*100);
				percentDone = (int)( ( totalBytesRead / totalBytes ) * 100 );
				if ( percentDone != lastPercent ) {
					if ( percentDone % 5 == 0 && percentDone != 0 ) {
						Logger.getLogger( this.getClass().getName() ).log( Level.INFO, "Download percentage " + percentDone + "%" );
					}
					lastPercent = percentDone;
				}
			}

			//System.out.println("done. " + (new Integer(totalBytesRead).toString()) + " bytes read");
			writer.close();
			reader.close();
			Logger.getLogger( this.getClass().getName() ).log( Level.INFO, "Download percentage " + ( percentDone + 1 ) + "%" );
		} catch ( UnknownHostException e ) {
			e.printStackTrace();
		} catch ( MalformedURLException e ) {
			e.printStackTrace();
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}

}