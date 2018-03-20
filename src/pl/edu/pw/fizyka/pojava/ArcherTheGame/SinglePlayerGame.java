package pl.edu.pw.fizyka.pojava.ArcherTheGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SinglePlayerGame extends JFrame {
	
	JLabel player1Label, SILabel;
	JPanel topPanel, bottomPanel, centrePanel;
	JButton exitButton, optionsButton;
	JTextField shotStrength, shotAngle;
	
	public void CloseSP(){
		super.dispose();
	}

	public SinglePlayerGame() throws HeadlessException {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(600,500);
		
		//---------Top-----------
		this.add(topPanel = new JPanel(), BorderLayout.PAGE_START);		
		player1Label = new JLabel("Gracz 1");
		SILabel = new JLabel("SI");		
		topPanel.add(player1Label);
		topPanel.add(SILabel);
		
		//--------Bottom-------
		this.add(bottomPanel = new JPanel(), BorderLayout.PAGE_END);
		shotAngle = new JTextField("Kat");
		shotStrength = new JTextField("Sila");
		optionsButton = new JButton("Opcje");
		exitButton = new JButton("Wyjscie");				
		exitButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				StartMenu window = new StartMenu();
				CloseSP();
				window.setVisible(true);
				
			}			
		});
		bottomPanel.add(shotAngle);
		bottomPanel.add(shotStrength);
		bottomPanel.add(optionsButton);
		bottomPanel.add(exitButton);
		
	}

	

}
