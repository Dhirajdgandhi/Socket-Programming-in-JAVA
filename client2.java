/****** DHIRAJ D GANDHI ********/
// This is client program which connects to server.
// It send request for Critical Section when it wants to access it
// It waits till the time Critical Section is empty

import java.io.*;
import java.net.*;

public class client2
{
    public static void main(String args[])throws IOException, InterruptedException
    {
        Socket s=new Socket("localhost",7000);
        BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
        
        //Streams
        PrintStream out = new PrintStream(s.getOutputStream());
        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        
        String str;
        while(true)
        {
                System.out.println("\nDo you want to use the Resource on server"); // Ask client wwhether to execute in CS
                System.out.println("Enter Yes or No");
                str=sc.readLine();
                if(str.equalsIgnoreCase("Yes")) //If yes then send request
                {
                	out.println("Yes"); // Put to server that this client wants CS
                	out.println(1);
                	out.flush();
                	
                	boolean flag=false;
                	while(true){
	                	String inp=in.readLine(); // See what server tells you
	                	//System.out.println(inp);
	                	if(inp.equals("TOKEN")){ //If server gave you acsess then fine
	                		System.out.println("You are accessing critical section");
	                		Thread.sleep(10000); // Dont ask for next request till CS is being executed 
	                		break;
	                	}
                		else{	
                			if(!flag) //Print only once
                				System.out.println("\nWaiting for Token....");
	                		flag=true;
                		}
                	}
                }
            
            
        }
    }
}
