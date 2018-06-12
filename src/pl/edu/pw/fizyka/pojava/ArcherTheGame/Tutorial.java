package pl.edu.pw.fizyka.pojava.ArcherTheGame;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Tutorial extends JFrame {
	
	BufferedImage im;
	JButton continueBtn;
	JPanel imagePain, bottom;
	
	Tutorial(){
		this.setSize(1000, 482);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);	
		setLocationRelativeTo(null);
		setUndecorated(true);
		setLayout(new BorderLayout());
		
		imagePain = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
					super.paintComponent(g);
	                try {
	        			im = ImageIO.read(getClass().getResource("/images/tutorial.png"));
	        		} catch (IOException e) {			
	        			e.printStackTrace();
	        		}
	                g.drawImage(im, 0, 0,this.getWidth(), this.getHeight(), this);
	            }
	        };
	        this.add(imagePain, BorderLayout.CENTER);
	        
	        add(bottom = new JPanel(), BorderLayout.PAGE_END);
	        bottom.add(continueBtn = new JButton("Continue"));
	        continueBtn.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					Tutorial.this.dispose();
				}
	        	
	        });
	        		
	}
	

}
