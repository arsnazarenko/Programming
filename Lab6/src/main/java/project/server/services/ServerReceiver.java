package project.server.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.util.JsonUtils;
import project.client.commands.Command;
import project.client.serialization.ISerializationManager;
import project.client.serialization.SerializationManager;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;

public class ServerReceiver implements IServerReceiver{
    private DatagramSocket serverSocket;
    private byte[] buffer;
    private int length;
    private ISerializationManager serializationManager;
    static Logger logger = LogManager.getLogger(ServerReceiver.class.getName());

    public ServerReceiver(DatagramSocket serverSocket, byte[] buffer, ISerializationManager serializationManager) {
        this.serverSocket = serverSocket;
        this.buffer = buffer;
        this.serializationManager = serializationManager;
        this.length = buffer.length;
    }

    public LetterInfo receive() {
        DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
        try {
            serverSocket.receive(datagramPacket);
            SocketAddress remoteAddress = datagramPacket.getSocketAddress();
            byte[] bytes = datagramPacket.getData();
            byte[] letter = new byte[datagramPacket.getLength()];
            for(int i = 0; i < letter.length; i++) {
                letter[i] = bytes[i];
            }
            buffer = new byte[length];
            return new LetterInfo(remoteAddress, (Command) serializationManager.objectDeserial(letter));
        } catch (IOException e) {
                logger.error("ОШИБКА ПАКЕТА ДЛЯ ПРИЕМА", e);
            return null;
        }
    }

    public DatagramSocket getServerSocket() {
        return serverSocket;
    }

    public void setServerSocket(DatagramSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public byte[] getBuffer() {
        return buffer;
    }

    public void setBuffer(byte[] buffer) {
        this.buffer = buffer;
    }

    public ISerializationManager getSerializationManager() {
        return serializationManager;
    }

    public void setSerializationManager(ISerializationManager serializationManager) {
        this.serializationManager = serializationManager;
    }

    public int getLength() {
        return length;
    }
}
