package project.client.servises;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SocketChannel;

public class Receiver {
    private ByteBuffer byteBuffer;
    private DatagramChannel datagramChannel;

    public Receiver(ByteBuffer byteBuffer, DatagramChannel datagramChannel) {
        this.datagramChannel = datagramChannel;
        this.byteBuffer = byteBuffer;
    }


    public String receive() {
        String result = "";
        try {
            datagramChannel.receive(byteBuffer);
            byteBuffer.flip();
            int limits = byteBuffer.limit();
            byte bytes[] = new byte[limits];
            byteBuffer.get(bytes, 0, limits);
            byteBuffer.clear();
            result = new String(bytes);
            return result;
        } catch (IOException e) {
            System.out.println("БУФФЕР НЕ ПОДДЕРЖИВАЕТ ЗАПИСЬ");;
        }
        return result;
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
