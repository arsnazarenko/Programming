package server.services;

import library.clientCommands.Command;
import library.serialization.SerializationManager;
import nioUDP.NioServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.net.SocketException;

public class ServerReceiver implements IServerReceiver{
    private Thread serverReceiverThread;
    private DatagramSocket serverSocket;
    private byte[] buffer;
    private int length;
    private static Logger logger = LogManager.getLogger(ServerReceiver.class.getName());

    private ServerHandler serverHandler;

    public ServerReceiver(DatagramSocket serverSocket, byte[] buffer, IHandlersController handlersController) {
        this.serverHandler = new ServerHandler(handlersController , serverSocket);
        this.serverSocket = serverSocket;
        this.buffer = buffer;
        this.length = buffer.length;
    }

    public void receive() {
        serverReceiverThread = new Thread(() -> {
            while(!Thread.currentThread().isInterrupted()) {
                try {
                    DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
                    serverSocket.receive(datagramPacket);
                    SocketAddress remoteAddress = datagramPacket.getSocketAddress();
                    NioServer.clientRegister(remoteAddress);
                    byte[] bytes = datagramPacket.getData();
                    serverHandler.executeHandleTask(new LetterInfo(remoteAddress, (Command) SerializationManager.objectDeserial(bytes)));
                    buffer = new byte[length];

                } catch (SocketException e) {
                    logger.error("SOCKET CLOSED\n" + e);
                }catch (IOException e) {
                    logger.error("RECEIVING PACKAGE ERROR\n", e);
                }
            }
        }, "receive_tread");
        serverReceiverThread.start();
    }

    public Thread getServerReceiverThread() {
        return serverReceiverThread;
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

    public int getLength() {
        return length;
    }
}
