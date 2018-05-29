import java.io.*;
import java.net.*;

public class TCPEchoClient1{
    public static void main(String[] args) throws IOException {

        String serverHostname = new String ("127.0.0.1");

        if (args.length > 0)
           serverHostname = args[0];
        System.out.println ("Attemping to connect to host " +
		serverHostname + " on port 10007.");

        Socket echoSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            echoSocket = new Socket(serverHostname, 10008); // connecting to port on the specified port
            out = new PrintWriter(echoSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(
                                        echoSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + serverHostname);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for "
                               + "the connection to: " + serverHostname);
            System.exit(1);
        }

        System.out.print ("Flooding Server with  Message: ");
        //loop to flood the server you can change the loop value to desired value you want 
	for(int i=1;i<25;i++) {
        System.out.println("Hello from Client");
        System.out.println("Count of the Message sent " + i);
	    out.println("Hello from client "); //message sent from client
	    System.out.println("Recieved from Server: " + in.readLine()); // message recieved from the server
	}

	out.close();
	in.close();
	echoSocket.close();
    }
}
