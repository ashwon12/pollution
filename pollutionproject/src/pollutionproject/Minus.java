package pollutionproject;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.sql.*;
import javax.swing.*;

public class Minus extends JPanel {

	private static Connection conn;

	private JPanel temp1, temp2;
	private JPanel p1, p2, p3, p4, p5;
	private JLabel mainText, areaLabel, gasLabel1, gasLabel2, dateLabel;
	private String Area[] = { "강남구", "강남대로", "강동구", "강변북로", "강북구", "강서구", "공항대로", "관악구", "광진구", "구로구", "금천구", "노원구",
			"도봉구", "도산대로", "동대문구", "동작구", "동작대로", "마포구", "서대문구", "서초구", "성동구", "성북구", "송파구", "신촌로", "양천구", "영등포구",
			"영등포로", "용산구", "은평구", "정릉로", "종로", "종로구", "중구", "중랑구", "천호대로", "청계천로", "한강대로", "홍릉로", "화랑로", "관악산", "궁동",
			"남산", "북한산", "세곡", "행주", "시흥대로" };
	private String Gas[] = { "선택 안함", "이산화질소", "오존", "일산화탄소", "아황산가스", "미세먼지", "초미세먼지" };
	private String Month[] = { "1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월" };
	private String Day31[] = { "1일", "2일", "3일", "4일", "5일", "6일", "7일", "8일", "9일", "10일", "11일", "12일", "13일", "14일",
			"15일", "16일", "17일", "18일", "19일", "20일", "21일", "22일", "23일", "24일", "25일", "26일", "27일", "28일", "29일",
			"30일", "31일" };
	private String Day30[] = { "1일", "2일", "3일", "4일", "5일", "6일", "7일", "8일", "9일", "10일", "11일", "12일", "13일", "14일",
			"15일", "16일", "17일", "18일", "19일", "20일", "21일", "22일", "23일", "24일", "25일", "26일", "27일", "28일", "29일",
			"30일" };
	private String Day28[] = { "1일", "2일", "3일", "4일", "5일", "6일", "7일", "8일", "9일", "10일", "11일", "12일", "13일", "14일",
			"15일", "16일", "17일", "18일", "19일", "20일", "21일", "22일", "23일", "24일", "25일", "26일", "27일", "28일" };

	private JButton result;

	private JComboBox<String> areaCombo, gasCombo, monthCombo, dateCombo;

	// listener
	Listener Listener = new Listener();

	private Font Big = new Font("맑은 고딕", Font.BOLD, 23);
	private Font middle = new Font("맑은 고딕", Font.PLAIN, 17);
	private Font small = new Font("맑은 고딕", Font.BOLD, 15);

	// 생성자
	public Minus() {
		this.setLayout(new GridLayout(5, 1));
		this.setBackground(new Color(250, 250, 250));
		setPanel();
	}

