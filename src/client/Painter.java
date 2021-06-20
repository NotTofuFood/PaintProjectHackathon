package client;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.JPanel;

public class Painter extends JPanel implements  MouseMotionListener, ActionListener
{
	private static final long serialVersionUID = 1L;
	private int x=-1000, y=-1000; //initial x and y locations, paint won't appear
    private Color col = Color.BLACK;
    private boolean checkErase = false;
    private int curRadius;
    
    private Canvas main;
    
    public Painter(Canvas main)
    {
    	this.main = main;
    	
        addMouseMotionListener(this);
        setVisible(true);
        
        //setOpaque(false);
        
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
            checkErase = false;
        }
    }

    public void mouseMoved(MouseEvent e)
    {
    }
    
    public  void mouseDragged(MouseEvent e)
    {
        x = e.getX(); y= e.getY();
        repaint();
        curRadius = main.getRadius();
        if(!checkErase) {
        	Client.sendCircle(x, y, curRadius, col);
        }else {
        	Client.sendEraser(x, y, curRadius);
        }
    }

    public void paint(Graphics g)
    {
    	int center_x = x-curRadius/4;
    	int center_y = y-curRadius/4;
    	
    	if(checkErase == false) {
    		g.setColor(col);
        	g.fillOval(center_x, center_y,curRadius,curRadius);
    	}
    	else {
    		g.clearRect(center_x, center_y,curRadius,curRadius);
    	}
    	if(revertOld) {
    		revertOld = false;
    		this.x = this.oldX;
    		this.y = this.oldY;
    		this.col = this.oldCol;
    		this.checkErase = this.oldCheckErase;
    		this.curRadius = this.oldRadius;
    	}
    	
    }
    
    int oldX;
    int oldY;
    Color oldCol;
    boolean oldCheckErase;
    int oldRadius;
    boolean revertOld = false;
    
    public void drawCircle(int x, int y, int radius, Color col) {
    	System.out.println("got circle from server");
    	this.oldX = this.x;
    	this.oldY = this.y;
    	this.oldCol = this.col;
    	this.oldCheckErase = this.checkErase;
    	this.revertOld = true;
    	this.oldRadius = this.curRadius;
    	
    	this.x = x;
    	this.y = y;
    	this.col = col;
    	this.curRadius = radius;
    	this.checkErase = false;
    	
    	repaint();
    }

    public void drawErase(int x, int y, int length){
    	System.out.println("got erase from server");
    	this.oldX = this.x;
    	this.oldY = this.y;
    	this.oldCol = this.col;
    	this.oldCheckErase = this.checkErase;
    	this.oldRadius = length;
    	this.revertOld = true;

    	this.x = x;
    	this.y = y;
    	this.curRadius = length;
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