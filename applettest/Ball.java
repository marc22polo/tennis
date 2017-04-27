package applettest;

import java.awt.Graphics;

public class Ball extends Elements {
	private int radius;
	
	public Ball(int x, int y, int radius) {
		super(x, y);
		// TODO Auto-generated constructor stub
		this.radius = radius;
	}
	
	public int getRadius() {
		return radius;
	}
	
	public void setRadius(int radius) {
		this.radius = radius;
	}
	
	@Override
	void draw(Graphics g) {
		// TODO Auto-generated method stub
		g.drawOval(getX() - radius, getY() - radius, radius * 2, radius * 2);
	}
}
