import java.awt.Graphics;
import java.util.ArrayList;

public abstract class GameObject {
	int x; // x and y coordinates upper left
	int y;

	int width; // width and height of the court
	int height;

	int velocityX; // Pixels to move each time move() is called.
	int velocityY;

	int rightBound; // Maximum permissible x, y values.
	int bottomBound;

	public GameObject(int x, int y, int velocityX, int velocityY, int width,
			int height) {
		this.x = x;
		this.y = y;
		this.velocityX = velocityX;
		this.velocityY = velocityY;
		this.width = width;
		this.height = height;
	}
	
	// adjusts the bounds of the court
	public void setBounds(int width, int height) {
		rightBound = width - this.width;
		bottomBound = height - this.height;
	}
	
	// sets the player's x velocity as long as it doesn't oppose current motion
	public void setXVelocity(int velocityX) {
		if (!(velocityX > 0 && this.velocityX < 0)
				&& !(velocityX < 0 && this.velocityX > 0)) {
			this.velocityX = velocityX;
		}
	}
	
	// sets the player's y velocity as long as it doesn't oppose current motion
	public void setYVelocity(int velocityY) {
		if (!(velocityY > 0 && this.velocityY < 0)
				&& !(velocityY < 0 && this.velocityY > 0)) {
			this.velocityY = velocityY;
		}
	}

	// Move the object at the given velocity.
	public void move() {
		x += velocityX;
		y += velocityY;

		accelerate();
		clip();
	}

	// Keep the object in the bounds of the court
	public void clip() {
		if (x < 0)
			x = 0;
		else if (x > rightBound)
			x = rightBound;

		if (y < 0)
			y = 0;
		else if (y > bottomBound)
			y = bottomBound;
	}

	/**
	 * Compute whether an object intersects a shape.
	 * 
	 * @param other
	 *            The other game object to test for intersection with.
	 * @return NONE if the objects do not intersect, otherwise UP.
	 * 
	 */
	public Intersection intersects(GameObject other) {
		if (other != this) {
			if (other.y - other.height/2 <= y + height/2 &&
				other.y + other.height/2 >= y - height/2 &&
				other.x - other.width/2 <= x + width/2 &&
				other.x + other.width/2 >= x - width/2) {
				return Intersection.UP;
			}
		}
		ArrayList<Shape> pa = other.getPath();
		for (int i = 0; i < pa.size() - 1; i++) {
			Shape k = pa.get(i);
			int x1 = k.getStartX();
			int y1 = k.getStartY();
			int x2 = k.getEndX();
			int y2 = k.getEndY();

			if (y1 == y2) {
				if (Math.abs(y1 - y) <= height/2 && 
					(x >= Math.min(x1, x2) && x <= Math.max(x1, x2))) {
					return Intersection.UP;
				}
			} else if (x1 == x2) {
				if (Math.abs(x1 - x) <= width/2 &&
					(y >= Math.min(y1, y2) && y <= Math.max(y1, y2))) {					
					return Intersection.UP;
				}
			}
		}
		return Intersection.NONE;
	}
	
	// checks if an object has crossed the bounds of the screen
	public abstract void accelerate();

	// draws the object
	public abstract void draw(Graphics g);
	
	// returns true if the player is alive
	public abstract boolean getAlive();
	
	// returns the player's path as a list of shapes
	public abstract ArrayList<Shape> getPath();
}
