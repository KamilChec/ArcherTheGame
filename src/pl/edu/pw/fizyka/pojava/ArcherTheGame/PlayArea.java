package pl.edu.pw.fizyka.pojava.ArcherTheGame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Rectangle;
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
	
	Player player;

	public PlayArea(Image image, JTextField shotAngle, JTextField shotStrength)
	{
		
		setImage( image );
		setLayout( new BorderLayout() );
		coeff = 1;
		g = 9.81;
		ro = 1.2;
		diameter = 0.1;
		mass = 0.1;
		a = coeff*diameter*ro*mass;
		v0 = 100;    	    	
		beta = alpha;
		xPos = 200;
		yPos = 350;
		player = new Player(this);
		
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				startPoint = new Point(e.getX(), e.getY());
				endPoint = new Point(startPoint);
				whenDraw = true;
				repaint();
			}
			public void mouseReleased(MouseEvent e) {
				whenDraw = false;
				shotAngle.setText("000");
				shotStrength.setText("000");
				
				v0=forceToPower(force);
				vx = v0*Math.cos(Math.toRadians(alpha));
		    	vy = v0*Math.sin(Math.toRadians(alpha));
				
		    	System.out.println(Double.toString(v0));
		    	System.out.println(Double.toString(alpha));
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
		private Paint painter;
		private Image image;
		 //	Set the image used as the background
		public void setImage(Image image)
		{
			this.image = image;
			repaint();
		}
		 //	Set the Paint object used to paint the background
		public void setPaint(Paint painter)
		{
			this.painter = painter;
			repaint();
		}

		private void drawScaled(Graphics g)
		{
			Dimension d = getSize();
			g.drawImage(image, 0, 0, d.width, d.height, null);
		}

	
		public double getAlpha() {

			double xLenght = startPoint.x - endPoint.x;
			double yLenght = endPoint.y - startPoint.y;		
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
			System.out.println("VY" + Double.toString(vy));
			
			xPos=xPos + (vx*t);		
			yPos=yPos - (vy*t);
			repaint();
		
			gamma=Math.atan(Math.toRadians(((vy*t)/(vx*t)))); 
		
			vx=vx+dVx;
			vy=vy+dVy;
		}	
		public void paintComponent(Graphics g){ 
			super.paintComponent(g);
			if (painter != null)
			{
				Dimension d = getSize();
				Graphics2D g2 = (Graphics2D) g;
				g2.setPaint(painter);
				g2.fill( new Rectangle(0, 0, d.width, d.height) );
			}

			//  Draw the image

			if (image == null ) return;
			
			drawScaled(g);
	
		    Graphics2D g2d = (Graphics2D)g;
			if(whenDraw) {
				g.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
				g.drawOval(startPoint.x - 10, startPoint.y - 10, 20, 20);
			}
			player.drawPlayer(g2d, player.firstArea, forceToPower(force));
			player.prepareToShot(g2d, alpha, forceToPower(force));
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
		    			g2d.rotate(Math.toRadians(-(beta-gamma)), xPos+(10*Math.cos(Math.toRadians(beta))), yPos-(Math.sin(Math.toRadians(beta))));
		    			g2d.fillRect((int) xPos, (int) yPos, 20, 2);
		    			beta=beta-gamma;
			    	
		    		}
		    		else if(vy<0)
		    		{    				    	
		    			g2d.rotate(Math.toRadians(-(beta-gamma)), xPos+(10*Math.cos(Math.toRadians(beta))), yPos-(Math.sin(Math.toRadians(beta))));
		    			g2d.fillRect((int) xPos, (int) yPos, 20, 2);
		    			beta = gamma+beta;		    	
		    		}	    	
		    	}
		    }
			
			
	    }

		@Override
		public void run() {
			if(count<2)
			{
				beta=alpha;
				vx=v0*Math.cos(Math.toRadians(alpha));
				vy=v0*Math.sin(Math.toRadians(alpha));
			}
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
				if(xPos>500)
				{
					cons=0;
					beta=alpha;				
				}
				if(xPos>496 && xPos<520)
				{
							
					if(yPos<440 && yPos>380)
					{						
						
						if(yPos>-2.5*xPos+1160)
						{

							cons=0;
							beta=alpha;
						}
					}
				}
			}
			
			
		}
	
	
	
}
