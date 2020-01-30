package networking_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ClassNotFoundException;
import java.net.ServerSocket;
import java.net.Socket;

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
    
    public static void main(String args[]) throws IOException, ClassNotFoundException{
        //create the socket server object
        server = new ServerSocket(port);
        Socket socket = server.accept();
        InputStream input = socket.getInputStream();
        System.out.println("Waiting for the client request");
        while(true){
            // Receive input in Server
            InputStreamReader reader = new InputStreamReader(input);
            BufferedReader buffReader = new BufferedReader(new InputStreamReader(input));
            String line = buffReader.readLine();    // reads a line of text
            System.out.println(line);
        }
        }    
}