package pollutionproject;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;

import java.util.*;

public class Area extends JPanel {
	
	private Connection conn;
	private JPanel left, right;
	
	//왼쪽 패널
	private JPanel p1,p2,p3,p4,p5;
	private JLabel mainText,area_Select,gas_Select,date_Select,text;
	private JComboBox<String> areaCombo,gasCombo,monthCombo,dateCombo;
	private String Area[] = {"강남구","강남대로","강동구","강변북로","강북구","강서구","공항대로",
			"관악구",
			"광진구",
			"구로구",
			"금천구",
			"노원구",
			"도봉구",
			"도산대로",
			"동대문구",
			"동작구",
			"동작대로",
			"마포구",
			"서대문구",
			"서초구",
			"성동구",
			"성북구",
			"송파구",
			"신촌로",
			"양천구",
			"영등포구",
			"영등포로",
			"용산구",
			"은평구",
			"정릉로",
			"종로",
			"종로구",
			"중구",
			"중랑구",
			"천호대로",
			"청계천로",
			"한강대로",
			"홍릉로",
			"화랑로",
			"관악산",
			"궁동",
			"남산",
			"북한산",
			"세곡",
			"행주",
			"시흥대로"};
	private String Gas[]= {"이산화질소","오존","일산화탄소","아황산가스","미세먼지","초미세먼지"};
	private String Month[] = { "1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월"};
	private String Day31[] = { "1일","2일","3일","4일","5일","6일","7일","8일","9일","10일","11일","12일","13일","14일","15일","16일"
			,"17일","18일","19일","20일","21일","22일","23일","24일","25일","26일","27일","28일","29일","30일","31일"};
	private String Day30[] = { "1일","2일","3일","4일","5일","6일","7일","8일","9일","10일","11일","12일","13일","14일","15일","16일"
			,"17일","18일","19일","20일","21일","22일","23일","24일","25일","26일","27일","28일","29일","30일"};
	private String Day28[] = { "1일","2일","3일","4일","5일","6일","7일","8일","9일","10일","11일","12일","13일","14일","15일","16일"
			,"17일","18일","19일","20일","21일","22일","23일","24일","25일","26일","27일","28일"};
	
	private JButton showResult;
	
	//오른쪽 패널
	private JPanel North,Center,South;
	private JLabel result;
	
	private JTable table;
	private DefaultTableModel model;
	private String title[] = {"날짜","농도"};
	private JScrollPane scrollpane;
	
	private JLabel unit; //단위
	private JButton showGraph;
	private JFrame areaGraph;
	
	//폰트
	private Font Big = new Font("맑은 고딕", Font.BOLD, 23);
	private Font middle = new Font("맑은 고딕", Font.PLAIN, 17);
	private Font small = new Font("맑은 고딕", Font.BOLD, 13);
	
	//리스너
	Listener Listener = new Listener();
	
	public Area() {
		
		this.setLayout(new GridLayout(1,2));
		Left();
		Right();
	}
	
