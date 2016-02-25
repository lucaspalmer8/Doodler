import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Model {

	private Color m_drawingColor;
	private int m_drawingWidth;
	private ArrayList<ViewInterface> m_views = new ArrayList<ViewInterface>();
	private ArrayList<Stroke> m_strokeList = new ArrayList<Stroke>();
	private int m_sliderNumber;
	private boolean m_fullSize;

	public static int CANVAS_WIDTH = 700;
	public static int CANVAS_HEIGHT = 400;

	private Doodle doodle;

	public Model(Doodle doodler) {
		m_drawingColor = Color.BLACK;
		m_drawingWidth = 10;
		m_sliderNumber = 0;
		m_fullSize = true;
		doodle = doodler;
	}

	public void addObserver(ViewInterface view) {
		m_views.add(view);
	}

	public void notifyViews() {
		for (ViewInterface view : m_views) {
			view.notifyView();
		}
	}

	//Reset everything to the default
	public void resetDefault() {
		m_drawingColor = Color.BLACK;
        m_drawingWidth = 10;
        setFullSize(true);
		m_strokeList = new ArrayList<Stroke>();
		notifyViews();
	}

	//Load a new doodle
	public void resetDoodle(ArrayList<Stroke> strokes) {
		m_strokeList = strokes;
		notifyViews();
	}
	
	public void setFullSize(boolean fullSize) {
		if (m_fullSize == fullSize) return;
		m_fullSize = fullSize;
		if (m_fullSize) {
			doodle.panel.remove(doodle.fitView);
			doodle.panel.add(doodle.fullView, BorderLayout.CENTER);
			doodle.panel.validate();
			doodle.panel.repaint();
		} else {
			doodle.panel.remove(doodle.fullView);
			doodle.panel.add(doodle.fitView, BorderLayout.CENTER);
			doodle.panel.validate();
			doodle.panel.repaint();
		}
	}
	
	public boolean getFullSize() {
		return m_fullSize;
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
		return doodle.frame;
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

	public void newStroke(Point point) {
		//right here, we must clean up the strokeList based on sliderNumber before adding things to it!!!!!!!!!
		while (m_strokeList.size() > 0 && m_strokeList.size() - 1 >= m_sliderNumber/100) {
			m_strokeList.remove(m_strokeList.size() - 1);
		}

		m_strokeList.add(new Stroke(point));
		notifyViews();
	}

	public void extendStroke(Point point) {
		Stroke current = m_strokeList.get(m_strokeList.size() - 1);
        current.addPoint(point);
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

	public class Point {
        private float m_x;
        private float m_y;
        private long m_timeStamp;

        public Point(float x, float y, long time) {
            m_x = x;
            m_y = y;
            m_timeStamp = time;
        }

        int getX() {
            if (m_fullSize) {
                return (int)m_x;
            } else {
                return (int)(m_x*doodle.fitView.getWidth()/Model.CANVAS_WIDTH);
            }
        }
        int getY() {
            if (m_fullSize) {
                return (int)m_y;
            } else {
                return (int)(m_y*doodle.fitView.getHeight()/Model.CANVAS_HEIGHT);
            }
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

		//For loading old Doodle
		public Stroke(int width, Color color) {
			m_color = color;
			m_width = width;
			m_finished = true;
		}

        public Stroke(Point point) {
            m_pointList.add(point);
            m_color = m_drawingColor;
			m_width = m_drawingWidth;
			m_finished = false;
        }

		public ArrayList<Point> getPointList() {
			return m_pointList;
		}

		public int getWidth() {
			return m_width;
		}

		public Color getColor() {
			return m_color;
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

		//public boolean isPointValid(int index) {
		//}

		public void draw(Graphics2D g2, int index) {
			if (isFinished() && index >= m_sliderNumber/100 + 1) {
				return;
			}

			Point last = m_pointList.get(m_pointList.size() - 1);
			Point first = m_pointList.get(0);
			float totalTime = last.getTimeStamp() - first.getTimeStamp();
//			System.out.println(":::::::total time::::::::::  " + totalTime);
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
				//System.out.println("draw strok:  " + drawEntireStroke + "  isFinished::  " + isFinished() + "  drawing Ratio:   " +drawingRatio+ "   slider ratio:   " + sliderRatio);
				//System.out.println(":::::::::::::::::::::::::   " + drawingRatio);
            	g2.setStroke(new BasicStroke(m_width, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            	g2.setColor(m_color);
            	if (i == 0) {//m_pointList.size() - 1) {
                	g2.drawLine(m_pointList.get(i).getX(), m_pointList.get(i).getY(),
                       	 	m_pointList.get(i).getX(), m_pointList.get(i).getY());
                	continue;
            	}
            	g2.drawLine(m_pointList.get(i - 1).getX(), m_pointList.get(i - 1).getY(),
                	        m_pointList.get(i).getX(), m_pointList.get(i).getY());
            }
        }
    }
}
