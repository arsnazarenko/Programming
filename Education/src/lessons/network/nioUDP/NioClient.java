package lessons.network.nioUDP;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import lessons.Message;
import lessons.serialization.Serial;
import lessons.serialization.SerializationManager;
import lessons.serialization.Types;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class NioClient {
    static int serverPort = 9999;
    public static void main(String[] args) throws IOException {
        SocketAddress serverAddress = new InetSocketAddress(InetAddress.getLocalHost(), serverPort);
        try(DatagramChannel client = DatagramChannel.open()) {
            client.bind(null);
            System.out.println("КЛИЕНТ ЗАПУЩЕН...");
        /*
        не нужно связывать канал с определенным адресом, нам лишь нужен хост и порт сервера,
        чтобы отправить ему информацию
         */
            //String msg = "ЭТО СУПЕР СООБЩЕНИЕ ДЛЯ ШЕСТОЙ ЛАБЫ!!!!!!";
            SerializationManager manager = new SerializationManager();
            Serial serial = new Serial(Types.GOOD, 12, "awdawd", new Message("awdawd", 123123));
        /*
        создали байтбуффер того же размера, что и наше сообщение, поэтому если мы хотим принимать
        ответ от вервера в наш буфер, то размер сообщения изменить не должен.
         */
            ByteBuffer buffer = ByteBuffer.wrap(manager.objectSerial(serial));
            client.send(buffer, serverAddress);
            buffer.clear();
            ByteBuffer bufferForAnswer = ByteBuffer.allocate(4 * 1048);
            client.receive(bufferForAnswer);
            bufferForAnswer.flip();
            int limits = bufferForAnswer.limit();
            byte bytes[] = new byte[limits];
            bufferForAnswer.get(bytes, 0, limits);
            //String answer = new String(bytes);
            String answer = manager.objectDeserial(bytes).toString();
            System.out.println("ServerAnswer: " + answer);
            bufferForAnswer.clear();
        }
    }
}
