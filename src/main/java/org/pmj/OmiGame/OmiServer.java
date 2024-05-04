/*
package org.pmj;

import org.pmj.OmiGame.OmiGame;

import java.io.*;
import java.net.*;
import java.util.*;

public class OmiServer {
    private ServerSocket serverSocket;
    private List<ClientHandler> clients;
    private OmiGame game;

    public OmiServer(int port) {
        try {
            serverSocket = new ServerSocket(port);
            clients = new ArrayList<>();
            game = new OmiGame();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        System.out.println("Server started. Waiting for clients...");
        game.start(); // Start the game

        while (clients.size() < 4) {
            try {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected: " + socket);
                ClientHandler handler = new ClientHandler(socket);
                clients.add(handler);
                new Thread(handler).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        int port = 12345;
        OmiServer server = new OmiServer(port);
        server.start();
    }

    class ClientHandler implements Runnable {
        private Socket socket;
        private PrintWriter writer;
        private BufferedReader reader;

        public ClientHandler(Socket socket) {
            this.socket = socket;
            try {
                writer = new PrintWriter(socket.getOutputStream(), true);
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            // Implement communication with clients here
            // Use writer to send messages to clients
            // Use reader to receive messages from clients
            try {
                // Listen for messages from the client
                String inputLine;
                while ((inputLine = reader.readLine()) != null) {
                    System.out.println("Received message from client: " + inputLine);
                    // Process the received message
                    handleMessage(inputLine);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        private void handleMessage(String message) {
            // Handle different types of messages from the client
            // For example, you might have messages for choosing trump suit,
            // playing a card, requesting game state, etc.
            // Parse the message and take appropriate action
            // Send back responses or updates to the client as needed
        }

        public void sendMessage(String message) {
            writer.println(message);
        }

    }
}
*/
