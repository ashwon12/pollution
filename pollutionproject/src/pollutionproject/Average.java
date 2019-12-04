package pollutionproject;

import javax.swing.*;
import java.sql.*;

public class Average extends JPanel {

	protected Connection conn;

	protected double Nitrogen[] = new double[12]; // 이산화질소 1~12월 평균
	protected double Ozone[] = new double[12]; // 오존 1~12월 평균
	protected double Carbon[] = new double[12]; // 이산화탄소 1~12월 평균
	protected double Sulfur[] = new double[12]; // 아황산가스 1~12월 평균
	protected double FineDust[] = new double[12]; // 미세먼지 1~12월 평균
	protected double UltrafineDust[] = new double[12]; // 초미세먼지 1~12월 평균

	protected Average() {
		try {
			conn = DB.makeConnection();

			getNitrogenMonth(); // 월 이산화질소 농도 계산
			getOzoneMonth(); // 월 오존 농도 계산
			getCarbonMonth(); // 월 이산화탄소 농도 계산
			getSulfurMonth(); // 월 아황산가스 농도 계산
			getFineDustMonth(); // 월 미세먼지 농도 계산
			getUltrafineDustMonth(); // 월 초미세먼지 농도 계산

			// 반드시 닫자@@
			conn.close();
		} catch (SQLException e) {
			System.out.println("오류:" + e);
		}
	}

	private void setDate(PreparedStatement pstmt, int month) {
		try {
			switch (month + 1) {
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:// 31일짜리
				pstmt.setString(1, "2018-" + (month + 1) + "-1"); // 첫번째 물음표
				pstmt.setString(2, "2018-" + (month + 1) + "-31"); // 두번째 물음표
				break;
			case 4:
			case 6:
			case 9:
			case 11:// 30일짜리
				pstmt.setString(1, "2018-" + (month + 1) + "-1"); // 첫번째 물음표
				pstmt.setString(2, "2018-" + (month + 1) + "-30"); // 두번째 물음표
				break;
			case 2: // 28일짜리
				pstmt.setString(1, "2018-2-1"); // 첫번째 물음표
				pstmt.setString(2, "2018-2-28"); // 두번째 물음표
				break;
			default:
			}
		} catch (SQLException e) {
			System.out.println("오류:" + e);
		}
	}

	private void getNitrogenMonth() {
		try {
			ResultSet rs;
			PreparedStatement pstmt = conn.prepareStatement("SELECT nitrogen FROM degree WHERE date between ? and ? ");

			for (int month = 0; month < 12; month++) {
				setDate(pstmt, month);
				rs = pstmt.executeQuery();
				double temp = 0;
				int total = 0;

				rs.beforeFirst();
				while (rs.next()) {
					try {
						temp += Double.parseDouble(rs.getString("nitrogen"));
						total++;
					} catch (NumberFormatException e) { // 빈 데이터에서 예외가 처리된다.
						continue;
					}
				}
				// 소수 넷째자리에서 반올림
				Nitrogen[month] = Math.round((temp / total) * 1000) / 1000.0;
			}

		} catch (SQLException e) {
			System.out.println("오류:" + e);
		}
	}

	private void getOzoneMonth() {
		try {
			ResultSet rs;
			PreparedStatement pstmt = conn.prepareStatement("SELECT ozone FROM degree WHERE date between ? and ? ");

			for (int month = 0; month < 12; month++) {
				setDate(pstmt, month);

				rs = pstmt.executeQuery();
				double temp = 0;
				int total = 0;

				rs.beforeFirst();
				while (rs.next()) {
					while (rs.next()) {
						try {
							temp += Double.parseDouble(rs.getString("ozone"));
							total++;
						} catch (NumberFormatException e) { // 빈 데이터에서 예외가 처리된다.
							continue;
						}
					}
				}
				// 소수 넷째자리에서 반올림
				Ozone[month] = Math.round((temp / total) * 1000) / 1000.0;
			}

		} catch (SQLException e) {
			System.out.println("오류:" + e);
		}
	}

