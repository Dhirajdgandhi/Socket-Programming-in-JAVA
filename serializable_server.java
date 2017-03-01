/****** DHIRAJ D GANDHI ********/
// This is server program which connects client using multithreading.
// It provides serializibility by using a read_write flag for each transaction.
// Client has 2 transactions to be performed

import java.io.*;
import java.net.*;


public class serializable_server {
	Socket socket=null;
    static ServerSocket ss1,ss2;
    static int A=50; // Initially we have 50rs. balance
    static int flag1=0; // To see if write is required
    static int flag2=0; 
    
    // Print function
    public static void sop(String s){
    	System.out.println(s);
    }
    
    serializable_server(Socket newSocket)
    {
        this.socket=newSocket;        
    }
    
    
    public static void main(String args[]) throws IOException, InterruptedException
    {
    	//Port for each transaction
        ss1=new ServerSocket(7000);
        ss2=new ServerSocket(7001);
        
        System.out.println("Server Started at 2 ports");
        
        // COnnect to each client
        Socket clientsoc1 = ss1.accept();
        Socket clientsoc2 = ss2.accept();
        
        // STreams for each port
        BufferedReader in1 = new BufferedReader(new InputStreamReader(clientsoc1.getInputStream()));
        PrintStream out1 = new PrintStream(clientsoc1.getOutputStream());
        BufferedReader in2 = new BufferedReader(new InputStreamReader(clientsoc2.getInputStream()));
        PrintStream out2 = new PrintStream(clientsoc2.getOutputStream());
  
        //Transaction 1
        Thread t1 = new Thread(new Runnable()  {
            public void run()  {
            	while(true){
                	int i=0;
        	        try {
						switch(in1.readLine().toLowerCase()){
							case "read1":
								/*flag1=1;
								sop("Giving value of A=" + A + " to client transaction 1");
								out1.println(A); // Give value to 1
								
								break;*/
								while(true){ //wait till other is writing
									if(flag2==0){
										flag1=1; // Write required
										sop("Giving value of A=" + A + " to client transaction 1");
										out1.println(A); // Give value to 2
										break;
									}
									else{
										if(i==0) // To print only once
											sop(String.valueOf(flag2));
										i++;}
								}
								break;
							case "write1":
								flag1=0;
								out1.println("READY"); // Server is Ready to accept and write
								sop("Writing value from client transaction 1......");
								//Thread.sleep(5000); // Take some time to write
								A=Integer.parseInt(in1.readLine()); // Writing value of A
								
								System.out.println("\n Value of A=" + A + " after transaction 1's write");
								
								break;
							
							
						}
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
					} /*catch (InterruptedException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
					}*/
                	
                }
            }
        });
        
        
        
      //Transaction 1
        Thread t2 = new Thread(new Runnable() {
            public void run() {
            	while(true){
                	int i=0;
        	        try {
						switch(in2.readLine().toLowerCase()){
							case "read2":	
								while(true){ //wait till other is writing
									if(flag1==0){
										flag2=1; // Write required
										sop("Giving value of A=" + A + " to client transaction 2");
										out2.println(A); // Give value to 2
										break;
									}
									else{
										if(i==0)
											sop(String.valueOf(flag1));
										i++;}
								}
								break;
							case "write2":
								//while(flag1==1){} //wait till other is writing
									flag2=0;
									out2.println("READY"); // Server is Ready to accept and write
									sop("Writing value from 2......");
									//Thread.sleep(5000); // Take some time to write
									A=Integer.parseInt(in2.readLine()); // Writing value of A
									System.out.println("\n Value of A=" + A + " after write2");
									return;
						}
						
					} catch (NumberFormatException | IOException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
					}
                	
                }
            }
        });
        
        // Handling the 2 transactions
        t1.start();
        t2.start();
        
    }
}
