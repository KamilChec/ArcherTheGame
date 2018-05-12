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
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JPanel;
import javax.swing.JTextField;






public class PlayArea extends JPanel {
	int cons=1;
	static int count=1;
	
	double xPos, yPos, vx, vy, dVx, dVy, alpha, beta, gamma, a, v0, g, ro, diameter, mass, coeff, force;
	boolean whenDraw = false;
	Point startPoint, endPoint;
	
	Player player;
	Arrow arrow;
	ArrayList<Arrow> arrows;

	public PlayArea(Image image, JTextField shotAngle, JTextField shotStrength)
	{
		
		setImage( image );
		setLayout( new BorderLayout() );
		coeff = 1;			//wsp�czynnik oporu zale�ny od kszta�tu
		g = 9.81;			//przyspieszenie grawitacyjne
		ro = 1.2;			//g�sto�� powietrza
		diameter = 0.1;		//�rednica strza�y
		mass = 0.1;			//masa strza�y
		a = coeff*diameter*ro*mass;	//wsp�czynnik proporcjonalno�ci oporu od pr�dko�ci
		v0 = 100;    	    	//pr�dko�� pocz�tkowa
		beta = alpha;			//k�t tymczasowy
		xPos = 200;				//pozycja pocz�tkowa w x
		yPos = 350;				//pozycja pocz�tkowa w y
		player = new Player(this);
		arrows = new ArrayList<Arrow>();
		
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
				arrow = new Arrow(PlayArea.this, alpha, forceToPower(force), arrowStartPos(alpha).x, arrowStartPos(alpha).y);
				ExecutorService exec = Executors.newSingleThreadExecutor();
				exec.execute(arrow);
				exec.shutdown();
				arrows.add(arrow);
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
		
		public Point arrowStartPos(double angle) {
			if(angle > -180 && angle < -10) 		return new Point(player.xPos + 122, player.yPos + 129);
			else if(angle >= -10 && angle <= 10) 	return new Point(player.xPos + 122, player.yPos + 83);
			else if(angle > 10 && angle < 45)		return new Point(player.xPos + 125, player.yPos + 61);
			else if(angle >= 45 && angle <= 80)  	return new Point(player.xPos + 110, player.yPos + 44);
			else /*(angle > 80 && angle <= 180)	 */	return new Point(player.xPos + 70, player.yPos + 28);
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
			for(Arrow arrow : arrows) {
				arrow.drawArrow(g2d);
			}
	    }	
}
