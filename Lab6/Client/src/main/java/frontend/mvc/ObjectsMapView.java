package frontend.mvc;

import library.сlassModel.Organization;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ObjectsMapView extends JPanel {
    private DrawPanel drawPanel;
    private JTextArea objectsInfo;
    private JPanel southPanel;
    private JScrollPane scrollPane;
    private JScrollPane scrollPaneForObjectsInfo;
    private JButton clearButton;
    private JPanel buttonPanel;

    public ObjectsMapView(Map<Organization, Map.Entry<Integer, Point>> orgCoordInfo, int cellSize, int cellCount) {
//        super("Organizations Map");
        init(orgCoordInfo, cellSize, cellCount);
    }

    public void init(Map<Organization, Map.Entry<Integer, Point>> orgCoordInfo, int cellSize, int cellCount) {
        drawPanel = new DrawPanel(orgCoordInfo, cellSize, cellCount);
        scrollPane = new JScrollPane(drawPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        scrollPane.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5),
                BorderFactory.createLineBorder(Color.black)));
        scrollPane.setWheelScrollingEnabled(true);

        Font myFont = new Font("lucida grande", Font.BOLD, 15);
        objectsInfo = new JTextArea();
        objectsInfo.setEditable(false);
        objectsInfo.setLineWrap(true);
        objectsInfo.setWrapStyleWord(true);
        objectsInfo.setFont(myFont);


        scrollPaneForObjectsInfo = new JScrollPane(objectsInfo, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPaneForObjectsInfo.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5),
                BorderFactory.createLineBorder(Color.black)));
        scrollPaneForObjectsInfo.setPreferredSize(new Dimension(1000, 150));


        southPanel = new JPanel();
        southPanel.setLayout(new BorderLayout());
        southPanel.add(scrollPaneForObjectsInfo, BorderLayout.CENTER);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        clearButton = new JButton("Clear");
        clearButton.setSize(20, 20);

        buttonPanel.add(clearButton);
        southPanel.add(buttonPanel, BorderLayout.WEST);

//        getContentPane().setLayout(new BorderLayout());
//        getContentPane().add(southPanel, BorderLayout.SOUTH);
//        getContentPane().add(scrollPane, BorderLayout.CENTER);

        setLayout(new BorderLayout());
        add(southPanel, BorderLayout.SOUTH);
        add(scrollPane, BorderLayout.CENTER);

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(900, 900);
        int x = (int) ((dimension.getWidth() - getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - getHeight()) / 2);
        setLocation(x, y);
//        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

    }

    public DrawPanel getDrawPanel() {
        return drawPanel;
    }

    public JTextArea getObjectsInfo() {
        return objectsInfo;
    }

    public JPanel getSouthPanel() {
        return southPanel;
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    public JScrollPane getScrollPaneForObjectsInfo() {
        return scrollPaneForObjectsInfo;
    }

    public JButton getClearButton() {
        return clearButton;
    }

    public JPanel getButtonPanel() {
        return buttonPanel;
    }

    public void panelUpdate(Deque<Organization> organizations) {

    }

    @Override
    public void repaint() {
        super.repaint();

    }

    public void setDrawPanel(DrawPanel drawPanel) {
        this.drawPanel = drawPanel;
    }
}
