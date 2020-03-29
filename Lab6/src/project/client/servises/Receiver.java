package project.client.servises;

import javafx.util.Pair;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Arrays;

public class Receiver {
    private ByteBuffer byteBuffer;
    private DatagramChannel datagramChannel;

    public Receiver(ByteBuffer byteBuffer, DatagramChannel datagramChannel) {
        this.datagramChannel = datagramChannel;
        this.byteBuffer = byteBuffer;
    }


    public LetterInfo receive() {
        byte[] bytes;
        try {
            SocketAddress remoteAdd = datagramChannel.receive(byteBuffer);
            byteBuffer.flip();
            int limits = byteBuffer.limit();
            bytes = new byte[limits];
            byteBuffer.get(bytes, 0, limits);
            byteBuffer.clear();
            return new LetterInfo(remoteAdd, bytes);
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

