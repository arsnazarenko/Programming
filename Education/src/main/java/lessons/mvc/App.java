package lessons.mvc;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        Model m = new Model("Arseniy", "Nazarenko");
        View v = new View("MVC with me");
        Controller c = new Controller(m, v);
        //SwingUtilities.invokeLater(c::initController);

    }
}
