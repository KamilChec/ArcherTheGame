package pl.edu.pw.fizyka.pojava.ArcherTheGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StartMenu extends JFrame {
	Dimension dimension;
	AudioPlayer bgMusic;
	
	BufferedImage backgroundImage , arrowImage;
	Image bgGif;
	Font labelFont;
	
	JLabel heading;
	JButton startButton, startMPButton, exitButton, soundOffButton;
	JPanel menuPanel;
	
	Boolean drawLine1 = false;
	Boolean drawLine2 = false;
	Boolean drawLine3= false;
	
	
	public StartMenu() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		setSize(dimension = new Dimension(600, 600));
		setResizable(false);
		setBackground(Color.WHITE);
		String musicName = "/audio/start_music.mp3";
		bgMusic = new AudioPlayer(musicName);
		bgMusic.play();

		
		add(menuPanel = new JPanel() {
			public void paintComponent(Graphics g) {
				setSize(dimension);
				Graphics2D g2d = (Graphics2D) g;
				try {
					backgroundImage = ImageIO.read(getClass().getResource("/images/background.jpg"));
					arrowImage = ImageIO.read(getClass().getResource("/images/arrow.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				g2d.drawImage(backgroundImage, 0, 0, dimension.width, dimension.height,this);
				if(drawLine1 == true) g2d.drawImage(arrowImage, 400, 125, 150, 90, this);
				if(drawLine2 == true) g2d.drawImage(arrowImage, 430, 215, 150, 90, this);
				if(drawLine3 == true) g2d.drawImage(arrowImage, 220, 300, 150, 90, this);
				repaint();
			}
		});
		menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.PAGE_AXIS));
		
		heading = new JLabel("Archer the Game");
		try {
			loadFont("/fonts/font1.ttf");
			loadFont("/fonts/font2.ttf");
			loadFont("/fonts/font3.ttf");
			loadFont("/fonts/font4.ttf");
			loadFont("/fonts/font5.ttf");
			loadFont("/fonts/font6.ttf");
		} catch (Exception e) {
			e.printStackTrace();
		}
		heading.setFont(new Font("101! Block LetterZ", Font.BOLD, 40));
		heading.setForeground(Color.red);
		startButton = new JButton("Gra jednoosobowa");
		startButton.setFont(new Font("MAKEN", Font.BOLD, 30));
		startButton.setBorderPainted(false);
		startButton.setForeground(Color.blue);
		startButton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent event) {
				drawLine1 = true;
			}
			public void mouseExited(MouseEvent event) {
				drawLine1 = false;
			}
		});
		startMPButton = new JButton("Gracz kontra gracz");
		startMPButton.setFont(new Font("MAKEN", Font.BOLD, 30));
		startMPButton.setBorderPainted(false);
		startMPButton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent event) {
				drawLine2 = true;
			}
			public void mouseExited(MouseEvent event) {
				drawLine2 = false;
			}
		});
		startMPButton.setForeground(Color.blue);
		exitButton = new JButton("Wyjscie");
		exitButton.setFont(new Font("MAKEN", Font.BOLD, 30));
		exitButton.setBorderPainted(false);
		exitButton.setForeground(Color.blue);
		exitButton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent event) {
				drawLine3 = true;
			}
			public void mouseExited(MouseEvent event) {
				drawLine3 = false;
			}
		});
		exitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		menuPanel.add(heading);
		menuPanel.add(Box.createRigidArea(new Dimension(100,100))); 
		startButton.setPreferredSize(new Dimension(100, 100));
		startMPButton.setPreferredSize(new Dimension(500, 50));
		exitButton.setPreferredSize(new Dimension(500, 50));
		menuPanel.add(startButton);
		menuPanel.add(Box.createRigidArea(new Dimension(100,50)));
		menuPanel.add(startMPButton);
		menuPanel.add(Box.createRigidArea(new Dimension(100,50)));
		menuPanel.add(exitButton);
	}
	
	public void loadFont(String name) throws Exception {
		URL fontUrl = getClass().getResource(name);
		Font preFont = Font.createFont(Font.TRUETYPE_FONT, fontUrl.openStream());
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		ge.registerFont(preFont);
	}


	public static void main(String[] args) {
		StartMenu menu = new StartMenu();
		menu.setVisible(true);
		

	}

}
