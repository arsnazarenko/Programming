package project.client.servises;

import project.client.commands.Command;
import project.client.serialization.SerializationManager;

import java.io.IOException;
import java.io.Serializable;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class Sender {
    private SerializationManager serializationManager;
    private DatagramChannel datagramChannel;

    public Sender(DatagramChannel datagramChannel, SerializationManager serializationManager) {
        this.datagramChannel = datagramChannel;

        this.serializationManager = serializationManager;
    }

    public<T extends Serializable> void send(T letter, SocketAddress address) {
        try {
            ByteBuffer buffer = ByteBuffer.wrap(serializationManager.objectSerial(letter));
            datagramChannel.send(buffer, address);
            buffer.clear();
        } catch (IOException e) {
            System.out.println("ОШИБКА АДРЕСА");
        }

    }

    public DatagramChannel getDatagramChannel() {
        return datagramChannel;
    }

    public void setDatagramChannel(DatagramChannel datagramChannel) {
        this.datagramChannel = datagramChannel;
    }
}
