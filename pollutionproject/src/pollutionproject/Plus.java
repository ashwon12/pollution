package pollutionproject;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;

public class Plus extends JPanel {

	private Connection conn;

	// left
	private JPanel temp1, temp2;
	private JPanel p1, p2, p3, p4;
	private JLabel mainText, areaLabel, gasLabel, dateLabel, valueLabel;
	private String Area[] = { "강남구", "강남대로", "강동구", "강변북로", "강북구", "강서구", "공항대로", "관악구", "광진구", "구로구", "금천구", "노원구",
			"도봉구", "도산대로", "동대문구", "동작구", "동작대로", "마포구", "서대문구", "서초구", "성동구", "성북구", "송파구", "신촌로", "양천구", "영등포구",
			"영등포로", "용산구", "은평구", "정릉로", "종로", "종로구", "중구", "중랑구", "천호대로", "청계천로", "한강대로", "홍릉로", "화랑로", "관악산", "궁동",
			"남산", "북한산", "세곡", "행주", "시흥대로" };
	private String Gas[] = { "이산화질소", "오존", "일산화탄소", "아황산가스", "미세먼지", "초미세먼지" };
	private String Month[] = { "1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월" };
	private String Day31[] = { "1일", "2일", "3일", "4일", "5일", "6일", "7일", "8일", "9일", "10일", "11일", "12일", "13일", "14일",
			"15일", "16일", "17일", "18일", "19일", "20일", "21일", "22일", "23일", "24일", "25일", "26일", "27일", "28일", "29일",
			"30일", "31일" };
	private String Day30[] = { "1일", "2일", "3일", "4일", "5일", "6일", "7일", "8일", "9일", "10일", "11일", "12일", "13일", "14일",
			"15일", "16일", "17일", "18일", "19일", "20일", "21일", "22일", "23일", "24일", "25일", "26일", "27일", "28일", "29일",
			"30일" };
	private String Day28[] = { "1일", "2일", "3일", "4일", "5일", "6일", "7일", "8일", "9일", "10일", "11일", "12일", "13일", "14일",
			"15일", "16일", "17일", "18일", "19일", "20일", "21일", "22일", "23일", "24일", "25일", "26일", "27일", "28일" };

	private JTextField value;
	private JButton result;

	private JComboBox<String> areaCombo, gasCombo, monthCombo, dateCombo;

	// listener
	Listener Listener = new Listener();

	private Font Big = new Font("맑은 고딕", Font.BOLD, 23);
	private Font middle = new Font("맑은 고딕", Font.PLAIN, 17);

	public Plus() {
		this.setLayout(new GridLayout(6, 1));
		this.setBackground(new Color(250, 250, 250));
		setPanel();
		// setRight();
	}

