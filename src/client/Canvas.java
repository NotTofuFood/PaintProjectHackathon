package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class Canvas extends JFrame implements ActionListener
{

	private static final long serialVersionUID = 1L;
	private int x=-1000, y=-1000; //initial x and y locations, paint won't appear
	private Color col = Color.BLACK;
	private boolean checkErase = false;

	private int curr_radius = 255/2;

	private JSlider slider;
	
	public List<Circle> circles = new ArrayList<>();
	private Painter painter;
	
	public Canvas()
	{
		//frame
		setTitle("Painter");
		setSize(800,600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);

		//panel
		JPanel p = new JPanel();
		p.setLayout (new GridLayout(8,2));
		JButton red = new JButton("RED");
		red.setBackground(Color.RED);

		JButton green = new JButton("GREEN");
		green.setBackground(Color.GREEN);

		JButton blue = new JButton("BLUE");
		blue.setBackground(Color.BLUE);

		JButton yellow = new JButton("YELLOW");
		yellow.setBackground(Color.YELLOW);

		JButton black = new JButton("BLACK");
		black.setBackground(Color.BLACK);

		JButton white = new JButton("ERASER");
		white.setBackground(Color.WHITE);

		//Slider
		slider = new JSlider(JSlider.VERTICAL, 1, 255, 255/2);
		p.add(slider);

		//adds the buttons to paint
		p.add(red);
		p.add(green);
		p.add(blue);
		p.add(yellow);
		p.add(black);
		p.add(white);

		//button triggers
		red.addActionListener(this);
		green.addActionListener(this);
		blue.addActionListener(this);
		yellow.addActionListener(this);
		black.addActionListener(this);
		white.addActionListener(this);

		JLabel instructions = new JLabel("Drag the mouse to draw",JLabel.RIGHT);

		JLabel direc =new JLabel("Drag to draw", JLabel.LEFT);
		p.add(direc);

		Container c =this.getContentPane();
		c.setLayout(new BorderLayout());

		p.setOpaque(false);
		c.add(p, BorderLayout.WEST);
		painter = new Painter(this);
		c.add(painter);

		setVisible(true);
		repaint();
	}

	public int getRadius() {
		return slider.getValue();
	}
	
	public void actionPerformed(ActionEvent e)
	{
		painter.actionPerformed(e);
	}
	
	public void drawCircle(int x, int y, int radius, Color col) {
		painter.drawCircle(x, y, radius, col);
	}
	
	public void drawErase(int x, int y, int length) {
		painter.drawErase(x, y, length);
	}
	
	public static void main (String args[])
	{
		Canvas p = new Canvas();
		Client.connect(p);
	}

}
