
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

@SuppressWarnings("serial")
public class TronMapStory extends TronMap {
	
	// timer that controls the time between levels
	Timer time;
	
	// used for drawing images based on results
	boolean win = false;
	boolean over = false;
	
	// constructor calls super
	public TronMapStory(JLabel sco1, JLabel sco2, int p) {
		super(sco1, sco2, p);

	}

	// moves Player objects, checks for intersections, and checks 
	// which Player objects are still alive
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
			int check = 0;
			for (Player k: players) {
				if (!k.getAlive()) {
					check++;
				}
			}
			if (check == players.length - 1) {
				run = false;
				timer.stop();
				addScore();
				setScore();
			} else {
				run = true;
				setScore();
			}
		}
		repaint();
	}
	
	// sets the score that is being displayed as the game moves on.
	public void setScore() {
		score1.setText("     Score: " + i + 
				"    Level: " + (players.length - 1));
		score2.setText("             Boost: " + player.getBoostsLeft());
		score1.repaint();
	}	
	
	// starts the next level after a win after the timer expires
	public void nextLevel() {
		time.stop();
		win = false;
		if (players.length != 8) {
			players = new Player[players.length + 1];
			addPlayers();
			timer.start();
			requestFocusInWindow();
		} else {
			win = true;
			over = true;
			repaint();
		}
	}
	
	// adds Player objects to the court for each new level
	public void addPlayers() {
		int[] start = getRandomStart();
		player = new PlayerHuman(
				start[0], start[1], start[2], start[3], colors[0]);
		players[0] = player;
		for (int j = 1; j < players.length; j++) {
			start = getRandomStart();
			players[j] = new PlayerAI(start[0], start[1], 
					start[2], start[3], colors[j]);
		}
		for (Player p: players) {
			p.addPlayers(players);
		}
	}
	
	// initializes all Player objects and restarts the story mode
	public void reset() {
		i = 0;
		over = false;
		win = false;
		players = new Player[2];
		addPlayers();
		timer.start();
		requestFocusInWindow();
	}
	
	// updates the player's score after successfully completing a level
	public void addScore() {
		if (player.getAlive()) {
			i += 50 * (players.length - 1);
			win = true;
			time = new Timer(1000, new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					nextLevel();
				}
			});
			time.start();
		} else {
			over = true;
		}
	}
	
	// draws images based on the results of a level
   @Override
	public void paintComponent(Graphics g) {
	   super.paintComponent(g);
	   if (win) {
		   try {
			   BufferedImage picture = ImageIO.read(new File("win.png"));
			   g.drawImage(
					   picture, MAPWIDTH / 2 - 130, MAPHEIGHT / 2 - 30, null);
		   } catch (IOException e) {
		   }
	   }
	   if (over) {
		   try{
			   BufferedImage picture = ImageIO.read(new File("over.png"));
			   g.drawImage(
					   picture, MAPWIDTH / 2 - 230, MAPHEIGHT / 2 - 110, null);
		   } catch (IOException e) {
		   }
	   }
	}
}