package frontend.graphicsInterface.loginForm;



import frontend.graphicsInterface.LocaleActionListener;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class AuthorizationPanel implements LocaleActionListener {
    private JTextFieldModel fieldModel;
    private JLabelModel labelModel;
    private LogInWindow.MainPanel mainPanel;
    private String font;
    private Icon icon;
    private Color color;

    public AuthorizationPanel(LogInWindow.MainPanel mainPanel, String font, Icon icon, Color color, Locale locale) {
        this.mainPanel = mainPanel;
        this.font = font;
        this.icon = icon;
        this.color = color;

        fieldModel = new JTextFieldModel();
        labelModel = new JLabelModel();
        localeChange(locale);
        addPanel();
    }

    private void addPanel() {

        mainPanel.getPanelTitle().add(labelModel.labelTitle);

        mainPanel.getPanelField().add(labelModel.labelLogin,
                new GridBagConstraints(0, 0, 1, 1, 0, 0,
                        GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                        new Insets(1, 1, 1, 1), 0, 0));
        mainPanel.getPanelField().add(labelModel.labelPassword,
                new GridBagConstraints(0, 2, 1, 1, 0, 0,
                        GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1),
                        0, 0));

        mainPanel.getPanelField().add(fieldModel.loginField,
                new GridBagConstraints(0, 1, 1, 1, 0, 0,
                        GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1),
                        0, 0));
        mainPanel.getPanelField().add(fieldModel.passwordField,
                new GridBagConstraints(0, 3, 1, 1, 0, 0,
                        GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1),
                        0, 0));
    }

    public void localeChange(Locale locale){
        ResourceBundle bundle = ResourceBundle.getBundle("translate",locale);
        labelModel.labelTitle.setText(bundle.getString("authorization"));
        labelModel.labelLogin.setText(bundle.getString("login"));
        labelModel.labelPassword.setText(bundle.getString("password"));
    }


    public class JTextFieldModel {
        private JTextField loginField;
        private JPasswordField passwordField;

        {
            loginField = new JTextField(10);
            loginField.setFont(new Font(font, Font.PLAIN, loginField.getFont().getSize()));
            loginField.setForeground(Color.GRAY);

            passwordField = new JPasswordField(10);
            passwordField.setForeground(Color.GRAY);
        }

        public JTextField getLoginField() {
            return loginField;
        }

        public JPasswordField getPasswordField() {
            return passwordField;
        }
    }

    public class JLabelModel {
        private JLabel labelTitle;
        private JLabel labelLogin;
        private JLabel labelPassword;
        {
            labelTitle = new JLabel();
            labelTitle.setFont(new Font(font, Font.PLAIN, 25));
            labelTitle.setForeground(color);

            labelLogin = new JLabel(icon, SwingConstants.LEFT);
            labelLogin.setFont(new Font(font, Font.PLAIN, 15));
            labelLogin.setForeground(color);

            labelPassword = new JLabel(icon, SwingConstants.LEFT);
            labelPassword.setFont(new Font(font, Font.PLAIN, 15));
            labelPassword.setForeground(color);
        }

        public JLabel getLabelTitle() {
            return labelTitle;
        }
    }

    public JTextFieldModel getFieldModel() {
        return fieldModel;
    }

    public JLabelModel getLabelModel() {
        return labelModel;
    }

}
