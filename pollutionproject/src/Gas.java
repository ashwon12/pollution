import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
	
	private JTable table;
	private DefaultTableModel model;
	private String title[] = {"날짜","지역","농도","아이콘","경보알림"};
	private JScrollPane scrollpane;
	
	private JLabel unit;
	private JButton showGraph;
	
	//폰트
	private Font Big = new Font("맑은 고딕", Font.BOLD, 23);
	private Font middle = new Font("맑은 고딕", Font.PLAIN, 17);
	private Font small = new Font("맑은 고딕", Font.BOLD, 13);
	
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
		button_gas[1] = new JRadioButton("이산화탄소");
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
		showResult = new JButton("결과 보기 ");
		showResult.setFont(middle);
		showResult.setBackground(new Color(242,242,242));
            
		//리스너 호출
		LeftListener Listener = new LeftListener();
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
		model = new DefaultTableModel(title,0);
		table = new JTable(model);
		for(int i = 0; i < 25; i++) {
			model = (DefaultTableModel) table.getModel();
			model.addRow(new String[]{"","","","",""});
		}
		table.setRowHeight(25);
		scrollpane = new JScrollPane(table);
		scrollpane.setPreferredSize(new Dimension(400,150));
		
		//단위
		unit = new JLabel("<html><center>-단위-</center><br>이산화질소, 오존, 이산화탄소, 아황산가스 : ppm<br>미세먼지, 초미세먼지 : (㎍/㎥)<br><br><br></html>");
		unit.setHorizontalAlignment(JLabel.CENTER);
		unit.setBackground(Color.white);
		unit.setFont(small);
		
		//버튼
		showGraph = new JButton ("그래프보기");
		showGraph.setFont(middle);
		showGraph.setBackground(new Color(242,242,242));
		
		//리스너 호출
		RightListener Listener = new RightListener();
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
		case "이산화탄소":
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
			System.out.println("선택 기체: "+selectGas);
			conn = DB.makeConnection();
			
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT date, area, "+selectGas);
			sql.append(" FROM degree ORDER BY CAST("+selectGas);
			sql.append(" as DOUBLE) DESC LIMIT 100");
			
			ResultSet rs;
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			
			//실행결과 저장
			rs = pstmt.executeQuery();
			rs.beforeFirst();
			
			/*
			 *	private String title[] = {"날짜","지역","농도","아이콘","경보알림"};
			*/
			//테이블 값 설정
			
			model = (DefaultTableModel) table.getModel(); //테이블 설정 전에 초기화시키기
			model.setNumRows(0);
			
			Object temp[] = new Object[5];
			while(rs.next()) { //상위 25개
				temp[0] = rs.getString("date");
				temp[1] = rs.getString("area");
				temp[2] = rs.getString(selectGas);
				temp[3] = "아이콘 들어갈거양";
				temp[4] = "경보들어갈거양";
				
				model = (DefaultTableModel) table.getModel();
				model.addRow(temp);
			}
			
			pstmt.close();
			conn.close();
		}catch(SQLException e) {
			System.out.println("오류:"+e);
		}
	}
	
	private class LeftListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			
			if(e.getSource() == showResult) { //결과보기 버튼을 눌렀을 경우
				System.out.println("결과보기 버튼 클릭");
				//사용자의 선택을 가져와서 데이터베이스에서 정보를 가져온다.
				/*
				 * 기체 : string 라디오버튼으로 하나만 존재
				 */
				String selectGas ="";
				String temp = "<html><br><br><br>다음은 ";
				boolean select = false;
				for(int i = 0; i < 6; i++) {
					if(button_gas[i].isSelected()) {
						selectGas += changeEnglish(button_gas[i].getText());
						temp += button_gas[i].getText();
						select = true;
					}
				}
				temp += " 농도의 내림차순 정렬 상위 100개 입니다.</html>";
				
				//선택한 버튼이 없을 경우 리턴시킨다.
				if(!select) {
					JOptionPane.showMessageDialog(null,"기체를 선택하지 않았습니다!");
					return;
				}
				resultText.setText(temp);
				
				//선택한 가스를 가지고 테이블 데이터를 설정하는 함수 호출
				setTable(selectGas);
			}
		}
	}
	
	private class RightListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == showGraph) {
				if(table.getValueAt(0, 1) == "") {
					JOptionPane.showMessageDialog(null,"조회 할 데이터가 없습니다!");
				}
				System.out.println("막대그래프로 보기 클릭");
			}
		}
	}
	
}
