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
                m_slider.setValue(m_slider.getValue() - 1);//m_slider.getMinimum());
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
			public void sleep() {
				try {
                        Thread.sleep(1000);                 //1000 milliseconds is one second.
                    } catch(InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
			}

            @Override
            public void actionPerformed(ActionEvent e) {
                m_slider.setValue(m_slider.getMinimum());
				//sleep();
				//m_slider.setValue(5);
				//sleep();
				//m_slider.setValue(10);
				//m_slider.setValue(m_slider.getMaximum()/2);
				/*Timer t = new Timer();
				t.schedule(new TimerTask() {
    				@Override
    				public void run() {
       					System.out.println("Hello World");
						m_slider.setValue(m_slider.getValue() + 1);
    				}
				}, 0, 10);*/
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


				/*for(int i = 1; i <= m_slider.getMaximum(); i++) {
					System.out.println("Setting the value to:::  " + i);
					//m_slider.m_code = true;
					m_slider.setValue(i);
					//Thread.wait(100);
					//m_slider.m_code = false;
				}*/
            }
        });

		JPanel panel1 = new JPanel();
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));
		panel1.add(startButton);
		panel1.add(endButton);
		
		JPanel panel2 = new JPanel();
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.X_AXIS));
		panel2.add(playButton);

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
