package frontend;

import library.сlassModel.Coordinates;
import library.сlassModel.Organization;
import library.сlassModel.OrganizationType;
import sun.swing.DefaultLayoutStyle;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DrawTest extends JFrame {
    private DrawPanel panel;
    private JScrollPane scrollPane;


    public void setPanel(DrawPanel panel) {
        this.panel = panel;
    }

    public void init(Deque<Organization> organizations) {

//        JButton button = new JButton();
//        button.setSize(20, 20);
//        button.addActionListener(e -> {
//            if (panel != null) {
//                Container contentPane = getContentPane();
//                contentPane.remove(scrollPane);
//
//                this.panel = new DrawPanel(new ArrayList<>());
//                scrollPane.setViewportView(panel);
//                contentPane.add(scrollPane);
//                contentPane.revalidate();
//                contentPane.repaint();
//            }
//
//        });
        //        scrollPane.setColumnHeaderView(new XHeader());
//        scrollPane.setRowHeaderView(new YHeader());
        //scrollPane.setCorner(JScrollPane.UPPER_LEFT_CORNER, button);
        this.panel = new DrawPanel(organizations);
        this.scrollPane = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setViewportBorder(BorderFactory.createLineBorder(Color.BLACK));
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(scrollPane);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(520, 520);
        int x = (int) ((dimension.getWidth() - getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - getHeight()) / 2);
        setLocation(x, y);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        scrollPane.setWheelScrollingEnabled(true);

        setVisible(true);
    }

    public DrawTest(Deque<Organization> organizations) {
        super("DrawingTest");
        init(organizations);
    }

    public void panelUpdate(Deque<Organization> organizations) {
        if (panel != null) {
            Container contentPane = getContentPane();
            contentPane.remove(scrollPane);
            this.panel = new DrawPanel(organizations);
            this.scrollPane.setViewportView(panel);
            contentPane.add(scrollPane);
            contentPane.revalidate();
            contentPane.repaint();
        }
    }

    static class DrawPanel extends JPanel implements Scrollable {
        private int CELL_SIZE = 10;
        private int CELL_COUNT;
        private int CONST;
        private Deque<Map.Entry<Integer, Integer>> draw;


        public DrawPanel(Deque<Organization> organizations) {
            int max;
            //if (!organizations.isEmpty()) {

                max = organizations.stream().
                        flatMap(o -> Stream.of(Math.round(o.getCoordinates().getX()), Math.round(o.getCoordinates().getY()))).
                        mapToInt(Number::intValue).
                        map(Math::abs).
                        filter(o -> o > 27).
                        max().
                        orElse(25) + 2;

//            } else {
//                max = 25;
//            }
            this.CONST = max;
            this.CELL_COUNT = max * 2;
            this.draw = calcCoordinate(organizations);

        }

        public Dimension getPreferredSize() {
            return new Dimension(CELL_SIZE * CELL_COUNT, CELL_SIZE * CELL_COUNT);
        }

        public Deque<Map.Entry<Integer, Integer>> calcCoordinate(Deque<Organization> organizations) {
            return organizations.stream().
                    map(o -> new AbstractMap.SimpleEntry<>((int) Math.round(o.getCoordinates().getX()) + CONST, CELL_COUNT - (Math.round(o.getCoordinates().getY()) + CONST))).
                    collect(Collectors.toCollection(ArrayDeque::new));


        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (int x = 0; x < CELL_COUNT; x++) {
                for (int y = 0; y < CELL_COUNT; y++) {
                    g.setColor(Color.black);
                    g.drawRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                }
            }
            g.setColor(Color.CYAN);
            g.drawLine(CELL_SIZE * CELL_COUNT / 2, 0, CELL_SIZE * CELL_COUNT / 2, CELL_SIZE * CELL_COUNT);
            g.drawLine(0, CELL_SIZE * CELL_COUNT / 2, CELL_SIZE * CELL_COUNT, CELL_SIZE * CELL_COUNT / 2);

            g.setColor(Color.RED);
            draw.forEach(o -> g.fillOval((o.getKey() * CELL_SIZE) - (CELL_SIZE / 2), (o.getValue() * CELL_SIZE) - (CELL_SIZE / 2), CELL_SIZE, CELL_SIZE));


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

    class XHeader extends JPanel {

        // Размер заголовка
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(panel.getPreferredSize().width, 20);
        }

        // Прорисовываем линейку
        @Override
        public void paintComponent(Graphics g) {
        }
    }

    class YHeader extends JPanel {
        // Размер заголовка
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(20, panel.getPreferredSize().height);
        }

        // Прорисовываем линейку
        @Override
        public void paintComponent(Graphics g) {

        }
    }



}
