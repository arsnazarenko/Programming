package frontend.graphicsInterface.mainWindow;



import frontend.graphicsInterface.LocaleActionListener;
import frontend.graphicsInterface.controllers.Controllers;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class UserPanel extends JPanel implements LocaleActionListener {
    private ImagePanel imagePanel;
    private JComboBox<String> listImages;
    private JLabel label;
    private String login;
    private String FONT;
    private JLabel loginLabel;

    public UserPanel(String login, String FONT) {
        this.login = login;
        this.FONT = FONT;

        imagePanel = new ImagePanel();

        label = new JLabel();
        label.setFont(new Font(FONT, Font.BOLD, 12));
        loginLabel = new JLabel();
        loginLabel.setFont(new Font(FONT, Font.BOLD, 12));
        createComboBox();
        localeChange(Controllers.getLocale());
        createPanel();
    }

    private void createComboBox(){
        listImages = new JComboBox<>();
        listImages.setFont(new Font(FONT, Font.PLAIN, 12));
        listImages.addActionListener(a-> imagePanel.chooseImage( listImages.getSelectedIndex()));
    }

    private void createPanel(){
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);
        add(label,  new GridBagConstraints(0, 0, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.CENTER,
                new Insets(1, 1, 1, 1), 0, 0));
        add(loginLabel,  new GridBagConstraints(0, 1, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.CENTER,
                new Insets(1, 1, 1, 1), 0, 0));
        add(imagePanel,  new GridBagConstraints(0, 2, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.CENTER,
                new Insets(1, 1, 1, 1), 0, 0));
        add(listImages,  new GridBagConstraints(0, 3, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.CENTER,
                new Insets(1, 1, 1, 1), 0, 0));
    }

    @Override
    public void localeChange(Locale locale ) {
        ResourceBundle bundle = ResourceBundle.getBundle("translate",locale);
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(new String[]{bundle.getString("usual_cat"),
                bundle.getString("sad_cat"),bundle.getString("happy_cat"),bundle.getString("sleeping_cat"),
                bundle.getString("tired_cat")});

        listImages.setModel(model);
        listImages.setToolTipText(bundle.getString("avatar"));
        label.setText(bundle.getString("user") + ":" );
        loginLabel.setText(login);
    }

    public JComboBox<String> getListImages() {
        return listImages;
    }

    public JLabel getLabel() {
        return label;
    }
}
