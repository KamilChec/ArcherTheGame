package pl.edu.pw.fizyka.pojava.ArcherTheGame;


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.util.ArrayList;


import javax.imageio.ImageIO;
public class Player {
	int health;
	int damage;
	int xPos;
	int yPos;
										 //	 angle:
	ArrayList<BufferedImage> firstArea;  //  (-90, -10)
	ArrayList<BufferedImage> secondArea; //  <-10, 10>
	ArrayList<BufferedImage> thirdArea;  //  (10, 45)
	ArrayList<BufferedImage> fourthArea; //	 <45, 80>
	ArrayList<BufferedImage> fifthArea;  //  (80, 90>
	ImageObserver observer;
	
	public Player(ImageObserver observer) {
		this.observer = observer;
		firstArea = new ArrayList<BufferedImage>();
		secondArea = new ArrayList<BufferedImage>();
		thirdArea = new ArrayList<BufferedImage>();
		fourthArea = new ArrayList<BufferedImage>();
		fifthArea = new ArrayList<BufferedImage>();
		xPos = 100;
		yPos = 300;
		
		for( int ii = 0; ii < 3; ii++) {
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
	}
	
	public BufferedImage loadImage(String name) throws IOException {
		return ImageIO.read(getClass().getResource(name));
	}
	public void drawPlayer(Graphics2D g2d, ArrayList<BufferedImage> images, int power) {
		if(power <= 30)                 g2d.drawImage(images.get(0), xPos, yPos, 159, 174, observer);
		if(power > 30 && power < 70)    g2d.drawImage(images.get(1), xPos, yPos, 159, 174, observer);
		if(power >= 70 && power <= 100) g2d.drawImage(images.get(2), xPos, yPos, 159, 174, observer);
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
