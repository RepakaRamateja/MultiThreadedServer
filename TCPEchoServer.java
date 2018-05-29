import java.net.*; 
import java.io.*; 

public class TCPEchoServer extends Thread //extending thread class to support multithreading
{ 
 protected static boolean serverContinue = true;
 protected Socket clientSocket;

 public static void main(String[] args) throws IOException 
   { 

    ServerSocket serverSocket = null; 

    try { 
         serverSocket = new ServerSocket(10008); //creating the server socket with port 10008
         System.out.println ("Connection Socket Created");
         try { 
              while (serverContinue)
                 {
                  serverSocket.setSoTimeout(10000000);// setting the connection time out on server socket
                  System.out.println ("Waiting for Connection");
                  try {
                       new TCPEchoServer (serverSocket.accept()); // accepting client socket connections
                      }
                  catch (SocketTimeoutException ste)
                      {
                       System.out.println ("Timeout Occurred");
                      }
                 }
             } 
         catch (IOException e) 
             { 
              System.err.println("Accept failed."); 
              System.exit(1); 
             } 
        } 
    catch (IOException e) 
        { 
         System.err.println("Could not listen on port: 10008."); 
         System.exit(1); 
        } 
    finally
        {
         try {
              System.out.println ("Closing Server Connection Socket");
              serverSocket.close(); //closing the socket connection
             }
         catch (IOException e)
             { 
              System.err.println("Could not close port: 10008."); 
              System.exit(1); 
             } 
        }
   }

 private TCPEchoServer (Socket clientSoc)
   {
    clientSocket = clientSoc;
    start(); //starting a new thread
   }

 public void run()
   {
    System.out.println ("New Communication Thread Started");
    try 
    { 
         clientSocket.setSoTimeout(10000000); // setting the time out on client socket responsible for breaking if client is idle

         PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), 
                                      true); 
         BufferedReader in = new BufferedReader( 
                 new InputStreamReader( clientSocket.getInputStream())); 

         String inputLine; 

         int i=0; // variable to count the requests

         while ((inputLine = in.readLine()) != null) 
             { 

              i++;

              if(i==20) // condition to close the socket connection when any one try to flood with messages more than 20
                break;
                
              System.out.println ("Server: " + inputLine); 

              if (inputLine.equals("?")) 
                  inputLine = new String ("\"Bye.\" ends Client, " +
                      "\"End Server.\" ends Server");

              out.println(inputLine); 

              if (inputLine.equals("Bye.")) //breaking the loop if recieve Bye. message from client
                  break; 

              if (inputLine.equals("End Server.")) 
                  serverContinue = false; 
             } 
         out.close(); 
         in.close(); 
         clientSocket.close(); //closing the client socket connection
         System.out.println("Closing the socket connection");
        } 
                  catch (SocketTimeoutException ste)
                      {
                       System.out.println ("Timeout Occurred");
                      }

    catch (IOException e) 
        { 
         System.err.println("Problem with Communication Server");
         System.exit(1); 
        }

        finally
        {
         try {
              System.out.println ("Closing Server Connection Socket");
              clientSocket.close(); //closing the socket connection
             }
         catch (IOException e)
             { 
              System.err.println("Could not close port: 10008."); 
              System.exit(1); 
             } 
        }
    }
} 