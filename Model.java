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

	private Doodle doodle;

	public Doodle getDoodle() {
		return doodle;
	}

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

	public ArrayList<DoodleStroke> getStrokeList() {
		return m_strokeList;
	}
}
