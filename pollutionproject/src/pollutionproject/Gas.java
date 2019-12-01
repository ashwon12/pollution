package pollutionproject;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;

public class Gas extends JPanel {
	
	private Connection conn;
	private JPanel left, right;
	
	//왼쪽 패널
	private JPanel p1,p2,p2_1,p2_2,p2_3,p3;
	private JLabel mainText;
	private JRadioButton[] button_gas = new JRadioButton[6];
	private ButtonGroup group_gas;
	private JButton showResult;
	
	//오른쪽 패널
	private JPanel North,Center,South;
	private JLabel resultText;
	
	private static JTable table;
	private DefaultTableModel model;
	private String title[] = {"날짜","지역","농도","상태","아이콘"};
	private JScrollPane scrollpane;
	
	private JLabel unit;
	private JButton showGraph;
	private JFrame gasGraph;
	
	//폰트
	private Font Big = new Font("맑은 고딕", Font.BOLD, 23);
	private Font middle = new Font("맑은 고딕", Font.PLAIN, 17);
	private Font small = new Font("맑은 고딕", Font.BOLD, 13);
	
	//아이콘 추가
	private Icon i1 = new ImageIcon("./images/1.png");//좋음
	private Icon i2 = new ImageIcon("./images/2.png");//보통
	private Icon i3 = new ImageIcon("./images/3.png");//나쁨
	private Icon i4 = new ImageIcon("./images/4.png");//매우나쁨
	
	//리스너 추가
	Listener Listener = new Listener();
	private static String myGas;
	private static String gasKor;
	
	public Gas() {
		this.setLayout(new GridLayout(1,2));
		Left();
		Right();	
	}
		
