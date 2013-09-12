import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

@SuppressWarnings("serial")
public class TronMapTwoPlayer extends TronMap {

	// the second human player
	private PlayerHuman player2;
	
	// scores of player one and two
	private int i = 0;
	private int j = 0;
	
	// outcome of the match
	private boolean p1 = false;
	private boolean p2 = false;
	private boolean tie = false;

	// constructor calls super and adds KeyListeners
	public TronMapTwoPlayer(JLabel sco1, JLabel sco2, int p) {
		super(sco1, sco2, p);
		
		// adds KeyListeners for player two
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (!player2.getAlive()) {
				} else if (e.getKeyCode() == KeyEvent.VK_A) {
					player2.setXVelocity(-VELOCITY);
					player2.setYVelocity(0);
				} else if (e.getKeyCode() == KeyEvent.VK_D) {
					player2.setXVelocity(VELOCITY);
					player2.setYVelocity(0);
				} else if (e.getKeyCode() == KeyEvent.VK_W) {
					player2.setYVelocity(-VELOCITY);
					player2.setXVelocity(0);
				} else if (e.getKeyCode() == KeyEvent.VK_S) {
					player2.setYVelocity(VELOCITY);
					player2.setXVelocity(0);
				} else if (e.getKeyCode() == KeyEvent.VK_Q) {
					player2.jump();
				} else if (e.getKeyCode() == KeyEvent.VK_1) {
					player2.startBoost();
				}
			}
			public void keyReleased(KeyEvent e) {
			}
		});
	}
	
	// moves both players and checks if they crash
	void tick() {
		player.setBounds(getWidth(), getHeight());
		player.move();
		player2.setBounds(getWidth(), getHeight());
		player2.move();
		for (Player k1: players) {
			for (Player k2: players) {
				k1.crash(k1.intersects(k2));
			}
		}
		if (!player.getAlive() || !player2.getAlive()) {
			timer.stop();
			run = false;
			addScore();
		} 
		setScore();
		repaint();
	}
	
	// restarts the score if the game is exited
	public void restartGame() {
		i = 0;
		j = 0;
	}
	
	// sets the players' scores and displays the boost left in game
	public void setScore() {
		score1.setText
			("   Player 1: " + i + "    Boost: " + player.getBoostsLeft());
		score2.setText
			("   Player 2: " + j + "    Boost: " + player2.getBoostsLeft());
	}	
	
	// initializes all players and restarts the timer
	public void reset() {
		p1 = false;
		p2 = false;
		tie = false;
		int[] start1 = getRandomStart();
		player = new PlayerHuman
				(start1[0], start1[1], start1[2], start1[3], Color.CYAN);
		players[0] = player;
		int[] start2 = getRandomStart();
		player2 = new PlayerHuman
				(start2[0], start2[1], start2[2], start2[3], Color.PINK);
		players[1] = player2;
		timer.start();
		requestFocusInWindow();
	}
	
	// updates the scores after each round
	public void addScore() {
		if (!run) {
			if (player2.getAlive()) {
				p2 = true;
				j++;
			} else if (player.getAlive()) {
				p1 = true;
				i++;
			} else {
				tie = true;
			}
		}
		score1.repaint();
		score2.repaint();
	}
	
	// draws the outcome of each match
	@Override
	public void paintComponent(Graphics g) {
	   super.paintComponent(g);
	   if (p1) {
		   try {
			   BufferedImage picture = ImageIO.read(new File("p1_wins.png"));
			   g.drawImage
			   		(picture, MAPWIDTH / 2 - 180, MAPHEIGHT / 2 - 30, null);
		   } catch (IOException e) {
		   }
	   }
	   if (p2) {
		   try{
			   BufferedImage picture = ImageIO.read(new File("p2_wins.png"));
			   g.drawImage
			   		(picture, MAPWIDTH / 2 - 180, MAPHEIGHT / 2 - 30, null);
		   } catch (IOException e) {
		   }
	   }
	   if (tie) {
		   try {
			   BufferedImage picture = ImageIO.read(new File("tie.png"));
			   g.drawImage
			   		(picture, MAPWIDTH / 2 - 120, MAPHEIGHT / 2 - 30, null);
		   } catch (IOException e) {			   
		   }
	   }
	}
}