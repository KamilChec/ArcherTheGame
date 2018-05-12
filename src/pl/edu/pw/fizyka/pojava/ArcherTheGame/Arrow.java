package pl.edu.pw.fizyka.pojava.ArcherTheGame;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Arrow implements Runnable {
	BufferedImage arrowImage;
	
	double xPos, yPos, vx, vy, dVx, dVy, alpha, beta, gamma, a, v0, g, ro, diameter, mass, coeff, force;
	int width, length;
	boolean shoot = true;
	boolean fly = true;
	
	JPanel panel;
	
	public Arrow(JPanel panel, double alpha, int force, double xPos, double yPos ) {
		this.alpha = alpha;
		this.panel = panel;
		width = panel.getWidth();
		length = panel.getHeight();

		coeff = 1;
		g = 9.81;
		ro = 1.2;
		diameter = 0.1;
		mass = 0.1;
		a = coeff*diameter*ro*mass;
		v0 = force;    	
		this.xPos = xPos;
		this.yPos = yPos;
		try {
			arrowImage = ImageIO.read(getClass().getResource("/images/arrow2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void Translate(double time) {		
		xPos += vx*time;
		yPos -= vy*time;
		
		dVx = a*vx*time/mass;
		dVy = a*vy*time/mass - g*time;

		vx -= dVx;
		vy += dVy;
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
			Translate(0.001);
			try {
				Thread.sleep(1);
			} 
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			panel.repaint();
			if(xPos > width || yPos > length) fly = false;
			if(xPos < 0 || yPos < 0) fly = false;
		}


	}
	
	public void drawArrow(Graphics2D g2d) {
//		AffineTransform tx = AffineTransform.getRotateInstance(-Math.atan(vy/vx), (int) xPos, (int) yPos);
//		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
//		g2d.drawImage(op.filter(arrowImage, null), (int) xPos, (int) yPos, panel);
		
		Rectangle2D myRect = new Rectangle2D.Double((int) xPos, (int) yPos, 20, 2);
		AffineTransform at = AffineTransform.getRotateInstance(-Math.atan(vy/vx), (int) xPos, (int) yPos);
		Shape rotatedRect = at.createTransformedShape(myRect);
		g2d.fill(rotatedRect);
		g2d.draw(rotatedRect);
//		g2d.drawImage(arrowImage, (int) xPos, (int) yPos, panel);
	}

		
	
}
