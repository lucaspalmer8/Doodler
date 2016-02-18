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
	}

	private class WidthItem extends JPanel {
		private int m_width;

		WidthItem(int width) {
			setBackground(Color.WHITE);
			setOpaque(true);
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
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g; // cast to get 2D drawing methods
	        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  // antialiasing look nicer
    	            RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setStroke(new BasicStroke(m_width));
            g2.setColor(Color.BLACK);
			g2.drawLine(0, getHeight()/2, getWidth(), getHeight()/2);	
		
		}
	}

   	private Model m_model;
	private JColorChooser m_chooser = new JColorChooser();

	private class TwoColors extends JPanel {
		public TwoColors(Color color1, Color color2) {
			setLayout(new GridLayout(1, 2));
			add(new ColorItem(color1));
			add(new ColorItem(color2));
		}
	}

	public DrawingSelector(Model model) {
		m_model = model;
		setBackground(Color.BLACK);
		setOpaque(true);
        setMaximumSize(new Dimension(100, 400));
        setPreferredSize(new Dimension(100, 400));
		setLayout(new GridLayout(13,1));
        add(new TwoColors(Color.BLACK, Color.RED));
		add(new TwoColors(Color.BLUE, Color.MAGENTA));
		add(new TwoColors(Color.GREEN, Color.ORANGE));
		add(new TwoColors(Color.WHITE, Color.CYAN));
		add(new TwoColors(Color.DARK_GRAY, Color.ORANGE));
		add(new TwoColors(Color.PINK, Color.YELLOW));

		JButton custom = new JButton("Custom"); 
		custom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//System.out.println("EhhhhhhhhhhhH");
				//JColorChooser chooser = new JColorChooser();
				//Color newColor = JColorChooser.showDialog(DrawingSelector.this, "Custom Color Selector", m_model.getColor());
				//if (newColor != null) {
				//	m_model.setColor(newColor);
				//}
				ActionListener actionListener = new ActionListener() {
					@Override
      				public void actionPerformed(ActionEvent actionEvent) {
						m_model.setColor(m_chooser.getColor());
					}
				};
				m_chooser.setColor(m_model.getColor());
				JDialog dialog = JColorChooser.createDialog(DrawingSelector.this, "Custom Color Chooser", false, m_chooser, actionListener, null);
				dialog.setVisible(true);
			}
		});
		add(custom);			

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
