package pl.edu.pw.fizyka.pojava.ArcherTheGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MultiPlayerGame extends JFrame {
	
	JLabel player1Label, player2Label,vsLabel;
	JPanel topPanel, bottomPanel, shootPanel1, shootPanel2;
	JButton exitButton, optionsButton;
	JTextField shotStrength, shotAngle, shotStrength1, shotAngle1;
	PlayArea centrePanel;
	Image im;
	
	public void CloseMP(){
		super.dispose();
	}

	public MultiPlayerGame() throws HeadlessException {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(600,600);
		setResizable(false);
		setLocationRelativeTo(null);
		
		//---------Top-----------
		this.add(topPanel = new JPanel(), BorderLayout.PAGE_START);		
		player1Label = new JLabel("Gracz 1");
		player1Label.setFont(new Font("Serif", Font.PLAIN, 30));
		topPanel.add(player1Label);
		topPanel.add(Box.createRigidArea(new Dimension(50,0)));
		vsLabel = new JLabel("VS");
		vsLabel.setFont(new Font("Serif", Font.PLAIN, 25));
		topPanel.add(vsLabel);
		topPanel.add(Box.createRigidArea(new Dimension(50,0)));
		player2Label = new JLabel("Gracz 2");
		player2Label.setFont(new Font("Serif", Font.PLAIN, 30));
		topPanel.add(player2Label);
		
		//--------Bottom-------
		this.add(bottomPanel = new JPanel(), BorderLayout.PAGE_END);
		shotAngle = new JTextField("Kat");
		shotStrength = new JTextField("Sila");
		shotAngle1 = new JTextField("Kat");
		shotStrength1 = new JTextField("Sila");
		optionsButton = new JButton("Opcje");
		exitButton = new JButton("Wyjscie");
		
		exitButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				StartMenu window = new StartMenu();
				CloseMP();
				window.setVisible(true);				
			}			
		});
		//temp
		optionsButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
		
			}			
		});
		//temp*
		bottomPanel.add(shotAngle);
		bottomPanel.add(shotStrength);		
		bottomPanel.add(optionsButton);
		bottomPanel.add(exitButton);
		bottomPanel.add(shotAngle1);
		bottomPanel.add(shotStrength1);
		
		//------------Centre---
		URL resource = getClass().getResource("/images/1.PNG");
		try {
			 im = ImageIO.read(resource);
			} 
		catch (IOException e) {
			 System.err.println("Blad odczytu obrazka");
			 e.printStackTrace();
			}
				
		PlayArea centrePanel = new PlayArea(im, shotAngle, shotStrength, 1);
		this.add(centrePanel);
		centrePanel.setBackground(Color.WHITE);
		centrePanel.setSize(400, 400);
	}

	

}
