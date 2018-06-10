package pl.edu.pw.fizyka.pojava.ArcherTheGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;




public class PlayArea extends JPanel {
	Player player;
	Player player1, player2;
	double alpha, force, wind;
	int turn = 0;
	int tagWidth, tagLenght;
	int tagIndex = 0;
	boolean whenDraw = false;
	boolean shot = false;
	boolean drawobstacle = false;
	boolean multiplayer = false;
	Point startPoint, endPoint;
	BufferedImage backgroundImage, tagImage, windArrow, windArrow1;
	
	Arrow arrow;
	Obstacle obstacle;
	Enemy enemy;
	EndGame endGame;
	JFrame currentGame;
	ArrayList<Arrow> arrows;
	ArrayList<Arrow> arrows1;
	ArrayList<Arrow> arrows2;
	
	JLabel windLabel;

	//---Singleplayer
	PlayArea(JLabel shotAngle, JLabel shotStrength, SinglePlayerGame currentGame) {
	
		
		setSize(new Dimension(1000, 400));
		this.currentGame = currentGame;
		player = new Player(this, false);
		enemy = new Enemy(this);
		arrows = new ArrayList<Arrow>();
		obstacle = new Obstacle(this);
		
		windLabel=new JLabel();
		windLabel.setAlignmentX(980);
		windLabel.setAlignmentY(10);
		this.add(windLabel);
		setWind();
		
		try {
			backgroundImage = ImageIO.read(getClass().getResource("/images/gameBackground2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			windArrow = ImageIO.read(getClass().getResource("/images/windArrow.png"));
		} catch (IOException e1) {			
			e1.printStackTrace();
		}
		try {
			windArrow1 = ImageIO.read(getClass().getResource("/images/windArrow1.png"));
		} catch (IOException e1) {			
			e1.printStackTrace();
		}

		
		ExecutorService exec = Executors.newFixedThreadPool(2);
		exec.execute(enemy);
		exec.execute(obstacle);
		exec.shutdown();
		
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				startPoint = new Point(e.getX(), e.getY());
				endPoint = new Point(startPoint);
				whenDraw = true;
				repaint();
			}
			public void mouseReleased(MouseEvent e) {
				whenDraw = false;
				shot = true;
				arrow = new Arrow(PlayArea.this, alpha, forceToPower(force), arrowStartPos(alpha).x, arrowStartPos(alpha).y, false, wind);
							
				setWind();				
				
				
				arrow.diameter=currentGame.hold.diameter;
				arrow.mass=currentGame.hold.mass;
				arrow.coeff=currentGame.hold.coeff;
				arrow.g=currentGame.hold.g;
				arrow.ro=currentGame.hold.ro;		
				
				
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
	//---Multiplayer
	PlayArea(JLabel shotAngle1, JLabel shotStrength1, JLabel shotAngle2, JLabel shotStrength2, MultiPlayerGame currentGame) {
		setSize(new Dimension(1000, 400));
		this.currentGame = currentGame;
		multiplayer = true;
		player1 = new Player(this, false);
		player2 = new Player(this, multiplayer);
		obstacle = new Obstacle(this);
		arrows1 = new ArrayList<Arrow>();
		arrows2 = new ArrayList<Arrow>();
		try {
			backgroundImage = ImageIO.read(getClass().getResource("/images/gameBackground2.png"));
			tagImage = ImageIO.read(getClass().getResource("/images/tag.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		tagWidth = (int) (tagImage.getWidth()*0.2);
		tagLenght = (int) (tagImage.getHeight()*0.2);
		
		ExecutorService exec = Executors.newSingleThreadExecutor();
		exec.execute(obstacle);
		exec.shutdown();
		
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				startPoint = new Point(e.getX(), e.getY());
				System.out.println(startPoint);
				endPoint = new Point(startPoint);
				whenDraw = true;
				repaint();
			}
			public void mouseReleased(MouseEvent e) {
				whenDraw = false;
				shot = true;
				if((turn % 2) == 0) {
					arrow = new Arrow(PlayArea.this, alpha, forceToPower(force), arrowStartPos(alpha).x, arrowStartPos(alpha).y, false, wind);
					
					
					arrow.diameter=currentGame.hold.diameter;
					arrow.mass=currentGame.hold.mass;
					arrow.coeff=currentGame.hold.coeff;
					arrow.g=currentGame.hold.g;
					arrow.ro=currentGame.hold.ro;
					
					ExecutorService exec = Executors.newSingleThreadExecutor();
					exec.execute(arrow);
					exec.shutdown();
					arrows1.add(arrow);
					
				} else {
					arrow = new Arrow(PlayArea.this, alpha, forceToPower(force), arrowStartPos(alpha).x, arrowStartPos(alpha).y, multiplayer, wind);
					
					
					arrow.diameter=currentGame.hold.diameter;
					arrow.mass=currentGame.hold.mass;
					arrow.coeff=currentGame.hold.coeff;
					arrow.g=currentGame.hold.g;
					arrow.ro=currentGame.hold.ro;
					
					ExecutorService exec = Executors.newSingleThreadExecutor();
					exec.execute(arrow);
					exec.shutdown();
					arrows2.add(arrow);
				}
				alpha = 0;
				force = 0;
				turn++;
				repaint();
			}
		});
		addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent e) {
				endPoint = new Point(e.getX(), e.getY());
				alpha = getAlpha();
				force = getForce();
				if((turn % 2) == 0) {
					shotAngle1.setText(String.valueOf(Math.round(getAlpha())));
					shotStrength1.setText(String.valueOf(forceToPower(force)));
				} else {
					shotAngle2.setText(String.valueOf(Math.round(getAlpha())));
					shotStrength2.setText(String.valueOf(forceToPower(force)));
				}
				
				
				repaint();
			}
		});
		
		
	}
			
