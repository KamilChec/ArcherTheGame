package pl.edu.pw.fizyka.pojava.ArcherTheGame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
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
	JButton but;
	
	Tutorial(){
		this.setSize(1000, 500);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setLayout(new BorderLayout());
		
		but=new JButton("Przejdz do gry");
		this.add(but, BorderLayout.PAGE_END);
		
		but.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CloseTutorial();
			}
			
		});
		
		JPanel pane = new JPanel() {
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
	        
	        
	        this.add(pane);
	}
	
	public void CloseTutorial() {
		this.dispose();
	}
	

}
