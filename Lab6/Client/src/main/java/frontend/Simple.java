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
        tf.setBounds(5, 50 ,150, 20);
        JButton button = new JButton("click");
        button.setBounds(5, 80, 100, 40);
        JLabel l1 = new JLabel();
        l1.setBounds(5, 10, 150, 30);
        button.addActionListener((e) -> {tf.setText("Listener");
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
        JScrollPane scrollPane = new JScrollPane();
        panelTwo.setLayout(null);
        panelTwo.setBounds(320, 130, 300, 300);
        panelTwo.setBackground(Color.YELLOW);
        setSize(650, 600);


        add(l1);
        add(button);
        add(tf);
        add(panel);
        add(panelTwo);
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

}
