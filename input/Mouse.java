package input;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class Mouse implements  MouseMotionListener, ActionListener {

	private int x=-10, y=-10;
	
	public Mouse(Container c) {
		c.addMouseMotionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {
        x = e.getX(); y= e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
}
