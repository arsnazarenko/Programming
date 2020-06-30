package frontend.mvc;



import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

public class CommandPanel extends JPanel  {
//    private final String FONT = "Century Gothic";
//    private CommandsActionListener commandsActionListener;
//    private Buttons buttons;
//    private JLabel titleLabel;
//
//    public CommandPanel(ClientManager clientManager, ResourceBundle bundle, ErrorActionListener errorActionListener, CommandsActionListener commandsActionListener) {
//        setLayout(new GridBagLayout());
//        setBackground(Color.WHITE);
//        setPreferredSize(new Dimension(180, getHeight()));
//        buttons = new Buttons();
//        titleLabel = new JLabel();
//        this.commandsActionListener = commandsActionListener;
//        setPosition();
//        localeChange(bundle);
//
//    }
//
//    private void setPosition() {
//
//        titleLabel.setFont(new Font(FONT, Font.PLAIN, 15));
//
//        add(titleLabel, new GridBagConstraints(0, 0, 1, 1, 0, 0,
//                GridBagConstraints.NORTH, GridBagConstraints.CENTER, new Insets(1, 1, 1, 1),
//                0, 0));
//        add(buttons.showButton, new GridBagConstraints(0, 1, 1, 1, 0, 0,
//                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1),
//                0, 0));
//        add(buttons.addButton, new GridBagConstraints(0, 2, 1, 1, 0, 0,
//                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1),
//                0, 0));
//        add(buttons.addIfMinButton, new GridBagConstraints(0, 3, 1, 1, 0, 0,
//                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1),
//                0, 0));
//        add(buttons.updateIdButton, new GridBagConstraints(0, 4, 1, 1, 0, 0,
//                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1),
//                0, 0));
//        add(buttons.removeIdButton, new GridBagConstraints(0, 5, 1, 1, 0, 0,
//                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1),
//                0, 0));
//        add(buttons.removeLowerButton, new GridBagConstraints(0, 6, 1, 1, 0, 0,
//                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1),
//                0, 0));
//        add(buttons.executeScriptButton, new GridBagConstraints(0, 7, 1, 1, 0, 0,
//                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1),
//                0, 0));
//        add(buttons.clearButton, new GridBagConstraints(0, 8, 1, 1, 0, 0,
//                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1),
//                0, 0));
//        add(buttons.infoButton, new GridBagConstraints(0, 9, 1, 1, 0, 0,
//                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1),
//                0, 0));
//        add(buttons.headButton, new GridBagConstraints(0, 10, 1, 1, 0, 0,
//                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1),
//                0, 0));
//        add(buttons.filterNameButton, new GridBagConstraints(0, 11, 1, 1, 0, 0,
//                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1),
//                0, 0));
//        add(buttons.maxEmplButton, new GridBagConstraints(0, 12, 1, 1, 0, 0,
//                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1),
//                0, 0));
//        add(buttons.printAscendButton, new GridBagConstraints(0, 13, 1, 1, 0, 0,
//                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1),
//                0, 0));
//    }
//
//    @Override
//    public void localeChange(ResourceBundle resourceBundle) {
//        titleLabel.setText(resourceBundle.getString("commands"));
//    }
//
//
//    private class Buttons {
//        private JButton showButton;
//        private JButton addButton;
//        private JButton addIfMinButton;
//        private JButton clearButton;
//        private JButton removeIdButton;
//        private JButton removeLowerButton;
//        private JButton infoButton;
//        private JButton updateIdButton;
//        private JButton executeScriptButton;
//        private JButton filterNameButton;
//        private JButton headButton;
//        private JButton maxEmplButton;
//        private JButton printAscendButton;
//
//        {
//
//
//            showButton = new JButton("show");
//            showButton.setBackground(Color.LIGHT_GRAY);
//            showButton.setFocusPainted(false);
//            showButton.setFont(new Font(FONT, Font.PLAIN, 12));
//            showButton.addActionListener(a -> {
//
//            });
//
//            addButton = new JButton("add");
//            addButton.setBackground(Color.LIGHT_GRAY);
//            addButton.setFocusPainted(false);
//            addButton.setFont(new Font(FONT, Font.PLAIN, 12));
//
//            addIfMinButton = new JButton("add if min");
//            addIfMinButton.setBackground(Color.LIGHT_GRAY);
//            addIfMinButton.setFocusPainted(false);
//            addIfMinButton.setFont(new Font(FONT, Font.PLAIN, 12));
//
//            clearButton = new JButton("clear");
//            clearButton.setBackground(Color.LIGHT_GRAY);
//            clearButton.setFocusPainted(false);
//            clearButton.setFont(new Font(FONT, Font.PLAIN, 12));
//            clearButton.addActionListener(a -> {
//
//            });
//
//            removeIdButton = new JButton("remove by id");
//            removeIdButton.setBackground(Color.LIGHT_GRAY);
//            removeIdButton.setFocusPainted(false);
//            removeIdButton.setFont(new Font(FONT, Font.PLAIN, 12));
//            removeIdButton.addActionListener(a -> {
//                Long id = commandsActionListener.validateId();
//                if (id != null) {
//
//                }
//            });
//
//            removeLowerButton = new JButton("remove Lower");
//            removeLowerButton.setBackground(Color.LIGHT_GRAY);
//            removeLowerButton.setFocusPainted(false);
//            removeLowerButton.setFont(new Font(FONT, Font.PLAIN, 12));
//
//            infoButton = new JButton("info");
//            infoButton.setBackground(Color.LIGHT_GRAY);
//            infoButton.setFocusPainted(false);
//            infoButton.setFont(new Font(FONT, Font.PLAIN, 12));
//            infoButton.addActionListener(a -> {
//
//            });
//
//            updateIdButton = new JButton("update by id");
//            updateIdButton.setBackground(Color.LIGHT_GRAY);
//            updateIdButton.setFocusPainted(false);
//            updateIdButton.setFont(new Font(FONT, Font.PLAIN, 12));
//
//            executeScriptButton = new JButton("execute Script");
//            executeScriptButton.setBackground(Color.LIGHT_GRAY);
//            executeScriptButton.setFocusPainted(false);
//            executeScriptButton.setFont(new Font(FONT, Font.PLAIN, 12));
//
//            filterNameButton = new JButton("filter contains name");
//            filterNameButton.setBackground(Color.LIGHT_GRAY);
//            filterNameButton.setFocusPainted(false);
//            filterNameButton.setFont(new Font(FONT, Font.PLAIN, 12));
//            filterNameButton.addActionListener(a -> {
//                String subString = commandsActionListener.validateName();
//                if (subString != null) {
//                }
//
//            });
//
//            headButton = new JButton("head");
//            headButton.setBackground(Color.LIGHT_GRAY);
//            headButton.setFocusPainted(false);
//            headButton.setFont(new Font(FONT, Font.PLAIN, 12));
//            headButton.addActionListener(a -> {
//
//            });
//
//            maxEmplButton = new JButton("max by Employees");
//            maxEmplButton.setBackground(Color.LIGHT_GRAY);
//            maxEmplButton.setFocusPainted(false);
//            maxEmplButton.setFont(new Font(FONT, Font.PLAIN, 12));
//            maxEmplButton.addActionListener(a -> {
//
//            });
//
//            printAscendButton = new JButton("print ascending");
//            printAscendButton.setBackground(Color.LIGHT_GRAY);
//            printAscendButton.setFocusPainted(false);
//            printAscendButton.setFont(new Font(FONT, Font.PLAIN, 12));
//            printAscendButton.addActionListener(a -> {
//
//            });
//        }
//    }
}