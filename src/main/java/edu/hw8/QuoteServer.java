package edu.hw8;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings({"checkstyle:uncommentedMain"})
public final class QuoteServer {

    private QuoteServer() {
    }

    private static final Logger LOGGER = Logger.getLogger(QuoteServer.class.getName());

    private static final int PORT = 34522;
    private static final int MAX_CONNECTIONS = 2;

    private static ServerSocket serverSocket;
    private static ExecutorService executorService;

    public static void main(String[] args) {
        executorService = Executors.newFixedThreadPool(MAX_CONNECTIONS);

        try {
            serverSocket = new ServerSocket(PORT); // or try-with-res
            LOGGER.info("Server started. Listening on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                executorService.execute(new ClientHandler(clientSocket));
            }
        } catch (SocketException e) {
            LOGGER.info("Closed server connection...");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Exception occurred in the server", e);
        } finally {
            close();
        }
    }

    public static void close() {
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
            if (executorService != null && !executorService.isShutdown()) {
                executorService.shutdown();
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error while closing server", e);
        }
    }
}

