package graphicsInterface;

import javax.swing.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class ErrorConstants implements LocaleActionListener {
    private String title;
    private String error_1;
    private String error_2;
    private String error_3;
    private String error_4;
    private String error_5;
    private String error_6;
    private String error_7;

    public ErrorConstants(Locale locale) {
        localeChange(locale);
    }

     @Override
    public void localeChange(Locale locale) {
        ResourceBundle bundle = ResourceBundle.getBundle("translate",locale);
        title = bundle.getString("error_title");
        error_1 = bundle.getString("error_1");
        error_2 = bundle.getString("error_2");
        error_3 = bundle.getString("error_3");
        error_4 = bundle.getString("error_4");
        error_5 = bundle.getString("error_5");
        error_6 = bundle.getString("error_6");
        error_7 = bundle.getString("error_7");
    }

    public String getTitle() {
        return title;
    }

    public String getError_1() {
        return error_1;
    }

    public String getError_2() {
        return error_2;
    }

    public String getError_3() {
        return error_3;
    }

    public String getError_4() {
        return error_4;
    }

    public String getError_5() {
        return error_5;
    }

    public String getError_6() {
        return error_6;
    }

    public String getError_7() {
        return error_7;
    }
}
