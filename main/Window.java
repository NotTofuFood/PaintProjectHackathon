package main;

import javax.swing.JFrame;

import display.Renderer;

public class Window {
	
	public static final int WIDTH = 800;
	public static final int HEIGHT = 800;
	
	public static void main(String[] args) {
		JFrame f = new JFrame("Paint Project");
		Renderer render = new Renderer();
		f.setSize(WIDTH, HEIGHT);
		f.setResizable(false);
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		f.add(render);
	}
	
}
