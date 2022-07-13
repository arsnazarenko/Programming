package lessons.mvc;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DrawPanel extends JPanel implements Scrollable {
    private final int CELL_SIZE;
    private final int CELL_COUNT;


    public DrawPanel(int cellSize, int cellCount) {
        this.CELL_SIZE = cellSize;
        this.CELL_COUNT = cellCount;
        setLayout(null);
    }



//    private void paintIcon(Graphics2D g2d, int size, int x, int y, Color color) {
//        int b = size / 2 * 3;
//        int c = size * 5;
//        int z = c / 2 * 3;
//        int d = size * 5;
//        int e = size * 3;
//        int f = size*2;
//        int j = f;
//        int[] xs = {x, x + size / 2, x + size / 2, x - size / 2, x - size / 2};
//        int[] ys = {y, y - b, y - b - c, y - b - c, y - b};
//        int n = xs.length;
//        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        g2d.setColor(color);
//        g2d.fillPolygon(xs, ys, n);
//        g2d.fillArc(x - d/2, y - b - c - f, d, f*2, 0, 180) ;
//        g2d.fillRect(x - e/2, y - b - c - z, e, z);
//        g2d.fillArc(x - d/2, y - b - c - z - j, d, j*2, 180, 180);
//        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
//    }



    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        for (int x = 0; x < CELL_COUNT; x++) {
            for (int y = 0; y < CELL_COUNT; y++) {
                g2d.setColor(Color.LIGHT_GRAY);
                g2d.drawRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }
        g2d.setStroke(new BasicStroke(2));
        g2d.setColor(Color.BLACK);
        g2d.drawLine(CELL_SIZE * CELL_COUNT / 2, 0, CELL_SIZE * CELL_COUNT / 2, CELL_SIZE * CELL_COUNT);
        g2d.drawLine(0, CELL_SIZE * CELL_COUNT / 2, CELL_SIZE * CELL_COUNT, CELL_SIZE * CELL_COUNT / 2);
        g2d.setStroke(new BasicStroke());

    }

    public int getCellCount() {
        return CELL_COUNT;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(CELL_SIZE * CELL_COUNT, CELL_SIZE * CELL_COUNT);
    }

    @Override
    public Dimension getPreferredScrollableViewportSize() {
        return getPreferredSize();
    }

    @Override
    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
        return CELL_SIZE;
    }

    @Override
    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
        return CELL_SIZE * 10;
    }

    @Override
    public boolean getScrollableTracksViewportWidth() {
        return false;
    }

    @Override
    public boolean getScrollableTracksViewportHeight() {
        return false;
    }

}
