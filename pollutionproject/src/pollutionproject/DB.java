package pollutionproject;
import java.io.*;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DriverManager;

public class DB {
	private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost:3306?serverTimezone=UTC";
	
	private static final String USER = "root";
	private static final String PASS = "jj1562";
	
	public static void deleteDatabase() {
		
		Connection conn = null;
		Statement stmt = null;
		
		try {
			//현재 사용하는 데이터베이스 커넥션을 가져온다.
			conn = makeConnection();
			
			stmt = conn.createStatement();
			String sql = "DROP DATABASE Flower2team";
			
			//사용하는 스키마를 삭제한다.
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
		//BufferedReader br = null;
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
			
			//파일 넣기(ver.1 - 그냥 로드데이터 한번 쓰기,이렇게 해도 되나..?)
			sql = "LOAD DATA INFILE './day.CSV' INTO TABLE degree CHARACTER SET UTF8 FIELDS TERMINATED BY ','";
			stmt.executeUpdate(sql);
			
			//파일넣기 (ver.2 - 파일 한줄씩 읽어서 직접 저장하기, 개느림..)
			/*br = Files.newBufferedReader(Paths.get("./file/day.CSV"));
			Charset.forName("UTF-8");
			String line = "";
			
			while((line = br.readLine()) != null) {
				String array[] = line.split(",");
				//빈곳은 null로 채워주기
				for(int i = 0; i < array.length; i++) {
					if(array[i].equals("")) {
						array[i] = "null";
					}
				}
				//끝 값이 빈 경우 길이차이가 나니까 바꿔주기
				if(array.length != 8) {
					String[] temp = new String[8];
					for(int i = 0; i < array.length; i++) {
						temp[i] = array[i];
					}
					for(int i = array.length; i < 8; i++) {
						temp[i] = "null";
					}
					array = temp;
				}
				StringBuilder sqlInsert = new StringBuilder();
				sqlInsert.append("INSERT degree (date, area, nitrogen, ozone, carbon, sulfur, fine_dust, ultrafine_dust)");
				sqlInsert.append(" VALUES (");
				sqlInsert.append("\""+array[0]+"\"");
				for(int i = 1; i < 8; i++) {
					sqlInsert.append(", \""+array[i]+"\"");
				}
				sqlInsert.append(")");
				stmt.executeUpdate(sqlInsert.toString());
			}*/
			
		}catch(ClassNotFoundException e) {
			System.out.println("오류:"+e);
		}catch(SQLException e) {
			System.out.println("오류:"+e);
		}/*catch(FileNotFoundException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}*/catch (Exception e){
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
			/*try {
				if(br != null) {
					br.close();
				}
			}catch(IOException e) {
				e.printStackTrace();
			}*/
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