	private void setPanel() {
		// 여기서 날짜(콤보박스), 지역(콤보박스), 오염물질 종류(콤보박스), 값(text)

		// 설명 라벨
		temp1 = new JPanel();
		temp1.setBackground(new Color(250, 250, 250));
		mainText = new JLabel("<html><br>데이터 입력 및 수정입니다.");
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
		p3 = new JPanel();
		p3.setBackground(new Color(250, 250, 250));
		gasLabel = new JLabel("오염물질 선택 : ");
		gasLabel.setFont(middle);
		gasCombo = new JComboBox<String>(Gas);
		gasCombo.setBackground(Color.white);

		p3.add(gasLabel);
		p3.add(gasCombo);

		this.add(p3);
		// 값
		p4 = new JPanel();
		p4.setBackground(new Color(250, 250, 250));
		valueLabel = new JLabel("값 : ");
		valueLabel.setBackground(Color.white);
		valueLabel.setFont(middle);
		value = new JTextField(10);

		p4.add(valueLabel);
		p4.add(value);

		this.add(p4);

		// 입력하기 버튼
		temp2 = new JPanel();
		temp2.setBackground(new Color(250, 250, 250));
		result = new JButton("데이터 입력하기");
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
				System.out.println("데이터 입력하기 버튼 클릭");

				// 날짜, 지역, 오염물질, 값 가져오기
				String selectArea = (String) areaCombo.getSelectedItem();
				String selectGas = (String) gasCombo.getSelectedItem();
				String selectMonth = ((String) monthCombo.getSelectedItem()).replaceAll("[^0-9]", "");
				String selectDay = ((String) dateCombo.getSelectedItem()).replaceAll("[^0-9]", "");
				String getValue = value.getText();

				// 숫자가 아닌 문자가 있는 경우 에러표시와 함께 리턴시키기
				try {
					Double.parseDouble(getValue);
				} catch (Exception e1) {
					System.out.println(e1.getMessage());
					JOptionPane.showMessageDialog(null, "<html>값에서 오류가 발생했습니다. 다시 확인해주세요.<br>(가능 값 : 실수)</html>");
					return;
				}

				// 날짜 디비 검색 편하게 바꾸기
				insertDatabase(selectArea, changeEnglish(selectGas), getDate(selectMonth, selectDay), getValue);

			}
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
		System.out.println("2018" + month + day);
		return "2018" + month + day;
	}

	private void insertDatabase(String area, String gas, String date, String value) {
		try {
			conn = DB.makeConnection();

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT " + gas);
			sql.append(" FROM degree WHERE area = ? AND date = ?");

			ResultSet rs;
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());

			pstmt.setString(1, area);
			pstmt.setString(2, date);

			rs = pstmt.executeQuery();
			rs.beforeFirst();
			if (!rs.next()) {
				// insert
				System.out.println("날짜 자체가 없어!");
				JOptionPane.showMessageDialog(null, "<html>이전 데이터가 없습니다. <br>데이터를 <b>추가</b>합니다!</html>");

				// 날짜, 지역, 오염물질, 값 추가

				StringBuilder insert = new StringBuilder();
				insert.append(
						"INSERT INTO degree (date, area, nitrogen, ozone, carbon, sulfur, fine_dust, ultrafine_dust) ");

				// null값인건 ""로 채우려고 switch문 사용
				switch (gas) {
				case "nitrogen":
					insert.append("VALUES ( ?, ? ,? ,'','','','','')");
					break;
				case "ozone":
					insert.append("VALUES ( ?, ? ,'', ? ,'','','','')");
					break;
				case "carbon":
					insert.append("VALUES ( ?, ? ,'','', ? ,'','','')");
					break;
				case "sulfur":
					insert.append("VALUES ( ?, ? ,'' ,'','', ? ,'','')");
					break;
				case "fine_dust":
					insert.append("VALUES ( ?, ? ,'' ,'','','',?,'')");
					break;
				case "ultrafine_dust":
					insert.append("VALUES ( ?, ? ,'' ,'','','','',?)");
					break;
				default:
					System.out.println("이상한 오염물질! " + gas);
				}

				System.out.println(insert.toString());
				PreparedStatement insertStatement = conn.prepareStatement(insert.toString());
				insertStatement.setString(1, date);
				insertStatement.setString(2, area);
				insertStatement.setString(3, value);

				int result = insertStatement.executeUpdate();
				System.out.println(result);
				
			} else {
				// update
				rs.beforeFirst();
				while (rs.next()) {
					if (rs.getString(gas).equals("")) {
						System.out.println("비엇쪙");
						JOptionPane.showMessageDialog(null, "<html>이전 데이터가 없습니다. <br>데이터를 <u>추가</u>합니다!</html>");

						// 해당 날짜의 해당 지역에 ""였던 해당 오염물질 값을 value로 바꿔줌.
						StringBuilder update = new StringBuilder();
						update.append("UPDATE degree SET " + gas + " = ?");
						update.append(" WHERE date = ? AND area = ?;");

						PreparedStatement updateStatement = conn.prepareStatement(update.toString());
						updateStatement.setString(1, value);
						updateStatement.setString(2, date);
						updateStatement.setString(3, area);

						updateStatement.executeUpdate();

					} else {
						System.out.println("안비엇쪙" + rs.getString(gas));
						JOptionPane.showMessageDialog(null, "<html>이전 데이터가 있습니다.<br> 데이터를 <u>수정</u>합니다! <br>"
								+ rs.getString(gas) + "=>" + value + "</html>");

						// 해당 날짜의 해당 지역에 "값있음"였던 해당 오염물질 값을 value로 바꿔줌.
						StringBuilder update = new StringBuilder();
						update.append("UPDATE degree SET " + gas + " = ?");
						update.append(" WHERE date = ? AND area = ?;");

						PreparedStatement updateStatement = conn.prepareStatement(update.toString());
						updateStatement.setString(1, value);
						updateStatement.setString(2, date);
						updateStatement.setString(3, area);

						updateStatement.executeUpdate();

					}
				}
			}

		} catch (SQLException e) {
			System.out.println("오류:" + e);
		}

	}
}
