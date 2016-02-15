import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.RenderingHints;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.lang.Thread;
import java.lang.Math;

public class DrawingSelector extends JPanel {//JComponent {
	
	private class ColorItem extends JPanel {
		private Color m_color;

		ColorItem(Color color) {
			m_color = color;
			setOpaque(true);
			setBackground(color);
			addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					System.out.println("The color is:: " + m_color);
				}
//			setBorder(BorderFactory.createLineBorder(color));
			//setMaximumSize(new Dimension(50, 50));
			});
		}

//		@Override
//		public void paintComponent(Graphics g) {
//
//		}
	}

	public DrawingSelector() {
//		setBorder(BorderFactory.createLineBorder(Color.RED));
		setMaximumSize(new Dimension(100, 400));
		setPreferredSize(new Dimension(100, 400));	
		setLayout(new GridLayout(6, 1));
		add(new ColorItem(Color.BLACK));
		add(new ColorItem(Color.RED));
		add(new ColorItem(Color.BLUE));
		add(new ColorItem(Color.MAGENTA));
		add(new ColorItem(Color.GREEN));
		add(new ColorItem(Color.ORANGE));
//		add(new ColorItem(Color.YELLOW));

		//this.addMouseListener(new MouseEventDemo());
		//this.addMouseMotionListener(new MouseMotionEventDemo());
	}

	private ArrayList<Stroke> m_strokeList = new ArrayList<Stroke>();

	private class Point {
		private int m_x;
		private int m_y;

		public Point(int x, int y) {
			m_x = x;
			m_y = y;
		}

		int getX() {
			return m_x;
		}
		int getY() {
			return m_y;
		}
	}

	private class Stroke {
		private ArrayList<Point> m_pointList = new ArrayList<Point>();
		
		public Stroke(Point point) {
			m_pointList.add(point);	
		}

		public void addPoint(Point point) {
			m_pointList.add(point);
		}

		public void draw(Graphics2D g2) {
			for (int i = 0; i < m_pointList.size(); i++) {
			g2.setStroke(new BasicStroke(10, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		    g2.setColor(Color.BLACK);
			if (i == m_pointList.size() - 1) {
				g2.drawLine(m_pointList.get(i).getX(), m_pointList.get(i).getY(),
                        m_pointList.get(i).getX(), m_pointList.get(i).getY());
				break;
			}
    		g2.drawLine(m_pointList.get(i).getX(), m_pointList.get(i).getY(),
						m_pointList.get(i + 1).getX(), m_pointList.get(i + 1).getY());
			}
		}
	}

	public class MouseEventDemo extends MouseAdapter {

    public void mousePressed(MouseEvent e) {
       saySomething("Mouse pressed; # of clicks: "
                    + e.getClickCount() + "hello::: " + e.getX() + " and " + e.getY(), e);
		m_strokeList.add(new Stroke(new Point(e.getX(), e.getY())));
		repaint();
    }

    public void mouseReleased(MouseEvent e) {
       saySomething("Mouse released; # of clicks: "
                    + e.getClickCount(), e);
    }

/*    public void mouseEntered(MouseEvent e) {
       saySomething("Mouse entered", e);
    }

    public void mouseExited(MouseEvent e) {
       saySomething("Mouse exited", e);
    }
*/
    public void mouseClicked(MouseEvent e) {
       saySomething("Mouse clicked (# of clicks: "
                    + e.getClickCount() + ")", e);
    }

    void saySomething(String eventDescription, MouseEvent e) {
        System.out.println(eventDescription + " detected on "
                        + e.getComponent().getClass().getName()
                        + ".");
    }

	}

	public class MouseMotionEventDemo implements MouseMotionListener {

    public void mouseMoved(MouseEvent e) {
       saySomething("Mouse moved", e);
    }

    public void mouseDragged(MouseEvent e) {
       saySomething("Mouse dragged", e);
		Stroke current = m_strokeList.get(m_strokeList.size() - 1);
		current.addPoint(new Point(e.getX(), e.getY()));
		repaint();
    }

    void saySomething(String eventDescription, MouseEvent e) {
        System.out.println(eventDescription 
                        + " (" + e.getX() + "," + e.getY() + ")"
                        + " detected on "
                        + e.getComponent().getClass().getName());
    }
	}

	public void paintComponent(Graphics g) {
	Graphics2D g2 = (Graphics2D) g; // cast to get 2D drawing methods
	g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  // antialiasing look nicer
				RenderingHints.VALUE_ANTIALIAS_ON);
	for (Stroke stroke : m_strokeList) {
		stroke.draw(g2);
	}
/*	g2.setStroke(new BasicStroke(getHeight()));
	g2.setColor(Color.BLACK);
	g2.drawLine(0, getHeight()/2, getWidth(), getHeight()/2);
		g2.setColor(Color.WHITE);
		g2.setFont(new Font("Arial", Font.BOLD, 20));			
		g2.drawString("Lucas Palmer 20534173", 50, 20);
		g2.drawString("Breakout", 50, 60);
		g2.drawString("Press Enter to start the game!", 50, 120);
		g2.drawString("Instructions:", 50, 150);
		g2.drawString("Press the left/right arrow keys to move the paddle.", 50, 180);
		g2.drawString("Press the Space Bar to pause the game.", 50, 210);
		g2.drawString("Press Enter to start the ball moving.", 50, 240);
		g2.drawString("Hit the bricks with a circle to get an extra ball.", 50, 270);
		*/
	}
}
