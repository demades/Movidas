package networking_server_udp;

import java.io.IOException;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

public class HttpGetDownloadFileExample {
	
	   public static void main(String url) {
		   
	        ReadableByteChannel readableChannelForHttpResponseBody = null;
	        FileChannel fileChannelForDownloadedFile = null;
	 
	        try {
	            // Define server endpoint
	            URL robotsUrl = new URL(url);
	            HttpURLConnection urlConnection = (HttpURLConnection) robotsUrl.openConnection();
	 
	            // Get a readable channel from url connection
	            readableChannelForHttpResponseBody = Channels.newChannel(urlConnection.getInputStream());
	 
	            // Create the file channel to save file
	            FileOutputStream fosForDownloadedFile = new FileOutputStream("file");
	            fileChannelForDownloadedFile = fosForDownloadedFile.getChannel();
	            System.out.println("file created");

	            // Save the body of the HTTP response to local file
	            fileChannelForDownloadedFile.transferFrom(readableChannelForHttpResponseBody, 0, Long.MAX_VALUE);
	            System.out.println("file saved");
	 
	        } catch (IOException ioException) {
	            System.out.println("IOException occurred while contacting server.");
	        } finally {
	 
	            if (readableChannelForHttpResponseBody != null) {
	 
	                try {
	                    readableChannelForHttpResponseBody.close();
	                } catch (IOException ioe) {
	                    System.out.println("Error while closing response body channel");
	                }
	            }
	 
	            if (fileChannelForDownloadedFile != null) {
	 
	                try {
	                    fileChannelForDownloadedFile.close();
	                } catch (IOException ioe) {
	                    System.out.println("Error while closing file channel for downloaded file");
	                }
	            }
	 
	        }
	    }

}