	private void Left() {
        
		//레이아웃 나누기
		left = new JPanel (new GridLayout(3,1));
		p1 = new JPanel(); 
		p2 = new JPanel(new GridLayout(3,1));
		p2_1 = new JPanel(new FlowLayout (FlowLayout.CENTER,10,0));
		p2_2 = new JPanel(new FlowLayout (FlowLayout.CENTER,10,0));
		p2_3 = new JPanel(new FlowLayout (FlowLayout.CENTER,10,0));
		p3 = new JPanel();
		
		//배경색 설정
		p1.setBackground(new Color(250,250,250));
		p2_1.setBackground(new Color(250,250,250));
		p2_2.setBackground(new Color(250,250,250));
		p2_3.setBackground(new Color(250,250,250));
		p3.setBackground(new Color(250,250,250));
		
        //레이블
        mainText = new JLabel("<html><br><br><center>오염물질 종류에 따른 <br>내림차순 조회입니다");
        mainText.setFont(Big);

        //라디오버튼 설정
        group_gas = new ButtonGroup();
        
        button_gas[0] = new JRadioButton("이산화질소");
        button_gas[0].setHorizontalAlignment(JRadioButton.LEFT);
		button_gas[1] = new JRadioButton("일산화탄소");
		button_gas[2] = new JRadioButton("아황산가스");
		button_gas[3] = new JRadioButton("미세먼지");
		button_gas[4] = new JRadioButton("초미세먼지");
		button_gas[5] = new JRadioButton("오존");
		
		//라디오버튼 배경색
		for(int i = 0; i < 6; i++) {
			button_gas[i].setBackground(new Color(250,250,250)); //배경색
			button_gas[i].setFont(middle);//폰트설정
			group_gas.add(button_gas[i]); //그룹화
		}
	
		//버튼
		showResult = new JButton("결과 보기");
		showResult.setFont(middle);
		showResult.setBackground(new Color(242,242,242));
            
		//리스너 달기
		showResult.addActionListener(Listener);
        
		//패널추가
        p1.add(mainText);
        for(int i = 0; i < 6; i++) {
        	switch(i) {
        	case 0: case 1:
        		p2_1.add(button_gas[i]);
        		break;
        	case 2: case 3:
        		p2_2.add(button_gas[i]);
        		break;
        	case 4: case 5:
        		p2_3.add(button_gas[i]);
        		break;
        	default:	
        	}
        }
        p2.add(p2_1);
        p2.add(p2_2);
        p2.add(p2_3);
        p3.add(showResult);
        
        left.add(p1);
        left.add(p2);
        left.add(p3);

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
		
		//기체 선택 후 결과보기 버튼 클릭 시 뭘 선택했는지 알려주는 라벨로 바뀐다.
		resultText = new JLabel ("<html><br><br><br>선택한 결과 입니다 ");
		resultText.setHorizontalAlignment(JLabel.CENTER);
		resultText.setFont(middle);
		
		//테이블
		model =  new DefaultTableModel(null, title) {
			public Class getColumnClass(int column) {
				return getValueAt(0, column).getClass();
			}
		};
		table = new JTable(model);
		for(int i = 0; i < 25; i++) {
			model = (DefaultTableModel) table.getModel();
			model.addRow(new String[]{"","","","",""});
		}
		table.setRowHeight(28);
		scrollpane = new JScrollPane(table);
		scrollpane.setPreferredSize(new Dimension(400,150));
		
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
		North.add(resultText);
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
	private void setTable(String selectGas) {
		
		try {
			//System.out.println("선택 오염물질: "+selectGas);
			conn = DB.makeConnection();
			
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT date, area, "+selectGas);
			sql.append(" FROM degree ORDER BY CAST("+selectGas);
			sql.append(" as DOUBLE) DESC LIMIT 500");
			
			ResultSet rs;
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			
			//실행결과 저장
			rs = pstmt.executeQuery();
			rs.beforeFirst();
			
			
			//테이블 값 설정
			
			model = (DefaultTableModel) table.getModel(); //테이블 설정 전에 초기화시키기
			model.setNumRows(0);
			
			Object temp[] = new Object[5];
			while(rs.next()) { //상위 20개
				temp[0] = rs.getString("date");
				temp[1] = rs.getString("area");
				temp[2] = rs.getString(selectGas);
				if(selectGas == "ultrafine_dust") {
					temp[2] = rs.getString(selectGas).replaceAll("[^0-9]","");
				}
				Object[] t = setData(temp[2],selectGas);
				//오염물질 종류와 값을 판단해 텍스트와 아이콘을 설정해준다.
				temp[3] = t[0];
				temp[4] = t[1];
				
				model = (DefaultTableModel) table.getModel();
				model.addRow(temp);
			}
			
			pstmt.close();
			conn.close();
		}catch(SQLException e) {
			System.out.println("오류:"+e);
		}
	}
	//기체 종류와 값을 판단해 아이콘을 설정해준다.
	private Object[] setData(Object t_data,String selectGas) {
		double data = Double.parseDouble((String)t_data);
		Object[][] r = {
				{"좋음", i1},
				{"보통", i2},
				{"나쁨",i3},
				{"매우나쁨",i4}
		};
		switch(selectGas) {
		case "nitrogen":
			if(data <= 0.030) {
				return r[0];
			}else if(data <= 0.60) {
				return r[1];
			}else if(data <= 0.200) {
				return r[2];
			}else if(data > 0.200) {
				return r[3];
			}else {
				return null;
			}
		case "carbon":
			if(data <= 2.0) {
				return r[0];
			}else if(data <= 9.0) {
				return r[1];
			}else if(data <= 15.0) {
				return r[2];
			}else if(data > 15.0) {
				return r[3];
			}else {
				return null;
			}
		case "ozone":
			if(data <= 0.030) {
				return r[0];
			}else if(data <= 0.090) {
				return r[1];
			}else if(data <= 0.150) {
				return r[2];
			}else if(data > 0.150) {
				return r[3];
			}else {
				return null;
			}
		case "sulfur":
			if(data <= 0.020) {
				return r[0];
			}else if(data <= 0.050) {
				return r[1];
			}else if(data <= 0.150) {
				return r[2];
			}else if(data > 0.150) {
				return r[3];
			}else {
				return null;
			}
		case "fine_dust":
			if(data <= 30) {
				return r[0];
			}else if(data <= 80) {
				return r[1];
			}else if(data <= 150) {
				return r[2];
			}else if(data > 150) {
				return r[3];
			}else {
				return null;
			}
		case "ultrafine_dust":
			if(data <= 15) {
				return r[0];
			}else if(data <= 35) {
				return r[1];
			}else if(data <= 75) {
				return r[2];
			}else if(data > 75) {
				return r[3];
			}else {
				return null;
			}
		default:
			return null;
		}
	}
	private class Listener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			
			if(e.getSource() == showResult) { //결과보기 버튼을 눌렀을 경우
				System.out.println("결과보기 버튼 클릭");
				//사용자의 선택을 가져와서 데이터베이스에서 정보를 가져온다.
				/*
				 * 기체 : string 라디오버튼으로 하나만 존재
				 */
				String selectGas = null;
				String temp = "<html><br><br><br>다음은 ";
				boolean select = false;
				for(int i = 0; i < 6; i++) {
					if(button_gas[i].isSelected()) {
						gasKor = button_gas[i].getText();
						selectGas = changeEnglish(button_gas[i].getText());
						temp += button_gas[i].getText();
						select = true;
						break;
					}
				}
				temp += " 농도의 내림차순 정렬 상위 500개 입니다. <br>그래프는 상위 20개까지 볼 수 있습니다.</html>";
				
				//선택한 버튼이 없을 경우 리턴시킨다.
				if(!select) {
					JOptionPane.showMessageDialog(null,"기체를 선택하지 않았습니다!");
					return;
				}
				resultText.setText(temp);
				
				//선택한 가스를 가지고 테이블 데이터를 설정하는 함수 호출
				setTable(selectGas);
				
				myGas = selectGas;
			}else if(e.getSource() == showGraph) {
				System.out.println("막대그래프로 보기 클릭");
				if(table.getValueAt(0, 1) == "") {
					JOptionPane.showMessageDialog(null,"조회 할 데이터가 없습니다!");
				}else {
					//기체별 그래프 x축 : 20개 날짜와 그에 해당하는 지역 이름
					String[] date = new String[25];
					String[] area = new String[25];
					String[] data = new String[25];
					String[] val = new String[25];
					
					//x축 가져오기(날짜 + 지역이름)
					/* 스트링 형태 : 
					 * 2018-01-01
					 * 세곡
					 */
					for(int i = 0; i < 25; i++) {
						date[i] = (String)table.getValueAt(i, 0);
						area[i] = (String)table.getValueAt(i, 1);
					}
					
					//데이터 가져오기
					for(int i = 0; i < 25; i++) {
						data[i] = (String)table.getValueAt(i, 2);
					}
					
					for(int i = 0; i < 25; i++) {
						val[i] = (String)table.getValueAt(i, 3);
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
					gasGraph = new GasGraph(date,area,data,val,myGas);
					gasGraph.setVisible(true);
				}
			}
		}
	}
	//저장버튼 리스너 만들기
	public static ActionListener gasSave = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			System.out.println("오염물질의 저장");
			
