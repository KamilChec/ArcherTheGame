package pl.edu.pw.fizyka.pojava.ArcherTheGame;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Obstacle implements Runnable{
	volatile int xPos, yPos, lenght, width;
	int velocity, yVelocity;
	BufferedImage obstackeImage;
	JPanel panel;
	
	public Obstacle(JPanel panel) {
		this.panel = panel;
		xPos = 465;
		yPos = 300;
		velocity = 1;
		yVelocity = 1;
		
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
	public int getPos() {
		return yPos;
	}
	@Override
	public void run() {
		while(true) {
			lenght += velocity;
			yVelocity = -velocity;
			yPos += yVelocity;
			if(lenght == 250) velocity = -1;
			if(lenght == 100) velocity = +1;
			
			try {
				Thread.sleep(80);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
			panel.repaint(); 
		}
	}
}
