package networking_client;

import java.net.*;  
import java.io.*;  

public class Main {

	public static void main(String[] args) throws IOException, IOException {
		Socket s= new Socket("www.google.com",443);  
		DataInputStream din=new DataInputStream(s.getInputStream());  
		DataOutputStream dout=new DataOutputStream(s.getOutputStream());  
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));  
		  
		String str="",str2="";  
		while(!str.equals("stop")){  
		str=br.readLine();  
		dout.writeUTF(str);  
		dout.flush();  
		str2=din.readUTF();  
		System.out.println("Server says: "+str2);  
		}  
		  
		dout.close();  
		s.close();  
	}

}
