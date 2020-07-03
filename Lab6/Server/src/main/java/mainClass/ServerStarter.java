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
import java.net.*;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ServerStarter {

    private final static Logger logger = LogManager.getLogger(ServerStarter.class.getName());

    public static void run(SocketAddress serverAddress, String host, String port,
                           String dataBaseName, String user, String password) throws SQLException, ClassNotFoundException, SocketException {

        DatabaseCreator.init(host, port, dataBaseName, user, password);
        ObjectDAO<Organization, Long> orgDao = new OrganizationDAO(DatabaseCreator.getConnection());
        UserDAO<UserData, String> userDAO = new UserDaoImpl(DatabaseCreator.getConnection());
        CollectionManager collectionManager = new CollectionManager();
        collectionManager.setOrgCollection(orgDao.readAll());
        IHandlersController handlersManager = new HandlersController(collectionManager, orgDao, userDAO);
        Map<Class<?>, BlockingQueue<LetterInfo>> queues = new HashMap<>();
        queues.put(ServerHandler.class, new ArrayBlockingQueue<>(35));
        queues.put(ServerSender.class, new ArrayBlockingQueue<>(35));

        MessageSystem messageSystem = new MessageSystem(queues);

        IService[] services = new IService[3];
        ServerReceiver receiver = new ServerReceiver(messageSystem, 256 * 1024, DatabaseCreator.getConnection(), serverAddress);
        DatagramSocket serverSocket = receiver.openConnection(); // открываем соединение, при ошибке сервер закроется
        services[0] = receiver;
        services[1] = new ServerHandler(handlersManager, messageSystem);
        services[2] = new ServerSender(serverSocket, messageSystem);

        for (IService service : services) {
            service.start();
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
            host = args[0].trim();
            port = Integer.parseInt(args[1].trim());
            dbHost = args[2].trim();
            dbPort = args[3].trim();
            dataBaseName = args[4].trim();
            dbUser = args[5].trim();
            dbPassword = args[6].trim();
            SocketAddress socketAddress = new InetSocketAddress(host, port);
            run(socketAddress, dbHost, dbPort, dataBaseName, dbUser, dbPassword);
        } catch (SQLException e) {
            logger.error("Error connecting to database");
            logger.info("Establish the correct database connection\nSAMPLE: java -jar Server.jar [host] [port] [DB host] [DB port] [DB name] [DB user] [DB password]");
        } catch (ClassNotFoundException e) {
            logger.error("DB driver Class load error");
        } catch (NumberFormatException e) {
            logger.error("INVALID PORT SPECIFIED");
            logger.info("SAMPLE: java -jar Server.jar [host] [port] [DB host] [DB port] [DB name] [DB user] [DB password] ");
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            logger.error("INVALID PARAMETERS");
            logger.info("SAMPLE: java -jar Server.jar [host] [port] [DB host] [DB port] [DB name] [DB user] [DB password] ");
        } catch (SocketException e) {
            logger.error("SERVER STARTING ERROR");
            logger.info("SAMPLE: java -jar Server.jar [host] [port] [DB host] [DB port] [DB name] [DB user] [DB password] ");

        }

    }

}
