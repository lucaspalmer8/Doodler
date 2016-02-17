import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class PlaybackControl extends JPanel implements ViewInterface {
   	private Model m_model;

 	public PlaybackControl(Model model) {
       	m_model = model;
       	setMaximumSize(new Dimension(400, 100));
		setMinimumSize(new Dimension(400, 100));
       	setPreferredSize(new Dimension(400, 100));
       	setLayout(new GridLayout(9, 2));
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
