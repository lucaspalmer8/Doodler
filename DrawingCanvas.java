import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class DrawingCanvas extends JPanel implements ViewInterface {

	private Model m_model;
	private boolean m_fullView;

	public DrawingCanvas(Model model, boolean fullView) {
		setBorder(BorderFactory.createLineBorder(Color.black, 5));
		setBackground(Color.WHITE);
		m_model = model;
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				float x = (float)e.getX()*Model.CANVAS_WIDTH/getWidth();
				float y = (float)e.getY()*Model.CANVAS_HEIGHT/getHeight();
				m_model.newStroke(x, y, System.currentTimeMillis());
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				m_model.strokeFinished(System.currentTimeMillis());
			}
		});

		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				float x = (float)e.getX()*Model.CANVAS_WIDTH/getWidth();
				float y = (float)e.getY()*Model.CANVAS_HEIGHT/getHeight();
				m_model.extendStroke(x, y, System.currentTimeMillis());
			}
		});
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(Model.CANVAS_WIDTH, Model.CANVAS_HEIGHT);
	}

	@Override
	public Dimension getMaximumSize() {
		return new Dimension(Model.CANVAS_WIDTH, Model.CANVAS_HEIGHT);
	}


	@Override
	public void notifyView() {
		repaint();
		revalidate();
	}

	@Override	 
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g; // cast to get 2D drawing methods
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  // antialiasing look nicer
				RenderingHints.VALUE_ANTIALIAS_ON);
		for (int i = 0; i < m_model.getStrokeList().size(); i++) {
			m_model.getStrokeList().get(i).draw(g2, i);
		}
	}
}
