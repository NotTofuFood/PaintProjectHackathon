package client;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
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
			Image img = (ImageIO.read(new File("src/paper.png")).getScaledInstance(800, 600, Image.SCALE_SMOOTH));
			bg = new BufferedImage(img.getWidth(null), img.getHeight(null),
			BufferedImage.TYPE_INT_RGB);
			Graphics g = bg.createGraphics();
			g.drawImage(img, 0, 0, null);
			g.dispose();

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
		int x = clamp(0, this.width-getRadius(), getX());
		int y = clamp(0, this.height-getRadius(), getY());
		
    	g.drawImage(bg.getSubimage(x, y, getRadius(), getRadius()), getX(), getY(), Canvas.io);
		//g.clearRect(getX(), getY(), getRadius(), getRadius());
	}
	
}