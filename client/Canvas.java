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


public class Canvas extends JFrame implements  MouseMotionListener, ActionListener
{
    private int x=-10, y=-10; //initial x and y locations, paint won't appear
    private Color col = Color.BLACK;
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
        if (act.equals("RED"))
            col =Color.RED;
        else if (act.equals("GREEN"))
            col =Color.GREEN;
        else if (act.equals("BLUE"))
            col =Color.BLUE;
        else if (act.equals("YELLOW"))
            col =Color.YELLOW;
        else if (act.equals("ERASER"))
            col =Color.WHITE;
        else
            col =Color.BLACK;

    }


    public void mouseMoved(MouseEvent e)
    {
    }
    public  void mouseDragged(MouseEvent e)
    {
        x = e.getX(); y= e.getY();
        repaint();
    }

    public void paint(Graphics g)
    {
        g.setColor(col);
        g.fillOval(x,y,10,10);
    }

    public static void main (String args[])
    {
        Canvas p = new Canvas();
    }


}






