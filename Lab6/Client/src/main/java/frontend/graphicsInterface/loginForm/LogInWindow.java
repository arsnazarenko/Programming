package frontend.graphicsInterface.loginForm;



import frontend.graphicsInterface.LocaleActionListener;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;


public class LogInWindow extends JFrame implements LocaleActionListener {
    private final String FONT;
    private final Color COLOR = new Color(132, 146, 189);

    private MainPanel mainPanel;
    private Buttons buttons;
    private AuthorizationPanel authorizationPanel;
    private RegistrationPanel registrationPanel;

    public String getFONT() {
        return FONT;
    }

    public LogInWindow(String FONT, Locale locale) {
        super("Registration Form");
        this.FONT = FONT;
        mainPanel = new MainPanel();
        buttons = new Buttons();
        ImageIcon ICON = new ImageIcon("src/main/resources/image/василек.png");
        authorizationPanel = new AuthorizationPanel(mainPanel, FONT, ICON, COLOR,locale);
        registrationPanel = new RegistrationPanel(mainPanel, FONT, ICON, COLOR,locale);
        localeChange(locale);
        createFrame();

    }

    private void createFrame() {
        setSize(380, 400);
        getContentPane().setBackground(COLOR);
        setLayout(new GridBagLayout());
        setResizable(false);
        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

    }

    @Override
    public void localeChange(Locale locale) {
        ResourceBundle bundle = ResourceBundle.getBundle("translate",locale);
        buttons.buttonLog.setText(bundle.getString("sing_in"));
        buttons.buttonReg.setText(bundle.getString("sing_up"));
        buttons.buttonSignIn.setText(bundle.getString("log_in"));
        buttons.buttonSignUp.setText(bundle.getString("registration"));
    }

    public class MainPanel extends JPanel{
        private JPanel panelTitle;
        private JPanel panelButton;
        private JPanel panelField;

        {
            setLayout(new BorderLayout());
            setBackground(Color.WHITE);
            setPreferredSize(new Dimension(300, 300));

            panelTitle = new JPanel();
            panelTitle.setBackground(Color.WHITE);

            panelButton = new JPanel(new GridBagLayout());
            panelButton.setBackground(Color.WHITE);
            panelButton.setPreferredSize(new Dimension(300, 70));


            panelField = new JPanel(new GridBagLayout());
            panelField.setBackground(Color.WHITE);

            add(panelTitle, BorderLayout.NORTH);
            add(panelButton, BorderLayout.SOUTH);
            add(panelField, BorderLayout.CENTER);

            getContentPane().add(this);
        }

        public JPanel getPanelTitle() {
            return panelTitle;
        }

        public JPanel getPanelField() {
            return panelField;
        }
    }

    public class Buttons {
       private JButton buttonSignIn;
       private JButton buttonSignUp;
       private JButton buttonLog;
       private JButton buttonReg;


        {
            buttonLog = new JButton();
            buttonLog.setBackground(COLOR);
            buttonLog.setFont(new Font(FONT, Font.PLAIN, 12));
            buttonLog.setForeground(Color.WHITE);
            buttonLog.setSize(20, 10);
            buttonLog.setFocusPainted(false);

            buttonSignUp = new JButton();
            buttonSignUp.setBackground(COLOR);
            buttonSignUp.setFont(new Font(FONT, Font.PLAIN, 12));
            buttonSignUp.setForeground(Color.WHITE);
            buttonSignUp.setSize(20, 10);
            buttonSignUp.setFocusPainted(false);

            buttonSignIn = new JButton();
            buttonSignIn.setBackground(COLOR);
            buttonSignIn.setFont(new Font(FONT, Font.PLAIN, 12));
            buttonSignIn.setForeground(Color.WHITE);
            buttonSignIn.setSize(20, 10);
            buttonSignIn.setFocusPainted(false);
            buttonSignIn.setVisible(false);

            buttonReg = new JButton();
            buttonReg.setBackground(COLOR);
            buttonReg.setFont(new Font(FONT, Font.PLAIN, 12));
            buttonReg.setForeground(Color.WHITE);
            buttonReg.setSize(20, 10);
            buttonReg.setVisible(false);
            buttonReg.setFocusPainted(false);

            regButtonsAdd();
            logButtonsAdd();
        }

        private void regButtonsAdd() {
            mainPanel.panelButton.add(buttonReg,
                    new GridBagConstraints(0, 1, 1, 1, 0, 0,
                            GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1),
                            0, 0));
            mainPanel.panelButton.add(buttonSignIn,
                    new GridBagConstraints(0, 0, 1, 1, 0, 0,
                            GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1),
                            0, 0));
        }

        private void logButtonsAdd() {
            mainPanel.panelButton.add(buttonLog,
                    new GridBagConstraints(0, 0, 1, 1, 0, 0,
                            GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1),
                            0, 0));
            mainPanel.panelButton.add(buttonSignUp,
                    new GridBagConstraints(0, 1, 1, 1, 0, 0,
                            GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1),
                            0, 0));
        }

        public JButton getButtonSignIn() {
            return buttonSignIn;
        }

        public JButton getButtonSignUp() {
            return buttonSignUp;
        }

        public JButton getButtonLog() {
            return buttonLog;
        }

        public JButton getButtonReg() {
            return buttonReg;
        }

    }


    public Buttons getButtons() {
        return buttons;
    }

    public AuthorizationPanel getAuthorizationPanel() {
        return authorizationPanel;
    }

    public RegistrationPanel getRegistrationPanel() {
        return registrationPanel;
    }

}
