
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;

@SuppressWarnings("serial")
public class TronMapSurvival extends TronMap {
	
	// creates the list of high scores
	Score highs = new Score("HighScores.txt");
	List<Integer> highScores = new ArrayList<Integer>();
	
	// constructor calls super and gets the current high scores
	public TronMapSurvival(JLabel sco1, int p) {
		super(sco1, null, p);
		this.highScores = highs.getHighScores();
	}
	
	// checks if the Player objects are in bounds, moves them,
	// and checks if they are alive
	void tick() {
		for (Player k: players) {
			if (k != null) {
				k.setBounds(getWidth(), getHeight());
				k.move();
			}
		}
		for (Player k1: players) {
			for (Player k2: players) {
				k1.crash(k1.intersects(k2));
			}
		}
		if (!player.getAlive()) {
			timer.stop();
			run = false;
			addScore();
			setScore();
		} else {
			run = true;
			setScore();
		}
		repaint();
	}
	
	// sets the score for the current game
	public void setScore() {
		score1.setText("   Score: " + ++i + 
				"   Boost: " + player.getBoostsLeft());
		score1.repaint();
	}	
	
	// reinitializes all players and restarts the timer.
	public void reset() {
		int[] start1 = getRandomStart();
		player = new PlayerHuman(start1[0], start1[1], 
				start1[2], start1[3], Color.CYAN);
		players[0] = player;
		i = 0;
		timer.start();
		requestFocusInWindow();
	}
	
	// adds the new score to the high scores.
	public void addScore() {
		try {
			highs.addHighScore(i + 1);
			highScores = highs.getHighScores();
		} catch (IOException e) {
		}
	}
	
	// creates a panel that displays the current high scores.
	public JPanel getHighs() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(6, 2));
		panel.setBackground(Color.BLACK);
		
		JLabel j0 = new JLabel("    Survival Mode High Scores: ");
		j0.setForeground(Color.WHITE);
		j0.setBackground(Color.BLACK);
		panel.add(j0);
		
		JLabel j100 = new JLabel("");
		j100.setBackground(Color.BLACK);
		panel.add(j100);
		
		int left = 1;
		int right = 6;
		for (int i = 0; i < 5; i++) {
			JLabel j1 = new JLabel("      " + left + ".) " + highScores.get(left - 1).toString());
			j1.setForeground(Color.WHITE);
			j1.setBackground(Color.BLACK);
			panel.add(j1);
			JLabel j6 = new JLabel("      " + right + ".) " + highScores.get(right - 1).toString());
			j6.setForeground(Color.WHITE);
			j6.setBackground(Color.BLACK);
			panel.add(j6);
			left++;
			right++;
		}
		return panel;
	}
	
	// paints "Game Over" if the player loses
	@Override
	public void paintComponent(Graphics g) {
	   super.paintComponent(g);
	   if (!player.getAlive()) {
		   try{
			   BufferedImage picture = ImageIO.read(new File("over.png"));
			   g.drawImage(
					   picture, MAPWIDTH / 2 - 230, MAPHEIGHT / 2 - 110, null);
		   } catch (IOException e) {
		   }
	   }
	}
}