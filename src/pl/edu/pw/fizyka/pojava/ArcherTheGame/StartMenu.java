package pl.edu.pw.fizyka.pojava.ArcherTheGame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;



public class StartMenu extends JFrame {
	JPanel topPanel, bottomPanel, centrePanel;
	JButton startButton, startMPButton, exitButton, soundOffButton;
	JMenuBar menuBar;
	JLabel heading;
	static SinglePlayerGame window;
	
	public void CloseMenu(){
		super.dispose();
	}
	
	public StartMenu() throws HeadlessException {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(800,600);
		BackgroundFiller obrazek = new BackgroundFiller();
		setSize(obrazek.getPreferredSize());
		//this.add(obrazek); 
		this.add(topPanel = new JPanel(), BorderLayout.PAGE_START);
		this.add(centrePanel = new JPanel(), BorderLayout.CENTER);
		this.setJMenuBar(menuBar = new JMenuBar());
		//centrePanel.add(obrazek);
		
		//--------Menu----------------
		JMenu menu = new JMenu("Menu");							
		menuBar.add(menu);
		JMenuItem exit = new JMenuItem("Zakoncz");
		exit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
			System.exit(0); }
		});
		menu.add(exit);
		
		//-------Centre---------------
		centrePanel.setLayout(new BoxLayout(centrePanel, BoxLayout.PAGE_AXIS));
		//centrePanel.setMaximumSize(new Dimension(600, 500));

		startButton = new JButton("Gra jednoosobowa");
		startMPButton = new JButton("Gracz kontra gracz");    
		exitButton = new JButton("Wyjscie");
		heading = new JLabel("Archer the Game");
		heading.setFont(new Font("Serif", Font.PLAIN, 40));
		centrePanel.add(heading);
		centrePanel.add(Box.createRigidArea(new Dimension(100,100))); 
		startButton.setPreferredSize(new Dimension(100, 100));
		startMPButton.setPreferredSize(new Dimension(500, 50));
		exitButton.setPreferredSize(new Dimension(500, 50));
		centrePanel.add(startButton);
		centrePanel.add(Box.createRigidArea(new Dimension(100,50)));
		centrePanel.add(startMPButton);
		centrePanel.add(Box.createRigidArea(new Dimension(100,50)));
		centrePanel.add(exitButton);
		 
		
		//--------Top---------------------
		topPanel.setLayout(new FlowLayout());
		//topPanel.setMaximumSize(new Dimension(600, 100));
		soundOffButton = new JButton();
		soundOffButton.setIcon(new ImageIcon("images/wycisz.png"));

		//soundOffButton.setBounds(600,0 ,100, 40); 
		
		soundOffButton.setPreferredSize(new Dimension(40, 40));
		topPanel.add(Box.createRigidArea(new Dimension(600,0)));
		topPanel.add(soundOffButton);		
		//--------startButton Listener----------
		
		startButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				window = new SinglePlayerGame();
				CloseMenu();
				window.setVisible(true);				
			}			
		});
		
		//--------startMPButton Listener----------
		
		startMPButton.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e) {						
						MultiPlayerGame window = new MultiPlayerGame();
						CloseMenu();
						window.setVisible(true);
					}			
				});
		
		
		//--------exitButton Listener----------
		
		exitButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);				
			}			
		});
	}
	public static void main(String[] args) {
		StartMenu interface1 = new StartMenu();
		interface1.setTitle("Archer the game");
		interface1.setVisible(true);

	}	

}
