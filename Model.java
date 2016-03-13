import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.io.*;

public class Model {

	private Color m_drawingColor;
	private int m_drawingWidth;
	private ArrayList<ViewInterface> m_views = new ArrayList<ViewInterface>();
	private ArrayList<DoodleStroke> m_strokeList = new ArrayList<DoodleStroke>();
	private int m_sliderNumber;
	private boolean m_fullSize;

	public static int CANVAS_WIDTH = 700;
	public static int CANVAS_HEIGHT = 400;

	private Doodle m_doodle;

	public Doodle getDoodle() {
		return m_doodle;
	}

	public Model(Doodle doodler) {
		m_drawingColor = Color.BLACK;
		m_drawingWidth = 10;
		m_sliderNumber = 0;
		m_fullSize = true;
		m_doodle = doodler;
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
		m_strokeList = new ArrayList<DoodleStroke>();
		notifyViews();
	}

	//Load a new doodle
	public void resetDoodle(ArrayList<DoodleStroke> strokes) {
		m_strokeList = strokes;
		m_sliderNumber = strokes.size()*100;
		notifyViews();
	}
	
	public void setFullSize(boolean fullSize) {
		if (m_fullSize == fullSize) return;
		m_fullSize = fullSize;
		if (m_fullSize) {
			m_doodle.getPanel().remove(m_doodle.getFitView());
			m_doodle.getPanel().add(m_doodle.getFullView(), BorderLayout.CENTER);
			m_doodle.getPanel().validate();
			m_doodle.getPanel().repaint();
		} else {
			m_doodle.getPanel().remove(m_doodle.getFullView());
			m_doodle.getPanel().add(m_doodle.getFitView(), BorderLayout.CENTER);
			m_doodle.getPanel().validate();
			m_doodle.getPanel().repaint();
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
		return m_doodle.getFrame();
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

	public void newStroke(float x, float y, long time) {
		//right here, we must clean up the strokeList based on sliderNumber before adding things to it!!!!!!!!!
		while (m_strokeList.size() > 0 && m_strokeList.size() - 1 >= m_sliderNumber/100) {
			m_strokeList.remove(m_strokeList.size() - 1);
		}

		m_strokeList.add(new DoodleStroke(x, y, time, this));
		notifyViews();
	}

	public void extendStroke(float x, float y, long time) {
		DoodleStroke current = m_strokeList.get(m_strokeList.size() - 1);
		current.addPoint(x, y, time);
		notifyViews();
	}

	public void strokeFinished(long time) {
		m_strokeList.get(m_strokeList.size() - 1).finished();
		m_strokeList.get(m_strokeList.size() - 1)
				.setElapsedTime(time - m_strokeList.get(m_strokeList.size() - 1).getPointList().get(0).getTimeStamp());
		m_sliderNumber = m_strokeList.size()*100;
		notifyViews();
	}
	
	public int getFinishedStrokes() {
		for (int i = 0; i < m_strokeList.size(); i++) {
			if (!m_strokeList.get(i).isFinished()) {
				return i;
			}
		}
		return m_strokeList.size();
	}

	public ArrayList<DoodleStroke> getStrokeList() {
		return m_strokeList;
	}
}
