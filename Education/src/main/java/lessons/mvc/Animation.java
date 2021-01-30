package lessons.mvc;


import javax.swing.*;
import java.awt.*;


public class Animation extends JPanel {
    private final int CELL_SIZE = 15;

    public Animation() {
        Icon rect1 =
                new Icon(0L, Color.BLUE, new Dimension(12, 12), 0, 0);
        Icon rect2 =
                new Icon(0L, Color.RED, new Dimension(12, 12), 0, 0);
        Icon transparentRect =
                new Icon(0L, Color.BLACK, new Dimension(12, 12), 0, 0);
        // укажем координаты вручную, чтобы компоненты
        // перекрывались
        setLayout(null);
        rect1.setLocation(25 * CELL_SIZE, 25 * CELL_SIZE);
        rect1.setSize(50, 50);
        rect2.setBounds(20 * CELL_SIZE, 14 * CELL_SIZE, 50, 50);
        transparentRect.setBounds(26 * CELL_SIZE, 14 * CELL_SIZE, 40, 40);
        add(transparentRect);
        add(rect1);
        add(rect2);
        // последним добавляем тяжеловесный компонент
        // выводим окно на экран
        setSize(80 * CELL_SIZE, 80 * CELL_SIZE);
        //setVisible(true);
        rect1.changeLocation(16 * CELL_SIZE, 16 * CELL_SIZE);
        rect2.changeLocation(30 * CELL_SIZE, 14 * CELL_SIZE);
        transparentRect.changeLocation(26 * CELL_SIZE, 0 * CELL_SIZE);
        JFrame frame = new JFrame("new");
        frame.getContentPane().add(this);
        frame.setSize(50 * CELL_SIZE, 50 * CELL_SIZE);
        frame.setVisible(true);
    }


    public static void main(String[] args) {
        new Animation();


    }

}
