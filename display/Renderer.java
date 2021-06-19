package display;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import main.Window;

public class Renderer extends JPanel {

	private static final long serialVersionUID = 1L;

	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.clearRect(0, 0, Window.WIDTH, Window.HEIGHT);
		
		repaint();
	}
	
}
