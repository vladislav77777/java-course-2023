package edu.hw8;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.logging.Logger;

class ClientHandler implements Runnable {
    private static final Logger LOGGER = Logger.getLogger(QuoteServer.class.getName());

    private static final String[] KEYWORDS = {"личности", "оскорбления", "глупый", "интеллект"};
    private static final String[] REPLIES = {
        "Не переходи на личности там, где их нет",
        "Если твои противники перешли на личные оскорбления, будь уверен — твоя победа не за горами",
        "А я тебе говорил, что ты глупый? Так вот, я забираю свои слова обратно... Ты просто бог идиотизма.",
        "Чем ниже интеллект, тем громче оскорбления"
    };

    private final Socket clientSocket;

    ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try (
            DataInputStream reader = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream())
        ) {
            while (true) {
                String clientMessage = reader.readUTF();  // read a message from the client
                String reply = getReply(clientMessage);
                output.writeUTF(reply);  // send reply to the client
            }
        } catch (Exception e) {
//            LOGGER.log(Level.SEVERE, "Exception occurred in the ClientHandler", e);
            QuoteServer.close();
        }
    }

    private String getReply(String clientMessage) {
        for (int i = 0; i < KEYWORDS.length; i++) {
            if (clientMessage.contains(KEYWORDS[i])) {
                return REPLIES[i];
            }
        }
        return "Не понял запроса";
    }
}
