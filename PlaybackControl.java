import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.io.*;
import javax.imageio.ImageIO;

public class PlaybackControl extends JPanel implements ViewInterface {
	private Model m_model;
	private Slider m_slider = new Slider();
	private JButton m_startButton;
	private JButton m_endButton;
	private JButton m_playButton;
	private JButton m_reverseButton;
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
					m_slider.setValue(m_slider.getValue() + 1);
					if (m_slider.getValue() != m_slider.getMaximum() && m_slider.getValue() % 100 == 0) {
						((Timer)evt.getSource()).stop();
						new SpecialTimer(m_index + 1).start();
					} else if (m_slider.getValue() == m_slider.getMaximum()) {
						((Timer)evt.getSource()).stop();
						m_slider.setEnabled(true);
						m_startButton.setEnabled(true);
						m_endButton.setEnabled(true);
						m_playButton.setEnabled(true);
						m_reverseButton.setEnabled(true);
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
					m_slider.setValue(m_slider.getValue() - 1);
					if (m_slider.getValue() != m_slider.getMinimum() && m_slider.getValue() % 100 == 0) {
						((Timer)evt.getSource()).stop();
						new SpecialReverseTimer(m_index - 1).start();
					} else if (m_slider.getValue() == m_slider.getMinimum()) {
						((Timer)evt.getSource()).stop();
						m_slider.setEnabled(true);
						m_startButton.setEnabled(true);
						m_endButton.setEnabled(true);
						m_playButton.setEnabled(true);
						m_reverseButton.setEnabled(true);
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
			m_labelTable.put(new Integer(0), new JLabel("0"));
			setLabelTable(m_labelTable);
			setMaximum(0);
			setMinimum(0);
			addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent e) {
					if (m_code) return;
					m_model.setSliderNumber(getValue());
				}
			});
		}

		public void update() {
			if (!m_inAnimation) {
				boolean val = m_model.getFinishedStrokes() != 0;
				m_playButton.setEnabled(val);
				m_reverseButton.setEnabled(val);
				m_startButton.setEnabled(val);
				m_endButton.setEnabled(val);
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
				m_code = true;
				setMaximum(size*TICKS);
				m_code = false;
				setValue(m_model.getSliderNumber());
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
	
		m_startButton = new JButton(new ImageIcon(newimg));
		m_startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				m_slider.setValue(m_slider.getMinimum());
			}
		});

		try {
			img = ImageIO.read(new File("fastforward.png"));
		} catch (IOException e) {}
		newimg = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);

		m_endButton = new JButton(new ImageIcon(newimg));
		m_endButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				m_slider.setValue(m_slider.getMaximum());
			}
		});

		try {
			img = ImageIO.read(new File("playbutton.png"));
		} catch (IOException e) {}
		newimg = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);

		m_playButton = new JButton(new ImageIcon(newimg));
		m_playButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				m_inAnimation = true;
				m_slider.setValue(m_slider.getMinimum());
				m_slider.setEnabled(false);
				m_startButton.setEnabled(false);
				m_endButton.setEnabled(false);
				m_playButton.setEnabled(false);
				m_reverseButton.setEnabled(false);
				SpecialTimer timer = new SpecialTimer(0);
				timer.start();
			}
		});

		try {
			img = ImageIO.read(new File("reverseplaybutton.png"));
		} catch (IOException e) {}
		newimg = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);

		m_reverseButton = new JButton(new ImageIcon(newimg));
		m_reverseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				m_inAnimation = true;
				m_slider.setValue(m_slider.getMaximum());
				m_slider.setEnabled(false);
				m_startButton.setEnabled(false);	
				m_endButton.setEnabled(false);
				m_playButton.setEnabled(false);
				m_reverseButton.setEnabled(false);
				SpecialReverseTimer timer = new SpecialReverseTimer(m_slider.getMaximum()/100 - 1);
				timer.start();
			}
		});


		JPanel panel1 = new JPanel();
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));
		panel1.add(m_startButton);
		panel1.add(m_endButton);
		
		JPanel panel2 = new JPanel();
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.X_AXIS));
		panel2.add(m_playButton);
		panel2.add(m_reverseButton);

		add(panel2, BorderLayout.WEST);
		add(panel1, BorderLayout.EAST);
		add(m_slider, BorderLayout.CENTER);
	}

	@Override
	public void notifyView() {
		m_slider.update();
	}
}
