package pl.edu.pw.fizyka.pojava.ArcherTheGame;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Tutorial extends JFrame {
	
	BufferedImage im;
	
	Tutorial(){
		this.setSize(1000, 482);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);	
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
	

}
