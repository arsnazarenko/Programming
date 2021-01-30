package client;

import client.servises.ArgumentValidateManager;
import client.servises.MessageService;
import frontend.ClientManager;
import frontend.graphicsInterface.Menu;
import frontend.graphicsInterface.controllers.Controllers;
import frontend.graphicsInterface.loginForm.LogInWindow;

import java.util.Locale;

public class FrontendInit {
    private ClientManager clientManager;
    private final Locale DEFAULT_LOCALE = new Locale("ru");
    private final String FONT = "Century Gothic";
    private Menu menu;
    private LogInWindow logInWindow;
    private Controllers controllers;


    public FrontendInit(MessageService messageService, ArgumentValidateManager argumentValidator) {

        menu = new Menu(DEFAULT_LOCALE, FONT);
        logInWindow = new LogInWindow(FONT,DEFAULT_LOCALE);
        clientManager = new ClientManager(messageService, argumentValidator);
        controllers = new Controllers(logInWindow,clientManager,menu,DEFAULT_LOCALE);
        controllers.setLogListeners();
        controllers.setMenuListeners();
        controllers.setWindowListener();
        logInWindow.setJMenuBar(menu);
        logInWindow.setVisible(true);
    }

    public synchronized Controllers getControllers() {
        return controllers;
    }
}
