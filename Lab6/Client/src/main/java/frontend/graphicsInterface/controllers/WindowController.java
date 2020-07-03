package frontend.graphicsInterface.controllers;



import frontend.graphicsInterface.LocaleActionListener;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.ResourceBundle;

public class WindowController implements LocaleActionListener {
    private String massage;
    private String title;
    private String[] objButtons;

    public WindowController(Locale locale) {
        localeChange(locale);
    }

    public void confirm(WindowEvent e){
        int PromptResult = JOptionPane.showOptionDialog(e.getWindow(),
                massage,title, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null,objButtons,objButtons[1]);
        if (PromptResult == 0) {
            System.exit(0);
        }
    }


    @Override
    public void localeChange(Locale locale) {
        ResourceBundle bundle = ResourceBundle.getBundle("translate",locale);
        objButtons = new String[]{bundle.getString("yes"), bundle.getString("no")};
        massage = bundle.getString("mg_exit");
        title = bundle.getString("confirmation");
    }
}
