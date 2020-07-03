package graphicsInterface.loginForm;

import graphicsInterface.LocaleActionListener;

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
        mainPanel.getPanelField().add(labelModel.labelError,
                new GridBagConstraints(0, 4, 1, 1, 0, 0,
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
        labelModel.labelError.setText(bundle.getString("mg_error_1"));
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

        public void setLoginField(JTextField loginField) {
            this.loginField = loginField;
        }

        public JPasswordField getPasswordField() {
            return passwordField;
        }

        public void setPasswordField(JPasswordField passwordField) {
            this.passwordField = passwordField;
        }
    }

    public class JLabelModel {
        private JLabel labelTitle;
        private JLabel labelLogin;
        private JLabel labelPassword;
        private JLabel labelError;

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

            labelError = new JLabel();
            labelError.setFont(new Font(font, Font.PLAIN, 10));
            labelError.setForeground(Color.RED);
            labelError.setVisible(false);
        }

        public JLabel getLabelTitle() {
            return labelTitle;
        }

        public void setLabelTitle(JLabel labelTitle) {
            this.labelTitle = labelTitle;
        }

        public JLabel getLabelLogin() {
            return labelLogin;
        }

        public void setLabelLogin(JLabel labelLogin) {
            this.labelLogin = labelLogin;
        }

        public JLabel getLabelPassword() {
            return labelPassword;
        }

        public void setLabelPassword(JLabel labelPassword) {
            this.labelPassword = labelPassword;
        }

        public JLabel getLabelError() {
            return labelError;
        }

        public void setLabelError(JLabel labelError) {
            this.labelError = labelError;
        }
    }

    public JTextFieldModel getFieldModel() {
        return fieldModel;
    }

    public void setFieldModel(JTextFieldModel fieldModel) {
        this.fieldModel = fieldModel;
    }

    public JLabelModel getLabelModel() {
        return labelModel;
    }

    public void setLabelModel(JLabelModel labelModel) {
        this.labelModel = labelModel;
    }

    public LogInWindow.MainPanel getMainPanel() {
        return mainPanel;
    }

    public void setMainPanel(LogInWindow.MainPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

}
