import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.io.*;

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

		ActionListener newDoodleListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("new");
                //m_model.setFullSize(false);
            }
        };

		ActionListener saveDoodleListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println("save");
				final JFileChooser chooser = new JFileChooser();
				int returnVal = chooser.showSaveDialog(m_model.getFrame());
				String path = chooser.getSelectedFile().getAbsolutePath();
				String fileName = chooser.getSelectedFile().getName();

        		try {
            		FileWriter fileWriter = new FileWriter(path);
					BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            		// Note that write() does not automatically
            		// append a newline character.
            		bufferedWriter.write("Hello there,");
            		bufferedWriter.write(" here is some text.");
            		bufferedWriter.newLine();
            		bufferedWriter.write("We are writing");
            		bufferedWriter.write(" the text to the file.");

            		bufferedWriter.close();
        
				} catch(IOException ex) {
            		System.out.println("Error writing to file '" + fileName + "'");
        		}	
            }
        };

		ActionListener loadDoodleListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
				final JFileChooser chooser = new JFileChooser();
                int returnVal = chooser.showOpenDialog(m_model.getFrame());
                String path = chooser.getSelectedFile().getAbsolutePath();
                String fileName = chooser.getSelectedFile().getName();

				String line = null;
               	try {
            		FileReader fileReader = new FileReader(path);
		            BufferedReader bufferedReader = new BufferedReader(fileReader);

            		while((line = bufferedReader.readLine()) != null) {
                		System.out.println(line);
            		}   
            
					bufferedReader.close();         
        		} catch(FileNotFoundException ex) {
            		System.out.println("Unable to open file '" + fileName + "'");                
        		} catch(IOException ex) {
            		System.out.println("Error reading file '" + fileName + "'");                  
        		}
            }
        };

		ActionListener exitDoodleListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("exit");
                //m_model.setFullSize(false);
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
		viewMenu.add(fullSize);
		viewMenu.add(fitSize);
		
		JMenuItem newDoodle = new JMenuItem("New Doodle");
		newDoodle.addActionListener(newDoodleListener);
		fileMenu.add(newDoodle);

		JMenuItem saveDoodle = new JMenuItem("Save Doodle");
        saveDoodle.addActionListener(saveDoodleListener);
        fileMenu.add(saveDoodle);

		JMenuItem loadDoodle = new JMenuItem("Load Doodle");
        loadDoodle.addActionListener(loadDoodleListener);
        fileMenu.add(loadDoodle);

		JMenuItem exitDoodle = new JMenuItem("Exit");
        exitDoodle.addActionListener(exitDoodleListener);
        fileMenu.add(exitDoodle);
	}
}
