package pl.edu.pw.fizyka.pojava.ArcherTheGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

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
	JButton exitButton, optionsButton, tempButton;
	JTextField shotStrength, shotAngle;
	static int temp=1;
	DataHolder hold;
	static Image im;
	OptionsWindow options;
	
	public void CloseSP(){
		super.dispose();
	}

	public SinglePlayerGame() throws HeadlessException {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(600,600);
		setResizable(false);
		setLocationRelativeTo(null);
		
		hold = new DataHolder();

		
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
		shotAngle = new JTextField("000");
		shotStrength = new JTextField("000");
		bottomPanel.add(angleLabel1 = new JLabel("Angle:"));
		bottomPanel.add(shotAngle);
		bottomPanel.add(angleLabel2 = new JLabel("Â°"));
		bottomPanel.add(forceLabel1 = new JLabel("Power:"));
		bottomPanel.add(shotStrength);
		bottomPanel.add(forceLabel2 = new JLabel("%"));
		bottomPanel.add(Box.createRigidArea(new Dimension(150,0)));
		optionsButton = new JButton("Opcje");
		exitButton = new JButton("Wyjscie");
		
		tempButton = new JButton("Strza³");
		bottomPanel.add(tempButton);
		
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
			
		
		//------------Centre---//
		URL resource = getClass().getResource("/images/1.PNG");
		try {
			 im = ImageIO.read(resource);
			} 
		catch (IOException e) {
			 System.err.println("Blad odczytu obrazka");
			 e.printStackTrace();
			}
		
		
		PlayArea centrePanel = new PlayArea(im, PlayArea.SCALED, 1.0f, 0.5f, shotAngle, shotStrength);
		GradientPaint paint = new GradientPaint(0, 0, Color.WHITE, 600, 0, Color.WHITE);
		centrePanel.setPaint(paint);
		centrePanel.setSize(400, 400);
		
		this.add(centrePanel);
		
		
		tempButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				centrePanel.g=hold.g;
				centrePanel.ro=hold.ro;
				centrePanel.mass=hold.mass;
				centrePanel.diameter=hold.diameter;
				centrePanel.coeff=hold.coeff;
				centrePanel.a=centrePanel.coeff*centrePanel.ro*centrePanel.mass*centrePanel.diameter;

				System.out.println("g " + Double.toString(hold.g));
				System.out.println("ro " +Double.toString(hold.ro));
				System.out.println("mass " +Double.toString(hold.mass));
				System.out.println("diameter " +Double.toString(hold.diameter));
				System.out.println("coeff " +Double.toString(hold.coeff));
				
				centrePanel.cons=1;
				centrePanel.xPos = 0;
				centrePanel.yPos = 400;
				
				Thread gamethread = new Thread(centrePanel);
				gamethread.start();			
				
			}			
		});
		
		
		
		
		
	}
	
}
