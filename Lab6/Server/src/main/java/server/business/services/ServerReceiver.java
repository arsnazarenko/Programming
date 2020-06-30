package server.business.services;

import library.clientCommands.SpecialSignals;
import library.serialization.SerializationManager;
import mainClass.ServerStarter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.postgresql.largeobject.BlobOutputStream;
import server.business.LetterInfo;
import server.business.MessageSystem;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.sql.Connection;
import java.sql.SQLException;

public class ServerReceiver implements Runnable, IService {
    private MessageSystem messageSystem;
    private int length;
    private final Connection connection;
    private volatile DatagramSocket datagramSocket;
    private final SocketAddress socketAddress;


    private static final Logger logger = LogManager.getLogger(ServerReceiver.class.getName());
    private Thread serverReceiverThread;

    public ServerReceiver(MessageSystem messageSystem, int length, Connection connection, SocketAddress socketAddress) {
        this.messageSystem = messageSystem;
        this.length = length;
        this.connection = connection;
        this.socketAddress = socketAddress;
    }

    public DatagramSocket openConnection() throws SocketException {
        DatagramSocket datagramSocket = new DatagramSocket(socketAddress);
        this.datagramSocket = datagramSocket;
        return datagramSocket;
    }

    @Override
    public void run() {
        try {
            logger.info("SERVER STARTED: " + socketAddress);
            logger.info("Receiver is started");
            while (!Thread.currentThread().isInterrupted()) {
                byte[] buffer = new byte[length];
                DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
                datagramSocket.receive(datagramPacket);
                if (!connection.isValid(1)) {
                    //отослать сигнал неисправости клиенту
                    messageSystem.putInQueues(ServerHandler.class, new LetterInfo(datagramPacket.getSocketAddress(), SpecialSignals.SERVER_DIED));
                    logger.info("NO CONNECTION WITH DATABASE");
                } else {
                    SocketAddress remoteAddress = datagramPacket.getSocketAddress();
                    messageSystem.addNewClient(remoteAddress);
                    byte[] bytes = datagramPacket.getData();
                    messageSystem.putInQueues(ServerReceiver.class, new LetterInfo(remoteAddress,
                            SerializationManager.objectDeserial(bytes)));
                }

            }
        } catch (IOException e) {
            logger.error("SERVER CONNECTION ERROR"); // если при созданни соединение было а затем случилось ошибка, этот поток завершится, остальные сервисы завершатся автоматически
            logger.info("SAMPLE: java -jar Server.jar [host] [port] [DB host] [DB port] [DB name] [DB user] [DB password] ");
        } catch (SQLException e) {
            logger.error("NO CONNECTION WITH DATABASE");
        } finally {
            datagramSocket.close();
        }

    }


    public void start() {
        this.serverReceiverThread = new Thread(this, "received_thread");
        serverReceiverThread.start();
    }

    public void stop() {
        this.serverReceiverThread.interrupt();
        logger.info("Receiver is interrupt");
    }


    public int getLength() {
        return length;
    }


}