	private void setPanel() {
		// 설명 라벨
		temp1 = new JPanel();
		temp1.setBackground(new Color(250, 250, 250));
		mainText = new JLabel("<html><br>데이터 삭제입니다.");
		mainText.setFont(Big);

		temp1.add(mainText);
		this.add(temp1);

		// 날짜
		p1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
		p1.setBackground(new Color(250, 250, 250));
		dateLabel = new JLabel("날짜 선택 : 2018년 ");
		dateLabel.setFont(middle);

		monthCombo = new JComboBox<String>(Month);
		monthCombo.setBackground(Color.white);
		dateCombo = new JComboBox<String>(Day31); // 기본 1월 선택
		dateCombo.setBackground(Color.white);

		monthCombo.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				p1.remove(dateCombo);

				switch ((String) e.getItem()) {

				case "1월":
				case "3월":
				case "5월":
				case "7월":
				case "8월":
				case "10월":
				case "12월": // 31일짜리
					dateCombo = new JComboBox<String>(Day31);
					p1.add(dateCombo);
					break;
				case "4월":
				case "6월":
				case "9월":
				case "11월": // 30일짜리
					dateCombo = new JComboBox<String>(Day30);
					p1.add(dateCombo);
					break;
				case "2월": // 28일짜리
					dateCombo = new JComboBox<String>(Day28);
					p1.add(dateCombo);
					break;
				default:
					System.out.println("월 선택 이상");
				}
				dateCombo.setBackground(Color.white);
				revalidate(); // 변경사항 바로 보기
			}
		});

		p1.add(dateLabel);
		p1.add(monthCombo);
		p1.add(dateCombo);

		this.add(p1);

		// 지역
		p2 = new JPanel();
		p2.setBackground(new Color(250, 250, 250));
		areaLabel = new JLabel("지역 선택 : ");
		areaLabel.setFont(middle);
		areaCombo = new JComboBox<String>(Area);
		areaCombo.setBackground(Color.white);

		p2.add(areaLabel);
		p2.add(areaCombo);

		this.add(p2);

		// 오염물질
		p3 = new JPanel(new GridLayout(2, 1));
		p3.setBackground(new Color(250, 250, 250));

		p4 = new JPanel();
		p4.setBackground(new Color(250, 250, 250));
		gasLabel2 = new JLabel("오염물질 선택 : ");
		gasLabel2.setFont(middle);
		gasCombo = new JComboBox<String>(Gas);
		gasCombo.setBackground(Color.white);
		p4.add(gasLabel2);
		p4.add(gasCombo);

		p5 = new JPanel();
		p5.setBackground(new Color(250, 250, 250));
		gasLabel1 = new JLabel("※선택 안함 항목 선택 시 선택 날짜의 선택 지역인 행 전체가 삭제됩니다.");
		gasLabel1.setFont(small);
		p5.add(gasLabel1);

		p3.add(p4);
		p3.add(p5);

		this.add(p3);

		// 입력하기 버튼
		temp2 = new JPanel();
		temp2.setBackground(new Color(250, 250, 250));
		result = new JButton("데이터 삭제하기");
		result.setFont(middle);
		result.setBackground(new Color(242, 242, 242));
		result.addActionListener(Listener);

		temp2.add(result);

		this.add(temp2);
	}

	private class Listener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() == result) {
				System.out.println("데이터 삭제하기 버튼 클릭");

				// 날짜, 지역, 오염물질, 값 가져오기
				String selectArea = (String) areaCombo.getSelectedItem();
				String selectGas = (String) gasCombo.getSelectedItem();
				String selectMonth = ((String) monthCombo.getSelectedItem()).replaceAll("[^0-9]", "");
				String selectDay = ((String) dateCombo.getSelectedItem()).replaceAll("[^0-9]", "");

				if (selectGas.equals("선택 안함")) {
					deleteDatabase(selectArea, getDate(selectMonth, selectDay));
				} else {
					// 데이터 하나 삭제
					deleteDatabase(selectArea, changeEnglish(selectGas), selectGas, getDate(selectMonth, selectDay));
				}

			}
		}
	}

	// 데이터 삭제 함수 오버로딩
	private void deleteDatabase(String area, String date) {
		// System.out.println(area+date+"행 삭제!");
		try {
			conn = DB.makeConnection();

			// 선택한 데이터의 존재 유무 확인
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM degree WHERE area = ? AND date = ?");

			ResultSet rs;
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());

			pstmt.setString(1, area);
			pstmt.setString(2, date);

			rs = pstmt.executeQuery();
			rs.beforeFirst();

			// 존재하면 지우고 없으면 삭제할 데이터 없다고 하기
			if (rs.next()) {
				JOptionPane.showMessageDialog(null, date + ", " + area + " 행 전체를 삭제합니다.");

				StringBuilder delete = new StringBuilder();
				delete.append("DELETE FROM degree WHERE date = ? AND area = ?");

				PreparedStatement deleteStatement = conn.prepareStatement(delete.toString());
				deleteStatement.setString(1, date);
				deleteStatement.setString(2, area);

				deleteStatement.executeUpdate();
				deleteStatement.close();
			} else {
				JOptionPane.showMessageDialog(null, "삭제할 데이터가 없습니다!");
				return;
			}

			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("오류:" + e);
		}
	}

	private void deleteDatabase(String area, String gas, String korean, String date) {
		try {
			conn = DB.makeConnection();

			// 선택한 데이터의 존재 유무 확인
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT " + gas);
			sql.append(" FROM degree WHERE area = ? AND date = ?");

			ResultSet rs;
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());

			pstmt.setString(1, area);
			pstmt.setString(2, date);

			rs = pstmt.executeQuery();
			rs.beforeFirst();

			// 날짜 자체가 없는 경우
			if (!rs.next()) {
				JOptionPane.showMessageDialog(null, "삭제할 데이터가 없습니다!");
				return;
			} else {
				rs.beforeFirst();
				while (rs.next()) {

					if (rs.getString(gas).equals("")) {
						JOptionPane.showMessageDialog(null, "삭제할 데이터가 없습니다!");
						return;
					} else {
						// 끝 열일 경우
						byte[] byte_str = new byte[rs.getString(gas).length()];
						int j = 0;
						// 문자열의 한문자씩 byte단위로 byte배열 byte_str에 저장하기.
						for (int i = 0; i < rs.getString(gas).length(); i++)
							byte_str[i] = (byte) rs.getString(gas).charAt(i); // 바이트의 단위로 인식할 수 있게 형변환.

						// 자료형 문자, byte단위의 문자 비교하기
						for (byte value : byte_str) {
							System.out.println(rs.getString(gas).charAt(j++) + " : " + value);
							// 첫번째가 숫자라면 빈값 아님
							if (value >= 48 && value <= 57) {
								break;
							}
							if (value == 13) {
								JOptionPane.showMessageDialog(null, "삭제할 데이터가 없습니다!");
								return;
							}
						}
					}
					System.out.println("삭제할 데이터 있음");
					JOptionPane.showMessageDialog(null, "<html>" + date + ", " + area + " ," + korean + ", "
							+ rs.getString(gas) + "<br>데이터를 삭제합니다.");

					// 오염물질 하나 삭제는 사실상 ""로 수정
					StringBuilder delete = new StringBuilder();
					delete.append("UPDATE degree SET " + gas + " = ''");
					delete.append(" WHERE date = ? AND area = ?");

					PreparedStatement deleteStatement = conn.prepareStatement(delete.toString());
					deleteStatement.setString(1, date);
					deleteStatement.setString(2, area);

					deleteStatement.executeUpdate();
					deleteStatement.close();
				}
			}

			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("오류:" + e);
		}
	}

	private String changeEnglish(String korean) {
		switch (korean) {
		case "이산화질소":
			return "nitrogen";
		case "일산화탄소":
			return "carbon";
		case "오존":
			return "ozone";
		case "아황산가스":
			return "sulfur";
		case "미세먼지":
			return "fine_dust";
		case "초미세먼지":
			return "ultrafine_dust";
		default:
			return null;
		}
	}

	private String getDate(String month, String day) {
		if (Integer.parseInt(month) < 10) {
			month = "0" + month;
		}
		if (Integer.parseInt(day) < 10) {
			day = "0" + day;
		}
		// System.out.println("2018"+month+day);
		return "2018" + month + day;
	}

	// 저장버튼 리스너 만들기
	public static ActionListener allSave = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			System.out.println("전체파일 저장");

			conn = DB.makeConnection();

			File savefile;
			String savepathname;

			// 파일 경로 선택
			JFileChooser chooser = new JFileChooser();// 객체 생성
			chooser.setCurrentDirectory(new File("C:\\")); // 맨처음경로를 C로 함
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // 디렉토리만 선택가능

			int re = chooser.showSaveDialog(null);

			if (re == JFileChooser.APPROVE_OPTION) { // 디렉토리를 선택했으면
				savefile = chooser.getSelectedFile(); // 선택된 디렉토리 저장하고
				savepathname = savefile.getAbsolutePath(); // 디렉토리결과+파일이름

			} else {
				JOptionPane.showMessageDialog(null, "경로를 선택하지않았습니다.", "경고", JOptionPane.WARNING_MESSAGE);
				return;
			}
			String isCSV = savepathname.substring(savepathname.length() - 4, savepathname.length());
			if (isCSV.contentEquals(".CSV") || isCSV.equals(".csv")) { // 이름 끝에 .CSV가 없다면 자동으로 .CSV를 구한다.
				System.out.println("csv맞음");
			} else {
				System.out.println("csv아님");
				savepathname += ".CSV";
			}

			savepathname = savepathname.replace("\\", "/");
			System.out.println(savepathname);

			StringBuilder save = new StringBuilder();
			save.append("SELECT * FROM degree INTO OUTFILE '" + savepathname);
			save.append("' CHARACTER SET euckr FIELDS TERMINATED BY ','");
			save.append(" ENCLOSED BY '\"'");
			save.append(" LINES TERMINATED BY '\n'");
			// System.out.println(save.toString());

			try {
				PreparedStatement saveStatement = conn.prepareStatement(save.toString());
				saveStatement.executeQuery();

				System.out.println(saveStatement.toString());

				saveStatement.close();
				conn.close();
				JOptionPane.showMessageDialog(null, "전체 데이터를 저장했습니다!");

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("오류 : " + e);
			}
		}
	};
}
