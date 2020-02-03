package networking_server;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
        ObjectOutputStream  output = new ObjectOutputStream(socket.getOutputStream());
        String client = socket.getRemoteSocketAddress().toString();
        ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
        System.out.println("Client connected from " + client);
        output.writeObject("ACCEPT");
        while(true){           
            String line = input.readObject().toString();            
            System.out.println(line);
            validateMessage(line);
            File f = new File("file");
            if(f.exists() && !f.isDirectory()) {
            	sendFile(f.getAbsolutePath());
            	System.out.println("File exists " + f.getAbsolutePath());
            }else {
            	System.out.println("File not exists " + f.getAbsolutePath());
            }
            if (line.equals("BYE")) {
            	break;
            }            
        }
        output.close();
        input.close();
        socket.close();
        System.out.println("session closed");
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