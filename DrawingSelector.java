import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class DrawingSelector extends JPanel implements ViewInterface {
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

		/*@Override
        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g; // cast to get 2D drawing methods
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  // antialiasing look nicer
                    RenderingHints.VALUE_ANTIALIAS_ON);
			Color background = Color.WHITE;
			if (m_color = m_model.getColor()) {
				background = Color.LIGHT_GRAY;
			}
            g2.setStroke(new BasicStroke(getHeight()));
            g2.setColor(backGround);
            g2.drawLine(0, getHeight()/2, getWidth(), getHeight()/2);
			
			g2.setStroke(new BasicStroke

        }*/
   	}

	private class WidthItem extends JPanel {
		private int m_width;

		WidthItem(int width) {
			m_width = width;
			addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					m_model.setWidth(m_width);
				}
			});
		}
		
		@Override
		public void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g; // cast to get 2D drawing methods
	        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  // antialiasing look nicer
    	            RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setStroke(new BasicStroke(m_width));
            g2.setColor(Color.BLACK);
			g2.drawLine(0, getHeight()/2, getWidth(), getHeight()/2);	
		
		}
	}

   	private Model m_model;

 	public DrawingSelector(Model model) {
       	m_model = model;
       	setMaximumSize(new Dimension(100, 400));
       	setPreferredSize(new Dimension(100, 400));
       	setLayout(new GridLayout(9, 2));
       	add(new ColorItem(Color.BLACK));
       	add(new ColorItem(Color.RED));
       	add(new ColorItem(Color.BLUE));
       	add(new ColorItem(Color.MAGENTA));
       	add(new ColorItem(Color.GREEN));
       	add(new ColorItem(Color.ORANGE));
		add(new ColorItem(Color.WHITE));
		add(new ColorItem(Color.CYAN));
		add(new ColorItem(Color.DARK_GRAY));
		add(new ColorItem(Color.ORANGE));
		add(new ColorItem(Color.PINK));
		add(new ColorItem(Color.YELLOW));

		add(new WidthItem(2));
		add(new WidthItem(4));
		add(new WidthItem(6));
		add(new WidthItem(8));
		add(new WidthItem(10));
		add(new WidthItem(12));
   	}

	@Override
	public void notifyView() {}
		
		/*@Override 
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			setBackground(Color.LIGHT_GRAY);
			setOpaque(true);
		}*/
}
