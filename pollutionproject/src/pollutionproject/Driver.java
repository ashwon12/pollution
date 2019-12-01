package pollutionproject;
import javax.swing.JFrame;

public class Driver extends JFrame{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DB.createDatabase();
		Main m1 = new Main();
		m1.setVisible(true);
		
		//프로그램 종료 시 데이터베이스를 삭제한다.
		Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                DB.deleteDatabase();
            }
        });
	}

}

