package server.business.dao.Exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

    private static final Logger logger = LogManager.getLogger(MyUncaughtExceptionHandler.class.getName());

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        logger.error(e.fillInStackTrace() + "\n" + e.getCause());
    }

}