			if(table.getValueAt(0, 1) == "") {
				JOptionPane.showMessageDialog(null,"저장 할 데이터가 없습니다!");
				return;
			}
			
			
			File savefile;
			String savepathname;
			
			//파일 경로 선택
			JFileChooser chooser = new JFileChooser();// 객체 생성
			chooser.setCurrentDirectory(new File("C:\\")); // 맨처음경로를 C로 함
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // 디렉토리만 선택가능
			
			int re = chooser.showSaveDialog(null);
			
			if (re == JFileChooser.APPROVE_OPTION) { //디렉토리를 선택했으면
				savefile = chooser.getSelectedFile(); //선택된 디렉토리 저장하고
				savepathname = savefile.getAbsolutePath();  //디렉토리결과+파일이름
				System.out.println(savepathname);
			}else{
				JOptionPane.showMessageDialog(null, "경로를 선택하지않았습니다.","경고", JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			ArrayList<String> list = new ArrayList<String>();
			//내용 저장
			//데이터 값 가져오기
			//날짜  지역   선택기체   범위
			for(int i = 0; i < 500; i++) {
				list.add((String)table.getValueAt(i,0));
				list.add((String)table.getValueAt(i,1));
				list.add((String)table.getValueAt(i,2));
				list.add((String)table.getValueAt(i,3));
			}
			
			//파일 작성
			try {

				BufferedWriter fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(savepathname), "EUC_KR"));
				//첫줄 작성
				//날짜  지역   선택기체   범위
				fw.write("날짜"+","); fw.write("지역"+","); fw.write(addUnit(gasKor)+","); fw.write("상태"); fw.newLine();
				
				//여긴 총 4열짜리
				int count = 0;
				for(String dom : list) {
					if(count == 3) {
						//3열이 채워지면 4열이 들어갈거임 쉼표 없이
						fw.write(dom);
						//이제 다 채워졌으니 새로운 열로 넘어가야함
						fw.newLine();
						//이제 count를 초기화 시켜줌으로써 여기 이프문에 안들어오고 밑에껄로 들어가게함
						count = 0;
					}else {
						fw.write(dom+","); //첫 열, 두번째 열, 세번째열 추가
						count++; //1과 2,3 을 거쳐서 카운트 3다음엔 4열이 들어가게 될거임
					}
				}
				
				fw.flush();
				fw.close();
			}catch(Exception e) {
				e.printStackTrace();	
			}
		}
	};
	
	private static String addUnit(String kor) {
		switch(kor) {
		case "이산화질소": case "아황산가스": case "일산화탄소": case "오존":
			return kor+" (ppm)";
		case "미세먼지": case "초미세먼지":
			return kor+" (㎍/㎥)";
			default:
				return null;
		}
	}
}
