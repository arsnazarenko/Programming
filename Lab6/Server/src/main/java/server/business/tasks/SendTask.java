package server.business.tasks;

import library.serialization.SerializationManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.business.LetterInfo;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class SendTask implements Runnable{
    private DatagramSocket datagramSocket;
    private LetterInfo response;

    final static Logger logger = LogManager.getLogger();

    public SendTask(DatagramSocket datagramSocket, LetterInfo response) {
        this.datagramSocket = datagramSocket;
        this.response = response;
    }

    public void send() {
        byte[] bytes = SerializationManager.objectSerial(response.getTransferObject());
        try {
            datagramSocket.send(new DatagramPacket(bytes, bytes.length, response.getRemoteAddress()));
            logger.info("SERVER SENT ANSWER TO " + response.getRemoteAddress() + "\n");
        } catch (IOException e) {
            logger.error("ADDRESS ERROR", e);
        }
    }

    @Override
    public void run() {
        send();
    }
}
