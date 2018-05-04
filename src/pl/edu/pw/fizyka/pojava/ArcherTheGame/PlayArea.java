package pl.edu.pw.fizyka.pojava.ArcherTheGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JViewport;



public class PlayArea extends JPanel implements Runnable {
	int cons=1;
	static int count=1;
	
	double xPos, yPos, vx, vy, dVx, dVy, alpha, beta, gamma, a, v0, g, ro, diameter, mass, coeff, force;
	boolean whenDraw = false;
	Point startPoint, endPoint;
	
	Player player;

	public PlayArea(Image image, int style, float alignmentX, float alignmentY, JTextField shotAngle, JTextField shotStrength)
	{
		
		setImage( image );
		setStyle( style );
		setImageAlignmentX( alignmentX );
		setImageAlignmentY( alignmentY );
		setLayout( new BorderLayout() );
		coeff = 1;
		g = 9.81;
		ro = 1.2;
		diameter = 0.1;
		mass = 0.1;
//		alpha = 45;
		a = coeff*diameter*ro*mass;
		v0 = 100;    	    	
		beta = alpha;
		xPos = 0;
		yPos = 500;
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
	
	//-----------------------------------------//
	
		public static final int SCALED = 0;
		public static final int TILED = 1;
		public static final int ACTUAL = 2;

		private Paint painter;
		private Image image;
		private int style = SCALED;
		private float alignmentX = 0.5f;
		private float alignmentY = 0.5f;
		private boolean isTransparentAdd = true;

		
		
		 //  Use the Paint interface to paint a background
		 
		public PlayArea(Paint painter)
		{
			setPaint( painter );
			setLayout( new BorderLayout() );
		}


		 //	Set the image used as the background
		 
		public void setImage(Image image)
		{
			this.image = image;
			repaint();
		}

		
		 //	Set the style used to paint the background image
		 
		public void setStyle(int style)
		{
			this.style = style;
			repaint();
		}

		
		 //	Set the Paint object used to paint the background
		 
		public void setPaint(Paint painter)
		{
			this.painter = painter;
			repaint();
		}

		
		 //  Specify the horizontal alignment of the image when using ACTUAL style
		 
		public void setImageAlignmentX(float alignmentX)
		{
			this.alignmentX = alignmentX > 1.0f ? 1.0f : alignmentX < 0.0f ? 0.0f : alignmentX;
			repaint();
		}

		
		 //  Specify the horizontal alignment of the image when using ACTUAL style
		 
		public void setImageAlignmentY(float alignmentY)
		{
			this.alignmentY = alignmentY > 1.0f ? 1.0f : alignmentY < 0.0f ? 0.0f : alignmentY;
			repaint();
		}

		
		 //  Override method so we can make the component transparent
		 
		public void add(JComponent component)
		{
			add(component, null);
		}

		
		 // Override to provide a preferred size equal to the image size
		 
		@Override
		public Dimension getPreferredSize()
		{
			if (image == null)
				return super.getPreferredSize();
			else
				return new Dimension(image.getWidth(null), image.getHeight(null));
		}

		
		 //  Override method so we can make the component transparent
		 
		public void add(JComponent component, Object constraints)
		{
			if (isTransparentAdd)
			{
				makeComponentTransparent(component);
			}

			super.add(component, constraints);
		}

		
		 //  Controls whether components added to this panel should automatically
		 //  be made transparent. That is, setOpaque(false) will be invoked.
		 //  The default is set to true.
		 
		public void setTransparentAdd(boolean isTransparentAdd)
		{
			this.isTransparentAdd = isTransparentAdd;
		}

		
		 //	Try to make the component transparent.
		 //  For components that use renderers, like JTable, you will also need to
		 // change the renderer to be transparent. An easy way to do this it to
		 // set the background of the table to a Color using an alpha value of 0.
		 
		private void makeComponentTransparent(JComponent component)
		{
			component.setOpaque( false );

			if (component instanceof JScrollPane)
			{
				JScrollPane scrollPane = (JScrollPane)component;
				JViewport viewport = scrollPane.getViewport();
				viewport.setOpaque( false );
				Component c = viewport.getView();

				if (c instanceof JComponent)
				{
					((JComponent)c).setOpaque( false );
				}
			}
		}
	
		 
		 //  Custom painting code for drawing a SCALED image as the background
		 
		private void drawScaled(Graphics g)
		{
			Dimension d = getSize();
			g.drawImage(image, 0, 0, d.width, d.height, null);
		}

		
		 //  Custom painting code for drawing TILED images as the background
		 
		private void drawTiled(Graphics g)
		{
			   Dimension d = getSize();
			   int width = image.getWidth( null );
			   int height = image.getHeight( null );

			   for (int x = 0; x < d.width; x += width)
			   {
				   for (int y = 0; y < d.height; y += height)
				   {
					   g.drawImage( image, x, y, null, null );
				   }
			   }
		}

		
		 //  Custom painting code for drawing the ACTUAL image as the background.
		//   The image is positioned in the panel based on the horizontal and
		//   vertical alignments specified.
		 
		private void drawActual(Graphics g)
		{
			Dimension d = getSize();
			Insets insets = getInsets();
			int width = d.width - insets.left - insets.right;
			int height = d.height - insets.top - insets.left;
			float x = (width - image.getWidth(null)) * alignmentX;
			float y = (height - image.getHeight(null)) * alignmentY;
			g.drawImage(image, (int)x + insets.left, (int)y + insets.top, this);
		}

		
		
	  //------------------------------------------------------//
	
		public double getAlpha() {

			double xLenght = endPoint.x-startPoint.x;
			System.out.println("X" + Double.toString(xLenght));
			
			double yLenght =startPoint.y - endPoint.y;
			System.out.println("Y" + Double.toString(yLenght));
			
			double lineLength = Math.sqrt((xLenght*xLenght) + (yLenght*yLenght));
			
			System.out.println("Dlugosc" + Double.toString(lineLength));
			
			System.out.println("Y/X" + Double.toString(lineLength/xLenght));
			
			System.out.println("AKT" + Double.toString(Math.toDegrees(Math.acos(xLenght/lineLength))));
			
		    if(yLenght > 0) {
		    	return Math.toDegrees(Math.acos(xLenght/lineLength));
		    }else {
		    	return -(Math.toDegrees(Math.acos(Math.toRadians(lineLength/xLenght))));
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

			//  Invoke the painter for the background

			if (painter != null)
			{
				Dimension d = getSize();
				Graphics2D g2 = (Graphics2D) g;
				g2.setPaint(painter);
				g2.fill( new Rectangle(0, 0, d.width, d.height) );
			}

			//  Draw the image

			if (image == null ) return;

			switch (style)
			{
				case SCALED :
					drawScaled(g);
					break;

				case TILED  :
					drawTiled(g);
					break;

				case ACTUAL :
					drawActual(g);
					break;

				default:
	            	drawScaled(g);
			}
			
			
		    Graphics2D g2d = (Graphics2D)g;
		    g2d.setColor(Color.BLACK);
			if(whenDraw) {
				g.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
				g.drawOval(startPoint.x - 10, startPoint.y - 10, 20, 20);
			}
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
				if(xPos>496 && xPos<520)
				{
					System.out.println("Kappa");					
					if(yPos<440 && yPos>380)
					{						
						System.out.println("PogChamp");
						if(yPos>-2.5*xPos+1160)
						{
							System.out.println("LUL");
							cons=0;
							beta=alpha;
						}
					}
				}
			}
			
			
		}
	
	
	
}
