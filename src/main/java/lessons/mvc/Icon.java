package lessons.mvc;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

public class Icon extends JComponent implements ActionListener {
    private final Long id;
    private Color color;
    private final Timer timer = new Timer(25, this);


    private int tmpX;
    private int tmpY;


    private int velocityX;
    private int velocityY;

    private int startX;
    private int startY;

    // параметы - цвет и нужно ли зарисовывать всю область

    public Icon(Long id, Color color, Dimension size, int startX, int startY) {
        this.id = id;
        this.color = color;
        setSize(size);
        this.startX = startX;
        this.startY = startY;
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println(id);
            }
        });
    }

    public void paintComponent(Graphics g) {
        Dimension size = getSize();
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(color);
        int x = 0;
        int y = 0;
        //Polygon polygon = new Polygon(xs, ys, n);
//        g2d.fill(polygon);
        int a = (int) size.getHeight();
        int c = a / 8;
        int b = (int) size.getWidth();
        g2d.fillArc(x, y - c, b, 2 * c, 180, 180);
        g2d.fillRect(x + b / 8, y, b * 6 / 8, a / 2);
        g2d.fillArc(x, y + a / 2 - c, b, 2 * c, 0, 180);
        int d = b / 4;
        int e = a / 2;
        int[] xs = {x + b / 2 - d / 2, x + b / 2 - d / 2, x + b / 2, x + b / 2 + d / 2, x + b / 2 + d / 2};
        int[] ys = {y + e, y + a - e / 5, y + a, y + a - e / 5, y + e};
        int n = xs.length;
        g2d.fillPolygon(xs, ys, n);
        g2d.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("нужно: (" + tmpX + ", " + tmpY + ") сейчас: (" + getLocation().x + ", " + getLocation().y + ")");
        if (getLocation().x == tmpX && getLocation().y == tmpY) {
            timer.stop();
            if (tmpY + getSize().height == 0) {
                this.getParent().remove(this);
            }
            this.startX = tmpX;
            this.startY = tmpY;

        } else {
            setLocation(getLocation().x + velocityX, getLocation().y + velocityY);
            repaint();
        }
    }


    private int gcd(int a, int b) {
        while (b != 0) {
            int tmp = a % b;
            a = b;
            b = tmp;
        }
        return a;
    }


    public void changeLocation(int x, int y) {
        Dimension size = getSize();
        this.tmpX = x - (int) size.getWidth() / 2;
        this.tmpY = y - (int) size.getHeight();
        int a = tmpX - getLocation().x;
        int b = tmpY - getLocation().y;
        int k = Math.abs(gcd(a, b));
        int vX = a / k;
        int vY = b / k;

        boolean bool = (Math.abs(vX) == 1 || Math.abs(vX) == 0) && (Math.abs(vY) == 1 || Math.abs(vY) == 0);
        System.out.println(bool);
        if (bool) {
            vX = vX * 20;
            System.out.println(vX);
            vY = vY * 20;
            System.out.println(vY);
        }
        velocityX = vX;
        velocityY = vY;
        System.out.println(velocityX + " " + velocityY);
        timer.start();
    }


    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Icon icon = (Icon) o;
        return tmpX == icon.tmpX &&
                tmpY == icon.tmpY &&
                velocityX == icon.velocityX &&
                velocityY == icon.velocityY &&
                startX == icon.startX &&
                startY == icon.startY &&
                Objects.equals(id, icon.id) &&
                Objects.equals(color, icon.color) &&
                Objects.equals(timer, icon.timer);
    }

    public Icon clone() {
        return new Icon(id, color, getSize(), startX, startY);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, color, timer, tmpX, tmpY, velocityX, velocityY, startX, startY);
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Icon{" +
                "id=" + id +
                ", color=" + color +
                ", tmpX=" + tmpX +
                ", tmpY=" + tmpY +
                '}';
    }
}