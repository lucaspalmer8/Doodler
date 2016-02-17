import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class DrawingCanvas extends JPanel implements ViewInterface {

	private Model m_model;

   	public DrawingCanvas(Model model) {
//		setMaximumSize(new Dimension(400, 400));
		//setBackground(Color.RED);
        m_model = model;
        addMouseListener(new MouseAdapter() {
           	@Override
           	public void mousePressed(MouseEvent e) {
               	m_model.newStroke(e);
               	repaint();
           	}
        });

       	addMouseMotionListener(new MouseMotionAdapter() {
           	@Override
           	public void mouseDragged(MouseEvent e) {
               	m_model.extendStroke(e);
               	repaint();
           	}
       	});
    }

	@Override
	public void notifyView() {}
    
   	public void paintComponent(Graphics g) {
		super.paintComponent(g);
       	Graphics2D g2 = (Graphics2D) g; // cast to get 2D drawing methods
       	g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  // antialiasing look nicer
           	    RenderingHints.VALUE_ANTIALIAS_ON);
       	for (Model.Stroke stroke : m_model.getStrokeList()) {
           	stroke.draw(g2);
       	}
   	}
}
