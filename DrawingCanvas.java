import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class DrawingCanvas extends JPanel implements ViewInterface {

	private Model m_model;

   	public DrawingCanvas(Model model) {
//		setMaximumSize(new Dimension(400, 400));
		setBackground(Color.WHITE);
        m_model = model;
        addMouseListener(new MouseAdapter() {
           	@Override
           	public void mousePressed(MouseEvent e) {
               	m_model.newStroke(e);
//               	repaint();
//				System.out.println(getWidth() + "   " + getHeight());
           	}
			@Override
			public void mouseReleased(MouseEvent e) {
				m_model.strokeFinished();
			}
        });

       	addMouseMotionListener(new MouseMotionAdapter() {
           	@Override
           	public void mouseDragged(MouseEvent e) {
               	m_model.extendStroke(e);
  //             	repaint();
           	}
       	});
    }

	@Override
	public void notifyView() {
		System.out.println("Repainting canvas");
		repaint();
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(700, 400);
	}

	@Override
	public Dimension getMaximumSize() {
		return new Dimension(700, 400);
	}

	@Override    
   	public void paintComponent(Graphics g) {
		super.paintComponent(g);
       	Graphics2D g2 = (Graphics2D) g; // cast to get 2D drawing methods
       	g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  // antialiasing look nicer
           	    RenderingHints.VALUE_ANTIALIAS_ON);
       	//for (Model.Stroke stroke : m_model.getStrokeList()) {
		for (int i = 0; i < m_model.getStrokeList().size(); i++) {
           	m_model.getStrokeList().get(i).draw(g2, i);
       	}
   	}
}
