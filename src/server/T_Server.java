package server;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class T_Server extends Thread {
	private ServerSocket serverSocket;
	public T_Server(int port) {
		try {
			serverSocket=new ServerSocket(port);
			serverSocket.setSoTimeout(10000000);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void database_connect() {
		try{  
			//Class.forName("com.mysql.jdbc.Driver").newInstance();  
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/systobase?useTimezone=true&serverTimezone=UTC","root",""); 
			//here sonoo is database name, root is username and password  
			//Statement stmt=con.createStatement();  
			//ResultSet rs=stmt.executeQuery("select * from emp");  
			//while(rs.next())  
			//System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));
			System.out.print("Database is connected !");
			con.close();  
		}
		catch(Exception e){ System.out.println(e);}  
	}
	public void run() {
	      while(true) {
	         try {
	            System.out.println("Waiting for client on port " + 
	               serverSocket.getLocalPort() + "...");
	            Socket server = serverSocket.accept();
	            
	            System.out.println("Just connected to " + server.getRemoteSocketAddress());
	            DataInputStream in = new DataInputStream(server.getInputStream());
	            
	            System.out.println(in.readUTF());
	            DataOutputStream out = new DataOutputStream(server.getOutputStream());
	            out.writeUTF("Thank you for connecting to " + server.getLocalSocketAddress()
	               + "\nGoodbye!");
	            server.close();
	            
	         } catch (SocketTimeoutException s) {
	            System.out.println("Socket timed out!");
	            break;
	         } catch (IOException e) {
	            e.printStackTrace();
	            break;
	         }
	      }
	   }
	
	public static void main(String [] args) {
      int port = 6066;
      Thread t = new T_Server(port);
      t.start();
	}

}
