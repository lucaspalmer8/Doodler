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
	private JFileChooser chooser = new JFileChooser();

	@Override
	public void notifyView() {
		if (m_model.getFullSize()) {
			fullSize.setSelected(true);
		}
	}

	private int getInt(String string) {
		return Integer.parseInt(string);
	}

	private float getFloat(String string) {
		float retval;
		if (string.startsWith("-")) { 
			string = string.substring(1);
			retval = -1*Float.valueOf(string);
		} else {
			retval = Float.valueOf(string);
		}
		return retval;
	}

	public MenuBar(Model model) {
		m_model = model;
		//JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu viewMenu = new JMenu("View");
        add(fileMenu);
        add(viewMenu);

		chooser.setAcceptAllFileFilterUsed(false);
		//Add the filters for .ser files and .txt files
		chooser.addChoosableFileFilter(new javax.swing.filechooser.FileFilter() {
			@Override
			public boolean accept(File f) {
        		if (f.isDirectory()) {
            		return true;
          		}
				String s = f.getName();
				return s.endsWith(".txt") || s.endsWith(".TXT");
   			}
			
			@Override
   			public String getDescription() {
       			return "*.txt,*.TXT";
  			}
		});

		chooser.addChoosableFileFilter(new javax.swing.filechooser.FileFilter() {
            @Override
            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                }
                String s = f.getName();
                return s.endsWith(".ser") || s.endsWith(".SER");
            }

            @Override
            public String getDescription() {
                return "*.ser,*.SER";
            }
        });

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
/*				final JOptionPane optionPane = new JOptionPane(
    "The only way to close this dialog is by\n"
    + "pressing one of the following buttons.\n"
    + "Do you understand?",
    JOptionPane.QUESTION_MESSAGE,
    JOptionPane.YES_NO_OPTION);

			optionPane.show();
*/
//				JOptionPane.showMessageDialog(m_model.getFrame(), "Eggs are not supposed to be green.");
				//Custom button text
				Object[] options = {"Yes, please", "No, thanks"};
				int n = JOptionPane.showOptionDialog(m_model.getFrame(),
    				"Are you sure you want to create\na new Doodle without saving?",
    				"Save Document?",
    				JOptionPane.YES_NO_OPTION,
    				JOptionPane.QUESTION_MESSAGE,
    				null,
    				options,
    				options[1]);

				if (n == 0) {
					m_model.resetDefault();
				}
System.out.println("The reslut if :::::::::::::::::;   " + n);


				//m_model.resetDefault();
                //m_model.setFullSize(false);
            }
        };

		ActionListener saveDoodleListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println("save");
