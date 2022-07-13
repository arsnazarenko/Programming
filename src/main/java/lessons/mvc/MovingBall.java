package lessons.mvc;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MovingBall {
    private static final int WIDTH = 480;
    private static final int HEIGHT = 360;
    private static final int DIAMETER = 20;
    private static final int FREQ = 10;
    private static final Color BACKGROUND_COLOR = Color.WHITE;
    private static final Color BALL_COLOR = Color.RED;

    private int xPos = 0;
    private int yPos = 0;
    private int dX = -1;
    private int dY = -1;
    private JPanel ballPanel;

    MovingBall() {
        ballPanel = new JPanel() {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(WIDTH, HEIGHT);
            }

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g.setColor(BALL_COLOR);

                g.fillOval(xPos, yPos, DIAMETER, DIAMETER);
            }
        };
        ballPanel.setOpaque(true);
        ballPanel.setBackground(BACKGROUND_COLOR);

        Timer timer = new Timer(FREQ, new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                if ( xPos < 1 || xPos + DIAMETER > WIDTH - 1 )
                    dX *= -1;
                if ( yPos < 1 || yPos > HEIGHT - DIAMETER - 1 )
                    dY *= -1;
                xPos += dX;
                yPos += dY;
                ballPanel.repaint();
            }
        });

        JFrame mainFrame = new JFrame("Moving Ball");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.add(ballPanel);
        mainFrame.setSize(WIDTH, HEIGHT + DIAMETER);
        mainFrame.setVisible(true);
        timer.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MovingBall();
            }
        });
    }
}
