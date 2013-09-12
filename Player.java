import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

public abstract class Player extends GameObject {
	
	// player's colors
	Color color;
	
	// states of the player
	boolean alive = true;
	boolean jump = false;
	boolean booster = false;
	
	// initial conditions
	int startVel = 0;
	int boostLeft = 3;

	// static values to be used by all Player objects
	static int WIDTH = 5;
	static int HEIGHT = 5;
	static int VELBOOST = 5;
	static int JUMPHEIGHT = 16;
	
	// timer for length of boost
	Timer boostTimer;
		
	// Player object's path
	ArrayList<Shape> lines = new ArrayList<Shape>();
	
	// constructor initializes initial conditions, timer, and color
	public Player(int randX, int randY, int velx, int vely, Color color) {
		super(randX, randY, velx, vely, WIDTH, HEIGHT);
		startVel = Math.max(Math.abs(velx), Math.abs(vely));
		boostTimer = new Timer(300, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				booster = false;
				boostTimer.stop();
			}
		});
		this.color = color;
	}
	
	// used for label to display the number of boosts left
	public int getBoostsLeft() {
		return boostLeft;
	}
	
	// changes state of Player if it exits the bounds
	public void accelerate() {
		if (x < 0 || x > rightBound) {
			velocityX = 0;
			alive = false;
		}
		if (y < 0 || y > bottomBound) {
			velocityY = 0;
			alive = false;
		}
	}	
	
	// changes state of Player to jumping
	public void jump() {
		jump = true;
	}
	
	// changes state of Player to boosting
	public void startBoost() {
		if (boostLeft > 0) {
			booster = true;
			boostTimer.start();
			boostLeft--;
		}
	}
	
	// changes velocity for boosting
	public void boost() {
		if (booster) {
			if (velocityX > 0) {
				velocityX = VELBOOST;
			} else if (velocityX < 0) {
				velocityX = -VELBOOST;
			} else if (velocityY > 0) {
				velocityY = VELBOOST;
			} else if (velocityY < 0) {
				velocityY = -VELBOOST;
			}
		} else {
			if (velocityX > 0) {
				velocityX = startVel;
			} else if (velocityX < 0) {
				velocityX = -startVel;
			} else if (velocityY > 0) {
				velocityY = startVel;
			} else if (velocityY < 0) {
				velocityY = -startVel;
			}
		}
	}
	
	// draws Player and path
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillRect(x - WIDTH/2, y - HEIGHT/2, WIDTH, HEIGHT);
		for (Shape k: lines) {
			k.draw(g);
		}
	}
	
	// returns the state of the Player
	public boolean getAlive() {
		return alive;
	}
	
	// returns the Player's path
	public ArrayList<Shape> getPath() {
		return lines;
	}
	
	// checks if the Player has crashed with a path
	public void crash(Intersection i) {
		if (i == Intersection.UP) {
			velocityX = 0;
			velocityY = 0;
			alive = false;
		}
	}
	
	// moves the Player on the screen based on its velocity
	public abstract void move();
	
	// adds Player objects to the field
	abstract void addPlayers(Player[] players);
	
}