		/*if(Arrow.counter % 5 == 0)
		{
			Random rand = new Random();
			randomX = rand.nextInt(300);
			randomY = rand.nextInt(300);
			randomWidth = rand.nextInt(50);
			randomHeigth = rand.nextInt(50);
		}*/
	public double getAlpha() {

		int xLenght = startPoint.x - endPoint.x;
		int yLenght = endPoint.y - startPoint.y;
		double lineLength = Math.sqrt((xLenght*xLenght) + (yLenght*yLenght));
	    if(yLenght > 0) { 
	    	if((turn % 2) == 0) {
	    		return Math.toDegrees(Math.acos(xLenght/lineLength));
	    	} else {
	    		return Math.toDegrees(Math.acos(-xLenght/lineLength));
	    	}
	    }else {
	    	if((turn % 2) == 0) {
	    		return -(Math.toDegrees(Math.acos(xLenght/lineLength)));
	    	} else {
	    		return -(Math.toDegrees(Math.acos(-xLenght/lineLength)));
	    	}
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
	public void setWind()
	{
		Random r = new Random();
		wind=r.nextDouble()+r.nextDouble()-1;
		windLabel.setText(Double.toString(Math.round(Math.abs(wind*10))));
		
	}
	
	
	public Point arrowStartPos(double angle) {
		if(multiplayer) {
			if((turn % 2) == 0) {
				if(angle > -180 && angle < -10) 		return new Point(player1.xPos + 122, player1.yPos + 129);
				else if(angle >= -10 && angle <= 10) 	return new Point(player1.xPos + 122, player1.yPos + 83);
				else if(angle > 10 && angle < 45)		return new Point(player1.xPos + 125, player1.yPos + 61);
				else if(angle >= 45 && angle <= 80)  	return new Point(player1.xPos + 110, player1.yPos + 44);
				else /*(angle > 80 && angle <= 180)	 */	return new Point(player1.xPos + 70, player1.yPos + 28);
			} else {
				if(angle > -180 && angle < -10) 		return new Point(player2.xPos - 122, player2.yPos + 129);
				else if(angle >= -10 && angle <= 10) 	return new Point(player2.xPos - 7, player2.yPos + 83);
				else if(angle > 10 && angle < 45)		return new Point(player2.xPos + 25, player2.yPos + 61);
				else if(angle >= 45 && angle <= 80)  	return new Point(player2.xPos + 50, player2.yPos + 44);
				else /*(angle > 80 && angle <= 180)	 */	return new Point(player2.xPos + 47, player2.yPos + 28);
			}
		} else {
			if(angle > -180 && angle < -10) 		return new Point(player.xPos + 122, player.yPos + 129);
			else if(angle >= -10 && angle <= 10) 	return new Point(player.xPos + 122, player.yPos + 83);
			else if(angle > 10 && angle < 45)		return new Point(player.xPos + 125, player.yPos + 61);
			else if(angle >= 45 && angle <= 80)  	return new Point(player.xPos + 110, player.yPos + 44);
			else /*(angle > 80 && angle <= 180)	 */	return new Point(player.xPos + 70, player.yPos + 28);
		}
	}	
	public void paintComponent(Graphics g){        
		super.paintComponent(g);
	    Graphics2D g2d = (Graphics2D)g;
	    g2d.setColor(Color.BLACK);
	    
	    
	    
	    if(multiplayer) {
	    	g2d.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
	    } else {
	    	g2d.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
	    }
	    
	    if(wind<0) {
	    	 g2d.drawImage(windArrow, 475, 0, 40, 10, this);
	    }
	    else if(wind>0)
	    {
	    	g2d.drawImage(windArrow1, 475, 0, 40, 10, this);
	    }
	    
	    
	    
	    
	    
	    
//	    g2d.drawImage(targetImage, 750, 250, 111, 168, this);
		if(whenDraw) {
			g.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
			g.drawOval(startPoint.x - 10, startPoint.y - 10, 20, 20);
		}
//		player.drawPlayer(g2d, player.firstArea, forceToPower(force));
		if(multiplayer) {
			if((turn % 2) == 0) {
				g2d.drawImage(tagImage, player1.xPos + player1.width/2 - 20, player1.yPos - 60, tagWidth, tagLenght, this);
				player1.prepareToShot(g2d, alpha, forceToPower(force));
				player2.prepareToShot(g2d, 0, 0);
			} else {
				g2d.drawImage(tagImage, player2.xPos + player2.width/2 - 20, player1.yPos - 60, tagWidth, tagLenght, this);
				player1.prepareToShot(g2d, 0, 0);
				player2.prepareToShot(g2d, alpha, forceToPower(force));
			}
		} else {
			player.prepareToShot(g2d, alpha, forceToPower(force));
		}
		if(multiplayer) {
			for(Arrow arrow : arrows1) {
				arrow.drawArrow(g2d);
				if((arrow.xPos > player2.xPos + 50 && arrow.xPos < player2.xPos + player2.width - 50) &&
						(arrow.yPos < player2.yPos + player2.lenght && arrow.yPos > player2.yPos + 50)) {
					if(arrow.hit) {
							arrow.hit = false;
							arrow.fly = false;
							player2.hit();
							if(player2.health == 0) {
								player2.continueGame();
								endGame = new EndGame(player1, player2, currentGame, 1);
								endGame.setVisible(true);
							}
					}
//					arrow.stuckedArrow(enemy.xVelocity, enemy.yVelocity);
				}
			}
			for(Arrow arrow : arrows2) {
				arrow.drawArrow(g2d);
				if((arrow.xPos > player1.xPos +20 && arrow.xPos < player1.xPos + player1.width - 80) &&
						(arrow.yPos < player1.yPos + player1.lenght && arrow.yPos > player1.yPos + 50 )) {
					if(arrow.hit) {
							arrow.hit = false;
							arrow.fly = false;
							player1.hit();
							if(player1.health == 0) {
								player1.continueGame();
								endGame = new EndGame(player1, player2, currentGame, 2);
								endGame.setVisible(true);
							}
					}
				}
			}
		} else {
			for(Arrow arrow : arrows) {
				arrow.drawArrow(g2d);
				if((arrow.xPos  > obstacle.xPos - 15 && arrow.xPos < obstacle.xPos + obstacle.width) &&
						(arrow.yPos < obstacle.yPos + obstacle.lenght && arrow.yPos > obstacle.yPos - 5)) {
					if(arrow.hit) {
						obstacle.hit();
					}
					arrow.hit = false;
					arrow.stuckedArrow(obstacle.yVelocity);
				}
				if((arrow.xPos  > enemy.xPos + 5 && arrow.xPos < enemy.xPos + enemy.width) &&
						(arrow.yPos < enemy.yPos + enemy.length - 15 && arrow.yPos > enemy.yPos - 5)) {
					if(arrow.hit) {
							arrow.hit = false;
							enemy.hit();
							if(enemy.health == 0) {
								enemy.continueGame();
								endGame = new EndGame(player, enemy,currentGame);
								endGame.setVisible(true);
							}
					}
					arrow.stuckedArrow(enemy.xVelocity, enemy.yVelocity);
				}
			}
			obstacle.drawObstacle(g2d);
		    enemy.drawEnemy(g2d);
		    enemy.drawHealth(g2d);
		}
		if(multiplayer) {
			player1.drawHealth(g2d);
			player2.drawHealth(g2d);
		}
//		g2d.drawImage(tagImage, 600, 200 , (int) (tagImage.getWidth()*0.5), (int) (tagImage.getHeight()*0.5), this);
//		g2d.setColor(Color.blue);
//		g2d.drawOval(100,300,10,10);
		
    }
		
	
}
