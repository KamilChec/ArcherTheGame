package pl.edu.pw.fizyka.pojava.ArcherTheGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SinglePlayerGame extends JFrame{
	
	JLabel player1Label, sILabel, vsLabel, angleLabel1, angleLabel2, forceLabel1, forceLabel2  ;
	JPanel topPanel, bottomPanel;
	PlayArea centrePanel;
	JButton exitButton, optionsButton;
	JTextField shotStrength, shotAngle;
	static int temp=1;	
	
	public void CloseSP(){
		super.dispose();
	}

	public SinglePlayerGame() throws HeadlessException {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(800,800);

		
		//---------Top-----------
		this.add(topPanel = new JPanel(), BorderLayout.PAGE_START);
		topPanel.setLayout(new FlowLayout());
		player1Label = new JLabel("Gracz 1");
		player1Label.setFont(new Font("Serif", Font.PLAIN, 30));
		topPanel.add(player1Label);
		topPanel.add(Box.createRigidArea(new Dimension(50,0)));
		vsLabel = new JLabel("VS");
		vsLabel.setFont(new Font("Serif", Font.PLAIN, 25));
		topPanel.add(vsLabel);
		topPanel.add(Box.createRigidArea(new Dimension(50,0)));
		sILabel = new JLabel("SI");	
		sILabel.setFont(new Font("Serif", Font.PLAIN, 30));
		topPanel.add(sILabel);
		
		//--------Bottom-------
		this.add(bottomPanel = new JPanel(), BorderLayout.PAGE_END);
		bottomPanel.setLayout(new FlowLayout());
		shotAngle = new JTextField("45");
		shotStrength = new JTextField("100");
		bottomPanel.add(angleLabel1 = new JLabel("Angle:"));
		bottomPanel.add(shotAngle);
		bottomPanel.add(angleLabel2 = new JLabel("°"));
		bottomPanel.add(forceLabel1 = new JLabel("Power:"));
		bottomPanel.add(shotStrength);
		bottomPanel.add(forceLabel2 = new JLabel("%"));
		bottomPanel.add(Box.createRigidArea(new Dimension(150,0)));
		optionsButton = new JButton("Opcje");
		exitButton = new JButton("Wyjscie");
		bottomPanel.add(optionsButton);
		bottomPanel.add(exitButton);
		exitButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				StartMenu window = new StartMenu();
				CloseSP();
				window.setVisible(true);

			}			
		});
		
		//temp
		optionsButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				centrePanel.v0=Integer.parseInt(shotStrength.getText());
				centrePanel.alpha=Integer.parseInt(shotAngle.getText());
				if(temp==1)
				{
					Thread gamethread = new Thread(centrePanel);
					gamethread.start();
					temp++;
				}
				else if(temp==2)
				{
					Thread gamethread1 = new Thread(centrePanel);
					gamethread1.start();
				}				
				//Thread gamethread = new Thread(centrePanel);
				//gamethread.start();				
			}			
		});
		//temp*
		bottomPanel.add(optionsButton);
		bottomPanel.add(exitButton);
		
		//------------Centre---
		this.add(centrePanel = new PlayArea(shotStrength, shotAngle), BorderLayout.CENTER);
		centrePanel.setBackground(Color.WHITE);
		centrePanel.setSize(600, 600);
		
		
		
		
	}
	
}
