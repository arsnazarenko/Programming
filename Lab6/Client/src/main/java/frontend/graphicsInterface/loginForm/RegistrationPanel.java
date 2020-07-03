package frontend.graphicsInterface.loginForm;




import frontend.graphicsInterface.LocaleActionListener;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class RegistrationPanel implements LocaleActionListener {
    private JTextFieldModel fieldModel;
    private JLabelModel labelModel;

    private LogInWindow.MainPanel mainPanel;
    private String font;
    private Icon icon;
    private Color color;


    public RegistrationPanel(LogInWindow.MainPanel mainPanel, String font, Icon icon, Color color, Locale locale) {
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

        mainPanel.getPanelField().add(labelModel.labelRestatePassword, new GridBagConstraints(0, 4, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1),
                0, 0));
        mainPanel.getPanelField().add(fieldModel.passwordRestateField, new GridBagConstraints(0, 5, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1),
                0, 0));
    }

    public void localeChange (Locale locale){
        ResourceBundle bundle = ResourceBundle.getBundle("translate",locale);
        labelModel.labelTitle.setText(bundle.getString("registration"));
        labelModel.labelRestatePassword.setText(bundle.getString("confirmation_password"));
    }


    public class JTextFieldModel {
        private JPasswordField passwordRestateField;

        {
            passwordRestateField = new JPasswordField(10);
            passwordRestateField.setForeground(Color.GRAY);
            passwordRestateField.setVisible(false);

        }

        public JPasswordField getPasswordRestateField() {
            return passwordRestateField;
        }

        public void setPasswordRestateField(JPasswordField passwordRestateField) {
            this.passwordRestateField = passwordRestateField;
        }
    }

    public class JLabelModel {
        private JLabel labelTitle;
        private JLabel labelRestatePassword;

        {
            labelTitle = new JLabel();
            labelTitle.setFont(new Font(font, Font.PLAIN, 25));
            labelTitle.setForeground(color);
            labelTitle.setVisible(false);

            labelRestatePassword = new JLabel();
            labelRestatePassword.setFont(new Font(font, Font.PLAIN, 15));
            labelRestatePassword.setForeground(color);
            labelRestatePassword.setVisible(false);
        }

        public JLabel getLabelTitle() {
            return labelTitle;
        }

        public JLabel getLabelRestatePassword() {
            return labelRestatePassword;
        }

    }

    public JTextFieldModel getFieldModel() {
        return fieldModel;
    }


    public JLabelModel getLabelModel() {
        return labelModel;
    }


}
