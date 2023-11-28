package edu.hw8;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings({"checkstyle:uncommentedMain"})
public final class QuoteClient {

    private QuoteClient() {
    }

    private static final Logger LOGGER = Logger.getLogger(QuoteClient.class.getName());

    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 34522;

    public static void main(String[] args) {
        try (
            Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            DataInputStream reader = new DataInputStream(socket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            Scanner scanner = new Scanner(System.in)
        ) {
            LOGGER.info("Client started. Send messages to the port " + SERVER_PORT);

            while (true) {
                LOGGER.info("Ваня: ");
                String input = scanner.nextLine();

                outputStream.writeUTF(input);  // send a message to the server

                String serverResponse = reader.readUTF(); // read the reply from the server
                LOGGER.info("Сервер: " + serverResponse);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Exception occurred in the client", e);

        }
    }
}
