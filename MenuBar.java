import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;
import java.awt.event.*;

class MenuBar extends JMenuBar {
	private Model m_model;

	public MenuBar(Model model) {
		m_model = model;
		//JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu viewMenu = new JMenu("View");
        add(fileMenu);
        add(viewMenu);

		ActionListener fullView = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//System.out.println("Hello1");
				m_model.setFullSize(true);
			}
		};

		ActionListener fitView = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println("Hello2");
				m_model.setFullSize(false);
            }
        };


		ButtonGroup group = new ButtonGroup();
		JRadioButtonMenuItem fullSize = new JRadioButtonMenuItem("Full-size");
		JRadioButtonMenuItem fitSize = new JRadioButtonMenuItem("Fit");
		group.add(fullSize);
		group.add(fitSize);
		fullSize.setSelected(true);
		fullSize.addActionListener(fullView);
		fitSize.addActionListener(fitView);
		//rbMenuItem.setMnemonic(KeyEvent.VK_O);
		//group.add(rbMenuItem);
		viewMenu.add(fullSize);
		viewMenu.add(fitSize);
		
	}


}
