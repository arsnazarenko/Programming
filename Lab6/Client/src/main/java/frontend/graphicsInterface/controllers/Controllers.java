package frontend.graphicsInterface.controllers;



import client.servises.ObjectDataValidatorConsole;
import frontend.ClientManager;
import frontend.graphicsInterface.LocaleActionListener;
import frontend.graphicsInterface.Menu;
import frontend.graphicsInterface.loginForm.LogInWindow;
import frontend.graphicsInterface.mainWindow.MainWindow;
import frontend.graphicsInterface.mainWindow.commands.CommandPanel;
import frontend.graphicsInterface.mainWindow.table.TablePanel;
import frontend.mvc.*;
import library.clientCommands.SpecialSignals;


import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Controllers {
    private LogInWindow logInWindow;
    private MainWindow mainWindow;
    private Menu menu;
    private static Locale locale;

    public static Locale getLocale() {
        return locale;
    }

    private LogInController logInController;
    private CommandsController commandsController;
    private WindowController windowController;
    private List<LocaleActionListener> listeners = new ArrayList<>();
    private OrganizationController organizationCreateController;
    private ObjectsMapController mapController;

    public Controllers(LogInWindow logInWindow, ClientManager clientManager, Menu menu, Locale locale) {
        this.logInWindow = logInWindow;
        this.menu = menu;
        this.locale = locale;
        organizationCreateController = new OrganizationController(new OrganizationView(), new ObjectCreatorUI(new ObjectDataValidatorConsole()), clientManager);
        ObjectsMapModel mapModel = new ObjectsMapModel(new ArrayDeque<>());
        mapController = new ObjectsMapController(new ObjectsMapView(mapModel.getOrganizationsCoordinateInfo(), mapModel.getCellSize(), mapModel.getCellCount()), mapModel);
        logInController = new LogInController(logInWindow,clientManager, mapController.getView());
        commandsController = new CommandsController(clientManager, locale, organizationCreateController, mapController);
        windowController = new WindowController(locale);
        listeners.add(windowController);
        listeners.add(commandsController);
        listeners.add(menu);
        listeners.add(logInWindow);
        listeners.add(logInWindow.getAuthorizationPanel());
        listeners.add(logInWindow.getRegistrationPanel());
    }

    public void setLogListeners(){
        LogInWindow.Buttons buttons = logInWindow.getButtons();
        buttons.getButtonLog().addActionListener(a -> {
            logInController.addLogButtonListener();

        });
        buttons.getButtonSignUp().addActionListener(a -> logInController.addSignButtonListener(true));
        buttons.getButtonSignIn().addActionListener(a -> logInController.addSignButtonListener(false));
        buttons.getButtonReg().addActionListener(a-> {
            logInController.addRegButtonListener();

        });
    }

    public void logResponseHandle(SpecialSignals signal) {
        mainWindow = logInController.logResponseHandle(signal);
        if (mainWindow!=null)  setMainWindow();
    }

    public void regResponseHandle(SpecialSignals signal) {
        mainWindow = logInController.regResponseHandle(signal);
        if (mainWindow!=null)  setMainWindow();
    }

    public void commandResponseHandle(Object response) {
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
        buttons.getAddButton().addActionListener(a-> commandsController.add());
        buttons.getAddIfMinButton().addActionListener(a-> commandsController.addIfMin());
        buttons.getRemoveLowerButton().addActionListener(a -> commandsController.removeLower());
        buttons.getUpdateIdButton().addActionListener(a -> commandsController.updateId(commandsController.validateId()));
        buttons.getExecuteScriptButton().addActionListener(a -> commandsController.executeScript());
    }

    private void setTableListeners(){
        TablePanel.ButtonsPanel buttonsPanel = mainWindow.getTablePanel().getButtonsPanel();
        JTable table = mainWindow.getTablePanel().getTable();
        buttonsPanel.getRemoveButton().addActionListener(a-> commandsController.removeId(Long.parseLong((String)table.getValueAt(table.getSelectedRow(),0))));

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
