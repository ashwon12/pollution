package pollutionproject;
import javax.swing.JFrame;

public class Driver extends JFrame{
	
	LoginView loginView;
	Main main;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Driver driver = new Driver();
		
		driver.loginView = new LoginView();
		DB.setMain(driver); //로그인 창에게 메인 클래스 보내기
		//DB.createDatabase();
		
		//프로그램 종료 시 데이터베이스를 삭제한다.
		Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                DB.deleteDatabase();
            }
        });
	}
	
	public void showMain() {
		loginView.dispose();
		this.main = new Main();
		main.setVisible(true);
	}

}

