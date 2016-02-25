import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class DrawingCanvas extends JPanel implements ViewInterface {

	private Model m_model;
	private boolean m_fullView;

   	public DrawingCanvas(Model model, boolean fullView) {
//		setMaximumSize(new Dimension(400, 400));
		setBorder(BorderFactory.createLineBorder(Color.black, 5));
		setBackground(Color.WHITE);
        m_model = model;
        addMouseListener(new MouseAdapter() {
           	@Override
           	public void mousePressed(MouseEvent e) {
				float x = (float)e.getX()*Model.CANVAS_WIDTH/getWidth();
				float y = (float)e.getY()*Model.CANVAS_HEIGHT/getHeight();
				Model.Point point =  m_model.new Point(x, y, System.currentTimeMillis());
               	m_model.newStroke(point);
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
				float x = (float)e.getX()*Model.CANVAS_WIDTH/getWidth();
                float y = (float)e.getY()*Model.CANVAS_HEIGHT/getHeight();
                Model.Point point =  m_model.new Point(x, y, System.currentTimeMillis());
               	m_model.extendStroke(point);
  //             	repaint();
           	}
       	});
    }

	@Override
	public void notifyView() {
		System.out.println("Repainting canvas aaaaaa");
		repaint();
		revalidate();
	}

	@Override
	public Dimension getPreferredSize() {
//		if (m_model.getFullSize()) {
			return new Dimension(m_model.CANVAS_WIDTH, m_model.CANVAS_HEIGHT);
	//	} else {
	//		return super.getPreferredSize();
	//	}
		//return new Dimension(10000, 10000);
	}

	@Override
	public Dimension getMaximumSize() {
	//	if (m_model.getFullSize()) {
			return new Dimension(m_model.CANVAS_WIDTH, m_model.CANVAS_HEIGHT);
	//	} else {
	//		return super.getMaximumSize();//new Dimension(getParent().getWidth(), getParent().getHeight());
	//	}
	}

	@Override    
   	public void paintComponent(Graphics g) {
		super.paintComponent(g);
       	Graphics2D g2 = (Graphics2D) g; // cast to get 2D drawing methods
       	g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  // antialiasing look nicer
           	    RenderingHints.VALUE_ANTIALIAS_ON);
       	//for (Model.Stroke stroke : m_model.getStrokeList()) {
		for (int i = 0; i < m_model.getStrokeList().size(); i++) {
			System.out.println("Drawing the stroke: " + i);
           	m_model.getStrokeList().get(i).draw(g2, i);
       	}
  	}
}
