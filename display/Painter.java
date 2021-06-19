package display;

import java.awt.Graphics2D;

import main.Circle;

public class Painter {
	
	public Circle[] visible_circles;
	
	public void renderCircles(Graphics2D g) {
		
		for(int i = 0; i < visible_circles.length; i++) {
			g.fillOval(visible_circles[i].getX(), visible_circles[i].getY(), 
					visible_circles[i].getRadius(), visible_circles[i].getRadius());
		}
	}
	
}
