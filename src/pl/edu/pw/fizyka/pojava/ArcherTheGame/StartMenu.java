package pl.edu.pw.fizyka.pojava.ArcherTheGame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;









public class StartMenu extends JFrame {
	JPanel topPanel, bottomPanel, centrePanel;
	JButton startButton, startMPButton, exitButton, soundOffButton;
	JMenuBar menuBar;
	
	public void CloseMenu(){
		super.dispose();
	}
	
	public StartMenu() throws HeadlessException {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(800,600);
		this.add(topPanel = new JPanel(), BorderLayout.PAGE_START);
		this.add(centrePanel = new JPanel(), BorderLayout.CENTER);
		this.setJMenuBar(menuBar = new JMenuBar());
		
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
		centrePanel.setMaximumSize(new Dimension(600, 500));
		
		BackgroundFiller obrazek = new BackgroundFiller();
		setSize(obrazek.getPreferredSize());
		add(obrazek);

		startButton = new JButton("Gra jednoosobowa");
		startMPButton = new JButton("Gracz kontra gracz");    
		exitButton = new JButton("Wyjscie");
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
		topPanel.setMaximumSize(new Dimension(600, 100));
		soundOffButton = new JButton("wycisz");
		soundOffButton.setPreferredSize(new Dimension(40, 40));
		topPanel.add(Box.createRigidArea(new Dimension(600,0)));
		topPanel.add(soundOffButton);		
		//--------startButton Listener----------
		
		startButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				SinglePlayerGame window = new SinglePlayerGame();
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
