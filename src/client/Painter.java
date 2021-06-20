package client;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;
import javax.swing.JSlider;

public class Painter extends JPanel implements  MouseMotionListener, ActionListener
{
	private static final long serialVersionUID = 1L;
	private int x=-1000, y=-1000; //initial x and y locations, paint won't appear
    private Color col = Color.BLACK;
    private boolean checkErase = false;

    private int curr_radius = 255/2;
    
    private JSlider slider;
    
    public Painter()
    {
        addMouseMotionListener(this);
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
        Client.sendCircle(x, y+curr_radius, curr_radius, col);
    }

    public void paint(Graphics g)
    {
    	curr_radius = slider.getValue();
    	
    	int center_x = x-curr_radius/4;
    	int center_y = y-curr_radius/4;
    	
    	if(checkErase == false) {
    		g.setColor(col);
        	g.fillOval(center_x, center_y,curr_radius,curr_radius);
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
        Client.getCanvas();
    }
}