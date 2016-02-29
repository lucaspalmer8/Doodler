import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.io.*;

public class DoodleStroke implements Serializable {
	private ArrayList<DoodlePoint> m_pointList = new ArrayList<DoodlePoint>();
	private Color m_color;
	private int m_width;
	private boolean m_finished;
	transient private Model m_model = null;
	private long m_elapsedTime;

	//For loading old Doodle
	public DoodleStroke(int width, Color color) {
		m_color = color;
		m_width = width;
		m_finished = true;
	}

	public DoodleStroke(float x, float y, long time, Model model) {
		m_model = model;
		m_pointList.add(new DoodlePoint(x, y, time));
		m_color = m_model.getColor();
		m_width = m_model.getWidth();
		m_finished = false;
	}

	public void setElapsedTime(long time) {
		m_elapsedTime = time;
	}

	public long getElapsedTime() {
		return m_elapsedTime;
	}

	 public class DoodlePoint implements Serializable {
        private float m_x;
        private float m_y;
        private long m_timeStamp;

        public DoodlePoint(float x, float y, long time) {
            m_x = x;
            m_y = y;
            m_timeStamp = time;
        }

		float getFloatX() {
			return m_x;
		}

		float getFloatY() {
			return m_y;
		}
		
		//Two getters for drawing
        int getX() {
            if (m_model.getFullSize()) {
                return (int)m_x;
            } else {
                return (int)(m_x*m_model.getDoodle().fitView.getWidth()/Model.CANVAS_WIDTH);
            }
        }
        int getY() {
            if (m_model.getFullSize()) {
                return (int)m_y;
            } else {
                return (int)(m_y*m_model.getDoodle().fitView.getHeight()/Model.CANVAS_HEIGHT);
            }
        }
        long getTimeStamp() {
            return m_timeStamp;
        }
    }


	public void setModel(Model model) {
		m_model = model;
	}

	public ArrayList<DoodlePoint> getPointList() {
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

	public void addPoint(float x, float y, long time) {
		m_pointList.add(new DoodlePoint(x, y, time));
	}

	public void draw(Graphics2D g2, int index) {
		if (isFinished() && index >= m_model.getSliderNumber()/100 + 1) {
			return;
		}

		DoodlePoint last = m_pointList.get(m_pointList.size() - 1);
		DoodlePoint first = m_pointList.get(0);
		float totalTime = last.getTimeStamp() - first.getTimeStamp();
//			System.out.println(":::::::total time::::::::::  " + totalTime);
		float sliderRatio = (m_model.getSliderNumber() % 100)/100f;
		boolean drawEntireStroke = index <= m_model.getSliderNumber()/100 - 1;
//			System.out.println("::::::::::::::::::::::  " + ratio);
		for (int i = 0; i < m_pointList.size(); i++) {
			DoodlePoint point = m_pointList.get(i);
			//if (totalTime == 0) {int k = 100/0;}
			float drawingRatio;
			if (totalTime == 0) {
				drawingRatio = 0;
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

