package pollutionproject;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

public class DB {
	public static Connection makeConnection() {
		Connection conn = null;
		
		String url = "jdbc:mysql://localhost:3306/pollution";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = java.sql.DriverManager.getConnection(url,"root","jj1562");
			System.out.println("데이터베이스 연결 성공");	 
			
			//view를 이용해 파일을 읽고 디비에 임시저장해야한다 + git test
			return conn;
		}catch(ClassNotFoundException e) {
			//클래스가 제대로 설치되어 있는지 검사
			System.out.println("드라이버가 존재하지 않습니다"+e);
			return null;
		}catch(SQLException e) {
			System.out.println("오류:"+e);
			return null;
		}
	}
}


