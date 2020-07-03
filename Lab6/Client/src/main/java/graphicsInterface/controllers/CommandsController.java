package graphicsInterface.controllers;

import graphicsInterface.ClientManager;
import graphicsInterface.ErrorConstants;
import graphicsInterface.LocaleActionListener;
import graphicsInterface.mainWindow.MainWindow;
import graphicsInterface.mainWindow.table.TableModel;
import library.clientCommands.SpecialSignals;
import library.clientCommands.commandType.*;
import library.сlassModel.Organization;

import javax.swing.*;
import java.util.*;
import java.util.stream.Collectors;

public class CommandsController implements LocaleActionListener {
    private ClientManager clientManager;
    private MainWindow mainWindow;
    private ErrorConstants errorConstants;
    private String idMassage;
    private String strMassage;



    public CommandsController(ClientManager clientManager, ErrorConstants errorConstants, Locale locale) {
        this.clientManager = clientManager;
        this.errorConstants = errorConstants;
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
        TableModel tableModel = mainWindow.getTablePanel().getTableModel();
        if (answerObject instanceof Deque) {
            Deque<Organization> organizations = ((Deque<?>) answerObject).stream().map(o -> (Organization) o).collect(Collectors.toCollection(ArrayDeque::new));
            tableModel.setDataList(new ArrayList<>(organizations));
        } else if(answerObject instanceof Organization) {
            tableModel.setDataList(new ArrayList<>(Collections.singletonList((Organization) answerObject)));
        } else if (answerObject instanceof String || answerObject instanceof SpecialSignals) {
            JOptionPane.showMessageDialog(null, answerObject);
        } else if (answerObject == null) {
            JOptionPane.showMessageDialog(null, "Объектов не найдено!");
        }
    }

    public Long validateId() {
        Long id;
        String result = JOptionPane.showInputDialog(null,
                idMassage);
        id = clientManager.getArgumentValidator().idValid(result);
        if (id == null) {
                JOptionPane.showMessageDialog(null, errorConstants.getError_5(), errorConstants.getTitle(), JOptionPane.ERROR_MESSAGE);
        }
        return id;
    }

    public String validateName() {
        String name;
        String result = JOptionPane.showInputDialog(null,
                strMassage);
        name = clientManager.getArgumentValidator().subStringIsValid(result);
        if (name == null) {
            JOptionPane.showMessageDialog(null, errorConstants.getError_6(), errorConstants.getTitle(), JOptionPane.ERROR_MESSAGE);
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
}
