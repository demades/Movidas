package networking_server_udp;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.validator.routines.UrlValidator;


/**
 * This class implements java Socket server
 * @author Fernando NAVARRO
 * Initial : https://www.journaldev.com/741/java-socket-programming-server-client & https://systembash.com/a-simple-java-udp-server-and-udp-client/
 */
public class Main {
    
    //static ServerSocket variable
    private static ServerSocket server;
    //socket server port on which it will listen
    private static int port = 4000;
	String URL = new String();
	String inmessage;	
        
    public static void main(String args[]) throws IOException, ClassNotFoundException{
        DatagramSocket serverSocket = new DatagramSocket(9876);
        byte[] receiveData = new byte[1024];
        byte[] sendData = new byte[1024];
        while(true)
        {
           DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
           serverSocket.receive(receivePacket);
           String sentence = new String( receivePacket.getData());
           System.out.println("RECEIVED: " + sentence);
           InetAddress IPAddress = receivePacket.getAddress();
           int port = receivePacket.getPort();
           String capitalizedSentence = sentence.toUpperCase();
           sendData = capitalizedSentence.getBytes();
           DatagramPacket sendPacket =
           new DatagramPacket(sendData, sendData.length, IPAddress, port);
           serverSocket.send(sendPacket);
        }
     }    
    
    private static void validateMessage(String line) {
		String pattern = "GET:(.*)";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(line);
		if (m.find()) {
			//validateURL(m.group(1));
			boolean  urlValidation = (validateURL(m.group(1)));
			if (urlValidation) {
				System.out.println("URL is valid " + m.group(1).toString());
				HttpGetDownloadFileExample.main(m.group(1).toString());
			}else {
				System.err.println("URL is NOT valid");
			}
		}else {
			System.err.println("not valid");
		}
			
	}
	
	private static boolean validateURL(String url) {
		UrlValidator defaultValidator = new UrlValidator();		
		return defaultValidator.isValid(url);
	}
	
	private static void sendFile(String file) throws IOException {
		Socket c = new Socket("localhost",5000); 
		DataOutputStream dos = new DataOutputStream(c.getOutputStream());
		FileInputStream fis = new FileInputStream(file);
		byte[] buffer = new byte[4096];		
		while (fis.read(buffer) > 0) {
			dos.write(buffer);						
		}
		System.out.println("file transfered");
		File filep = new File("file");
		filep.delete();
		System.out.println("file removed on server side");
		fis.close();
		dos.close();	
	}
}