import java.io.*;
import java.net.*;

public class TCPEchoClient {
    public static void main(String[] args) throws IOException {

        String serverHostname = new String ("127.0.0.1"); // using the serverhostname as localhost(127.0.0.1)

        if (args.length > 0)
           serverHostname = args[0];
        System.out.println ("Attemping to connect to host " +
        serverHostname + " on port 10008.");

        Socket echoSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            echoSocket = new Socket(serverHostname, 10008);   //connecting to port 10008
            out = new PrintWriter(echoSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(
                                        echoSocket.getInputStream())); //storing socket input stream in buffered reader to process and display it
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + serverHostname);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for "
                               + "the connection to: " + serverHostname);
            System.exit(1);
        }

    BufferedReader stdIn = new BufferedReader(
                                   new InputStreamReader(System.in)); // which reads input from the console
    String userInput;

        System.out.print ("input: ");
    while ((userInput = stdIn.readLine()) != null) {
        out.println(userInput); //sends to the server after reading the input
        System.out.println("Recieved from Server: " + in.readLine()); //Recieved from server
            System.out.print ("input: ");
    }
    out.close();
    in.close();
    stdIn.close();
    echoSocket.close(); //closing the socket connection
    }
}
