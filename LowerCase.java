import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class LowerCase {
    // receive input from user
    // server returns input but all in lower case
    private static final String SERVER_IP = "kei";
    private static final int SERVER_PORT = 8080;

    public static void main(String[] args) throws IOException
    {
        Socket socket = new Socket(SERVER_IP, SERVER_PORT);

        System.out.println("You are now connected to Server!");

        BufferedReader input = new BufferedReader(new InputStreamReader((socket.getInputStream())));
        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        System.out.println("What's on your mind?");
        System.out.print("$ ");
        String command = keyboard.readLine();

        out.println(command);

        String serverResponse = input.readLine();
        System.out.println(serverResponse);
    }  
}
