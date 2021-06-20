package client;

import java.awt.Color;
import java.awt.Graphics;

public class Circle {

	private int x = 0;
	private int y = 0;
	
	private int radius = 0;

	private Color c;
	
	public Circle(int x, int y, int radius, Color c) {
		super();
		this.x = x;
		this.y = y;
		this.radius = radius;
		this.c = c;
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

	public int getRadius() {
		return radius;
	}

	public Color getColor() {
		return c;
	}
	
	public void setRadius(int radius) {
		this.radius = radius;
	}
	
	public void draw(Graphics g) {
		g.setColor(getColor());
		g.fillOval(getX(), getY(), getRadius(), getRadius());
	}
}