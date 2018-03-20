package pl.edu.pw.fizyka.pojava.ArcherTheGame;

import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SinglePlayerGame extends JFrame {
	
	JLabel player1Label, SILabel;
	JPanel topPanel, bottomPanel, centrePanel;
	JButton exitButton, optionsButton;

	public SinglePlayerGame() throws HeadlessException {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(600,500);
		
		//---------Top-----------
		this.add(topPanel = new JPanel(), BorderLayout.PAGE_START);
		player1Label = new JLabel("Gracz 1");
		SILabel = new JLabel("SI");
		topPanel.add(player1Label);
		topPanel.add(SILabel);
	}

	

}
