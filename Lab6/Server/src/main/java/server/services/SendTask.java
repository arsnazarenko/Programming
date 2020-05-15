package server.services;

import library.serialization.SerializationManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;

public class SendTask implements Runnable{
    private DatagramSocket datagramSocket;
    final static Logger logger = LogManager.getLogger();
    private Object letter;
    private SocketAddress remoteAddress;

    public SendTask(DatagramSocket datagramSocket, Object letter, SocketAddress remoteAddress) {
        this.datagramSocket = datagramSocket;
        this.letter = letter;
        this.remoteAddress = remoteAddress;
    }

    public void send() {
        byte[] bytes = SerializationManager.objectSerial(letter);
        try {
            datagramSocket.send(new DatagramPacket(bytes, bytes.length, remoteAddress));
            logger.info("SERVER SENT ANSWER TO " + remoteAddress + "\n");
        } catch (IOException e) {
            logger.error("ADDRESS ERROR", e);
        }
    }




    @Override
    public void run() {
        send();
    }
}
