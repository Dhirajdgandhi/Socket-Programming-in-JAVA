/****** DHIRAJ D GANDHI ********/
// This is server program which connects clients using multithreading.
// It calls a main method of the Critical Section when client wants to access it
// Semaphore is used for Mutual Exclusion

import java.io.*;
import java.net.*;

public class server implements Runnable
{
    Socket socket=null;
    static ServerSocket ss;
    static int semaphore=0; //Semaphore used for CS
    server(Socket newSocket)
    {
        this.socket=newSocket;        
    }
    public static void main(String args[]) throws IOException
    {
        ss=new ServerSocket(7000);
        System.out.println("Server Started");
        while(true)
        {
            Socket socket = ss.accept(); //Accept connection from client
            server s = new server(socket);
            (new Thread(s)).start(); // Give client to new thread and Run thread
             
        }
    }
    public void run()
    {
        try { 
        	// Input and Output streams
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintStream out = new PrintStream(socket.getOutputStream());
            
            while(true)
            {
            	System.out.println("\nWaiting for some input .........");
            	String inp=in.readLine();
            	String ClientID=in.readLine(); // Get client ID
                //System.out.println(inp);
                CriticalSection cs=new CriticalSection(); //Critical section object
                
                if(inp.equalsIgnoreCase("Yes")){
                	
                	boolean flag=false;
                	while(true){
	                	if(semaphore==0){ //If semaphore is 0 then access
	                		System.out.println("\nYour client " + ClientID + " is accessing critical section");
	                		out.println("TOKEN"); // Tell client that your call for CS was successful.
	                		out.flush();
	                		semaphore=1; // LOCK the variable
	                		
	                		// CS execution
	                		String[] args = {ClientID};
	                		cs.main(args);
	                		
	                		System.out.println("Finished using CS by Client "+ ClientID);
	                		semaphore=0; // Reset semaphore value
	                		//System.out.println(semaphore);
	                		break;
	            		}
	                	else{ // If semaphore is 1, then HOLD
	                		if(!flag){
	                			System.out.println("Your client " + ClientID + " cannot access CS now.");
                			}
	                		out.println("WAIT"); // Tell client that your call for CS is on WAIT.
            				//out.flush();
	                			
	                		flag=true;
	                	}
                	}
                }
            }
        }
        catch(Exception e)
        {}
    }
}
