package pl.edu.pw.fizyka.pojava.ArcherTheGame;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
 
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class BackgroundFiller extends JPanel {
	 
    private BufferedImage image;
 
    public BackgroundFiller() {
        super();
        
        URL resource = getClass().getResource("images/bard-the-bowman.jpg");        
        try {
            image = ImageIO.read(resource);
        } catch (IOException e) {
            System.err.println("Blad odczytu obrazka");
            e.printStackTrace();
        }
 
        Dimension dimension = new Dimension(image.getWidth(), image.getHeight());
        setPreferredSize(dimension);
    }
 
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(image, 0, 0, null);
       
    }
}


