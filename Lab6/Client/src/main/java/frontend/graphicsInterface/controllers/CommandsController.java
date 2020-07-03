package frontend.graphicsInterface.controllers;

import frontend.ClientManager;
import frontend.graphicsInterface.LocaleActionListener;
import frontend.graphicsInterface.mainWindow.MainWindow;
import frontend.mvc.OrganizationController;
import library.clientCommands.SpecialSignals;
import library.clientCommands.commandType.*;
import library.сlassModel.Organization;

import javax.swing.*;
import java.util.*;
import java.util.stream.Collectors;

public class CommandsController implements LocaleActionListener {
    private ClientManager clientManager;
    private MainWindow mainWindow;
    private String idMassage;
    private String strMassage;
    private OrganizationController organizationCreator;



    public CommandsController(ClientManager clientManager, Locale locale, OrganizationController organizationCreator) {
        this.clientManager = clientManager;
        this.organizationCreator = organizationCreator;
        localeChange(locale);
    }

    public void show() {
        clientManager.executeCommand(new ShowCommand(clientManager.getUserData()));
    }

    public void removeId(Long id) {
        if (id != null) {
            clientManager.executeCommand(new RemoveIdCommand(id, clientManager.getUserData()));
        }
    }

    public void clear() {
        clientManager.executeCommand(new ClearCommand(clientManager.getUserData()));
    }

    public void filter() {
        String subString = validateName();
        if (subString != null) {
            clientManager.executeCommand(new FilterContainsNameCommand(subString, clientManager.getUserData()));
        }
    }

    public void head(){
        clientManager.executeCommand(new HeadCommand(clientManager.getUserData()));
    }

    public void max(){
        clientManager.executeCommand(new MaxByEmployeeCommand(clientManager.getUserData()));
    }

    public void print(){
        clientManager.executeCommand(new PrintAscendingCommand(clientManager.getUserData()));
    }

    public void info(){
        clientManager.executeCommand(new InfoCommand(clientManager.getUserData()));
    }

    public void handle(Object answerObject) {
        ResourceBundle bundle = ResourceBundle.getBundle("translate",Controllers.getLocale());
        if (answerObject instanceof Deque) {
            Deque<Organization> organizations = ((Deque<?>) answerObject).stream().map(o -> (Organization) o).collect(Collectors.toCollection(ArrayDeque::new));
            mainWindow.getTablePanel().getCollection().setCollection(new ArrayList<>(organizations));
            mainWindow.getTablePanel().updateData();
        } else if(answerObject instanceof Organization){
            mainWindow.getTablePanel().getCollection().setCollection(new ArrayList<>(Collections.singletonList((Organization) answerObject)));
            mainWindow.getTablePanel().updateData();
        } else if (answerObject instanceof String || answerObject instanceof SpecialSignals) {
            JOptionPane.showMessageDialog(null, answerObject);
        } else if (answerObject == null) {
            JOptionPane.showMessageDialog(null,bundle.getString("nul"));
        }
    }

    public Long validateId() {
        ResourceBundle bundle = ResourceBundle.getBundle("translate",Controllers.getLocale());
        Object o [] = {bundle.getString("ok"),bundle.getString("cancel")};
        Long id;
        //сделай здесь панельку плиз
        String result = JOptionPane.showInputDialog(null,
                idMassage,null);
        id = clientManager.getArgumentValidator().idValid(result);
        if (id == null) {
            Object ok [] = {bundle.getString("ok")};
                JOptionPane.showOptionDialog(null,
                        bundle.getString("error_5"), bundle.getString("error_title"), JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE,
                        null, ok, ok[0]);
        }
        return id;
    }

    public String validateName() {
        ResourceBundle bundle = ResourceBundle.getBundle("translate",Controllers.getLocale());
        String name;
        //И тут
        String result = JOptionPane.showInputDialog(null,
                strMassage,null);
        name = clientManager.getArgumentValidator().subStringIsValid(result);
        if (name == null) {
            Object ok [] = {bundle.getString("ok")};
            JOptionPane.showOptionDialog(null,
                    bundle.getString("error_6"), bundle.getString("error_title"), JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE,
                    null, ok, ok[0]);
        }
        return name;
    }

    @Override
    public void localeChange(Locale locale) {
        ResourceBundle bundle = ResourceBundle.getBundle("translate",locale);
        idMassage = bundle.getString("id_massage");
        strMassage = bundle.getString("str_massage");
    }

    public void setMainWindow(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    public void add() {
        organizationCreator.runCreation(AddCommand.class);
    }

    public void addIfMin() {
        organizationCreator.runCreation(AddIfMinCommand.class);
    }

    public void removeLower() {
        organizationCreator.runCreation(RemoveLowerCommand.class);
    }

    public void updateId(Long id) {
        if (id != null) {
            organizationCreator.setIdForUpdate(id);
            organizationCreator.runCreation(UpdateIdCommand.class);
        }
    }

    public void executeScript() {

    }
}
