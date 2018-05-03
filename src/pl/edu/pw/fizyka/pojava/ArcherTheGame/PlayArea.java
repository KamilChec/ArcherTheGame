package pl.edu.pw.fizyka.pojava.ArcherTheGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class PlayArea extends JPanel implements Runnable {
	int cons=1;
	static int count=1;
	
	double xPos, yPos, vx, vy, dVx, dVy, alpha, beta, gamma, a, v0, g, ro, diameter, mass, coeff, force;
	boolean whenDraw = false;
	Point startPoint, endPoint;

	PlayArea(JTextField shotAngle, JTextField shotStrength)
	{
		coeff = 1;
		g = 9.81;
		ro = 1.2;
		diameter = 0.1;
		mass = 0.1;
//		alpha = 45;
		a = coeff*diameter*ro*mass;
		v0 = 100;
    	vx = v0*Math.cos(Math.toRadians(alpha));
    	vy = v0*Math.sin(Math.toRadians(alpha));    	
		beta = alpha;
		xPos = 0;
		yPos = 500;
		
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				startPoint = new Point(e.getX(), e.getY());
				endPoint = new Point(startPoint);
				whenDraw = true;
				repaint();
			}
			public void mouseReleased(MouseEvent e) {
//				alpha = getAlpha();
//				force = getForce();
//				shotAngle.setText(String.valueOf(getAlpha()));
//				shotStrength.setText(String.valueOf(forceToPower(force)));
				System.out.println("Force: " + String.valueOf(Math.round(getForce())));    // możesz sprawdzić jak działa potem usunac
				System.out.println("Angle: " + String.valueOf(Math.round(getAlpha())));
				System.out.println("Power: " + forceToPower(force));
				
				whenDraw = false;
				repaint();
			}
		});
		addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent e) {
				endPoint = new Point(e.getX(), e.getY());
				alpha = getAlpha();
				force = getForce();
				shotAngle.setText(String.valueOf(Math.round(getAlpha())));
				shotStrength.setText(String.valueOf(forceToPower(force)));
				
				repaint();
			}
		});
	}
	public double getAlpha() {

		int xLenght = startPoint.x - endPoint.x;
		int yLenght = endPoint.y - startPoint.y;
		double lineLength = Math.sqrt((xLenght*xLenght) + (yLenght*yLenght));
	    if(yLenght > 0) {
	    	return Math.toDegrees(Math.acos(xLenght/lineLength));
	    }else {
	    	return -(Math.toDegrees(Math.acos(xLenght/lineLength)));
	    }
	}
	public double getForce() {
		int xLenght = startPoint.x - endPoint.x;
		int yLenght = endPoint.y - startPoint.y;
		double lineLength = Math.sqrt((xLenght*xLenght) + (yLenght*yLenght));
		 if(lineLength <= 150 ) {
			 if(lineLength > 50) {
				 return lineLength;
			 } else {
				 return 50;
			 } 
		 } else {
			 return 150;
		 }
	}
	public int forceToPower(double force) {
		if(force > 50) {
			return (int) (force*(100f/150));
		} else {
			return 0;
		}
			
	}
	
	public void Translate(double t)
	{
		dVx=-a*vx*t/mass;
		dVy=-a*vy*t/mass-g*t;
		
		System.out.println("VX" + Double.toString(vx));
		System.out.println("VX" + Double.toString(vy));
		
		xPos=xPos + (vx*t);		
		yPos=yPos - (vy*t);
		repaint();
	
		gamma=Math.atan(Math.toRadians(((vy*t)/(vx*t)))); 
	
		vx=vx+dVx;
		vy=vy+dVy;
	}	
	public void paintComponent(Graphics g){        
		super.paintComponent(g);
	    Graphics2D g2d = (Graphics2D)g;
	    g2d.setColor(Color.BLACK);
		if(whenDraw) {
			g.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
			g.drawOval(startPoint.x - 10, startPoint.y - 10, 20, 20);
		}
	    if(count==2)
	    {
	    	g2d.rotate(Math.toRadians(-45), xPos, yPos);
	    	g2d.fillRect((int) xPos, (int) yPos, 20, 2);
	    }
	    else
	    {
	    	if(count>10){
	    		if(vy>0)
	    		{	    			    	
	    			g2d.rotate(Math.toRadians(-(beta-gamma)), xPos, yPos);
	    			g2d.fillRect((int) xPos, (int) yPos, 20, 2);
	    			beta=beta-gamma;
		    	
	    		}
	    		else if(vy<0)
	    		{    				    	
	    			g2d.rotate(Math.toRadians(-(beta-gamma)), xPos, yPos);
	    			g2d.fillRect((int) xPos, (int) yPos, 20, 2);
	    			beta = gamma+beta;		    	
	    		}	    	
	    	}
	    }
	    
    }

	@Override
	public void run() {
		beta=alpha;
		vx=v0*Math.cos(Math.toRadians(alpha));
    	vy=v0*Math.sin(Math.toRadians(alpha));
		
		while(cons==1)
		{
			System.out.println(Integer.toString(cons));
			System.out.println("X" + Double.toString(xPos));
			System.out.println("Y" + Double.toString(yPos));
			
			Translate(0.001);
			repaint();
			count++;
			try {
				Thread.sleep(1);
			} 
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(yPos>500)
			{
				cons=0;
				beta=alpha;				
			}			
		}
	}	
}
