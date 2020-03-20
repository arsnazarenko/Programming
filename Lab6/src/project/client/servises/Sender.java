package project.client.servises;

import project.client.commands.Command;
import project.client.serialization.SerializationManager;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class Sender {
    private SerializationManager serializationManager;
    private DatagramChannel datagramChannel;
    private SocketAddress address;

    public Sender(DatagramChannel datagramChannel, SocketAddress address, SerializationManager serializationManager) {
        this.datagramChannel = datagramChannel;
        this.address = address;
        this.serializationManager = serializationManager;
    }

    public void send(Command command) {
        ByteBuffer buffer = ByteBuffer.wrap(serializationManager.objectSerial(command));
        try {
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

    public SocketAddress getAddress() {
        return address;
    }

    public void setAddress(SocketAddress address) {
        this.address = address;
    }
}
