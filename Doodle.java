import javax.swing.*;
import java.awt.*;

public class Doodle {

//	public DrawingCanvas m_drawingCanvas;// = new DrawingCanvas();
  //  public DrawingSelector m_drawingSelector;// = new DrawingSelector();
    //public JFrame m_frame = new JFrame("Doodle");
    //public JPanel m_panel1 = new JPanel();
    //public JPanel m_panel2 = new JPanel();
    //private Model m_model;

    public static void main(String[] args) {
		JFrame frame = new JFrame("Doodle");

		//Create and initialize model.
        Model model = new Model();

		//Create view/controller and tell it about model.
        DrawingCanvas drawingCanvas = new DrawingCanvas(model);
		model.addObserver(drawingCanvas);

		//Create view/controller and tell it about model.
        DrawingSelector drawingSelector = new DrawingSelector(model);
		model.addObserver(drawingSelector);

		//Create view/controller and tell it about model.
		PlaybackControl playbackControl = new PlaybackControl(model);
		model.addObserver(drawingSelector);

		//Notify the views that they are connected to the model.
		model.notifyViews();

		JPanel m_panel1 = new JPanel();
		JPanel m_panel2 = new JPanel();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setSize(900, 600);
        frame.setMinimumSize(new Dimension(200, 300));
        m_panel2.setLayout(new BoxLayout(m_panel2, BoxLayout.X_AXIS));
        m_panel1.setLayout(new BoxLayout(m_panel1, BoxLayout.Y_AXIS));
        m_panel1.add(m_panel2);
        m_panel1.add(playbackControl);//new JButton( "Panel One" ));//, BorderLayout.NORTH );
        //m_panel1.add( new JButton( "One" ));//, BorderLayout.NORTH );
        m_panel2.add(drawingSelector);
        m_panel2.setBackground(Color.LIGHT_GRAY);
//      m_panel2.add( new JColorChooser());//, BorderLayout.NORTH );

        drawingCanvas.setMinimumSize(new Dimension(400, 300));
        m_panel2.add(drawingCanvas);

        m_panel1.setBorder(BorderFactory.createLineBorder(Color.black));
        m_panel2.setBorder(BorderFactory.createLineBorder(Color.black));
        /*m_panel1.add(m_homeScreen);
        m_panel1.add( new JButton( "One" ), BorderLayout.NORTH );
        *///m_panel1.add( new JButton( "Two" ), BorderLayout.CENTER );
        frame.add(m_panel1);
        frame.setVisible(true);
    }
}
