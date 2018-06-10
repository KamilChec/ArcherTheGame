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
	int yVelocity, xVelocity;
	volatile int health = 1;
	List<BufferedImage> beeImages;
	List<BufferedImage> healthImages;
	int imageIndex = 0;
	JPanel panel;
	int width, length;
	int panelWidth, panelLength;
	AudioPlayer hitSound, victory;
	
	public Enemy(JPanel panel) {
		this.panel = panel;
		panelWidth = panel.getWidth();
		panelLength = panel.getHeight();
		xPos = 750;
		yPos = 250;
		yVelocity = 5;
		xVelocity = -2;
		beeImages = new ArrayList<BufferedImage>();
		healthImages = new ArrayList<BufferedImage>();
		hitSound = new AudioPlayer("/audio/classic_hurt.mp3");
		victory = new AudioPlayer("/audio/victory.mp3");
		for (int i = 0; i < 6; i++) {
			String image = "/images/beeImages/" + (i+1) + ".png";
			try {
				beeImages.add(ImageIO.read(getClass().getResource(image)));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		for(int i = 0; i < 7; i++) {
			String image = "/images/healthBar/" + (i) + ".png";
			try {
				healthImages.add(ImageIO.read(getClass().getResource(image)));
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
	public void drawHealth(Graphics2D g2d) {
		int barXPos = xPos + 30;
		int barYPos = yPos - 30;
		g2d.drawImage(healthImages.get(health),barXPos, barYPos, panel);
	}
	public void hit() {
		health--;
		if(health == 0) {
			kill();
		}
		hitSound.play();
	}
	public void kill() {
		victory.play();
	}
	public void continueGame() {
		health = 6;
	}

	@Override
	public void run() {
		while(true) {
			yPos += yVelocity;
			xPos += xVelocity;

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
