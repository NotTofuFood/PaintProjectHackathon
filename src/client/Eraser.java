package client;

import java.awt.Color;
import java.awt.Graphics;

public class Eraser extends Circle{

	public Eraser(int x, int y, int radius) {
		super(x, y, radius, Color.BLACK);
	}
	
	public void draw(Graphics g) {
		g.clearRect(getX(), getY(), getRadius(), getRadius());
	}
	
}
