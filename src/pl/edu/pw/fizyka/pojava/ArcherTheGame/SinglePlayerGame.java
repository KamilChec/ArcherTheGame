package pl.edu.pw.fizyka.pojava.ArcherTheGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class SinglePlayerGame extends JFrame{
	
	JLabel player1Label, aiLabel, vsLabel, angleLabel1, angleLabel2, forceLabel1, forceLabel2, shotAngleValue, shotForceValue;
	JPanel topPanel, bottomPanel;
	PlayArea centrePanel;
	JButton exitButton, optionsButton;
	static int temp=1;
	DataHolder hold;
	static Image im;
	OptionsWindow options;
	Font maken;
	BufferedImage arrowImage;
	
	public void CloseSP(){
		super.dispose();
	}

	public SinglePlayerGame() throws HeadlessException {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(1000, 600);
		setResizable(false);
		setLocationRelativeTo(null);
		
		hold = new DataHolder();
		maken = new Font("MAKEN", Font.BOLD, 30);

		
		//---------Top-----------
		this.add(topPanel = new JPanel(), BorderLayout.PAGE_START);
		topPanel.setLayout(new FlowLayout());
		player1Label = new JLabel("Player 1");
		player1Label.setFont(maken);
		topPanel.add(player1Label);
		topPanel.add(Box.createRigidArea(new Dimension(50,0)));
		vsLabel = new JLabel("VS");
		vsLabel.setFont(maken);
		topPanel.add(vsLabel);
		topPanel.add(Box.createRigidArea(new Dimension(50,0)));
		aiLabel = new JLabel("AI");	
		aiLabel.setFont(maken);
		topPanel.add(aiLabel);
		
		
		
		//--------Bottom-------
		this.add(bottomPanel = new JPanel(), BorderLayout.PAGE_END);
		bottomPanel.setLayout(new FlowLayout());
		shotAngleValue = new JLabel("000");
		shotForceValue = new JLabel("000");
		bottomPanel.add(angleLabel1 = new JLabel("Angle:"));
		bottomPanel.add(shotAngleValue);
		bottomPanel.add(angleLabel2 = new JLabel("°"));
		bottomPanel.add(forceLabel1 = new JLabel("Power:"));
		bottomPanel.add(shotForceValue);
		bottomPanel.add(forceLabel2 = new JLabel("%"));
		optionsButton = new JButton("Option");
		optionsButton.setFont(maken);
		optionsButton.setBorderPainted(false);
		exitButton = new JButton("Exit");
		exitButton.setFont(maken);
		exitButton.setBorderPainted(false);
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
		
		
		optionsButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				options = new OptionsWindow(hold);				
				options.setVisible(true);				
				
			}			
		});
			

		//---Center
		add(centrePanel = new PlayArea(shotAngleValue, shotForceValue, SinglePlayerGame.this), BorderLayout.CENTER);
		
		MouseListener buttonListener = new MouseAdapter() {
			public void mouseEntered(MouseEvent event) {
				Object source = event.getSource();
				((JComponent) source).setForeground(Color.RED);
			}
			public void mouseExited(MouseEvent event) {
				Object source = event.getSource();
				((JComponent) source).setForeground(Color.BLACK);

			}
		};
		optionsButton.addMouseListener(buttonListener);
		exitButton.addMouseListener(buttonListener);
	}
}	
	

