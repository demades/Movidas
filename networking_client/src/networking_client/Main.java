/**
 * https://www.admfactory.com/socket-example-in-java/
 */
package networking_client;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.validator.routines.UrlValidator;

import java.net.*;  
import java.io.*;  

public class Main {

	public static void main(String[] args) throws IOException, Exception {
        String url = new String();
		Scanner inUser = new Scanner(System.in);
		ServerSocket server = new ServerSocket(5000);
		Socket c = new Socket("localhost",4000);  
		ObjectInputStream input = new ObjectInputStream(c.getInputStream());
		ObjectOutputStream output = new ObjectOutputStream(c.getOutputStream());
		output.writeObject("CONNECT");
		while (true) {
			String message = input.readObject().toString();
			switch (message) {
			case "ACCEPT":
				System.out.println("Introduce your URL");
				String text = inUser.nextLine();
				if (!validateURL(text)) {
					System.out.println("URL Not correct");}else {
						output.writeObject("GET:" + text);	
					}
				break;
			case "COPY":
					output.writeObject("BYE");
					input.close();
					output.close();
					c.close();
					break;
					
			default:
				break;
			
			}
		}
	}	

	
	private static boolean validateURL(String url) {
		UrlValidator defaultValidator = new UrlValidator();		
		return defaultValidator.isValid(url);
	}

}
