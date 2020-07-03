package graphicsInterface.controllers;


import frontend.mvc.ObjectsMapController;
import graphicsInterface.ClientManager;
import graphicsInterface.ErrorConstants;
import graphicsInterface.LocaleActionListener;
import graphicsInterface.Menu;
import graphicsInterface.loginForm.LogInWindow;
import graphicsInterface.mainWindow.MainWindow;
import graphicsInterface.mainWindow.commands.CommandPanel;
import graphicsInterface.mainWindow.table.TablePanel;
import library.clientCommands.SpecialSignals;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Controllers {
    private LogInWindow logInWindow;
    private MainWindow mainWindow;
    private ClientManager clientManager;
    private Menu menu;
    private Locale locale;

    private LogInController logInController;
    private CommandsController commandsController;
    private WindowController windowController;
    private ErrorConstants errorConstants;
    private List<LocaleActionListener> listeners = new ArrayList<>();

    public Controllers(LogInWindow logInWindow, ClientManager clientManager, Menu menu, ErrorConstants errorConstants, Locale locale) {
        this.logInWindow = logInWindow;
        this.clientManager = clientManager;
        this.menu = menu;
        this.errorConstants = errorConstants;
        this.locale = locale;
        logInController = new LogInController(logInWindow,clientManager);
        commandsController = new CommandsController(clientManager, errorConstants,locale);
        windowController = new WindowController(locale);
        listeners.add(windowController);
        listeners.add(commandsController);
        listeners.add(menu);
        listeners.add(logInWindow);
        listeners.add(logInWindow.getAuthorizationPanel());
        listeners.add(logInWindow.getRegistrationPanel());
        listeners.add(errorConstants);
    }

    public void setLogListeners(){
        LogInWindow.Buttons buttons = logInWindow.getButtons();
        buttons.getButtonLog().addActionListener(a -> {
            logInController.addLogButtonListener(locale, errorConstants);

        });
        buttons.getButtonSignUp().addActionListener(a -> logInController.addSignButtonListener(true));
        buttons.getButtonSignIn().addActionListener(a -> logInController.addSignButtonListener(false));
        buttons.getButtonReg().addActionListener(a-> {
            logInController.addRegButtonListener(locale, errorConstants);
        });
    }

    public void logResponseHandle(SpecialSignals signal) {
        mainWindow = logInController.logResponseHandle(signal);
        if (mainWindow!=null)  setMainWindow();
    }

    public void regResponseHandle(SpecialSignals signal) {
        mainWindow = logInController.regResponseHandle(signal);
        if(mainWindow!= null) setMainWindow();
    }

    public void commandsResponseHandle(Object response) {
        commandsController.handle(response);
    }

    private void setMainWindow(){
        listeners.add(mainWindow.getCommandPanel());
        listeners.add(mainWindow.getTablePanel());
        listeners.add(mainWindow.getUserPanel());
        listeners.add(mainWindow);
        mainWindow.setJMenuBar(logInWindow.getJMenuBar());
        commandsController.setMainWindow(mainWindow);
        setCommandsListeners();
        setTableListeners();
        mainWindow.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                windowController.confirm(e);
            }
        });
        mainWindow.setVisible(true);
    }

    private void setCommandsListeners(){
        CommandPanel.Buttons buttons = mainWindow.getCommandPanel().getButtons();
        buttons.getShowButton().addActionListener(a-> commandsController.show());
        buttons.getClearButton().addActionListener(a-> commandsController.clear());
        buttons.getFilterNameButton().addActionListener(a-> commandsController.filter());
        buttons.getHeadButton().addActionListener(a-> commandsController.head());
        buttons.getMaxEmplButton().addActionListener(a-> commandsController.max());
        buttons.getPrintAscendButton().addActionListener(a-> commandsController.print());
        buttons.getRemoveIdButton().addActionListener(a-> commandsController.removeId(commandsController.validateId()));
        buttons.getInfoButton().addActionListener(a-> commandsController.info());
    }

    private void setTableListeners(){
        TablePanel.ButtonsPanel buttonsPanel = mainWindow.getTablePanel().getButtonsPanel();
        JTable table = mainWindow.getTablePanel().getTable();
        buttonsPanel.getRemoveButton().addActionListener(a-> commandsController.removeId((Long)table.getValueAt(table.getSelectedRow(),0)));

    }

    public void setMenuListeners(){
        menu.getRussian().addActionListener(a->{
            locale = new Locale("ru");
             setLanguage(locale);
        });
        menu.getSpanish().addActionListener(a->{
            locale = new Locale("es_GT");
           setLanguage(locale);
        });
        menu.getPolish().addActionListener(a->{
            locale = new Locale("pl");
             setLanguage(locale);
        });
        menu.getSlovak().addActionListener(a->{
            locale = new Locale("sk");
            setLanguage(locale);
        });

    }

    public void setWindowListener(){
        logInWindow.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                windowController.confirm(e);
            }
        });
    }

    private void setLanguage(Locale locale){
        listeners.forEach(o->o.localeChange(locale));
    }
}
