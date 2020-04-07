package project.server.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import project.client.serialization.ISerializationManager;
import project.client.serialization.SerializationManager;

import java.io.IOException;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class ServerSender implements IServerSender{
    private ISerializationManager serializationManager;
    private DatagramSocket datagramSocket;
    final static Logger logger = LogManager.getLogger(ServerSender.class.getName());

    public ServerSender(DatagramSocket datagramSocket, ISerializationManager serializationManager) {
        this.datagramSocket = datagramSocket;
        this.serializationManager = serializationManager;
    }

    public void send(Object letter, SocketAddress address) {
        byte[] bytes = serializationManager.objectSerial(letter);
        try {
            datagramSocket.send(new DatagramPacket(bytes, bytes.length, address));
            logger.info("SERVER SENT ANSWER TO " + address + "\n");
        } catch (IOException e) {
            logger.error("ADDRESS ERROR", e);
        }
    }

    public ISerializationManager getSerializationManager() {
        return serializationManager;
    }

    public void setSerializationManager(ISerializationManager serializationManager) {
        this.serializationManager = serializationManager;
    }
}
