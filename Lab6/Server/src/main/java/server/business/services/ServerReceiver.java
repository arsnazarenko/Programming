package server.business.services;

import library.serialization.SerializationManager;
import mainClass.ServerStarter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.business.LetterInfo;
import server.business.MessageSystem;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.net.SocketException;

public class ServerReceiver implements Runnable, IService{
    private MessageSystem messageSystem;
    private DatagramSocket serverSocket;
    private int length;


    private static final Logger logger = LogManager.getLogger(ServerReceiver.class.getName());
    private Thread serverReceiverThread;


    public ServerReceiver(MessageSystem messageSystem, DatagramSocket serverSocket, int length) {
        this.serverSocket = serverSocket;
        this.length = length;
        this.messageSystem = messageSystem;
    }


    @Override
    public void run() {
        logger.info("receiver is started");
        while(!Thread.currentThread().isInterrupted()) {
            try {
                byte[] buffer = new byte[length];
                DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
                serverSocket.receive(datagramPacket);
                SocketAddress remoteAddress = datagramPacket.getSocketAddress();
                ServerStarter.clientRegister(remoteAddress);
                byte[] bytes = datagramPacket.getData();
                messageSystem.putInQueues(ServerReceiver.class, new LetterInfo(remoteAddress,
                        SerializationManager.objectDeserial(bytes)));
            } catch (SocketException e) {
                logger.error("SOCKET CLOSED\n" + e);
            }catch (IOException e) {
                logger.error("RECEIVING PACKAGE ERROR\n", e);
            }
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


    public DatagramSocket getServerSocket() {
        return serverSocket;
    }

    public void setServerSocket(DatagramSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public int getLength() {
        return length;
    }





}
