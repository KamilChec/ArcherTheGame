package pl.edu.pw.fizyka.pojava.ArcherTheGame;


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class Player {
	int health = 6;
	int damage;
	int xPos;
	int yPos;
	int width, lenght;
	boolean multiplayer;
	AudioPlayer hitSound, victory;
										 //	 angle:
	ArrayList<BufferedImage> firstArea;  //  (-90, -10)
	ArrayList<BufferedImage> secondArea; //  <-10, 10>
	ArrayList<BufferedImage> thirdArea;  //  (10, 45)
	ArrayList<BufferedImage> fourthArea; //	 <45, 80>
	ArrayList<BufferedImage> fifthArea;  //  (80, 90>
	List<BufferedImage> healthImages;
	JPanel panel;
	
	public Player(JPanel panel, boolean multiplayer) {
		this.panel = panel;
		this.multiplayer = multiplayer;
		firstArea = new ArrayList<BufferedImage>();
		secondArea = new ArrayList<BufferedImage>();
		thirdArea = new ArrayList<BufferedImage>();
		fourthArea = new ArrayList<BufferedImage>();
		fifthArea = new ArrayList<BufferedImage>();
		healthImages = new ArrayList<BufferedImage>();
		if(multiplayer) {
//			xPos = (int) (panel.getWidth()*0.8);
			xPos = 700;
		} else {
//			xPos = (int) (panel.getWidth()*0.2); 
			xPos = 150;
		}
		yPos = 270;
	
		
		for( int ii = 0; ii < 6; ii++) {
			try {
				firstArea.add(loadImage("/images/shotPositiones/minus45degrees" + ii + ".png"));
				secondArea.add(loadImage("/images/shotPositiones/0degrees" + ii + ".png"));
				thirdArea.add(loadImage("/images/shotPositiones/30degrees" + ii + ".png"));
				fourthArea.add(loadImage("/images/shotPositiones/60degrees" + ii + ".png"));
				fifthArea.add(loadImage("/images/shotPositiones/90degrees" + ii + ".png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		for(int ii = 0; ii < 7; ii++) {
			String image = "/images/healthBar/" + (ii) + ".png";
			try {
				healthImages.add(ImageIO.read(getClass().getResource(image)));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		hitSound = new AudioPlayer("/audio/classic_hurt.mp3");
		victory = new AudioPlayer("/audio/announcerVictory.mp3");
		width = (int) (firstArea.get(1).getWidth()*0.3);
		lenght = (int) (firstArea.get(1).getHeight()*0.3);
	}
	
	public BufferedImage loadImage(String name) throws IOException {
		return ImageIO.read(getClass().getResource(name));
	}
	public void drawHealth(Graphics2D g2d) {
		int barXPos;
		int barYPos;
		if(multiplayer) {
			barXPos = xPos + 100;
			barYPos = yPos + 15;
		} else {
			barXPos = xPos + 10;
			barYPos = yPos + 15;
		}
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
	public void drawPlayer(Graphics2D g2d, ArrayList<BufferedImage> images, int power) {
		if(multiplayer) {
			if(power <= 30)                 g2d.drawImage(images.get(3), xPos, yPos, width, lenght, panel);
			if(power > 30 && power < 70)    g2d.drawImage(images.get(4), xPos, yPos, width, lenght, panel);
			if(power >= 70 && power <= 100) g2d.drawImage(images.get(5), xPos, yPos, width, lenght, panel);
			
		} else {
			if(power <= 30)                 g2d.drawImage(images.get(0), xPos, yPos, width, lenght, panel);
			if(power > 30 && power < 70)    g2d.drawImage(images.get(1), xPos, yPos, width, lenght, panel);
			if(power >= 70 && power <= 100) g2d.drawImage(images.get(2), xPos, yPos, width, lenght, panel);
		}
	}

	public void prepareToShot(Graphics g, double angle, int power) {
		Graphics2D g2d = (Graphics2D) g;
		
		if(angle > -180 && angle < -10) 		 drawPlayer(g2d, firstArea, power);
		else if(angle >= -10 && angle <= 10) drawPlayer(g2d, secondArea, power);
		else if(angle > 10 && angle < 45)	 drawPlayer(g2d, thirdArea, power);
		else if(angle >= 45 && angle <= 80)  drawPlayer(g2d, fourthArea, power);
		else if(angle > 80 && angle <= 180)	 drawPlayer(g2d, fifthArea, power);
	}
	

}
