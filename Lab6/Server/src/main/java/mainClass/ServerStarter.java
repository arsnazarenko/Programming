package mainClass;

import library.clientCommands.UserData;
import library.сlassModel.Organization;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.business.*;
import server.business.dao.*;
import server.business.services.IService;
import server.business.services.ServerHandler;
import server.business.services.ServerReceiver;
import server.business.services.ServerSender;

import java.io.IOException;
import java.net.*;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ServerStarter {

    private final static Logger logger = LogManager.getLogger(ServerStarter.class.getName());
    public static void run(SocketAddress serverAddress, String host, String port, String dataBaseName, String user, String password) throws IOException, SQLException, ClassNotFoundException {
        DatabaseCreator.init(host, port, dataBaseName, user, password);
        ObjectDAO<Organization, Long> orgDao = new OrganizationDAO(DatabaseCreator.getConnection());
        UserDAO<UserData, String> userDAO = new UserDaoImpl(DatabaseCreator.getConnection());
        CollectionManager collectionManager = new CollectionManager();
        collectionManager.setOrgCollection(orgDao.readAll());
        IHandlersController handlersManager = new HandlersController(collectionManager, orgDao, userDAO);
        Scanner scanner = new Scanner(System.in);

        Map<Class<?>, BlockingQueue<LetterInfo>> queues = new HashMap<>();
        queues.put(ServerHandler.class, new ArrayBlockingQueue<>(15));
        queues.put(ServerSender.class, new ArrayBlockingQueue<>(15));


        MessageSystem messageSystem = new MessageSystem(queues);

        logger.info("SERVER STARTED: " + serverAddress);
        System.out.println(DatabaseCreator.getConnection().isValid(1));
        try (DatagramSocket serverSocket = new DatagramSocket(serverAddress)) {

            IService[] services = new IService[3];

            services[0] = new ServerReceiver(messageSystem, serverSocket, 12 * 1024);
            services[1] = new ServerHandler(handlersManager, messageSystem);
            services[2] = new ServerSender(serverSocket, messageSystem);

            for (IService service : services) {
                service.start();
            }

            while (true) {
                if (scanner.nextLine().equals("stop")) {
                    for (IService service : services) {
                        service.stop();
                    }
                    DatabaseCreator.closeConnection();
                    scanner.close();
                    break;
                }
            }
        }
    }
    public static void main(String[] args) {
        final String host;
        final int port;
        final String dbHost;
        final String dbPort;
        final String dataBaseName;
        final String dbUser;
        final String dbPassword;
        try {
            if(args.length == 5) {
                host = "127.0.0.1";
                dbHost = "127.0.0.1";
                port = Integer.parseInt(args[0].trim());
                dbPort = args[1].trim();
                dataBaseName = args[2].trim();
                dbUser = args[3].trim();
                dbPassword = args[4].trim();
            } else {
                host = args[0].trim();
                port = Integer.parseInt(args[1].trim());
                dbHost = args[2].trim();
                dbPort = args[3].trim();
                dataBaseName = args[4].trim();
                dbUser = args[5].trim();
                dbPassword = args[6].trim();
            }
            SocketAddress socketAddress = new InetSocketAddress(host, port);
            run(socketAddress, dbHost, dbPort, dataBaseName, dbUser, dbPassword);
        } catch (SQLException e) {
            logger.error("Error connecting to database");
            logger.info("Establish the correct database connection");
        }catch(ClassNotFoundException e){
            logger.error("Class load error");
        } catch (IOException e) {
            logger.error("SERVER STARTING ERROR");
            logger.info("SAMPLE: java -jar Server.jar [host] [port] ");
        } catch (NumberFormatException e) {
            logger.error("INVALID PORT SPECIFIED");
            logger.info("SAMPLE: java -jar Server.jar [host] [port] ");
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            logger.error("INVALID PARAMETERS");
            logger.info("SAMPLE: java -jar Server.jar [host] [port] ");
        }

    }

}
