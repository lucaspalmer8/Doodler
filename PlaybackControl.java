import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.io.*;
import javax.imageio.ImageIO;
//import java.util.Timer;
//import java.util.TimerTask;

public class PlaybackControl extends JPanel implements ViewInterface {
   	private Model m_model;
	private Slider m_slider = new Slider();
	private JButton startButton;
	private JButton endButton;
	private JButton playButton;
	private JButton reverseButton;
	private boolean m_inAnimation = false;

	private class SpecialTimer extends Timer {
		private int m_index;

		public SpecialTimer(int index) {
			super(1, null);
			setInitialDelay(0);
			m_index = index;
		}

		@Override 
		public void start() {
			final Timer timer = new Timer((int)(m_model.getStrokeList().get(m_index).getElapsedTime()/100), new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					//System.out.println("Heyyy");
					m_slider.setValue(m_slider.getValue() + 1);
					if (m_slider.getValue() != m_slider.getMaximum() && m_slider.getValue() % 100 == 0) {
						((Timer)evt.getSource()).stop();
						new SpecialTimer(m_index + 1).start();
					} else if (m_slider.getValue() == m_slider.getMaximum()) {
						((Timer)evt.getSource()).stop();
						m_slider.setEnabled(true);
						startButton.setEnabled(true);
                		endButton.setEnabled(true);
                		playButton.setEnabled(true);
                		reverseButton.setEnabled(true);
						m_inAnimation = false;
					}
				}
			});
			timer.start();
		}
	}

	private class SpecialReverseTimer extends Timer {
        private int m_index;

        public SpecialReverseTimer(int index) {
            super(1, null);
            setInitialDelay(0);
            m_index = index;
        }

        @Override
        public void start() {
            final Timer timer = new Timer((int)(m_model.getStrokeList().get(m_index).getElapsedTime()/100), new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    //System.out.println("Heyyy");
                    m_slider.setValue(m_slider.getValue() - 1);
                    if (m_slider.getValue() != m_slider.getMinimum() && m_slider.getValue() % 100 == 0) {
                        ((Timer)evt.getSource()).stop();
                        new SpecialReverseTimer(m_index - 1).start();
                    } else if (m_slider.getValue() == m_slider.getMinimum()) {
                        ((Timer)evt.getSource()).stop();
						m_slider.setEnabled(true);
						startButton.setEnabled(true);
	                	endButton.setEnabled(true);
    	            	playButton.setEnabled(true);
        	        	reverseButton.setEnabled(true);
						m_inAnimation = false;
                    }
                }
            });
            timer.start();
        }
    }

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
					//System.out.println("HelllooooooooooooO");
					//System.out.println("The value is::::::: " + getValue());
					if (m_code) return;
					m_model.setSliderNumber(getValue());
				}
			});
		}

		public void update() {
			if (!m_inAnimation) {
				boolean val = m_model.getFinishedStrokes() != 0;
				playButton.setEnabled(val);
				reverseButton.setEnabled(val);
				startButton.setEnabled(val);
				endButton.setEnabled(val);
				setEnabled(val);
			}
			int size = m_model.getFinishedStrokes();
			if (size*TICKS != getMaximum()) {
				//When a new Doodle file is opened, so labels might not be in the hashtable
				//So add them all up to the current number of strokes
				for (int i = 1; i <= size; i++) {
					m_labelTable.put(new Integer(i*TICKS), new JLabel(Integer.toString(i)));
				}
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
	
		BufferedImage img = null;
		Image newimg = null;
		try {
    		img = ImageIO.read(new File("rewind.png"));
		} catch (IOException e) {}
		newimg = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH); 
	
		startButton = new JButton(new ImageIcon(newimg));
		startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                m_slider.setValue(m_slider.getMinimum());//m_slider.getMinimum());
            }
        });

		try {
            img = ImageIO.read(new File("fastforward.png"));
        } catch (IOException e) {}
        newimg = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);

		endButton = new JButton(new ImageIcon(newimg));
        endButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                m_slider.setValue(m_slider.getMaximum());
            }
        });

		try {
            img = ImageIO.read(new File("playbutton.png"));
        } catch (IOException e) {}
        newimg = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);

		playButton = new JButton(new ImageIcon(newimg));
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
				m_inAnimation = true;
                m_slider.setValue(m_slider.getMinimum());
				/*final Timer timer = new Timer(10, new ActionListener() {
    				public void actionPerformed(ActionEvent evt) {
						System.out.println("In timeer");
						m_slider.setValue(m_slider.getValue() + 1);
						if (m_slider.getValue() == m_slider.getMaximum()) {
        					((Timer)evt.getSource()).stop();
						}
    				}
				});*/
				m_slider.setEnabled(false);
				startButton.setEnabled(false);
                endButton.setEnabled(false);
                playButton.setEnabled(false);
                reverseButton.setEnabled(false);
				SpecialTimer timer = new SpecialTimer(0);
				timer.start();
            }
        });

		try {
            img = ImageIO.read(new File("reverseplaybutton.png"));
        } catch (IOException e) {}
        newimg = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);

		//ImageIcon reversePlayIcon = new ImageIcon("reverseplaybutton.png");
        //img = reversePlayIcon.getImage();
        //newimg = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        reverseButton = new JButton(new ImageIcon(newimg));
        reverseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
				m_inAnimation = true;
                m_slider.setValue(m_slider.getMaximum());
                /*final Timer timer = new Timer(10, new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        System.out.println("In timeer");
                        m_slider.setValue(m_slider.getValue() - 1);
                        if (m_slider.getValue() == m_slider.getMinimum()) {
                            ((Timer)evt.getSource()).stop();
                        }
                    }
                });*/
				m_slider.setEnabled(false);
				startButton.setEnabled(false);	
				endButton.setEnabled(false);
				playButton.setEnabled(false);
				reverseButton.setEnabled(false);
				SpecialReverseTimer timer = new SpecialReverseTimer(m_slider.getMaximum()/100 - 1);
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
