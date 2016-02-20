import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Hashtable;

public class PlaybackControl extends JPanel implements ViewInterface {
   	private Model m_model;
	private Slider m_slider = new Slider();

	private class Slider extends JSlider {
		private Hashtable<Integer, JLabel> m_labelTable = new Hashtable<Integer, JLabel>();
		private int TICKS = 100;
		private boolean m_code = false;

		public Slider() {
			setPaintTicks(true);
			setPaintLabels(true);
			setMajorTickSpacing(TICKS);
			setMinorTickSpacing(1);
			//setSnapToTicks(false);
			//if (getSnapToTicks()) System.out.println("AHHHHHHHHHHHHHHHHHHHH");
			//setValue(3.5);
			m_labelTable.put(new Integer(0), new JLabel("0"));
			setLabelTable(m_labelTable);
			setMaximum(0);
			setMinimum(0);
			addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent e) {
					System.out.println("HelllooooooooooooO");
					if (m_code) return;
					m_model.setSliderNumber(getValue());
				}
			});
		}

		public void update() {
			int size = m_model.getFinishedStrokes();
			if (size*TICKS != getMaximum()) {
				m_labelTable.put(new Integer(size*TICKS), new JLabel(Integer.toString(size)));
				setLabelTable(m_labelTable);
				System.out.println("Setting Max");
				m_code = true;
				setMaximum(size*TICKS);
				m_code = false;
				System.out.println("Setting val");
				setValue(m_model.getSliderNumber());
				System.out.println("Setting the value t0:::::  " + m_model.getSliderNumber());
				//System.out.println("In here! Settin max to:  " + size);
			} 
		}

	}

 	public PlaybackControl(Model model) {
       	m_model = model;
       	setMaximumSize(new Dimension(400, 100));
		setMinimumSize(new Dimension(400, 100));
       	setPreferredSize(new Dimension(400, 100));
       	setLayout(new BorderLayout());
		
		JPanel panel1 = new JPanel();
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));
		panel1.add(new JButton("Start"));
		panel1.add(new JButton("End"));
		
		JPanel panel2 = new JPanel();
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.X_AXIS));
		panel2.add(new JButton("Play"));

		add(panel2, BorderLayout.WEST);
		add(panel1, BorderLayout.EAST);
		add(m_slider, BorderLayout.CENTER);
   	}

	@Override
	public void notifyView() {
		m_slider.update();
//		System.out.println("Notifying view!");
	}
		
		/*@Override 
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			setBackground(Color.LIGHT_GRAY);
			setOpaque(true);
		}*/
}
