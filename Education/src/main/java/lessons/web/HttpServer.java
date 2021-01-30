package lessons.web;


import lessons.network.TCP.Server;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

public class HttpServer {
    private static final int LINGER_TIME = 5000;
    private File rootDir = new File("www");
    private int port = 8080;

    private static final Logger logger = Logger.getLogger(HttpServer.class.getName());


    private void serve() {
        try {
            ServerSocket listeningSocket = new ServerSocket(port);
            logger.info("Server started on port: " + port);
            while (true) {
                Socket clientSocket = listeningSocket.accept();
                clientSocket.setSoLinger(true, LINGER_TIME);
                logger.info("New connection");
                Thread handler = new Thread(new RequestHandler(clientSocket, rootDir));
                handler.setPriority(Thread.MAX_PRIORITY);
                handler.start();
            }
        } catch (IOException e) {
            System.err.println("Server failure.");
        }
    }

    public static void main(String[] args) {
        HttpServer server = new HttpServer();
        server.parseArgument(args);
        server.serve();
    }

    private void parseArgument(String[] args) {
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
            rootDir = new File(args[1]);
        }
    }

}
