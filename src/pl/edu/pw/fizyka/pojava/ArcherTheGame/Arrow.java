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
	BufferedImage arrowImage;
	
	double xPos, yPos, vx, vy, dVx, dVy, alpha, beta, gamma, a, v0, g, ro, diameter, mass, coeff, force, wind, wind1;
	int width, length, arrowWidth, arrowLength;
	boolean shoot = true;
	boolean fly = true;
	static int counter=0;
	static int randomX=0;
	static int randomY=0;
	static int randomWidth=0; 
	static int randomHeigth=0;
	
	JPanel panel;
	Obstacle obstacle;
	Enemy enemy;
	
	public Arrow(JPanel panel, double alpha, int force, double xPos, double yPos ) {
		obstacle = new Obstacle();
		enemy = new Enemy(panel);
		this.alpha = alpha;
		this.panel = panel;
		width = panel.getWidth();
		length = panel.getHeight();
		
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
		v0 = force;
		
		wind=0;
		wind1=0;
		
		this.xPos = xPos;
		this.yPos = yPos;
		try {
			arrowImage = ImageIO.read(getClass().getResource("/images/arrow1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		counter++;
		
		arrowWidth = (int) (arrowImage.getWidth()*0.2);
		arrowLength = (int) (arrowImage.getHeight()*0.2);
	}
	
	public void Translate(double time) {		
		xPos += vx*time;
		yPos -= vy*time;
		
		dVx = a*vx*time/mass+(wind-wind1)/100;
		dVy = a*vy*time/mass - g*time;
		
		System.out.println("wind: " + Double.toString(((wind-wind1)/100)));
		System.out.println("dvx: " + Double.toString((dVx)));

		vx -= dVx;
		vy += dVy;
	}
	
	public void setWind()
	{
		Random r = new Random();
		wind=1+r.nextInt(10);
		wind1=-1+r.nextInt(10);
		
		System.out.println("wind: " + Double.toString((wind)));
		System.out.println("wind1: " + Double.toString((wind1)));
	}

	@Override
	public void run() {
		if(shoot) {
			beta = alpha;
			vx = v0*Math.cos(Math.toRadians(alpha));
			vy = v0*Math.sin(Math.toRadians(alpha));
			shoot = false;
		}
		while(fly) {
//			Translate(0.001);
			Translate(0.01);          // testowo
			try {
				Thread.sleep(1);
			} 
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			panel.repaint();
			if(xPos > width || yPos > length) fly = false;
			if(xPos < 0 || yPos < 0) fly = false;
			if((xPos  > obstacle.xPos - 15 && xPos < obstacle.xPos + obstacle.width) &&
					(yPos < obstacle.yPos + obstacle.lenght && yPos > obstacle.yPos - 5)) fly = false;
			if((xPos  > enemy.xPos + 5 && xPos < enemy.xPos + enemy.width) &&
					(yPos < enemy.yPos + enemy.length - 15 && yPos > enemy.yPos - 5)) {
						fly = false;
						enemy.hit();
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
		
		   AffineTransform backup = g2d.getTransform();
		   AffineTransform at = AffineTransform.getRotateInstance(-Math.atan(vy/vx), (int) xPos, (int) yPos);
		   g2d.setTransform(at);
		   g2d.drawImage(arrowImage, (int) xPos, (int) yPos, 78, 7, panel);
		   g2d.setTransform(backup);

	}

		
	
}
