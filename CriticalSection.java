/****** DHIRAJ D GANDHI ********/
// This is Critical Section program which takes parameter as CLientID.
// The client uses it through the thread assigned to it.
// It executes for some msec of time by sleep method of thread

import java.io.*;
import java.net.*;

public class CriticalSection {
	 public static void main(String args[])throws IOException, InterruptedException
	    {
		 	Thread.sleep(10000); // Execute for some time
		 	System.out.println("CS talking: Hello Client " + args[0]);
	    }
}
