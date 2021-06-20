package client;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Eraser extends Circle{

   	BufferedImage bg = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
	
   	private int width;
   	private int height;
   	
	public Eraser(int x, int y, int radius) {
		super(x, y, radius, Color.BLACK);
    	try {
			bg = ImageIO.read(new File("src/paper.png"));
			width = bg.getWidth();
			height = bg.getHeight();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int clamp(int min, int max, int value) {
		if(value >= max) return max;
		if(value <= min) return min;
		return value;
	}
	
	public void draw(Graphics g) {
		int x = clamp(0, width, getX());
		int y = clamp(0, height, getY());

		int radius = clamp(0, getX() + getRadius(), getRadius());
		int radius_y = clamp(0, getY() + getRadius(), getRadius());
		
    	g.drawImage(bg.getSubimage(x, y, radius, radius_y), getX(), getY(), Canvas.io);
		//g.clearRect(getX(), getY(), getRadius(), getRadius());
	}
	
}