//				final JFileChooser chooser = new JFileChooser();
				int returnVal = chooser.showSaveDialog(m_model.getFrame());
				//if (chooser.getSelectedFile() == null) return;
				String ext = "";
				if (returnVal == chooser.APPROVE_OPTION) {
					String extension = chooser.getFileFilter().getDescription();

       				if (extension.equals("*.ser,*.SER")) { 
          				ext = ".ser";
      				}
					if (extension.equals("*.txt,*.TXT")) {
						ext = ".txt";
					}
				} else {
					return;
				}

				String path = chooser.getSelectedFile().getAbsolutePath() + ext;
				String fileName = chooser.getSelectedFile().getName() + ext;

				if (ext == ".txt") {
					try {
						FileWriter fileWriter = new FileWriter(path);
						BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

						// Note that write() does not automatically
						// append a newline character.
						try {
							bufferedWriter.write(String.valueOf(m_model.getStrokeList().size()));
							bufferedWriter.newLine();
							for (int i = 0; i < m_model.getStrokeList().size(); i++) {
								DoodleStroke stroke = m_model.getStrokeList().get(i);
								
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

								ArrayList<DoodleStroke.DoodlePoint> pointList = stroke.getPointList();
								bufferedWriter.write(String.valueOf(pointList.size()));
								bufferedWriter.newLine();
								for(int j = 0; j < pointList.size(); j++) {
									DoodleStroke.DoodlePoint point = pointList.get(j);
									bufferedWriter.write(String.valueOf(point.getFloatX()));
									bufferedWriter.newLine();
									bufferedWriter.write(String.valueOf(point.getFloatY()));
									bufferedWriter.newLine();
									bufferedWriter.write(String.valueOf(point.getTimeStamp()));
									bufferedWriter.newLine();
								}
							}
						} finally { 
							bufferedWriter.close();
						}					

						/*bufferedWriter.write("Hello there,");
						bufferedWriter.write(" here is some text.");
						bufferedWriter.newLine();
						bufferedWriter.write("We are writing");
						bufferedWriter.write(" the text to the file.");
	*/
						//bufferedWriter.close();
			
					} catch(IOException ex) {
						System.out.println("Error writing to file '" + fileName + "'");
					}
				} else if (ext == ".ser") {

					try {
      					OutputStream file = new FileOutputStream(path);
      					OutputStream buffer = new BufferedOutputStream(file);
      					ObjectOutput output = new ObjectOutputStream(buffer);
						ArrayList<DoodleStroke> list = m_model.getStrokeList();
						try {
    						output.writeObject(list);
						} finally {
							output.close();
						}
    				} catch(IOException ex) {
						System.out.println(ex);
						System.out.println("Error writing to file '" + fileName + "'");
					}
				}	
            }
        };

		ActionListener loadDoodleListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//				final JFileChooser chooser = new JFileChooser();
                int returnVal = chooser.showOpenDialog(m_model.getFrame());
				
				String ext = "";
                if (returnVal == chooser.APPROVE_OPTION) {
                    String extension = chooser.getFileFilter().getDescription();

                    if (extension.equals("*.ser,*.SER")) {
                        ext = ".ser";
                    }
                    if (extension.equals("*.txt,*.TXT")) {
                        ext = ".txt";
                    }
                } else {
                    return;
                }

                String path = chooser.getSelectedFile().getAbsolutePath();
                String fileName = chooser.getSelectedFile().getName();

				String line = null;
				if (ext == ".txt") {
					try {
						FileReader fileReader = new FileReader(path);
						BufferedReader bufferedReader = new BufferedReader(fileReader);

						ArrayList<DoodleStroke> strokeList = new ArrayList<DoodleStroke>();

						try {
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
						
								DoodleStroke stroke = new DoodleStroke(width, new Color(red, green, blue));	
								stroke.setModel(m_model);

								//ArrayList<Model.Point> pointList = stroke.getPointList();
								line = bufferedReader.readLine();
								int pointListSize = getInt(line);
								for(int j = 0; j < pointListSize; j++) {
									line = bufferedReader.readLine();
									float x = getFloat(line);
									line = bufferedReader.readLine();
									float y = getFloat(line);
									line = bufferedReader.readLine();
									long time = Long.parseLong(line);

									stroke.addPoint(x, y, time);
									//Model.Point point = pointList.get(j);
									//bufferedWriter.write(point.getY());
								}
								strokeList.add(stroke);
							}
						} finally {
							bufferedReader.close();
						}
							
						m_model.resetDoodle(strokeList);
					
						//if (bufferedReader.readLine() == null) System.out.println("YayyyyiyyyyyyyyyyyyyyYYY");
						//bufferedReader.close();         
					} catch(FileNotFoundException ex) {
						System.out.println("Unable to open file '" + fileName + "'");       
						System.out.println(ex);         
					} catch(IOException ex) {
						System.out.println("Error reading file '" + fileName + "'");                  
					}
				} else if (ext == ".ser") {
					try {					
						InputStream file = new FileInputStream(path);
						InputStream buffer = new BufferedInputStream(file);
						ObjectInput input = new ObjectInputStream (buffer);
		
						ArrayList<DoodleStroke> strokeList = new ArrayList<DoodleStroke>();

						try {
							//deserialize the List
//							try {
								Object theOne = input.readObject();
								//if (theOne instanceof List<DoodleStroke>) {
									strokeList = (ArrayList<DoodleStroke>)theOne;
								//}
								//aif (
								//strokeList = (ArrayList<DoodleStroke>)input.readObject();
								System.out.println("The size is:::::::::::   " + strokeList.size());
								for (DoodleStroke stroke : strokeList) {
									stroke.setModel(m_model);
									//stroke.setColor(Color.BLACK);
									System.out.println(stroke.getColor());
									System.out.println(stroke.getWidth());
									//System.out.println(stroke.getPointSize());
									System.out.println(stroke.isFinished());
									//stroke.info();

								}
//							} finally {
//								input.close();
//							}
							m_model.resetDoodle(strokeList);
						} catch(ClassNotFoundException ex) {
							System.out.println(ex);
						} catch(IOException ex){
							System.out.println(ex);
						} finally {
							input.close();
						}
					} catch (IOException ex) {
						System.out.println(ex);
					}
				}
            }
        };

		ActionListener exitDoodleListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("exit");
				System.exit(0);
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
