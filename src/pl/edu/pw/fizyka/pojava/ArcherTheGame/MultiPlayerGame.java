package pl.edu.pw.fizyka.pojava.ArcherTheGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
	JLabel  shotAngleValue1, shotForceValue1, shotAngleValue2, shotForceValue2;
	JLabel angleLabel1, angleLabel2, forceLabel1, forceLabel2;
	PlayArea centrePanel;
	Image im;
	
	public void CloseMP(){
		super.dispose();
	}

	public MultiPlayerGame() throws HeadlessException {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(1000, 600);
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
		
		//---BottomPanel---
		add(bottomPanel = new JPanel(), BorderLayout.PAGE_END);
		bottomPanel.setLayout(new FlowLayout());
		shotAngleValue1 = new JLabel("000");
		shotForceValue1 = new JLabel("000");
		bottomPanel.add(angleLabel1 = new JLabel("Angle:"));
		bottomPanel.add(shotAngleValue1);
		bottomPanel.add(angleLabel2 = new JLabel("°"));
		bottomPanel.add(forceLabel1 = new JLabel("Power:"));
		bottomPanel.add(shotForceValue1);
		bottomPanel.add(forceLabel2 = new JLabel("%"));
		
		bottomPanel.add(Box.createRigidArea(new Dimension(150,0)));
		
		bottomPanel.add(angleLabel1 = new JLabel("Angle:"));
		shotAngleValue2 = new JLabel("000");
		shotForceValue2 = new JLabel("000");
		bottomPanel.add(shotAngleValue2);
		bottomPanel.add(angleLabel2 = new JLabel("°"));
		bottomPanel.add(forceLabel1 = new JLabel("Power:"));
		bottomPanel.add(shotForceValue2);
		bottomPanel.add(forceLabel2 = new JLabel("%"));
		//temp
		optionsButton = new JButton("Options");
		optionsButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
		
			}			
		});
		//temp*
		
		bottomPanel.add(optionsButton);
		exitButton = new JButton("Exit");
		bottomPanel.add(exitButton);

		//---CentrePanel
		add(centrePanel = new PlayArea(shotAngleValue1, shotForceValue1,
				shotAngleValue2, shotForceValue2, MultiPlayerGame.this), BorderLayout.CENTER);
		
	}

	

}
