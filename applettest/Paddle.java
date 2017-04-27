package applettest;

import java.awt.Graphics;

public class Paddle extends Elements {
	private int width, height;
	
	public Paddle(int x, int y, int width, int height) {
		super(x, y);
		// TODO Auto-generated constructor stub
		this.width = width;
		this.height = height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	@Override
	void draw(Graphics g) {
		// TODO Auto-generated method stub
		g.drawRect(getX(), getY(), width, height);
	}

}
