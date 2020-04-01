package project.client.servises;

import javafx.util.Pair;
import project.client.serialization.SerializationManager;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Arrays;

public class ClientReceiver implements IClientReceiver{
    private ByteBuffer byteBuffer;
    private DatagramChannel datagramChannel;
    private SerializationManager serializationManager;

    public ClientReceiver(ByteBuffer byteBuffer, DatagramChannel datagramChannel, SerializationManager serializationManager) {
        this.datagramChannel = datagramChannel;
        this.byteBuffer = byteBuffer;
        this.serializationManager = serializationManager;
    }


    public String receive() {
        byte[] bytes;
        try {
            datagramChannel.receive(byteBuffer);
            byteBuffer.flip();
            int limits = byteBuffer.limit();
            bytes = new byte[limits];
            byteBuffer.get(bytes, 0, limits);
            byteBuffer.clear();
            return (String) serializationManager.objectDeserial(bytes);
        } catch (IOException e) {
            System.out.println("БУФФЕР НЕ ПОДДЕРЖИВАЕТ ЗАПИСЬ");;
        }
        return null;
    }

    public ByteBuffer getByteBuffer() {
        return byteBuffer;
    }

    public void setByteBuffer(ByteBuffer byteBuffer) {
        this.byteBuffer = byteBuffer;
    }

    public DatagramChannel getDatagramChannel() {
        return datagramChannel;
    }

    public void setDatagramChannel(DatagramChannel datagramChannel) {
        this.datagramChannel = datagramChannel;
    }

}

