package server.business.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.business.LetterInfo;
import server.business.MessageSystem;
import server.business.dao.Exception.MyUncaughtExceptionHandler;
import server.business.tasks.SendTask;

import java.net.DatagramSocket;
import java.util.concurrent.ForkJoinPool;

public class ServerSender implements Runnable, IService {
    private final DatagramSocket serverSocket;
    private final MessageSystem messageSystem;

    private final ForkJoinPool senderPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors(),
            ForkJoinPool.defaultForkJoinWorkerThreadFactory, new MyUncaughtExceptionHandler(), false);
    private static final Logger logger = LogManager.getLogger(ServerSender.class);
    private Thread senderThread;

    public ServerSender(DatagramSocket serverSocket, MessageSystem messageSystem) {
        this.serverSocket = serverSocket;
        this.messageSystem = messageSystem;
    }


    public void start() {
        senderThread = new Thread(this, "sender_thread");
        senderThread.start();
    }

    public void stop() {
        senderPool.shutdown();
        senderThread.interrupt();
        logger.info("Sender is interrupted");
    }

    @Override
    public void run() {
        logger.info("Sender is started");
        try {
            while (!Thread.currentThread().isInterrupted()) {
                LetterInfo response = messageSystem.getFromQueues(ServerSender.class);
                senderPool.execute(new SendTask(serverSocket, response));

            }
        } catch (InterruptedException e) {
            logger.error("Sender is interrupted");
        }
    }
}
