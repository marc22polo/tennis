package applettest;

import java.awt.Graphics;

public abstract class Elements {
	private int x, y;

	public Elements(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public int[] getCoor(){
		int[] coor = {x, y};
		return coor;
	}
	
	abstract void draw(Graphics g);
	
}