	private void Left() {
		
		// 레이아웃 나누기 
		left = new JPanel (new GridLayout(5,1));
		p1 = new JPanel();
		p2 = new JPanel();
		p3 = new JPanel();
		p4 = new JPanel (new FlowLayout (FlowLayout.CENTER,10,0));
		p5 = new JPanel();
		
		// 배경색
		p1.setBackground(new Color(250,250,250));
		p2.setBackground(new Color(250,250,250));
		p3.setBackground(new Color(250,250,250));
		p4.setBackground(new Color(250,250,250));
		p5.setBackground(new Color(250,250,250));

		
		//레이블
		mainText = new JLabel ("<html><br>지역별 농도 조회입니다");
		area_Select = new JLabel ("지역 선택 : ");
		gas_Select = new JLabel ("오염물질 선택 :");
		date_Select = new JLabel ("날짜 선택 : 2018년");
		text = new JLabel ("부터 일주일");
		
		//레이블 폰트 적용
		mainText.setFont(Big);
		area_Select.setFont(middle);
		gas_Select.setFont(middle);
		date_Select.setFont(middle);
		text.setFont(middle);
		
		//콤보박스
		areaCombo = new JComboBox<String>(Area);
		gasCombo = new JComboBox<String>(Gas);
		monthCombo = new JComboBox<String>(Month);
		dateCombo = new JComboBox<String>(Day31); //기본 1월 선택
				
		//배경색
		areaCombo.setBackground(Color.white);
		gasCombo.setBackground(Color.white);
		monthCombo.setBackground(Color.white);
		dateCombo.setBackground(Color.white);
		
		//월마다 다른 일수 콤보박스 넣어주기
		monthCombo.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				p4.remove(dateCombo);
				p4.remove(text);
				
				switch((String)e.getItem()) {
				
				case "1월": case "3월": case "5월": case "7월": 
				case "8월": case "10월": case "12월": //31일짜리
					dateCombo = new JComboBox<String>(Day31);
					p4.add(dateCombo); 
					break;
				case "4월": case "6월": case "9월": case "11월": //30일짜리
					dateCombo = new JComboBox<String>(Day30);
					p4.add(dateCombo);
					break;
				case "2월": //28일짜리
					dateCombo = new JComboBox<String>(Day28);
					p4.add(dateCombo);
					break;
				default:
					System.out.println("월 선택 이상");
				}
				dateCombo.setBackground(Color.white);
				p4.add(text);
				revalidate(); //변경사항 바로 보기
			}
		});
		
		//버튼
		showResult = new JButton("결과 보기");
		showResult.setFont(middle);
		showResult.setBackground(new Color(242,242,242));
		
		//리스너 달기
		showResult.addActionListener(Listener);
		
		//패널에 추가 
		p1.add(mainText);
		p2.add(area_Select);
		p2.add(areaCombo);
		
		p3.add(gas_Select);
		p3.add(gasCombo);

		p4.add(date_Select);
		p4.add(monthCombo);
		p4.add(dateCombo);
		p4.add(text);
		
		p5.add(showResult);	
		
		left.add(p1);
		left.add(p2);
		left.add(p3);
		left.add(p4);
		left.add(p5);
		
		add(left);
	}

	private void Right() {
		
		right = new JPanel(new GridLayout(4,1));
		right.setBackground(Color.white);
		North = new JPanel();
		Center = new JPanel();
		South = new JPanel();
		North.setBackground(Color.white);
		Center.setBackground(Color.white);
		South.setBackground(Color.white);
		
		//오른쪽 상위 라벨 기본 설정
		result = new JLabel ("<html><br><br><br>선택한 결과 입니다 .<br>");
		result.setHorizontalAlignment(JLabel.CENTER);
		result.setFont(middle);

		//테이블
		model = new DefaultTableModel(title,0);
		table = new JTable(model);
		for(int i = 0; i < 7; i++) {
			model = (DefaultTableModel) table.getModel();
			model.addRow(new String[]{"","","","",""});
		}
		
		scrollpane = new JScrollPane(table);
		scrollpane.setPreferredSize(new Dimension(400,135));
		
		//단위
		unit = new JLabel("<html><center>-단위-</center><br>이산화질소, 오존, 일산화탄소, 아황산가스 : ppm<br>미세먼지, 초미세먼지 : (㎍/㎥)<br><br><br></html>");
		unit.setHorizontalAlignment(JLabel.CENTER);
		unit.setBackground(Color.white);
		unit.setFont(small);
		
		//버튼
		showGraph = new JButton ("그래프보기");
		showGraph.setFont(middle);
		showGraph.setBackground(new Color(242,242,242));
		
		//리스너 달기
		showGraph.addActionListener(Listener);
		
		//패널추가
		North.add(result);
		Center.add(scrollpane);
		South.add(showGraph);
		
		right.add(North);
		right.add(Center);
		right.add(unit);
		right.add(South);
		
		add(right);
	}
	private String changeEnglish(String korean) {
		switch(korean) {
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
	private String getEndDate(String month, String day) {
		int startMonth = Integer.parseInt(month);
		int startDay = Integer.parseInt(day);
		
		switch(startMonth) {
			case 1: case 3: case 5: case 7: case 8:	case 10://31일짜리
				if(startDay>25) { //26일부터
					return "2018-"+(startMonth+1)+"-"+(startDay-25);
				}else {
					return "2018-"+startMonth+"-"+(startDay+6);
				}
			case 12: //12월 31일까지의 데이터만 있으므로
				if(startDay>25) { //26일부터여도 12/31까지만 보여준다.
					return "2019-1-"+(startDay-25);
				}else {
					return "2018-"+startMonth+"-"+(startDay+6);
				}
			case 4: case 6: case 9: case 11://30일짜리
				if(startDay>24) { //25일부터
					return "2018-"+(startMonth+1)+"-"+(startDay-24);
				}else {
					return "2018-"+startMonth+"-"+(startDay+6);
				}
			case 2:	//28일짜리
				if(startDay>22) { //23일부터
					return "2018-"+(startMonth+1)+"-"+(startDay-22);
				}else {
					return "2018-"+startMonth+"-"+(startDay+6);
				}
			default:
				System.out.println("이상한 날짜");
				return null;
		}
	}
	private void setTable(String area, String pollution, String startDate,String endDate) {
		
		try {
			System.out.println("시작 날짜: "+startDate);
			
			//System.out.println("끝 날짜: "+endDate);
			
			//1월 넘어가는거 나오면 표시는 하되 데이터가 없습니다. 하고싶은뎅,,,, - 기준으로 읽어서 앞이 2019인 경우는 null로하도록하자
			conn = DB.makeConnection();
			
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT date, "+pollution);
			sql.append(" FROM degree WHERE area = ?");
			sql.append(" AND date between ? and ? ORDER BY date ASC");
			
			ResultSet rs;
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1,area);
			pstmt.setString(2,startDate);
			pstmt.setString(3,endDate);
			
			StringBuilder sql2 = new StringBuilder();
			sql2.append("SELECT "+pollution);
			sql2.append(" FROM degree WHERE area = ? AND date = ?");
			
			//실행결과 저장
			rs = pstmt.executeQuery();
			rs.beforeFirst();
			
			//테이블 값 설정
			model = (DefaultTableModel) table.getModel(); //테이블 설정 전에 초기화시키기
			model.setNumRows(0);
			
			String temp[] = new String[2];
			int count = 0;
			
			Calendar cal= Calendar.getInstance();
			String[] date = startDate.trim().split("-");
			
			switch(date[1]) { //월
			case "10": case "11": case "12":
				break;
				default:
					date[1] = "0"+date[1];
			}
			//System.out.println(date[1]);
			switch(date[2]) { //일
			case "1":case "2":case "3":case "4":case "5":case "6":case "7":case "8":case "9":
				date[2] = "0"+date[2];
				break;
				default:
			}
			//System.out.println(date[2]);
			String calstart = date[0]+"-"+date[1]+"-"+date[2];
			//System.out.println(calstart);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Date start = format.parse(calstart);
				cal.setTime(start);
			}catch(Exception e) {
				System.out.println("오류 :"+e);
			}
			while(rs.next()) {
				//캘린더 포맷을 설정하고 캘린더를 이용해 일주일 날짜를 먼저 입력한다.
				temp[0] = format.format(cal.getTime()); //set
				temp[1] = rs.getString(pollution);
				
				pstmt = conn.prepareStatement(sql2.toString());
				pstmt.setString(1,area);
				pstmt.setString(2,temp[0]);
				ResultSet rs2 = pstmt.executeQuery();
				if(!rs2.isBeforeFirst()) { //날짜가 존재하지 않아 아예 일거오지 않은 시작, 중간 부분들
					temp[1] = "";
					rs.previous();
				}
				
				//날짜가 없는건 아예 읽지를 않는다....
				model = (DefaultTableModel) table.getModel();
				model.addRow(temp);
				
				cal.add(Calendar.DATE, 1); //하루 늘려주기
				count++;
			}
			
			if(count < 7) {//날짜가 존재하지않아 아예 읽어오지 않은 마지막 부분들
				for(int i = 0; i < 7-count; i++) {
					//빈만큼 비었다고 알려준다.
					//temp[0] = "데이터가 없습니다.";
					temp[0] = format.format(cal.getTime());
					temp[1] = "";
				
					cal.add(Calendar.DATE, 1); //하루 늘려주기
					model = (DefaultTableModel) table.getModel();
					model.addRow(temp);
				}
			}
			
			pstmt.close();
			conn.close();
		}catch(SQLException e) {
			System.out.println("오류:"+e);
		}
	
	}
	private class Listener implements ActionListener{
		private String myGas;
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == showResult) { //결과보기 버튼을 눌렀을 경우
				System.out.println("결과보기 버튼 클릭");
				//사용자의 선택 : 지역, 기체, 시작날짜
				//지역, 기체 시작 모두 하나만 선택 가능하다.
				String selectArea = ""; 
				String selectGas = ""; 
				String selectMonth = "";
				String selectDay = ""; 
				
				String temp = ""; //오른쪽 라벨 설명
				//선택한 정보를 읽는다.
				selectArea = (String) areaCombo.getSelectedItem();
				selectGas = (String) gasCombo.getSelectedItem();
				selectMonth = (String) monthCombo.getSelectedItem();
				selectDay = (String) dateCombo.getSelectedItem();
				
				temp += "<html><br><br><br>다음은 ";
				temp += selectArea+"의 "+selectGas+"에 대한 <br>2018년 "+selectMonth+" "+selectDay+"부터 일주일간의 농도 입니다.";

				//오른쪽 라벨에 저장해준다.
				result.setText(temp);
				
				//데이터베이스의 열 이름과 같게 만들어준다.
				selectGas = changeEnglish(selectGas);
				
				//시작날짜와 끝 날짜를 구한다.
				String startDate = "2018-"+selectMonth.replaceAll("[^0-9]","")+"-"+selectDay.replaceAll("[^0-9]","");
				String endDate = getEndDate(selectMonth.replaceAll("[^0-9]",""),selectDay.replaceAll("[^0-9]",""));
				//setTable함수를 만들어서 내 선택사항을 보낸다.
				setTable(selectArea,selectGas,startDate,endDate);
				
				myGas = selectGas;
			}else if(e.getSource() == showGraph) {
				System.out.println("막대그래프로 보기 클릭");	
				if(table.getValueAt(0, 1) == "") {
					JOptionPane.showMessageDialog(null,"조회 할 데이터가 없습니다!");
				}else {
					//지역별 그래프 x축 : 시작날짜와 그 뒤 일주일까지(무조건 7개)
					//지역별 그래프 y축 : 기체 농도
					
					String[] x = new String[7];
					String[] data = new String[7];
					
					//x축 가져오기
					for(int i = 0; i < 7; i++) {
						x[i] = (String)table.getValueAt(i, 0);
					}
					
					//데이터 가져오기
					for(int i = 0; i < 7; i++) {
						data[i] = (String)table.getValueAt(i, 1);
					}
					
					/*
					 * myGas =>데이터베이서 열 이름과 같게 설정
					 * 이산화질소	: nitrogen
					 * 일산화탄소	: carbon
					 * 오존		: ozone
					 * 아황산가스	: sulfur
					 * 미세먼지	: fine_dust
					 * 초미세먼지	: ultrafine_dust
					 * */
					
					
					areaGraph = new areaGraph(x,data,myGas);
					areaGraph.setVisible(true);
				}
			}
		}
	}
}
