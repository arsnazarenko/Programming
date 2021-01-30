package frontend.mvc;

import frontend.ClientManager;
import library.clientCommands.Command;
import library.clientCommands.UserData;
import library.clientCommands.commandType.UpdateIdCommand;
import library.сlassModel.Organization;
import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;

public class OrganizationController {

    private OrganizationView view;
    private ObjectCreatorUI objectCreator;
    private ClientManager clientManager;

    private Class<?> commandClass;
    private Long idForUpdate;

    private boolean addressEnabled = false;
    private boolean townEnabled = false;

    public OrganizationController(OrganizationView view, ObjectCreatorUI objectCreator, ClientManager clientManager) {
        this.clientManager = clientManager;
        this.view = view;
        this.objectCreator = objectCreator;
        this.objectCreator.setView(view);
        initController();
    }

    public void runCreation(Class<?> commandClass) {
        this.commandClass = commandClass;
        view.getFrame().setVisible(true);
    }

    public void unsetVisibleOrganizationCreator() {
        view.getFrame().setVisible(false);
    }

    private void initController() {
        view.getAddressRadioButton().addActionListener(e -> addressManage());
        view.getTownRadioButton().addActionListener(e -> townManage());
        view.getSaveObjectButton().addActionListener(e -> saveObject());
        view.getClearButton().addActionListener(e -> clear());
        view.getFrame().addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                clear();
                e.getWindow().setVisible(false);
            }
        });
    }

    private void clear() {
        JRadioButton addressButton = view.getAddressRadioButton();
        if (addressButton.isSelected()) {
            addressButton.doClick();
        }
        view.getTypeBox().setSelectedIndex(0);
        view.getOrganizationTextComponents().forEach(c -> c.setText(""));
    }

    private void saveObject() {
        Organization organization = objectCreator.create(addressEnabled, townEnabled);
        if (organization == null) {
            StringBuilder sb = new StringBuilder("Fields:" + "\n");
            objectCreator.getValidateResults().forEach(s -> sb.append(s + "\n"));
            JOptionPane.showMessageDialog(null, sb.toString(), "IncorrectFieldsInput", JOptionPane.ERROR_MESSAGE);
        } else {
            Command command = createCommand(commandClass, organization);
            if (command != null)
            clientManager.executeCommand(command);
            this.commandClass = null;
            this.idForUpdate = null;
            clear();
            unsetVisibleOrganizationCreator();
        }

    }

    private Command createCommand(Class<?> commandClass, Organization organization) {

        try {
            if(commandClass == UpdateIdCommand.class) {
                return (Command) commandClass.getDeclaredConstructor(Organization.class, Long.class, UserData.class).newInstance(organization, idForUpdate , clientManager.getUserData());
            }
            return (Command) commandClass.getDeclaredConstructor(Organization.class, UserData.class).newInstance(organization, clientManager.getUserData());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }


    private void townManage() {
        if(!townEnabled) {
            view.getTownComponents().forEach(c -> c.setEnabled(true));
            townEnabled = true;
        } else {
            view.getTownTextComponents().forEach(c -> c.setText(""));
            view.getTownComponents().forEach(c -> c.setEnabled(false));
            townEnabled = false;
        }
    }

    private void addressManage() {
        JRadioButton townRadioButton = view.getTownRadioButton();
        if (!addressEnabled) {
            view.getAddressComponents().forEach(c -> c.setEnabled(true));
            addressEnabled = true;
        } else {

            if (townRadioButton.isSelected()) {
                townRadioButton.doClick();
            }
            view.getTownTextComponents().forEach(c -> c.setText(""));
            view.getAddressTextComponents().forEach(c -> c.setText(""));
            view.getTownComponents().forEach(c -> c.setEnabled(false));
            view.getAddressComponents().forEach(c -> c.setEnabled(false));
            addressEnabled = false;
            townEnabled = false;
        }

    }

    public void setIdForUpdate(Long idForUpdate) {
        this.idForUpdate = idForUpdate;
    }

    //    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> new OrganizationController(new OrganizationView(), new ObjectCreatorUI(new ObjectDataValidator())));
//    }


}
