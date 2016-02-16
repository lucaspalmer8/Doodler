import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Model {

	private Color m_drawingColor;
	private int m_width;
	private View m_view;

	public Model() {
		m_drawingColor = Color.BLACK;
		m_width = 10;
	}

	public void setView(View view) {
		m_view = view;
	}

	public Color getColor() {
		return m_drawingColor;
	}

	public int getWidth() {
		return m_width;
	}

	public void setColor(Color color) {
		m_drawingColor = color;
	}

	public void setWidth(int width) {
		m_width = width;
	}

	public void newStroke(MouseEvent e) {
		m_strokeList.add(new Stroke(new Point(e.getX(), e.getY())));
	}

	public void extendStroke(MouseEvent e) {
		Stroke current = m_strokeList.get(m_strokeList.size() - 1);
        current.addPoint(new Point(e.getX(), e.getY()));
	}

	public ArrayList<Stroke> getStrokeList() {
		return m_strokeList;
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

    public class Stroke {
        private ArrayList<Point> m_pointList = new ArrayList<Point>();
        private Color m_color;

        public Stroke(Point point) {
            m_pointList.add(point);
            m_color = m_drawingColor;
            //System.out.println("Settttttttttttttttttttttttttttttttttttttttttttttttting the color to:::::: " + m_color);
        }

        public void addPoint(Point point) {
            m_pointList.add(point);
        }
		public void draw(Graphics2D g2) {
            for (int i = 0; i < m_pointList.size(); i++) {
            g2.setStroke(new BasicStroke(10, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2.setColor(m_color);
            //System.out.println("Drawingttttttttttttttttttttttttttttttttttttttttttttttttt the color to:   " + m_color);
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
}
