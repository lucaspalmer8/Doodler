import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Hashtable;
//import java.util.Timer;
//import java.util.TimerTask;

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
					System.out.println("The value is::::::: " + getValue());
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
//				System.out.println("Setting value to ::::  " + m_model.getSliderNumber());
//				setValue(m_model.getSliderNumber());
//				System.out.println("The new value is:::::: " + getValue());
//				System.out.println("Settingmax");
				
				m_code = true;
				setMaximum(size*TICKS);
				m_code = false;
//				System.out.println("Setting val");
				setValue(m_model.getSliderNumber());
//				System.out.println("Setting the value t0:::::  " + m_model.getSliderNumber());
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
		
		JButton startButton = new JButton("Start");
		startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                m_slider.setValue(m_slider.getMinimum());//m_slider.getMinimum());
            }
        });

		JButton endButton = new JButton("End");
        endButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                m_slider.setValue(m_slider.getMaximum());
            }
        });

		JButton playButton = new JButton("Play");
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                m_slider.setValue(m_slider.getMinimum());
				final Timer timer = new Timer(10, new ActionListener() {
    				public void actionPerformed(ActionEvent evt) {
						System.out.println("In timeer");
						m_slider.setValue(m_slider.getValue() + 1);
						if (m_slider.getValue() == m_slider.getMaximum()) {
        					((Timer)evt.getSource()).stop();
						}
    				}
				});
				timer.start();
            }
        });

		JButton reverseButton = new JButton("Reverse");
        reverseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                m_slider.setValue(m_slider.getMaximum());
                final Timer timer = new Timer(10, new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        System.out.println("In timeer");
                        m_slider.setValue(m_slider.getValue() - 1);
                        if (m_slider.getValue() == m_slider.getMinimum()) {
                            ((Timer)evt.getSource()).stop();
                        }
                    }
                });
                timer.start();
            }
        });


		JPanel panel1 = new JPanel();
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));
		panel1.add(startButton);
		panel1.add(endButton);
		
		JPanel panel2 = new JPanel();
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.X_AXIS));
		panel2.add(playButton);
		panel2.add(reverseButton);

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
