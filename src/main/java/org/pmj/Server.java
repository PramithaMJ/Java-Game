package org.pmj;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;
    private static final int MAX_CLIENTS = 4;
    private int connectedClients = 0;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void startServer() {
        try {
            while (!serverSocket.isClosed() && connectedClients < MAX_CLIENTS) {
                Socket socket = serverSocket.accept();
                if (connectedClients < MAX_CLIENTS) {
                    System.out.println("New Player is connected");
                    connectedClients++;

                    ClientHandler clientHandler = new ClientHandler(socket, this);
                    Thread thread = new Thread(clientHandler);
                    thread.start();
                } else {
                    // If the maximum number of clients is reached, close the socket for this client
                    socket.close();
                    System.out.println("Connection rejected: Maximum number of clients reached.");
                }
            }
        } catch (IOException e) {
            closeServerSocket();
        }
    }

    // Method to decrement connectedClients when a client disconnects
    public synchronized void decrementConnectedClients() {
        connectedClients--;
    }

    public void closeServerSocket() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}