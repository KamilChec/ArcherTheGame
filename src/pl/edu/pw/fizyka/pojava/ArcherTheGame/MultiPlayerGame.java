package pl.edu.pw.fizyka.pojava.ArcherTheGame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MultiPlayerGame extends JFrame {
	
	JLabel player1Label, player2Label,vsLabel;
	JPanel topPanel, bottomPanel, centrePanel, shootPanel1, shootPanel2;
	JButton exitButton, optionsButton;
	JTextField shotStrength, shotAngle, shotStrength1, shotAngle1;
	
	public void CloseMP(){
		super.dispose();
	}

	public MultiPlayerGame() throws HeadlessException {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(600,500);
		
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
		bottomPanel.add(shotAngle);
		bottomPanel.add(shotStrength);		
		bottomPanel.add(optionsButton);
		bottomPanel.add(exitButton);
		bottomPanel.add(shotAngle1);
		bottomPanel.add(shotStrength1);
	}

	

}
