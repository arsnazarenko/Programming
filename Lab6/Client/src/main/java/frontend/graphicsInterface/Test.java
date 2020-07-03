package frontend.graphicsInterface;

import client.servises.*;
import frontend.ClientManager;
import frontend.graphicsInterface.controllers.Controllers;
import frontend.graphicsInterface.loginForm.LogInWindow;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.UnresolvedAddressException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Test {
    public static void main(String[] args) {
        try {
//            DatagramChannel datagramChannel = DatagramChannel.open();
//            int port = Integer.parseInt(args[1]);
//            SocketAddress socketAddress = new InetSocketAddress(args[0], port);
//            datagramChannel.connect(socketAddress);
//            ClientReader clientReader = new ClientReader(datagramChannel, ByteBuffer.allocate(256 * 1024));
//            ClientWriter clientWriter = new ClientWriter(socketAddress, datagramChannel);
//            ValidateManager validateManager = new ValidateManager();
//
            //ClientManager clientManager = new ClientManager(clientReader, clientWriter, validateManager);
//
//            final Locale DEFAULT_LOCALE = new Locale("ru");
//            final String FONT = "Century Gothic";
//            Menu menu = new Menu(DEFAULT_LOCALE, FONT);
//
//            LogInWindow logInWindow = new LogInWindow(FONT,DEFAULT_LOCALE);
//            Controllers controllers = new Controllers(logInWindow,clientManager,menu,DEFAULT_LOCALE);
//            controllers.setLogListeners();
//            controllers.setMenuListeners();
//            controllers.setWindowListener();
//            logInWindow.setJMenuBar(menu);
//            logInWindow.setVisible(true);


//        } catch (IOException e) {
//            System.out.println("ОШИБКА ПОДКЛЮЧЕНИЯ К СЕРВЕРУ");
        } catch (NoSuchElementException e) {
            System.out.println("ЭКСТРЕННОЕ ЗАВЕРШЕНИЕ");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("УКАЖИТЕ ХОСТ И ПОРТ СЕРВЕРА\nSAMPLE: java -jar Client.jar [hostname] [port]");
        } catch (NumberFormatException e) {
            System.out.println("НЕКОРРЕКТНЫЙ ПОРТ\nSAMPLE: java -jar Client.jar [hostname] [port]");
        } catch (UnresolvedAddressException e) {
            System.out.println("НЕКОРРЕКТНЫЙ ХОСТ\nSAMPLE: java -jar Client.jar [hostname] [port]");
        } catch (IllegalArgumentException e) {
            System.out.println("НЕКОРРЕКТНЫЙ ВВОД\nSAMPLE: java -jar Client.jar [hostname] [port]");
        }
    }
}