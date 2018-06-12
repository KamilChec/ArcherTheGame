package pl.edu.pw.fizyka.pojava.ArcherTheGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class MultiPlayerGame extends JFrame {
	
	JLabel player1Label, player2Label,vsLabel;
	JPanel topPanel, bottomPanel, shootPanel1, shootPanel2;
	JButton exitButton, optionsButton;
	JLabel  shotAngleValue1, shotForceValue1, shotAngleValue2, shotForceValue2;
	JLabel angleLabel1, angleLabel2, forceLabel1, forceLabel2;
	PlayArea centrePanel;
	Font maken;
	DataHolder hold;	
	OptionsWindow options;
	
	public void CloseMP(){
		super.dispose();
	}

	public MultiPlayerGame() throws HeadlessException {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(1000, 600);
		setResizable(false);
		setLocationRelativeTo(null);
		hold = new DataHolder();
		maken = new Font("MAKEN", Font.BOLD, 30);
		
		//---------Top-----------
		this.add(topPanel = new JPanel(), BorderLayout.PAGE_START);		
		player1Label = new JLabel("Player 1");
		player1Label.setFont(maken);
		topPanel.add(player1Label);
		topPanel.add(Box.createRigidArea(new Dimension(50,0)));
		vsLabel = new JLabel("VS");
		vsLabel.setFont(maken);
		topPanel.add(vsLabel);
		topPanel.add(Box.createRigidArea(new Dimension(50,0)));
		player2Label = new JLabel("Player 2");
		player2Label.setFont(maken);
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
		
		bottomPanel.add(Box.createRigidArea(new Dimension(100,0)));
		
		optionsButton = new JButton("Options");
		optionsButton.setFont(maken);
		optionsButton.setBorderPainted(false);
		exitButton = new JButton("Exit");
		exitButton.setFont(maken);
		exitButton.setBorderPainted(false);
		bottomPanel.add(optionsButton);
		bottomPanel.add(exitButton);
		
		bottomPanel.add(Box.createRigidArea(new Dimension(100,0)));
		
		bottomPanel.add(angleLabel1 = new JLabel("Angle:"));
		shotAngleValue2 = new JLabel("000");
		shotForceValue2 = new JLabel("000");
		bottomPanel.add(shotAngleValue2);
		bottomPanel.add(angleLabel2 = new JLabel("°"));
		bottomPanel.add(forceLabel1 = new JLabel("Power:"));
		bottomPanel.add(shotForceValue2);
		bottomPanel.add(forceLabel2 = new JLabel("%"));
		
		optionsButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				options = new OptionsWindow(hold);				
				options.setVisible(true);		
				
			}			
		});
		exitButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				StartMenu window = new StartMenu();
				CloseMP();
				window.setVisible(true);

			}			
		});

		//---CentrePanel---
		add(centrePanel = new PlayArea(shotAngleValue1, shotForceValue1,
				shotAngleValue2, shotForceValue2, MultiPlayerGame.this), BorderLayout.CENTER);
		
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
