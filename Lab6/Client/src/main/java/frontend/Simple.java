package frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Simple extends JFrame {

    public Simple() {
        final JTextField tf = new JTextField();
        tf.setBounds(5, 50, 150, 20);
        JButton button = new JButton("click");
        button.setBounds(5, 80, 100, 40);
        JLabel l1 = new JLabel();
        l1.setBounds(5, 10, 150, 30);
        button.addActionListener((e) -> {
            tf.setText("Listener");
            try {
                String info = InetAddress.getLocalHost().toString();
                l1.setText(info);
            } catch (UnknownHostException ex) {
                ex.printStackTrace();
            }
        });
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(10, 130, 300, 300);
        panel.setBackground(Color.GRAY);

        JPanel panelTwo = new JPanel();
        TestPanel testPanel = new TestPanel();
        setSize(650, 600);


        add(l1);
        add(button);
        add(tf);
        add(panel);
        add(testPanel);
        setLayout(null);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    static class Main {
        public static void main(String[] args) {
            new Simple();
        }
    }

    static class TestPanel extends JPanel {
        public TestPanel() {
            setLayout(null);
            setBounds(320, 130, 300, 400);
        }

        @Override
        protected void paintComponent(Graphics g) {
            int x = 200;
            int y = 200;
            int size = 2 + 3;
            int b = size/2 * 3;
            int c = size * 5;
            int z = c / 2 * 3;
            int d = size * 5;
            int e = size * 3;
            int f = size*2;
            int j = f;
            int[] xs = {x, x + size / 2, x + size / 2, x - size / 2, x - size / 2};
            int[] ys = {y, y - b, y - b - c, y - b - c, y - b};
            int n = xs.length;
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(Color.green);
            g2d.fillPolygon(xs, ys, n);
            //g2d.fillRect(xs - b / 2, ys - b - c, b, c);
            g2d.fillArc(x - d/2, y - b - c - f, d, f*2, 0, 180) ;
            g2d.fillRect(x - e/2, y - b - c - z, e, z);
            g2d.fillArc(x - d/2, y - b - c - z - j, d, j*2, 180, 180);
        }
    }

}
