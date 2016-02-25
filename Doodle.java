import javax.swing.*;
import java.awt.*;

public class Doodle {

//	public DrawingCanvas m_drawingCanvas;// = new DrawingCanvas();
  //  public DrawingSelector m_drawingSelector;// = new DrawingSelector();
    public JFrame frame;
	public JPanel panel;
	public JScrollPane fullView;
	public JPanel fitView;
    //public JPanel m_panel1 = new JPanel();
    //public JPanel m_panel2 = new JPanel();
    //private Model m_model;

	public static void main(String[] args) {
		Doodle doodle = new Doodle();
	}

    public Doodle() {
		frame = new JFrame("Doodle");
		panel = new JPanel();
        panel.setLayout(new BorderLayout());

		JPanel panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));
        JPanel panel2 = new JPanel();
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));

		fullView = new JScrollPane(panel1);
		fitView = new JPanel(new GridLayout(1,1));

		//Create and initialize model.
        Model model = new Model(this);

		//Create view/controller and tell it about model.
        DrawingCanvas drawingCanvas = new DrawingCanvas(model, true) {
			@Override
    		public Dimension getPreferredSize() {
            	return new Dimension(700, 400);
    		}

    		@Override
    		public Dimension getMaximumSize() {
            	return new Dimension(700, 400);
    		}
		};
		model.addObserver(drawingCanvas);

		//Create view/controller and tell it about model.
        DrawingCanvas drawingCanvas2 = new DrawingCanvas(model, false);
        model.addObserver(drawingCanvas2);

		//Create view/controller and tell it about model.
        DrawingSelector drawingSelector = new DrawingSelector(model);
		model.addObserver(drawingSelector);

		//Create view/controller and tell it about model.
		PlaybackControl playbackControl = new PlaybackControl(model);
		model.addObserver(playbackControl);

		//Notify the views that they are connected to the model.
		model.notifyViews();

		//JPanel panel = new JPanel();
		//panel.setLayout(new BorderLayout());

//		drawingView.setBorder(BorderFactory.createLineBorder(Color.black, 5));
//		drawingSelector.setBorder(BorderFactory.createLineBorder(Color.black, 5));
//		playbackControl.setBorder(BorderFactory.createLineBorder(Color.black, 5));

		//JMenuBar menuBar = new JMenuBar();
		//JMenu fileMenu = new JMenu("File");
		//JMenu viewMenu = new JMenu("View");
		//menuBar.add(fileMenu);
		//menuBar.add(viewMenu);

//		drawingCanvas.setPreferredSize(new Dimension(700, 400));
//		drawingCanvas.setMaximumSize(new Dimension(700, 400));
//		drawingCanvas.setMinimumSize(new Dimension(700, 400));

		///Here it iss!!!!!
/*		JPanel panel1 = new JPanel();
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));
		JPanel panel2 = new JPanel();
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
*/
		fitView.add(drawingCanvas2);

		panel2.add(new JPanel());
		panel2.add(drawingCanvas);
		panel2.add(new JPanel());
		panel1.add(new JPanel());
		panel1.add(panel2);
		panel1.add(new JPanel());

/*		JPanel panelFit = new JPanel();
		panelFit.add(drawingCanvas);
	
		//JPanel panelFull = new JScrollPane(panel1);

		CardLayout layout = new CardLayout();

		JPanel panelMain = new JPanel(layout);
		JScrollPane panelFull = new JScrollPane(panel1);
		panelMain.add(panelFull, "FULL");
		panelMain.add(panelFit, "FIT");
		layout.show(panelMain, "FIT");
		//panelMain.add(new JScrollPane(panel1));
//(new BoxLayout(m_panel2, BoxLayout.X_AXIS));
    //    m_panel1.setLayout(new BoxLayout(m_panel1, BoxLayout.Y_AXIS));
		//panelMain.show("FIT");
*/
		
		panel.add(new MenuBar(model), BorderLayout.NORTH);
		panel.add(drawingSelector, BorderLayout.WEST);
		panel.add(fullView, BorderLayout.CENTER);
		panel.add(playbackControl, BorderLayout.SOUTH);


//		JPanel m_panel1 = new JPanel();
//		JPanel m_panel2 = new JPanel();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setSize(900, 600);
        frame.setMinimumSize(new Dimension(200, 300));
  //      m_panel2.setLayout(new BoxLayout(m_panel2, BoxLayout.X_AXIS));
    //    m_panel1.setLayout(new BoxLayout(m_panel1, BoxLayout.Y_AXIS));
      //  m_panel1.add(m_panel2);
        //m_panel1.add(playbackControl);//new JButton( "Panel One" ));//, BorderLayout.NORTH );
        //m_panel1.add( new JButton( "One" ));//, BorderLayout.NORTH );
        //m_panel2.add(drawingSelector);
        //m_panel2.setBackground(Color.LIGHT_GRAY);
//      m_panel2.add( new JColorChooser());//, BorderLayout.NORTH );

        //drawingCanvas.setMinimumSize(new Dimension(400, 300));
        //m_panel2.add(drawingCanvas);

        //m_panel1.setBorder(BorderFactory.createLineBorder(Color.black));
        //m_panel2.setBorder(BorderFactory.createLineBorder(Color.black));*/
        /*m_panel1.add(m_homeScreen);
        m_panel1.add( new JButton( "One" ), BorderLayout.NORTH );
        *///m_panel1.add( new JButton( "Two" ), BorderLayout.CENTER );
        frame.add(panel);
        frame.setVisible(true);
    }
}
