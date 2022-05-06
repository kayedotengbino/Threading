// a Java server application with a new thread to serve each new client
import java.net.*;
import java.util.ArrayList;
import java.io.*;

public class TCPServer
{
    private static ArrayList<ClientThread> client = new ArrayList<>();
    public static void main(String args[])
    {
        try
        {
            int serverPort = 8080;
            ServerSocket listenSocket = new ServerSocket(serverPort);

            while(true)
            {
                System.out.println("Waiting for a client...");
                Socket clientSocket = listenSocket.accept();
                System.out.println("Connection received from " + clientSocket.getInetAddress().getHostName());

                //start a new thread
                ClientThread c = new ClientThread(clientSocket);
                client.add(c);  // adding the client to the thread
            }
        }
        catch(IOException e)
        {
            System.err.println("Error in connection " + e.getMessage());
        }
    }
}

class ClientThread extends Thread
{
    PrintWriter out = null;
    BufferedReader in = null;
    BufferedReader keyboard = null;
    Socket clientSocket;

    public ClientThread(Socket aClientSocket)
    {            
        try
        {
            clientSocket = aClientSocket;
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            keyboard = new BufferedReader(new InputStreamReader(System.in));
            
            this.start();   // call run()
        }
        catch(IOException e)
        {
            System.err.println("Error 1 in connection " + e);
            System.exit(0);
        }
    }

    public void run()
    {
        // Assume client sends a string, receives a string response then quits
        String lineIn = "";
        
        try
        {
            // must cast from Object to String
            lineIn = in.readLine();
            //out.println(command);

            System.out.println("Received from " + clientSocket.getInetAddress().getHostName() + ": " + lineIn);

            // important to create a new object in this case a String
            out.println("From Server: " + lineIn.toLowerCase());
        }
        catch(IOException e2)
        {
            System.err.println("Error in I/O " + e2);
            System.exit(0);
        }

        // close client
        try
        {
            in.close();
            out.close();
            clientSocket.close();
            System.out.println("Connection closed from " + clientSocket.getInetAddress().getHostName());
        }
        catch(IOException e3)
        {
            System.err.println("Error 2 in connection " + e3);
            System.exit(0);
        }
    }
}