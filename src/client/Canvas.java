package client;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.ActionListener;

import java.awt.Container;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.BorderLayout;
import java.awt.GridLayout;
//import client.*;

public class Canvas extends JFrame implements  MouseMotionListener, ActionListener
{
    private int x=-10, y=-10; //initial x and y locations, paint won't appear
    private Color col = Color.BLACK;
    private boolean checkErase = false;

    public Canvas()
    {
        //frame
        setTitle("Painter");
        setSize(800,600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //panel
        JPanel p = new JPanel();
        p.setLayout (new GridLayout(6,1));
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
        Container c =this.getContentPane();
        c.setLayout(new BorderLayout());

        JLabel direc =new JLabel("Drag to draw", JLabel.RIGHT);
        c.add(direc,BorderLayout.SOUTH);
        c.add(p, BorderLayout.WEST);

        c.addMouseMotionListener(this);
        setVisible(true);
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
        Client.sendCircle(x, y+25, 10, col);
    }

    public void paint(Graphics g)
    {
    	if(checkErase == false) {
    		g.setColor(col);
        	g.fillOval(x,y+25,10,10);
    	}
    	else {
    		g.clearRect(x,y+25,10,10);
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

    public static void main (String args[])
    {
        Canvas p = new Canvas();
        Client.connect(p);
    }


}