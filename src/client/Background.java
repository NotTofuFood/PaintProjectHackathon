package client;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Background extends JPanel{
	private static final long serialVersionUID = 1L;

	public Background() {
		
	}
	
	public void paintComponent(Graphics g) {
    	BufferedImage bg = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
    	try {
			bg = ImageIO.read(new File("src/paper.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	g.drawImage(bg, 0, 0, 800, 600, this);
    }
}
