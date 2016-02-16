import javax.swing.*;
import java.awt.*;

public class Doodle {

	public static void main(String[] args) {
		Model model = new Model();
		View view = new View(model);
		model.setView(view);
	}
}
