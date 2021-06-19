package main;

public class Circle {

	private int x = 0;
	private int y = 0;
	
	private int radius = 0;

	public Circle(int x, int y, int radius) {
		super();
		this.x = x;
		this.y = y;
		this.radius = radius;
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

	public void setRadius(int radius) {
		this.radius = radius;
	}
	
}
