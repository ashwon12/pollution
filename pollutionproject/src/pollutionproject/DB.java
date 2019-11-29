package pollutionproject;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DriverManager;

public class DB {
	private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost:3306?serverTimezone=UTC";
	
	private static final String USER = "root";
	private static final String PASS = "jj1562";
	
	public static void deleteDatabase(Connection conn) {
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			String sql = "DROP DATABASE Flower2team";
			
			System.out.println("Database delete...");
			stmt.executeUpdate(sql);
			System.out.println("Database delete successfully...");
		}catch(SQLException e) {
			System.out.println("오류:"+e);
		}finally {
			try {
				if(stmt != null)
					stmt.close();
			}catch(SQLException e) {
				System.out.println("오류: "+e);
			}
		}
	}
	public static void createDatabase() {
		Statement stmt = null;
		Connection conn = null; 
		try {
			Class.forName(JDBC_DRIVER);
			System.out.println("Connecting to database...");
			conn = java.sql.DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("Creating database...");
			stmt = conn.createStatement();
			
			//데이터베이스만들기
			String sql = "CREATE DATABASE Flower2team";
			stmt.executeUpdate(sql);
			System.out.println("Database created successfully...");
			
			//테이블만들기
			sql = "USE Flower2team";
			stmt.executeUpdate(sql);
			
			sql = "CREATE TABLE degree"
					+ "( date DATE, area VARCHAR(45)"
					+ ", nitrogen VARCHAR(45), ozone VARCHAR(45)"
					+ ", carbon VARCHAR(45), sulfur VARCHAR(45)"
					+ ", fine_dust VARCHAR(45), ultrafine_dust VARCHAR(45))";
			stmt.executeUpdate(sql);
			
			//파일 넣기
			sql = "LOAD DATA INFILE './day.CSV' INTO TABLE degree CHARACTER SET UTF8 FIELDS TERMINATED BY ','";
			stmt.executeUpdate(sql);
			
		}catch(ClassNotFoundException e) {
			System.out.println("오류:"+e);
		}catch(SQLException e) {
			System.out.println("오류:"+e);
			//데이터베이스를 drop하고 다시 create한다.
			deleteDatabase(conn);
			createDatabase();
		}catch (Exception e){
			System.out.println("오류:"+e);
		}finally {
			try {
				if(stmt != null)
					stmt.close();
			}catch(SQLException e) {
				System.out.println("오류: "+e);
			}	
			try {
				if(conn != null) {
					conn.close();
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static Connection makeConnection() {
		
		Connection conn = null;
		
		String url = "jdbc:mysql://localhost:3306/Flower2team";
		try {
			Class.forName(JDBC_DRIVER);
			conn = java.sql.DriverManager.getConnection(url,USER,PASS);
			System.out.println("Connecting...");	 
		}catch(ClassNotFoundException e) {
			//클래스가 제대로 설치되어 있는지 검사
			System.out.println("드라이버가 존재하지 않습니다"+e);
			return null;
		}catch(SQLException e) {
			System.out.println("오류:"+e);
			return null;
		}
		return conn;
		
	}
}