	private void getCarbonMonth() {
		try {
			ResultSet rs;
			PreparedStatement pstmt = conn.prepareStatement("SELECT carbon FROM degree WHERE date between ? and ? ");

			for (int month = 0; month < 12; month++) {
				setDate(pstmt, month);

				rs = pstmt.executeQuery();
				double temp = 0;
				int total = 0;

				rs.beforeFirst();
				while (rs.next()) {
					try {
						temp += Double.parseDouble(rs.getString("carbon"));
						total++;
					} catch (NumberFormatException e) { // 빈 데이터에서 예외가 처리된다.
						continue;
					}
				}
				// 소수 넷째자리에서 반올림
				Carbon[month] = Math.round((temp / total) * 1000) / 1000.0;
			}

		} catch (SQLException e) {
			System.out.println("오류:" + e);
		}
	}

	private void getSulfurMonth() {
		try {
			ResultSet rs;
			PreparedStatement pstmt = conn.prepareStatement("SELECT sulfur FROM degree WHERE date between ? and ? ");

			for (int month = 0; month < 12; month++) {
				setDate(pstmt, month);

				rs = pstmt.executeQuery();
				double temp = 0;
				int total = 0;

				rs.beforeFirst();
				while (rs.next()) {
					try {
						temp += Double.parseDouble(rs.getString("sulfur"));
						total++;
					} catch (NumberFormatException e) { // 빈 데이터에서 예외가 처리된다.
						continue;
					}
				}
				// 소수 넷째자리에서 반올림
				Sulfur[month] = Math.round((temp / total) * 10000) / 10000.0;
			}

		} catch (SQLException e) {
			System.out.println("오류:" + e);
		}
	}

	private void getFineDustMonth() {
		try {
			ResultSet rs;
			PreparedStatement pstmt = conn.prepareStatement("SELECT fine_dust FROM degree WHERE date between ? and ? ");

			for (int month = 0; month < 12; month++) {
				setDate(pstmt, month);

				rs = pstmt.executeQuery();
				double temp = 0;
				int total = 0;

				rs.beforeFirst();
				while (rs.next()) {
					try {
						temp += Double.parseDouble(rs.getString("fine_dust"));
						total++;
					} catch (NumberFormatException e) { // 빈 데이터에서 예외가 처리된다.
						continue;
					}
				}
				// 소수 넷째자리에서 반올림
				FineDust[month] = Math.round((temp / total) * 1000) / 1000.0;
			}

		} catch (SQLException e) {
			System.out.println("오류:" + e);
		}
	}

	private void getUltrafineDustMonth() {
		try {
			ResultSet rs;
			PreparedStatement pstmt = conn
					.prepareStatement("SELECT date, ultrafine_dust FROM degree WHERE date between ? and ? ");

			for (int month = 0; month < 12; month++) {
				setDate(pstmt, month);

				rs = pstmt.executeQuery();
				double temp = 0;
				int total = 0;

				rs.beforeFirst();
				while (rs.next()) {
					try {
						temp += Double.parseDouble(rs.getString("ultrafine_dust"));
						total++;
					} catch (NumberFormatException e) { // 빈 데이터에서 예외가 처리된다.
						continue;
					}
				}
				// 소수 넷째자리에서 반올림
				UltrafineDust[month] = Math.round((temp / total) * 1000) / 1000.0;
			}

		} catch (SQLException e) {
			System.out.println("오류:" + e);
		}
	}

	protected double getAverage(int m, String gas) {
		switch (gas) {
		case "nitrogen":
			return Nitrogen[m];
		case "ozone":
			return Ozone[m];
		case "carbon":
			return Carbon[m];
		case "sulfur":
			return Sulfur[m];
		case "fine_dust":
			return FineDust[m];
		case "ultrafine_dust":
			return UltrafineDust[m];
		default:
			System.out.println("잘못된 매개변수 입니다.");
			return -1;
		}
	}

	protected static String addUnit(String kor) {
		switch (kor) {
		case "이산화질소":
		case "아황산가스":
		case "일산화탄소":
		case "오존":
			return kor + " (ppm)";
		case "미세먼지":
		case "초미세먼지":
			return kor + " (㎍/㎥)";
		default:
			return null;
		}
	}
}
