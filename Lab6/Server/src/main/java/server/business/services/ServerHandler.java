package server.business.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.business.IHandlersController;
import server.business.LetterInfo;
import server.business.MessageSystem;
import server.business.dao.Exception.MyUncaughtExceptionHandler;
import server.business.tasks.HandleTask;

import java.util.concurrent.ForkJoinPool;

public class ServerHandler implements Runnable, IService{
    private IHandlersController handlersController;
    private final ForkJoinPool handlePool = new ForkJoinPool(Runtime.getRuntime().availableProcessors(),
            ForkJoinPool.defaultForkJoinWorkerThreadFactory, new MyUncaughtExceptionHandler(), false);
    private Thread handlerThread;
    private static final Logger logger = LogManager.getLogger(ServerHandler.class);
    private MessageSystem messageSystem;

    public ServerHandler(IHandlersController handlersController, MessageSystem messageSystem) {
        this.handlersController = handlersController;
        this.messageSystem = messageSystem;
    }

    public void start() {
        handlerThread = new Thread(this, "handler_thread");
        handlerThread.start();
    }

    public void stop() {
        handlePool.shutdown();
        handlerThread.interrupt();
        logger.info("Handler is interrupted");

    }


    @Override
    public void run() {
        logger.info("Handler is started");
        try {
            while (!Thread.currentThread().isInterrupted()) {
                LetterInfo request = messageSystem.getFromQueues(ServerHandler.class);
                handlePool.execute(new HandleTask(request, handlersController, messageSystem));
            }
        } catch (InterruptedException e) {
            logger.error("Handler is interrupted");
        }
    }
}
