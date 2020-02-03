/**
 * https://www.admfactory.com/socket-example-in-java/
 */
package networking_client;
import java.util.Scanner;


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
		System.out.println("Introduce your URL");
		String text = inUser.nextLine();
		output.writeObject("GET:" + text);
        
		Thread.sleep(10000);
		  
	}

}
