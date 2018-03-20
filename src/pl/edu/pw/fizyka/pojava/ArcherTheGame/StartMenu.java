package pl.edu.pw.fizyka.pojava.ArcherTheGame;

import java.awt.BorderLayout;
import java.awt.Dimension;
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


//KOMENTARZTEST

public class StartMenu extends JFrame {
	JPanel topPanel, bottomPanel, centrePanel;
	JButton startButton, soundOffButton;
	JMenuBar menuBar;
	
	public StartMenu() throws HeadlessException {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(600,500);
		this.add(topPanel = new JPanel(), BorderLayout.PAGE_START);
		this.add(centrePanel = new JPanel(), BorderLayout.CENTER);
		this.setJMenuBar(menuBar = new JMenuBar());
		
		//--------Menu----------------
		JMenu menu = new JMenu("Menu");							
		menuBar.add(menu);
		JMenuItem exit = new JMenuItem("Zakończ");
		exit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
			System.exit(0); }
		});
		menu.add(exit);
		
		//-------Centre---------------
		centrePanel.setLayout(new BoxLayout(centrePanel, BoxLayout.PAGE_AXIS));
		startButton = new JButton("Start");
		//startButton.setBounds(x,y,width,height);
		centrePanel.add(Box.createRigidArea(new Dimension(0,100)));
		startButton.setPreferredSize(new Dimension(500, 50));
		centrePanel.add(startButton);
	}
	public static void main(String[] args) {
		StartMenu interface1 = new StartMenu();
		interface1.setTitle("Archer the game");
		interface1.setVisible(true);

	}

}
