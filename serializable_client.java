/****** DHIRAJ D GANDHI ********/
// This is client program which connects server through 2 ports.
// Client has 2 transactions to be performed
// Each transaction has its own port to connect to server.
// Transaction 1 : A=A+50
// Transction 2 : A=A/2

import java.io.*;
import java.net.*;

public class serializable_client {

    public static void sop(String s){
    	System.out.println(s);
    }
    
	public static void main(String args[])throws IOException,InterruptedException
    {
        Socket s1=new Socket("localhost",7000);
        Socket s2=new Socket("localhost",7001);
        
        PrintStream out1 = new PrintStream(s1.getOutputStream());
        PrintStream out2 = new PrintStream(s2.getOutputStream());
        
        //Input and Output Streams
        BufferedReader in1 = new BufferedReader(new InputStreamReader(s1.getInputStream()));
        BufferedReader in2 = new BufferedReader(new InputStreamReader(s2.getInputStream()));
        BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
        
        
        //Transaction 1
        Thread t1 = new Thread(new Runnable() {
            public void run() {
            	sop("Transaction 1 started");
            	out1.println("READ1");
            	sop("1:Reading A's value");
            	
            	String inp="";
            	int value=0;
				try {
					value = Integer.parseInt(in1.readLine());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} // A
            	
            	//Processing A
            	value=value+50;
            	sop("1:Processing A's value.......");
            	try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	sop("1:Processed value of A="+value);
            	
            	sop("1:Trying to write A's value......");
            	out1.println("WRITE1");
            
            
            	//while(true){
            		
            		try {
						if(in1.readLine().equalsIgnoreCase("READY")){	
							sop("1:Writing A's value");
							out1.println(String.valueOf(value)); // send A
							//break;
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						
					}
            		
            	//}
            }
        });

        
        
        Thread t2 = new Thread(new Runnable() {
            public void run() {
            	sop("Transaction 2 started");
            	out2.println("READ2");
            	sop("2:Reading A's value");
            	
            	//String inp="";
            	int value=0;
				try {
					value = Integer.parseInt(in2.readLine());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} // A
            	
            	//Processing A
            	value=value/2;
            	sop("2:Processing A's value.......");
            	try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	sop("2:Processed value of A="+value);
            	
            	sop("2:Trying to write A's value......");
            	out2.println("WRITE2");
            
            
            	//while(true){
            		
            		try {
						if(in2.readLine().equalsIgnoreCase("READY")){	
							sop("2:Writing A's value");
							out2.println(String.valueOf(value)); // send A
							//return;
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						
					}
            		
            	//}
            }
        });
        
        // Starting the 2 transactions
        t1.start();
        t2.start();
        //Thread.sleep(5000);
        
    }
	

}
