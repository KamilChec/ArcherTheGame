package pl.edu.pw.fizyka.pojava.ArcherTheGame;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class BackgroundFiller extends JPanel {
	 
    private BufferedImage image;
 
    public BackgroundFiller() {
        super();
        
        // Plik umieszczony w podpakiecie "obrazki"
        URL resource = getClass().getResource("file:///Users/skamander/git/ArcherTheGame/src/resource/images/menuBackground.jpg");        
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
        g2d.drawImage(image, 0, 0, this);
       
        // SKALOWANIE WKLEJANEGO OBRAZKA:
        //g2d.drawImage(image, 50, 50, image.getWidth()/3, image.getHeight()/3, this);
    }
}


