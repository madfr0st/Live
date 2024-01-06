package bin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class PortListen {
    private static final int PORT = 8080; // Specify the port number you want to listen to

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Listening on port " + PORT);

            while (true) {
                // Accept incoming client connections
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress().getHostAddress());

                // Create a new thread to handle each client connection
                Thread clientThread = new Thread(() -> handleClient(clientSocket));
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void handleClient(Socket clientSocket) {
        try {
            // Create a BufferedReader to read input from the client
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//            String response = "Hello from the server!";
//            clientSocket.getOutputStream().write(response.getBytes());

            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                // Process the received input
                System.out.println("Received from client: " + inputLine);
            }



            // Remember to close the reader and client socket when done
            reader.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



