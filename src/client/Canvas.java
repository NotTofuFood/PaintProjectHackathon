package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class Canvas extends JFrame implements  MouseMotionListener, ActionListener
{

	private static final long serialVersionUID = 1L;
	private int x=-1000, y=-1000; //initial x and y locations, paint won't appear
	private Color col = Color.BLACK;
	private boolean checkErase = false;

	private int curr_radius = 255/2;

	private JSlider slider;
	
	public List<Circle> circles = new ArrayList<>();

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

		c.add(p, BorderLayout.WEST);

		c.addMouseMotionListener(this);

		setVisible(true);
		repaint();
	}

	public void actionPerformed(ActionEvent e)
	{
		String act = e.getActionCommand();
		if (act.equals("RED")) {
			checkErase = false;
			col =Color.RED;
		}
		else if (act.equals("GREEN")) {
			checkErase = false;
			col =Color.GREEN;
		}
		else if (act.equals("BLUE")) {
			checkErase = false;
			col =Color.BLUE;
		}
		else if (act.equals("YELLOW")) {
			checkErase = false;
			col =Color.YELLOW;
		}
		else if (act.equals("ERASER")) {
			checkErase = true;
		}
		else {
			col =Color.BLACK;
		}
	}


	public void mouseMoved(MouseEvent e)
	{

	}

	public  void mouseDragged(MouseEvent e)
	{
		x = e.getX(); y= e.getY();
		repaint();


		int center_x = x-curr_radius/4;
		int center_y = y-curr_radius/4;


		circles.add(new Circle(center_x, center_y, curr_radius, col));

		//Client.sendCircle(center_x, center_y, curr_radius, col);

	}

	public void paintComponent(Graphics g)
	{
		
		drawBG(g);

		for(int i = 0; i < circles.size(); i++) {
			g.setColor(circles.get(i).getColor());
			g.fillOval(circles.get(i).getX(), circles.get(i).getY(), circles.get(i).getRadius(),circles.get(i).getRadius());
		}

		curr_radius = slider.getValue();

		int center_x = x-curr_radius/4;
		int center_y = y-curr_radius/4;

		if(checkErase == false) {

		}
		else {
			g.clearRect(center_x, center_y,curr_radius,curr_radius);
		}


		if(revertOld) {
			revertOld = false;
			this.x = this.oldX;
			this.y = this.oldY;
			this.col = this.oldCol;
			this.checkErase = this.oldCheckErase;
		}

	}

	int oldX;
	int oldY;
	Color oldCol;
	boolean oldCheckErase;
	boolean revertOld = false;

	public void drawBG(Graphics g) {
		BufferedImage bg = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
		try {
			bg = ImageIO.read(new File("src/paper.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		g.drawImage(bg, 0, 0, 800, 600, this);
		//repaint();
	}

	public void drawCircle(int x, int y, int radius, Color col) {
		System.out.println("got circle from server");
		this.oldX = this.x;
		this.oldY = this.y;
		this.oldCol = this.col;
		this.oldCheckErase = this.checkErase;
		this.revertOld = true;

		this.x = x;
		this.y = y;
		this.col = col;
		this.checkErase = false;
		repaint();
	}

	public void drawErase(int x, int y, int length){
		System.out.println("got erase from server");
		this.oldX = this.x;
		this.oldY = this.y;
		this.oldCol = this.col;
		this.oldCheckErase = this.checkErase;
		this.revertOld = true;

		this.x = x;
		this.y = y;
		this.checkErase = true;
		repaint();
	}



	public static void main (String args[])
	{
		Canvas p = new Canvas();
		Client.connect(p);
	}


}
