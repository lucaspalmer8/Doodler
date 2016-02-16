import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class View {
	public static DrawingCanvas m_drawingCanvas;// = new DrawingCanvas();
	public static DrawingSelector m_drawingSelector;// = new DrawingSelector();
	public static JFrame m_frame = new JFrame("Doodle");
	public static JPanel m_panel1 = new JPanel();
	public static JPanel m_panel2 = new JPanel();
	private Model m_model;

	View(Model model) {
		m_model = model;
		m_drawingCanvas = new DrawingCanvas(m_model);
		m_drawingSelector = new DrawingSelector(m_model);

		m_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		m_frame.setResizable(true);
		m_frame.setSize(1000, 500);
		m_frame.setMinimumSize(new Dimension(200, 300));
		m_panel2.setLayout(new BoxLayout(m_panel2, BoxLayout.X_AXIS));
		m_panel1.setLayout(new BoxLayout(m_panel1, BoxLayout.Y_AXIS));
		m_panel1.add(m_panel2);
		m_panel1.add( new JButton( "Panel One" ));//, BorderLayout.NORTH );
		//m_panel1.add( new JButton( "One" ));//, BorderLayout.NORTH );
		m_panel2.add(m_drawingSelector);
//		m_panel2.add( new JColorChooser());//, BorderLayout.NORTH );

		m_drawingCanvas.setMinimumSize(new Dimension(400, 300));
		m_panel2.add(m_drawingCanvas);
		
		m_panel1.setBorder(BorderFactory.createLineBorder(Color.black));
		m_panel2.setBorder(BorderFactory.createLineBorder(Color.black));
		/*m_panel1.add(m_homeScreen);
		m_panel1.add( new JButton( "One" ), BorderLayout.NORTH );
		*///m_panel1.add( new JButton( "Two" ), BorderLayout.CENTER );
		m_frame.add(m_panel1);
		m_frame.setVisible(true);
	}

	public class DrawingCanvas extends JPanel {

    	public DrawingCanvas(Model model) {
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
    
    	public void paintComponent(Graphics g) {
        	Graphics2D g2 = (Graphics2D) g; // cast to get 2D drawing methods
        	g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  // antialiasing look nicer
            	    RenderingHints.VALUE_ANTIALIAS_ON);
        	for (Model.Stroke stroke : m_model.getStrokeList()) {
            	stroke.draw(g2);
        	}
    	}
	}

	public class DrawingSelector extends JPanel {
    	private class ColorItem extends JPanel {
        	private Color m_color;

        	ColorItem(Color color) {
            	m_color = color;
            	setOpaque(true);
            	setBackground(color);
            	addMouseListener(new MouseAdapter() {
                	@Override
                	public void mousePressed(MouseEvent e) {
                    	m_model.setColor(m_color);
                	}
            	});
        	}
    	}

    	private Model m_model;

   		public DrawingSelector(Model model) {
        	m_model = model;
        	setMaximumSize(new Dimension(100, 400));
        	setPreferredSize(new Dimension(100, 400));
        	setLayout(new GridLayout(6, 1));
        	add(new ColorItem(Color.BLACK));
        	add(new ColorItem(Color.RED));
        	add(new ColorItem(Color.BLUE));
        	add(new ColorItem(Color.MAGENTA));
        	add(new ColorItem(Color.GREEN));
        	add(new ColorItem(Color.ORANGE));
    	}
	}
}
