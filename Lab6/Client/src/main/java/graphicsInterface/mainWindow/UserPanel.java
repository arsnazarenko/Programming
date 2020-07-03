package graphicsInterface.mainWindow;

import graphicsInterface.ErrorConstants;
import graphicsInterface.LocaleActionListener;

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

    public UserPanel(Locale locale, String login, String FONT, ErrorConstants errorConstants) {
        this.login = login;
        this.FONT = FONT;

        imagePanel = new ImagePanel(errorConstants);
        label = new JLabel();
        label.setFont(new Font(FONT, Font.BOLD, 12));

        createComboBox();
        localeChange(locale);
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
        add(imagePanel,  new GridBagConstraints(0, 1, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.CENTER,
                new Insets(1, 1, 1, 1), 0, 0));
        add(listImages,  new GridBagConstraints(0, 2, 1, 1, 0, 0,
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
        label.setText(bundle.getString("user") + ": " + login);
    }

    public ImagePanel getImagePanel() {
        return imagePanel;
    }

    public void setImagePanel(ImagePanel imagePanel) {
        this.imagePanel = imagePanel;
    }

    public JComboBox<String> getListImages() {
        return listImages;
    }

    public void setListImages(JComboBox<String> listImages) {
        this.listImages = listImages;
    }

    public JLabel getLabel() {
        return label;
    }

    public void setLabel(JLabel label) {
        this.label = label;
    }

}
