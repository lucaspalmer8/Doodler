import javax.swing.*;
import java.awt.*;
import java.io.*;

public class Doodle {

	private JFrame m_frame;
	private JPanel m_panel;
	private JScrollPane m_fullView;
	private JPanel m_fitView;

	public static void main(String[] args) {
		Doodle doodle = new Doodle();
	}

	public Doodle() {
		m_frame = new JFrame("Doodle");
		m_panel = new JPanel();
		m_panel.setLayout(new BorderLayout());

		JPanel panel1 = new JPanel();
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));
		JPanel panel2 = new JPanel();
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));

		m_fullView = new JScrollPane(panel1);
		m_fitView = new JPanel(new GridLayout(1,1));

		//Create and initialize model.
		Model model = new Model(this);

		//Create view/controller and tell it about model.
		MenuBar menuBar = new MenuBar(model);
		model.addObserver(menuBar);

		//Create view/controller and tell it about model.
		DrawingCanvas drawingCanvas = new DrawingCanvas(model, true);
		model.addObserver(drawingCanvas);

		//Create view/controller and tell it about model.
		DrawingCanvas drawingCanvas2 = new DrawingCanvas(model, false);
		model.addObserver(drawingCanvas2);

		//Create view/controller and tell it about model.
		DrawingSelector drawingSelector = new DrawingSelector(model);
		model.addObserver(drawingSelector);

		//Create view/controller and tell it about model.
		PlaybackControl playbackControl = new PlaybackControl(model);
		model.addObserver(playbackControl);

		//Notify the views that they are connected to the model.
		model.notifyViews();

		m_fitView.add(drawingCanvas2);

		panel2.add(new JPanel());
		panel2.add(drawingCanvas);
		panel2.add(new JPanel());
		panel1.add(new JPanel());
		panel1.add(panel2);
		panel1.add(new JPanel());

		m_panel.add(menuBar, BorderLayout.NORTH);
		m_panel.add(drawingSelector, BorderLayout.WEST);
		m_panel.add(m_fullView, BorderLayout.CENTER);
		m_panel.add(playbackControl, BorderLayout.SOUTH);


		m_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		m_frame.setResizable(true);
		m_frame.setSize(900, 600);
		m_frame.setMinimumSize(new Dimension(300, 330));
		m_frame.add(m_panel);
		m_frame.setVisible(true);
	}

	public JFrame getFrame() {
		return m_frame;
	}

	public JPanel getPanel() {
		return m_panel;
	}

	public JScrollPane getFullView() {
		return m_fullView;
	}

	public JPanel getFitView() {
		return m_fitView;
	}
}
