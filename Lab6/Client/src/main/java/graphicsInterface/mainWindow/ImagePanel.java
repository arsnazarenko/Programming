package graphicsInterface.mainWindow;

import graphicsInterface.ErrorConstants;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImagePanel extends JPanel {

    private BufferedImage happy_cat;
    private BufferedImage sad_cat;
    private BufferedImage sleep_cat;
    private BufferedImage tired_cat;
    private BufferedImage normal_cat;
    private BufferedImage curr_cat;


    public ImagePanel(ErrorConstants errorConstants) {
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(150, 150));

        try {
            happy_cat = ImageIO.read(new File("src/main/resources/image/happy_cat.png"));
            sad_cat = ImageIO.read(new File("src/main/resources/image/sad_cat.png"));
            sleep_cat = ImageIO.read(new File("src/main/resources/image/sleeping_cat.png"));
            tired_cat = ImageIO.read(new File("src/main/resources/image/tired_cat.png"));
            normal_cat = ImageIO.read(new File("src/main/resources/image/normal_cat.png"));
            curr_cat = normal_cat;
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null,errorConstants.getError_7(), errorConstants.getTitle(), JOptionPane.ERROR_MESSAGE);
            System.out.println("Ошибка загрузки изображения");
        }
    }

    void chooseImage(int index) {
        switch (index) {
            case 0:
                curr_cat = normal_cat;
                break;
            case 1:
                curr_cat = sad_cat;
                break;
            case 2:
                curr_cat = happy_cat;
                break;
            case 3:
                curr_cat = sleep_cat;
                break;
            case 4:
                curr_cat = tired_cat;
                break;
        }
        repaint();
        revalidate();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(curr_cat, 0, 0, null);
    }

}