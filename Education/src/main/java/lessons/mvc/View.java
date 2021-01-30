package lessons.mvc;

import javax.swing.*;
import java.awt.*;

public class View {

    private JFrame frame;
    private JLabel firstNameLabel;
    private JLabel lastNameLabel;
    private JTextField firstNameTextField;
    private JTextField lastNameTextField;
    private JButton firstNameSaveButton;
    private JButton lastNameSaveButton;
    private JButton hello;
    private JButton bye;


    public View(String title) {
        frame = new JFrame("title");
        frame.getContentPane().setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 120);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);



        firstNameLabel = new JLabel("FirstName: ");
        lastNameLabel = new JLabel("LastName: ");
        firstNameTextField = new JTextField();
        lastNameTextField = new JTextField();
        firstNameSaveButton = new JButton("Save FirstName");
        lastNameSaveButton = new JButton("Save LastName");
        hello = new JButton("Hello!");
        bye = new JButton("Bye!");


        GroupLayout layout = new GroupLayout(frame.getContentPane());
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(firstNameLabel)
                        .addComponent(lastNameLabel))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(firstNameTextField)
                        .addComponent(lastNameTextField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(firstNameSaveButton)
                        .addComponent(lastNameSaveButton))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(hello)
                        .addComponent(bye))
        );
        layout.setVerticalGroup(layout.createSequentialGroup().
                addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).
                        addComponent(firstNameLabel).
                        addComponent(firstNameTextField).
                        addComponent(firstNameSaveButton).
                        addComponent(hello))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).
                        addComponent(lastNameLabel).
                        addComponent(lastNameTextField).
                        addComponent(lastNameSaveButton).
                        addComponent(bye))
        );
        layout.linkSize(SwingConstants.HORIZONTAL, firstNameSaveButton, lastNameSaveButton);
        layout.linkSize(SwingConstants.HORIZONTAL, hello, bye);
        frame.getContentPane().setLayout(layout);

    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public JLabel getFirstNameLabel() {
        return firstNameLabel;
    }

    public void setFirstNameLabel(JLabel firstNameLabel) {
        this.firstNameLabel = firstNameLabel;
    }

    public JLabel getLastNameLabel() {
        return lastNameLabel;
    }

    public void setLastNameLabel(JLabel lastNameLabel) {
        this.lastNameLabel = lastNameLabel;
    }

    public JTextField getFirstNameTextField() {
        return firstNameTextField;
    }

    public void setFirstNameTextField(JTextField firstNameTextField) {
        this.firstNameTextField = firstNameTextField;
    }

    public JTextField getLastNameTextField() {
        return lastNameTextField;
    }

    public void setLastNameTextField(JTextField lastNameTextField) {
        this.lastNameTextField = lastNameTextField;
    }

    public JButton getFirstNameSaveButton() {
        return firstNameSaveButton;
    }

    public void setFirstNameSaveButton(JButton firstNameSaveButton) {
        this.firstNameSaveButton = firstNameSaveButton;
    }

    public JButton getLastNameSaveButton() {
        return lastNameSaveButton;
    }

    public void setLastNameSaveButton(JButton lastNameSaveButton) {
        this.lastNameSaveButton = lastNameSaveButton;
    }

    public JButton getHello() {
        return hello;
    }

    public void setHello(JButton hello) {
        this.hello = hello;
    }

    public JButton getBye() {
        return bye;
    }

    public void setBye(JButton bye) {
        this.bye = bye;
    }
}
