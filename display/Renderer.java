package display;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import input.Mouse;
import main.Window;

public class Renderer extends JPanel {

	private static final long serialVersionUID = 1L;

	public static Painter painter_manager;
	
	private Mouse mouse;
	
	public Renderer() {
		painter_manager = new Painter();
		mouse = new Mouse(this);
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.clearRect(0, 0, Window.WIDTH, Window.HEIGHT);
		g.drawRect(mouse.getX(), mouse.getY(), 5,5);
	//	painter_manager.renderCircles(g2);
		repaint();
	}
	
}
