package lessons.mvc;

import javax.swing.*;

public class Controller {

    private Model model;
    private View view;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
        initView();
    }

    private void initView() {
    }

    public void initController() {
        view.getFirstNameSaveButton().addActionListener(e -> saveFistName());
        view.getLastNameSaveButton().addActionListener(e -> saveLastName());
        view.getHello().addActionListener(e -> sayHallo());
        view.getBye().addActionListener(e -> sayBye());


    }

    private void sayBye() {
        JOptionPane.showMessageDialog(null, "Bye " + model.getFirstname() + " " + model.getLastname(), "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    private void sayHallo() {
        JOptionPane.showMessageDialog(null, "Hello " + model.getFirstname() + " " + model.getLastname(), "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    private void saveLastName() {
        model.setLastname(view.getLastNameTextField().getText());
        JOptionPane.showMessageDialog(null, "LastName saved", "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    private void saveFistName() {
        model.setFirstname(view.getFirstNameTextField().getText());
        JOptionPane.showMessageDialog(null, "FirstName saved", "Info", JOptionPane.INFORMATION_MESSAGE);

    }
}
