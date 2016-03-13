import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

class MenuBar extends JMenuBar implements ViewInterface {
	private Model m_model;
	private JRadioButtonMenuItem m_fullSize;
	private JRadioButtonMenuItem m_fitSize; 
	private JFileChooser m_chooser = new JFileChooser();
	private JMenuItem m_saveDoodle;

	@Override
	public void notifyView() {
		m_saveDoodle.setEnabled(m_model.getFinishedStrokes() != 0);
		if (m_model.getFullSize()) {
			m_fullSize.setSelected(true);
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
		JMenu fileMenu = new JMenu("File");
		JMenu viewMenu = new JMenu("View");
		add(fileMenu);
		add(viewMenu);

		m_chooser.addChoosableFileFilter(new javax.swing.filechooser.FileFilter() {
			@Override
			public boolean accept(File f) {
				if (f.isDirectory()) {
					return true;
				}
				String s = f.getName();
				return s.endsWith(".txt");
			}
			
			@Override
			public String getDescription() {
				return "*.txt,*.TXT";
			}
		});

		m_chooser.addChoosableFileFilter(new javax.swing.filechooser.FileFilter() {
			@Override
			public boolean accept(File f) {
				if (f.isDirectory()) {
					return true;
				}
				String s = f.getName();
				return s.endsWith(".ser");
			}

			@Override
			public String getDescription() {
				return "*.ser,*.SER";
			}
		});

		ActionListener fullView = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				m_model.setFullSize(true);
			}
		};

		ActionListener fitView = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				m_model.setFullSize(false);
			}
		};

		ActionListener newDoodleListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (m_model.getStrokeList().size() != 0) {
					Object[] options = {"Yes, please", "No, thanks"};
					int n = JOptionPane.showOptionDialog(m_model.getFrame(),
						"Are you sure you want to create\na new Doodle without saving?",
						"Save Document?",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE,
						null,
						options,
						options[1]);
					if (n == 1) {
						return;
					}
				}

				m_model.resetDefault();
			}
		};

		ActionListener saveDoodleListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (m_model.getStrokeList().size() == 0) {
					Object[] options = {"Yes, please", "No, thanks"};
					int n = JOptionPane.showOptionDialog(m_model.getFrame(),
						"Are you sure you want to save\nan empty Doodle?",
						"Save Document?",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE,
						null,
						options,
						options[1]);
					if (n == 1) {
						return;
					}
				}

				int returnVal = m_chooser.showSaveDialog(m_model.getFrame());
				String ext = "";
				if (returnVal == m_chooser.APPROVE_OPTION) {
					String extension = m_chooser.getFileFilter().getDescription();

					if (extension.equals("*.ser,*.SER")) { 
						ext = ".ser";
					} else if (extension.equals("*.txt,*.TXT")) {
						ext = ".txt";
					} else {
						ext = ".txt";
					}
				} else {
					return;
				}

				String path = m_chooser.getSelectedFile().getAbsolutePath() + ext;
				String fileName = m_chooser.getSelectedFile().getName() + ext;

				if (ext.equals(".txt")) {
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

					} catch(IOException ex) {
					}
				} else if (ext.equals(".ser")) {

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
					}
				}	
			}
		};

		ActionListener loadDoodleListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (m_model.getStrokeList().size() != 0) {
					Object[] options = {"Yes, please", "No, thanks"};
					int n = JOptionPane.showOptionDialog(m_model.getFrame(),
						"Are you sure you want to load a new Doodle\nwithout saving this one?",
						"Save Document?",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE,
						null,
						options,
						options[1]);
				
					if (n == 1) {
						return;
					}
				}


				int returnVal = m_chooser.showOpenDialog(m_model.getFrame());
				
				String ext = "";
				if (returnVal != m_chooser.APPROVE_OPTION) {
					return;
				}

				String path = m_chooser.getSelectedFile().getAbsolutePath();
				String fileName = m_chooser.getSelectedFile().getName();

				if (path.endsWith(".txt")) {
					ext = ".txt";
				} else if (path.endsWith(".ser")) {
					ext = ".ser";
				}

				String line = null;
				if (ext.equals(".txt")) {
					try {
						FileReader fileReader = new FileReader(path);
						BufferedReader bufferedReader = new BufferedReader(fileReader);

						ArrayList<DoodleStroke> strokeList = new ArrayList<DoodleStroke>();

						try {
							line = bufferedReader.readLine();
							int size = getInt(line);
							for (int i = 0; i < size; i++) {
								line = bufferedReader.readLine();
								int width = getInt(line);

								line = bufferedReader.readLine();
								int blue = getInt(line);
								line = bufferedReader.readLine();
								int red = getInt(line);
								line = bufferedReader.readLine();
								int green = getInt(line);
						
								DoodleStroke stroke = new DoodleStroke(width, new Color(red, green, blue));	
								stroke.setModel(m_model);

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
								}
								strokeList.add(stroke);
							}
						} finally {
							bufferedReader.close();
						}
							
						m_model.resetDoodle(strokeList);
					} catch(FileNotFoundException ex) {
					} catch(IOException ex) {
					}
				} else if (ext.equals(".ser")) {
					try {					
						InputStream file = new FileInputStream(path);
						InputStream buffer = new BufferedInputStream(file);
						ObjectInput input = new ObjectInputStream (buffer);
		
						ArrayList<DoodleStroke> strokeList = new ArrayList<DoodleStroke>();

						try {
							Object theOne = input.readObject();
							strokeList = (ArrayList<DoodleStroke>)theOne;
							for (DoodleStroke stroke : strokeList) {
								stroke.setModel(m_model);
							}
							m_model.resetDoodle(strokeList);
						} catch(ClassNotFoundException ex) {
						} catch(IOException ex){
						} finally {
							input.close();
						}
					} catch (IOException ex) {
					}
				}
			}
		};

		ActionListener exitDoodleListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (m_model.getStrokeList().size() != 0) {
					Object[] options = {"Yes, please", "No, thanks"};
					int n = JOptionPane.showOptionDialog(m_model.getFrame(),
						"Are you sure you want to exit\nwithout saving the current Doodle?",
						"Save Document?",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE,
						null,
						options,
						options[1]);
					if (n == 1) {
						return;
					}
				}

				System.exit(0);
			}
		};

		ButtonGroup group = new ButtonGroup();
		m_fullSize = new JRadioButtonMenuItem("Full-size");
		m_fitSize = new JRadioButtonMenuItem("Fit");
		group.add(m_fullSize);
		group.add(m_fitSize);
		m_fullSize.setSelected(true);
		m_fullSize.addActionListener(fullView);
		m_fitSize.addActionListener(fitView);
		viewMenu.add(m_fullSize);
		viewMenu.add(m_fitSize);
		
		JMenuItem newDoodle = new JMenuItem("New Doodle");
		newDoodle.addActionListener(newDoodleListener);
		fileMenu.add(newDoodle);

		m_saveDoodle = new JMenuItem("Save Doodle");
		m_saveDoodle.addActionListener(saveDoodleListener);
		fileMenu.add(m_saveDoodle);

		JMenuItem loadDoodle = new JMenuItem("Load Doodle");
		loadDoodle.addActionListener(loadDoodleListener);
		fileMenu.add(loadDoodle);

		JMenuItem exitDoodle = new JMenuItem("Exit");
		exitDoodle.addActionListener(exitDoodleListener);
		fileMenu.add(exitDoodle);
	}
}
