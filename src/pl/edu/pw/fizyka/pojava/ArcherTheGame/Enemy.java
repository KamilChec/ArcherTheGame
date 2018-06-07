package pl.edu.pw.fizyka.pojava.ArcherTheGame;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Enemy implements Runnable {
	
	int xPos, yPos;
	double yVelocity, xVelocity;
	int health = 100;
	List<BufferedImage> beeImages;
	int imageIndex = 0;
	JPanel panel;
	int width, length;
	int panelWidth, panelLength;
	AudioPlayer hitSound;
	
	public Enemy(JPanel panel) {
		this.panel = panel;
		panelWidth = panel.getWidth();
		panelLength = panel.getHeight();
		xPos = 750;
		yPos = 250;
		yVelocity = 5;
		xVelocity = -2;
		beeImages = new ArrayList<BufferedImage>();
		hitSound = new AudioPlayer("/audio/enemyHit.mp3");
		for (int i = 0; i < 6; i++) {
			String image = "/images/beeImages/" + (i+1) + ".png";
			try {
				beeImages.add(ImageIO.read(getClass().getResource(image)));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		width = (int) (beeImages.get(1).getWidth()*0.3);
		length = (int) (beeImages.get(1).getHeight()*0.3);
	}
	
	public void drawEnemy(Graphics2D g2d){
        g2d.drawImage(beeImages.get(imageIndex),xPos, yPos, width, length, panel);
        if (imageIndex < 6) imageIndex++;
        if (imageIndex == 6) imageIndex = 0;
    }
	public void hit() {
		health -= 20;
		hitSound.play();
	}

	@Override
	public void run() {
		while(true) {
//			yPos += yVelocity;
//			xPos += xVelocity;

			if(xPos < panelWidth*0.6) {
				xVelocity = 2;
			}
			if(xPos > panelWidth*0.85) {
				xVelocity = -2;
			}
			if(yPos < panelLength*0.2) {
				yVelocity = 5;
			} else if(yPos > panelLength*0.8) {
				yVelocity = -5;
			}
			
			try {
				Thread.sleep(80);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
			panel.repaint(); 
		}
		
	}
}
