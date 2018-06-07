package pl.edu.pw.fizyka.pojava.ArcherTheGame;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Obstacle {
	int xPos, yPos, lenght, width;
	BufferedImage obstackeImage;
	
	public Obstacle() {
		xPos = 465;
		yPos = 300;
		
		try {
			obstackeImage = ImageIO.read(getClass().getResource("/images/obstacle.png"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		lenght = (int) (obstackeImage.getHeight()*0.3);
		width = (int) (obstackeImage.getWidth()*0.3);
	}
	public void drawObstacle(Graphics2D g2d) {
		g2d.drawImage(obstackeImage, (int) xPos, (int) yPos, width, lenght, null);
	}
}
