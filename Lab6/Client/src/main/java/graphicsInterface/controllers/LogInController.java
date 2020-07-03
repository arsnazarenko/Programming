package graphicsInterface.controllers;

import graphicsInterface.ClientManager;
import graphicsInterface.ErrorConstants;
import graphicsInterface.loginForm.AuthorizationPanel;
import graphicsInterface.loginForm.LogInWindow;
import graphicsInterface.loginForm.RegistrationPanel;
import graphicsInterface.mainWindow.MainWindow;
import graphicsInterface.mainWindow.UserPanel;
import graphicsInterface.mainWindow.commands.CommandPanel;
import graphicsInterface.mainWindow.table.TablePanel;
import library.clientCommands.SpecialSignals;
import library.clientCommands.UserData;
import library.clientCommands.commandType.LogCommand;
import library.clientCommands.commandType.RegCommand;

import java.util.Locale;

public class LogInController {
    private LogInWindow logInWindow;
    private ClientManager clientManager;
    private AuthorizationPanel authorizationPanel;
    private RegistrationPanel registrationPanel;
    private LogInWindow.Buttons buttons;

    private UserData sessionUserData;

    private ErrorConstants errorConstants;
    private Locale locale;


    public LogInController(LogInWindow logInWindow, ClientManager clientManager) {
        this.logInWindow = logInWindow;
        this.clientManager = clientManager;
        authorizationPanel = logInWindow.getAuthorizationPanel();
        registrationPanel = logInWindow.getRegistrationPanel();
        buttons = logInWindow.getButtons();
    }

    public void addSignButtonListener(boolean flag) {
        authorizationPanel.getLabelModel().getLabelTitle().setVisible(!flag);
        buttons.getButtonLog().setVisible(!flag);
        buttons.getButtonSignUp().setVisible(!flag);

        registrationPanel.getLabelModel().getLabelTitle().setVisible(flag);
        registrationPanel.getLabelModel().getLabelRestatePassword().setVisible(flag);
        registrationPanel.getFieldModel().getPasswordRestateField().setVisible(flag);
        buttons.getButtonSignIn().setVisible(flag);
        buttons.getButtonReg().setVisible(flag);

        registrationPanel.getLabelModel().getLabelErrorLogin().setVisible(false);
        registrationPanel.getLabelModel().getLabelErrorPassword().setVisible(false);
        authorizationPanel.getLabelModel().getLabelError().setVisible(false);
    }

    public void addLogButtonListener(Locale locale, ErrorConstants errorConstants) {
        String login = authorizationPanel.getFieldModel().getLoginField().getText();
        String password = new String(authorizationPanel.getFieldModel().getPasswordField().getPassword());
        UserData userData = new UserData(login, password);
        sessionUserData = userData;
        this.locale = locale;
        this.errorConstants = errorConstants;
        clientManager.executeCommand(new LogCommand(userData));

    }

    public MainWindow logResponseHandle(SpecialSignals signals) {
        boolean res = clientManager.handlerAuth(signals);
        System.out.println(res);
        if (res) {
            logInWindow.dispose();
            return createMain(sessionUserData,locale, errorConstants);
        } else {
            authorizationPanel.getLabelModel().getLabelError().setVisible(true);
        }
        return null;
    }

    public void addRegButtonListener(Locale locale, ErrorConstants errorConstants) {
        String login = authorizationPanel.getFieldModel().getLoginField().getText();
        String password = new String(authorizationPanel.getFieldModel().getPasswordField().getPassword());
        String restatePassword = new String(registrationPanel.getFieldModel().getPasswordRestateField().getPassword());
        if (password.equals(restatePassword)) {
            UserData userData = new UserData(login, password);
            sessionUserData = userData;
            this.locale = locale;
            this.errorConstants = errorConstants;
            clientManager.executeCommand(new RegCommand(userData));

        } else {
            registrationPanel.getLabelModel().getLabelErrorLogin().setVisible(false);
            registrationPanel.getLabelModel().getLabelErrorPassword().setVisible(true);
        }
    }

    public MainWindow regResponseHandle(SpecialSignals signals) {
        boolean res = clientManager.handlerAuth(signals);
        if (res) {
            logInWindow.dispose();
            return createMain(sessionUserData,locale, errorConstants);
        } else {
            registrationPanel.getLabelModel().getLabelErrorPassword().setVisible(false);
            authorizationPanel.getLabelModel().getLabelError().setVisible(true);
        }
        return null;


    }

    private MainWindow createMain(UserData userData, Locale locale, ErrorConstants errorConstants){
        clientManager.setUserData(userData);
        String FONT = logInWindow.getFONT();
        TablePanel tablePanel = new TablePanel(FONT,locale, errorConstants);
        UserPanel userPanel = new UserPanel(locale,userData.getLogin(),FONT,errorConstants);
        CommandPanel commandPanel = new CommandPanel(FONT,locale);
        return new MainWindow(FONT,commandPanel,tablePanel,userPanel,locale);
    }
}
