import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

class MenuBar extends JMenuBar implements ViewInterface {
	private Model m_model;
	private JRadioButtonMenuItem fullSize;
	private JRadioButtonMenuItem fitSize; 

	@Override
	public void notifyView() {
		if (m_model.getFullSize()) {
			fullSize.setSelected(true);
		}
	}

	private int getInt(String string) {
		if (string.startsWith("-")) { 
			string = string.substring(1);
		}
		return Integer.parseInt(string);
	}

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
				m_model.resetDefault();
                //m_model.setFullSize(false);
            }
        };

		ActionListener saveDoodleListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println("save");
				final JFileChooser chooser = new JFileChooser();
				int returnVal = chooser.showSaveDialog(m_model.getFrame());
				if (chooser.getSelectedFile() == null) return;
				String path = chooser.getSelectedFile().getAbsolutePath() + ".txt";
				String fileName = chooser.getSelectedFile().getName() + ".txt";

        		try {
            		FileWriter fileWriter = new FileWriter(path);
					BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            		// Note that write() does not automatically
            		// append a newline character.
					bufferedWriter.write(String.valueOf(m_model.getStrokeList().size()));
					bufferedWriter.newLine();
					for (int i = 0; i < m_model.getStrokeList().size(); i++) {
						Model.Stroke stroke = m_model.getStrokeList().get(i);
						
						int width = stroke.getWidth();
						bufferedWriter.write(String.valueOf(width));
	                    bufferedWriter.newLine();

						Color color = stroke.getColor();
						bufferedWriter.write(String.valueOf(color.getBlue()));
        	            bufferedWriter.newLine();
						bufferedWriter.write(String.valueOf(color.getRed()));
    	                bufferedWriter.newLine();
						bufferedWriter.write(String.valueOf(color.getGreen()));
	                    bufferedWriter.newLine();

						ArrayList<Model.Point> pointList = stroke.getPointList();
						bufferedWriter.write(String.valueOf(pointList.size()));
    	                bufferedWriter.newLine();
						for(int j = 0; j < pointList.size(); j++) {
							Model.Point point = pointList.get(j);
							bufferedWriter.write(String.valueOf(point.getX()));
        		            bufferedWriter.newLine();
							bufferedWriter.write(String.valueOf(point.getY()));
		                    bufferedWriter.newLine();
							bufferedWriter.write(String.valueOf(point.getTimeStamp()));
							bufferedWriter.newLine();
						}
	
					}					

            		/*bufferedWriter.write("Hello there,");
            		bufferedWriter.write(" here is some text.");
            		bufferedWriter.newLine();
            		bufferedWriter.write("We are writing");
            		bufferedWriter.write(" the text to the file.");
*/
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
				if (chooser.getSelectedFile() == null) return;
                String path = chooser.getSelectedFile().getAbsolutePath();
                String fileName = chooser.getSelectedFile().getName();

				String line = null;
               	try {
            		FileReader fileReader = new FileReader(path);
		            BufferedReader bufferedReader = new BufferedReader(fileReader);

					ArrayList<Model.Stroke> strokeList = new ArrayList<Model.Stroke>();

					line = bufferedReader.readLine();
					int size = getInt(line);
                    for (int i = 0; i < size; i++) {
                        //Model.Stroke stroke = m_model.getStrokeList().get(i);
						
						line = bufferedReader.readLine();
						int width = getInt(line);

                        //Color color = stroke.getColor();
						line = bufferedReader.readLine();
						int blue = getInt(line);
						line = bufferedReader.readLine();
						int red = getInt(line);
						line = bufferedReader.readLine();
						int green = getInt(line);
				
						Model.Stroke stroke = m_model.new Stroke(width, new Color(red, green, blue));	

                        //ArrayList<Model.Point> pointList = stroke.getPointList();
						line = bufferedReader.readLine();
						int pointListSize = getInt(line);
                        for(int j = 0; j < pointListSize; j++) {
							line = bufferedReader.readLine();
							int x = getInt(line);
							line = bufferedReader.readLine();
							int y = getInt(line);
							line = bufferedReader.readLine();
							long time = Long.parseLong(line);

							stroke.addPoint(m_model.new Point(x, y, time));
                            //Model.Point point = pointList.get(j);
                            //bufferedWriter.write(point.getY());
                        }
						strokeList.add(stroke);
                    }
					m_model.resetDoodle(strokeList);
					if (bufferedReader.readLine() == null) System.out.println("YayyyyiyyyyyyyyyyyyyyYYY");
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
		fullSize = new JRadioButtonMenuItem("Full-size");
		fitSize = new JRadioButtonMenuItem("Fit");
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
