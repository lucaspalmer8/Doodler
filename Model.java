import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Model {

	private Color m_drawingColor;
	private int m_drawingWidth;
	private ArrayList<ViewInterface> m_views = new ArrayList<ViewInterface>();
	private ArrayList<Stroke> m_strokeList = new ArrayList<Stroke>();
	private JFrame m_frame;
	private int m_sliderNumber;

	public Model(JFrame frame) {
		m_drawingColor = Color.BLACK;
		m_drawingWidth = 10;
		m_frame = frame;
		m_sliderNumber = 0;
	}

	public void addObserver(ViewInterface view) {
		m_views.add(view);
	}

	public void notifyViews() {
		for (ViewInterface view : m_views) {
			view.notifyView();
		}
	}

	public void setSliderNumber(int num) {
		if (m_sliderNumber == num) {
			return;
		}
		m_sliderNumber = num;
		notifyViews();
	}

	public int getSliderNumber() {
		return m_sliderNumber;
	}

	public JFrame getFrame() {
		return m_frame;
	}

	public Color getColor() {
		return m_drawingColor;
	}

	public int getWidth() {
		return m_drawingWidth;
	}

	public void setColor(Color color) {
		m_drawingColor = color;
	}

	public void setWidth(int width) {
		m_drawingWidth = width;
	}

	public void newStroke(MouseEvent e) {
		//right here, we must clean up the strokeList based on sliderNumber before adding things to it!!!!!!!!!
		while (m_strokeList.size() > 0 && m_strokeList.size() - 1 >= m_sliderNumber/100) {
			m_strokeList.remove(m_strokeList.size() - 1);
		}

		m_strokeList.add(new Stroke(new Point(e.getX(), e.getY(), System.currentTimeMillis())));
		notifyViews();
	}

	public void extendStroke(MouseEvent e) {
		Stroke current = m_strokeList.get(m_strokeList.size() - 1);
        current.addPoint(new Point(e.getX(), e.getY(), System.currentTimeMillis()));
		notifyViews();
	}

	public void strokeFinished() {
		m_strokeList.get(m_strokeList.size() - 1).finished();
		m_sliderNumber = m_strokeList.size()*100;
		System.out.println("Settin the model slider number to::   " + m_sliderNumber);
		notifyViews();
		System.out.println("Stroke finished");
	}
	
	public int getFinishedStrokes() {
		for (int i = 0; i < m_strokeList.size(); i++) {
			if (!m_strokeList.get(i).isFinished()) {
				return i;
			}
		}
		return m_strokeList.size();
	}

	public ArrayList<Stroke> getStrokeList() {
		return m_strokeList;
	}

    private class Point {
        private int m_x;
        private int m_y;
		private long m_timeStamp;

        public Point(int x, int y, long time) {
            m_x = x;
            m_y = y;
			m_timeStamp = time;
        }

        int getX() {
            return m_x;
        }
        int getY() {
            return m_y;
        }
		long getTimeStamp() {
			return m_timeStamp;
		}
    }

    public class Stroke {
        private ArrayList<Point> m_pointList = new ArrayList<Point>();
        private Color m_color;
		private int m_width;
		private boolean m_finished;

        public Stroke(Point point) {
            m_pointList.add(point);
            m_color = m_drawingColor;
			m_width = m_drawingWidth;
			m_finished = false;
        }

		public void finished() {
			m_finished = true;
		}

		public boolean isFinished() {
			return m_finished;
		}

        public void addPoint(Point point) {
            m_pointList.add(point);
        }

		public void draw(Graphics2D g2, int index) {
			if (isFinished() && index >= m_sliderNumber/100 + 1) {
				return;
			}

			Point last = m_pointList.get(m_pointList.size() - 1);
			Point first = m_pointList.get(0);
			float totalTime = last.getTimeStamp() - first.getTimeStamp();
			System.out.println(":::::::total time::::::::::  " + totalTime);
			float sliderRatio = (m_sliderNumber % 100)/100f;
			boolean drawEntireStroke = index <= m_sliderNumber/100 - 1;
//			System.out.println("::::::::::::::::::::::  " + ratio);
            for (int i = 0; i < m_pointList.size(); i++) {
				Point point = m_pointList.get(i);
				//if (totalTime == 0) {int k = 100/0;}
				float drawingRatio;
				if (totalTime == 0) {
					drawingRatio = 1;
				} else {
					drawingRatio = (point.getTimeStamp() - first.getTimeStamp())/totalTime;
				}
				if (!drawEntireStroke && isFinished() && drawingRatio >= sliderRatio) break;
				System.out.println("draw strok:  " + drawEntireStroke + "  isFinished::  " + isFinished() + "  drawing Ratio:   " +drawingRatio+ "   slider ratio:   " + sliderRatio);
				//System.out.println(":::::::::::::::::::::::::   " + drawingRatio);
            	g2.setStroke(new BasicStroke(m_width, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            	g2.setColor(m_color);
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
