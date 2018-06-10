package pl.edu.pw.fizyka.pojava.ArcherTheGame;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class EndGame extends JFrame {
	JPanel center, bottom;
	int imageLenght, imageWidth;
	JButton continueBtnMulti, continueBtnSingle, exitBtn;
	JLabel winnerLabel;
	JFrame currentGame;
	Player player1, player2;
	Enemy enemy;
	StartMenu  menu;
	BufferedImage trophyImage;
	int winner;
	

	public EndGame(Player player1,Player player2, JFrame currentGame, int winner) {
		setSize(300, 100);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		setUndecorated(true);
		
		this.player1 = player1;
		this.player2 = player2;
		this.currentGame = currentGame;
		this.winner = winner;
		
		currentGame.setEnabled(false);
		add(center = new JPanel(), BorderLayout.CENTER);
		winnerLabel = new JLabel();
		winnerLabel.setFont(new Font("MAKEN", Font.BOLD, 30));
		if(winner == 1) {
			winnerLabel.setText("Player 1 won!");
		} else if(winner == 2) {
			winnerLabel.setText("Player 2 won!");
		}
		center.add(winnerLabel);
		
		BtnListener listener = new BtnListener();
		add(bottom = new JPanel(), BorderLayout.PAGE_END);
		bottom.add(continueBtnMulti = new JButton("Play again"));
		continueBtnMulti.addActionListener(listener);
		bottom.add(exitBtn = new JButton("Exit to menu"));
		exitBtn.addActionListener(listener);
		
	}
	
	public EndGame(Player player1, Enemy enemy,JFrame currentGame) {
		setSize(300, 100);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		setUndecorated(true);
		
		this.player1 = player1;
		this.enemy = enemy;
		this.currentGame = currentGame;
		
		currentGame.setEnabled(false);
		add(center = new JPanel(), BorderLayout.CENTER);
		BtnListener listener = new BtnListener();
		winnerLabel = new JLabel("You won!");
		winnerLabel.setFont(new Font("MAKEN", Font.BOLD, 30));
		center.add(winnerLabel);
		add(bottom = new JPanel(), BorderLayout.PAGE_END);
		bottom.add(continueBtnSingle = new JButton("Play again"));
		continueBtnSingle.addActionListener(listener);
		bottom.add(exitBtn = new JButton("Exit to menu"));
		exitBtn.addActionListener(listener);
	}
	
	class BtnListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			Object source = e.getSource();
			if(source == continueBtnMulti) {
				currentGame.dispose();
				EndGame.this.setVisible(false);

				MultiPlayerGame multi= new MultiPlayerGame();
				multi.setVisible(true);
				
			} else if (source == continueBtnSingle){
				currentGame.dispose();
				EndGame.this.setVisible(false);
				SinglePlayerGame single = new SinglePlayerGame();
				single.setVisible(true);
			} else if( source == exitBtn) {
				currentGame.setVisible(false);
				menu = new StartMenu();
				menu.setVisible(true);
				EndGame.this.setVisible(false);
			}
			
		}
		
	}

} 