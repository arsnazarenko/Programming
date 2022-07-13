package lessons.mvc;



import java.awt.*;

public class ColorGenerator {
    public static Color generate(String str) {
        String a = str.length() * 100 + str + str.toUpperCase() + str.length() + Math.pow(str.length() * 1.1, str.length());
        int b = a.hashCode();
        return new Color(b);
    }
}
