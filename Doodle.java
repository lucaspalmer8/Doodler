import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.RenderingHints;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.lang.Thread;
import java.lang.Math;

public class Doodle {
//	public static boolean m_gameStarted = false;
//	public static Breakout m_breakout = new Breakout();
	public static DrawingCanvas m_drawingCanvas = new DrawingCanvas();
	public static JFrame m_frame = new JFrame("Doodle");
	public static JPanel m_panel1 = new JPanel();
	public static JPanel m_panel2 = new JPanel();
//	public static JPanel m_panel3 = new JPanel();

	public static void main(String[] args) {
//		if (args.length == 2) {
//			m_breakout.setRates(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
//		}
//		m_frame.addKeyListener(new GameKeyListener());
		m_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		m_frame.setResizable(true);
		m_frame.setSize(1000, 500);
		m_frame.setMinimumSize(new Dimension(200, 300));
		//m_frame.setContentPane(m_homeScreen);
		//m_panel.setLayout(new GridLayout(3, 1));
		m_panel2.setLayout(new BoxLayout(m_panel2, BoxLayout.X_AXIS));
		m_panel1.setLayout(new BoxLayout(m_panel1, BoxLayout.Y_AXIS));
		m_panel1.add(m_panel2);
		m_panel1.add( new JButton( "One" ));//, BorderLayout.NORTH );
		m_panel1.add( new JButton( "One" ));//, BorderLayout.NORTH );
		m_panel2.add( new JButton( "One" ));
//		m_panel2.add( new JColorChooser());//, BorderLayout.NORTH );
		m_panel2.add(m_drawingCanvas);
		
		m_panel1.setBorder(BorderFactory.createLineBorder(Color.black));
		m_panel2.setBorder(BorderFactory.createLineBorder(Color.black));
		/*m_panel1.add(m_homeScreen);
		m_panel1.add( new JButton( "One" ), BorderLayout.NORTH );
		*///m_panel1.add( new JButton( "Two" ), BorderLayout.CENTER );
		m_frame.add(m_panel1);
		m_frame.setVisible(true);
	}

	/*public static class GameKeyListener extends KeyAdapter {		
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				if (!m_gameStarted) {
					return;
				}
//				System.out.println("Setting the direction to 1");
				m_breakout.getPaddle().setDirection(1);
			}
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				if (!m_gameStarted) {
					return;
				}
//				System.out.println("setting the direction to -1");
				m_breakout.getPaddle().setDirection(-1);
			}
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				if (!m_gameStarted) {
					m_gameStarted = true;
					m_frame.setContentPane(m_breakout);
					m_frame.getContentPane().revalidate();
//					m_breakout.startThread();
					return;
				}
				if (m_breakout.getGameState() == Breakout.GameState.GAME_OVER) {
					m_breakout.startNewGame();
				} else if (m_breakout.getBallList().size() == 1) {
					if (m_breakout.getBallList().getBall().getSpeed() == 0) {
						m_breakout.getBallList().getBall().setDirection(-1, 1);
						m_breakout.getBallList().getBall().setSpeed(10);
						m_breakout.setGameState(Breakout.GameState.GAME_STARTED);
					}
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				if (m_gameStarted) {
					m_breakout.pauseResume();
				}
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				if (m_breakout.getPaddle().getDirection() > 0) {
					m_breakout.getPaddle().setDirection(0);
//					System.out.println("Setting the direction to 0");
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				if (m_breakout.getPaddle().getDirection() < 0) {
//					System.out.println("Setting the direction to 0");
					m_breakout.getPaddle().setDirection(0);
				}
			}
		}

		@Override
		public void keyTyped(KeyEvent e) {
//			System.out.println("Key typed");
		}
	}*/
}
