package frontend.graphicsInterface;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class Menu extends JMenuBar implements LocaleActionListener {
    private JMenu languageMenu;
    private JMenuItem russian;
    private JMenuItem spanish;
    private JMenuItem polish;
    private JMenuItem slovak;

    public Menu(Locale locale, String FONT) {
        setBackground(Color.WHITE);

        languageMenu = new JMenu();
        languageMenu.setFont(new Font(FONT,Font.PLAIN,12));

        russian = new JMenuItem();
        russian.setFont(new Font(FONT,Font.PLAIN,12));
        russian.setBackground(Color.WHITE);

        spanish = new JMenuItem();
        spanish.setFont(new Font(FONT,Font.PLAIN,12));
        spanish.setBackground(Color.WHITE);

        polish = new JMenuItem();
        polish.setFont(new Font(FONT,Font.PLAIN,12));
        polish.setBackground(Color.WHITE);

        slovak = new JMenuItem();
        slovak.setFont(new Font(FONT,Font.PLAIN,12));
        slovak.setBackground(Color.WHITE);

        localeChange(locale);
        languageMenu.add(russian);
        languageMenu.add(polish);
        languageMenu.add(spanish);
        languageMenu.add(slovak);

        add(languageMenu);
    }

    @Override
    public void localeChange(Locale locale) {
        ResourceBundle bundle = ResourceBundle.getBundle("translate",locale);
        languageMenu.setText(bundle.getString("language"));
        russian.setText(bundle.getString("russian"));
        slovak.setText(bundle.getString("slovak"));
        spanish.setText(bundle.getString("spanish"));
        polish.setText(bundle.getString("polish"));
    }

    public JMenu getLanguageMenu() {
        return languageMenu;
    }

    public void setLanguageMenu(JMenu languageMenu) {
        this.languageMenu = languageMenu;
    }

    public JMenuItem getRussian() {
        return russian;
    }

    public void setRussian(JMenuItem russian) {
        this.russian = russian;
    }

    public JMenuItem getSpanish() {
        return spanish;
    }

    public void setSpanish(JMenuItem spanish) {
        this.spanish = spanish;
    }

    public JMenuItem getPolish() {
        return polish;
    }

    public void setPolish(JMenuItem polish) {
        this.polish = polish;
    }

    public JMenuItem getSlovak() {
        return slovak;
    }

    public void setSlovak(JMenuItem slovak) {
        this.slovak = slovak;
    }
}