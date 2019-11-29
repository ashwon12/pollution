package pollutionproject;
import javax.swing.JFrame;

public class Driver extends JFrame{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DB.createDatabase();
		Main m1 = new Main();
		m1.setVisible(true);
	}

}

