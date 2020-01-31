package networking_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.ClassNotFoundException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.validator.routines.UrlValidator;

/**
 * This class implements java Socket server
 * @author Fernando NAVARRO
 * Initial : https://www.journaldev.com/741/java-socket-programming-server-client
 */
public class Main {
    
    //static ServerSocket variable
    private static ServerSocket server;
    //socket server port on which it will listen
    private static int port = 4000;
	String URL = new String();
	String inmessage;	
        
    public static void main(String args[]) throws IOException, ClassNotFoundException{
        //create the socket server object
        server = new ServerSocket(port);
        System.out.println("Listening for connections");
        Socket socket = server.accept();
        OutputStream output = socket.getOutputStream();
        String client = socket.getRemoteSocketAddress().toString();
        InputStream input = socket.getInputStream();
        System.out.println("Client connected from " + client);
        output.write("ACCEPT".getBytes());
        BufferedReader buffReader = new BufferedReader(new InputStreamReader(input));
        while(true){           
            String line = buffReader.readLine();    // reads a line of text            
            System.out.println(line);
            if (line.equals("BYE")) {
            	break;
            }            
        }
        output.close();
        input.close();
        socket.close();
        System.out.println("session closed");
     }    
    
    private void validateMessage(String inmessage) {
		String pattern = "GET(.*)";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(inmessage);
		if (m.find()) {
			validateURL(m.group(1));
		}
	}
	
	private boolean validateURL(String url) {
		UrlValidator defaultValidator = new UrlValidator();
		return defaultValidator.isValid(url);
	}
}