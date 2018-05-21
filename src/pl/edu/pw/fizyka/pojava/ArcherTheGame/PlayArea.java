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
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JPanel;
import javax.swing.JTextField;






public class PlayArea extends JPanel {
	int cons=1;
	static int count=1;
	static int mode;
	static int turn=0;
	
	double xPos, yPos, vx, vy, dVx, dVy, alpha, beta, gamma, a, v0, g, ro, diameter, mass, coeff, force;
	boolean whenDraw = false;
	Point startPoint, endPoint;
	
	//int randomX=0;
	//int randomY=0;
	//int randomWidth=0; 
	//int randomHeigth=0;
	
	Player player, player2;
	Arrow arrow;
	ArrayList<Arrow> arrows;

	public PlayArea(Image image, JTextField shotAngle, JTextField shotStrength, int m)
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
		player2 = new Player(this);
		arrows = new ArrayList<Arrow>();
		mode=m;
		
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
				if(turn==0) turn=1;
				else if(turn==1) turn=0;				
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
			
		/*if(Arrow.counter % 5 == 0)
		{
			Random rand = new Random();
			randomX = rand.nextInt(300);
			randomY = rand.nextInt(300);
			randomWidth = rand.nextInt(50);
			randomHeigth = rand.nextInt(50);
		}*/
		
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
			if(mode==1)
			{
				if(turn==0)
				{
					if(angle > -180 && angle < -10) 		return new Point(player.xPos + 122, player.yPos + 129);
					else if(angle >= -10 && angle <= 10) 	return new Point(player.xPos + 122, player.yPos + 83);
					else if(angle > 10 && angle < 45)		return new Point(player.xPos + 125, player.yPos + 61);
					else if(angle >= 45 && angle <= 80)  	return new Point(player.xPos + 110, player.yPos + 44);
					else /*(angle > 80 && angle <= 180)	 */	return new Point(player.xPos + 70, player.yPos + 28);
				}
				else if(turn==1)
				{
					if(angle > -180 && angle < -10) 		return new Point(player2.xPos1 + 122, player2.yPos1 + 129);
					else if(angle >= -10 && angle <= 10) 	return new Point(player2.xPos1 + 122, player2.yPos1 + 83);
					else if(angle > 10 && angle < 45)		return new Point(player2.xPos1 + 125, player2.yPos1 + 61);
					else if(angle >= 45 && angle <= 80)  	return new Point(player2.xPos1 + 110, player2.yPos1 + 44);
					else /*(angle > 80 && angle <= 180)	 */	return new Point(player2.xPos1 + 70, player2.yPos1 + 28);
				}
				else return new Point(player.xPos + 70, player.yPos + 28);
			}
			else if(mode==0)
			{
				if(angle > -180 && angle < -10) 		return new Point(player.xPos + 122, player.yPos + 129);
				else if(angle >= -10 && angle <= 10) 	return new Point(player.xPos + 122, player.yPos + 83);
				else if(angle > 10 && angle < 45)		return new Point(player.xPos + 125, player.yPos + 61);
				else if(angle >= 45 && angle <= 80)  	return new Point(player.xPos + 110, player.yPos + 44);
				else /*(angle > 80 && angle <= 180)	 */	return new Point(player.xPos + 70, player.yPos + 28);
				
			}
			else return new Point(player.xPos + 70, player.yPos + 28);
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
			
			if(mode==0)
			{
				player.drawPlayer(g2d, player.firstArea, forceToPower(force));
				player.prepareToShot(g2d, alpha, forceToPower(force));
			}
			
			if(mode==1)
			{
				if(turn==0)
				{
					player.drawPlayer(g2d, player.firstArea, forceToPower(force));
					player.prepareToShot(g2d, alpha, forceToPower(force));
					
					player2.drawPlayer2(g2d, player2.firstArea, 1);
				}
				else if(turn==1)
				{			
					player2.drawPlayer2(g2d, player2.firstArea, forceToPower(force));
					player2.prepareToShot2(g2d, alpha, forceToPower(force));
					
					player.drawPlayer(g2d, player.firstArea, 1);
				}
			}
			
			g2d.fillRect(Arrow.randomX, Arrow.randomY, Arrow.randomWidth, Arrow.randomHeigth);
			
			for(Arrow arrow : arrows) {
				arrow.drawArrow(g2d);
			}
	    }	
}
