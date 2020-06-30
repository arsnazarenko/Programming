package frontend.mvc;

import client.servises.ObjectDataValidator;
import library.сlassModel.Organization;
import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class OrganizationController {

    private OrganizationView view;
    private ObjectCreatorUI objectCreator;

    private boolean addressEnabled = false;
    private boolean townEnabled = false;

    public OrganizationController(OrganizationView view, ObjectCreatorUI objectCreator) {
        this.view = view;
        this.objectCreator = objectCreator;
        this.objectCreator.setView(view);
        initController();
    }

    public void setVisibleOrganizationCreator() {
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
            System.out.println(organization);
            //model.setOrganization(organization)
            //model.submitForWriting()
            clear();
            unsetVisibleOrganizationCreator();
        }

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


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new OrganizationController(new OrganizationView(), new ObjectCreatorUI(new ObjectDataValidator())));
    }


}
