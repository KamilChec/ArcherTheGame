package pl.edu.pw.fizyka.pojava.ArcherTheGame;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;



public class Arrow implements Runnable {
	BufferedImage arrowImage1;
	BufferedImage arrowImage2;
	
	double xPos, yPos, vx, vy, dVx, dVy, alpha, beta, gamma, a, v0, g, ro, diameter, mass, coeff, force, wind, wind1;
	int width, length, arrowWidth, arrowLength;
	int obstacleVelocity = 0;
	int enemyXVelocity = 0;
	int enemyYVelocity = 0;
	boolean multiplayer;
	boolean shoot = true;
	boolean fly = true;
	boolean hit = true;
	
	
	static int counter=0;
	static int randomX=0;
	static int randomY=0;
	static int randomWidth=0; 
	static int randomHeigth=0;
	
	JPanel panel;
	Obstacle obstacle;
	Enemy enemy;
	
	public Arrow(JPanel panel, double alpha, int force, double xPos, double yPos,  boolean multiplayer, double wind) {
		obstacle = new Obstacle(panel);
		enemy = new Enemy(panel);
		this.alpha = alpha;
		this.panel = panel;
		this.multiplayer = multiplayer;
		width = panel.getWidth();
		length = panel.getHeight();
		this.wind=wind;
		
		if(counter % 5 == 0)
		{
			Random rand = new Random();
			randomX = 300+rand.nextInt(50);
			randomY = 200+rand.nextInt(50);
			randomWidth = 100+rand.nextInt(10);
			randomHeigth = 100+rand.nextInt(10);
		}

		coeff = 1;
		g = 9.81;
		ro = 1.2;
		diameter = 0.1;
		mass = 0.1;
		a = coeff*diameter*ro*mass;
		v0 = force*1.4;					
		
		this.xPos = xPos;
		this.yPos = yPos;
		try {
			arrowImage1 = ImageIO.read(getClass().getResource("/images/arrow1.png"));
			arrowImage2 = ImageIO.read(getClass().getResource("/images/arrow2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		counter++;
		
		arrowWidth = (int) (arrowImage1.getWidth()*0.3);
		arrowLength = (int) (arrowImage1.getHeight()*0.3);
	}
	
//	public void Translate(double time) {		
//		xPos += vx*time;
//		yPos -= vy*time;
//		
//		dVx = a*vx*time/mass+(wind-wind1)/100;
//		dVy = a*vy*time/mass - g*time;
//		
//		System.out.println("wind: " + Double.toString(((wind-wind1)/100)));
//		System.out.println("dvx: " + Double.toString((dVx)));
//
//		vx -= dVx;
//		vy += dVy;
//	}
	public void Translate(double time) {
		if(multiplayer) {
			xPos -= vx*time;
		} else { 
			xPos += vx*time;
		}
		yPos -= vy*time;
		
		if(multiplayer) {
			dVx = a*vx*time/mass - wind/10;
		} else { 
			dVx = a*vx*time/mass + wind/10;
		}
		
		//dVx = a*vx*time/mass + wind/10;
		dVy = a*vy*time/mass + g*time;

		vx -= dVx;
		vy -= dVy;
	}
	
//	public void setWind()
//	{
//		Random r = new Random();
//		wind=r.nextDouble()+r.nextDouble()-1;
//		
//	}

	@Override
	public void run() {
		if(shoot) {
			beta = alpha;
			vx = v0*Math.cos(Math.toRadians(alpha));
			vy = v0*Math.sin(Math.toRadians(alpha));
			
			System.out.println(Double.toString(vx));
			System.out.println(Double.toString(vy));
			
			System.out.println(Double.toString(xPos));
			System.out.println(Double.toString(yPos));
			
			shoot = false;
		}
		while(fly) {
//			if(hit) Translate(0.001);
			if(hit) Translate(0.01);       // testowo
			try {
				if(hit) Thread.sleep(1);
				if(!hit) Thread.sleep(80);
			} 
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			panel.repaint();
			if(xPos > width || yPos > length) fly = false;
			if(xPos < 0 || yPos < 0) fly = false;
			if(!hit) {
			   	yPos += obstacleVelocity;
			   	yPos += enemyYVelocity;
			   	xPos += enemyXVelocity;
		    }
			
			
			if(xPos>randomX && xPos<randomX+randomWidth)
			{				
				if(yPos>randomY && yPos<randomY+randomHeigth)
				{					
//					fly=false;         // testowo
				}
			}
		}
	}
	
	public void drawArrow(Graphics2D g2d) {
		
//		Rectangle2D myRect = new Rectangle2D.Double((int) xPos, (int) yPos, 20, 2);
//		AffineTransform at = AffineTransform.getRotateInstance(-Math.atan(vy/vx), (int) xPos, (int) yPos);
//		Shape rotatedRect = at.createTransformedShape(myRect);
//		g2d.fill(rotatedRect);
//		g2d.draw(rotatedRect);
		
		if(multiplayer) {
		    AffineTransform backup = g2d.getTransform();
		    AffineTransform at = AffineTransform.getRotateInstance(Math.atan(vy/vx), (int) xPos , (int) yPos );
		    g2d.setTransform(at);
		    g2d.drawImage(arrowImage2, (int) xPos, (int) yPos, arrowWidth, arrowLength, panel);
		    g2d.setTransform(backup);
		} else {
		    AffineTransform backup = g2d.getTransform();
		    AffineTransform at = AffineTransform.getRotateInstance(-Math.atan(vy/vx), (int) xPos , (int) yPos );
		    g2d.setTransform(at);
		    g2d.drawImage(arrowImage1, (int) xPos, (int) yPos, arrowWidth, arrowLength, panel);
		    g2d.setTransform(backup);
		}

	}
	public void stuckedArrow(int yVelocity) {
		obstacleVelocity = yVelocity;
		
	}
	public void stuckedArrow(int xVelocity,int yVelocity) {
		enemyXVelocity = xVelocity;
		enemyYVelocity = yVelocity;
	}

		
	
}
