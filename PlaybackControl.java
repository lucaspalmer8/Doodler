import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class PlaybackControl extends JPanel implements ViewInterface {
   	private Model m_model;

	/*private class ColorsItem extends JPanel {
		private Color m_color;
	ColorsItem(Color color) {
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
	}*/
	
	private class Slider extends JSlider {

		public Slider() {
			setPaintTicks(true);
			setPaintLabels(true);
			setMajorTickSpacing(1);
			//setMinorTickSpacing(1);
			//setSnapToTicks(false);
			//if (getSnapToTicks()) System.out.println("AHHHHHHHHHHHHHHHHHHHH");
			//setValue(3.5);
			setMaximum(0);
			setMinimum(0);
		}

	}

 	public PlaybackControl(Model model) {
       	m_model = model;
       	setMaximumSize(new Dimension(400, 100));
		setMinimumSize(new Dimension(400, 100));
       	setPreferredSize(new Dimension(400, 100));
       	setLayout(new BorderLayout());
		add(new JButton("Back"), BorderLayout.WEST);
		add(new JButton("Forward"), BorderLayout.EAST);
		add(new Slider(), BorderLayout.CENTER);
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
