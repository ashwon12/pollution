import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Season extends Average{
	
	private JPanel left, right; //왼쪽 : 선택, 오른쪽 : 결과
	
	//왼쪽 패널 구성 요소
	private JPanel p1,p2,p3;
	private JLabel label1;//달을 선택하라는 안내 라벨
	private JCheckBox[] season = new JCheckBox[4];
	private JButton showResult;//결과 보기 버튼
	
	//오른쪽패널구성
	private JPanel North,Center,South;
	private JLabel label2;
	private JTable table;
	private String header[] = {"오염물질","평균"};
	private String contents[][] = {
			{"이산화질소",""},
			{"오존 ",""},
			{"이산화탄소",""},
			{"아황산가스",""},
			{"미세먼지",""},
			{"초미세먼지",""}
			};
	private JScrollPane scrollpane;
	private JLabel unit;
	private JButton showGraph;
	
	//폰트
	private Font Big = new Font("맑은 고딕", Font.BOLD, 23);
	private Font middle = new Font("맑은 고딕", Font.PLAIN, 17);
	private Font small = new Font("맑은 고딕", Font.BOLD, 13);
	
	public Season() {
		this.setLayout(new GridLayout(1,2));
		Left();
		Right();
	}
	
	private void Left() {
		
		//레이아웃 나누기 
		left = new JPanel (new GridLayout(3,1));
		p1 = new JPanel(); 
		p2 = new JPanel(new GridLayout(2,2));
		p3 = new JPanel();
		
		//배경색 설정
		p1.setBackground(new Color(250,250,250));
		p3.setBackground(new Color(250,250,250));
		
		//레이블 작성
		label1 = new JLabel("<html><br><br><center>선택한 계절에 대한 <br> 기체 농도 평균 조회입니다");
		label1.setFont(Big);
		
		//체크박스 설정
		season[0] = new JCheckBox("봄");
		season[1] = new JCheckBox("여름");
		season[2] = new JCheckBox("가을");
		season[3] = new JCheckBox("겨울");
		
		for(int i=0; i<4; i++) {
			season[i].setBackground(new Color(250,250,250));
			season[i].setFont(middle);
			season[i].setHorizontalAlignment(JCheckBox.CENTER);
			p2.add(season[i]);
		}
		
		//버튼
		showResult = new JButton("결과보기");
		showResult.setFont(middle);
		showResult.setBackground(new Color(242,242,242));
		
		//리스너 호출
		LeftListener Listener = new LeftListener();
		showResult.addActionListener(Listener);
		
		//패널추가
		p1.add(label1);
		p3.add(showResult);
     
        left.add(p1);
        left.add(p2);
        left.add(p3);
		
		add(left);
	}
	
	private void Right() {
		//레이아웃 설정
			right = new JPanel(new GridLayout(4,1));
			right.setBackground(Color.white);
			North = new JPanel();
			Center = new JPanel();
			South = new JPanel();
			North.setBackground(Color.white);
			Center.setBackground(Color.white);
			South.setBackground(Color.white);
			
			//라벨은 액션리스너에서 바꿔준다.
			label2 = new JLabel("<html><br><br><br>선택한 결과 입니다.");
			label2.setFont(middle);
			
			//테이블
			table = new JTable(contents, header); //contents에서 내용을 가지고 있으면 된다.
			scrollpane = new JScrollPane(table);
			scrollpane.setPreferredSize(new Dimension(400,119));
			
			//단위
			unit = new JLabel("<html><center>-단위-</center><br>이산화질소, 오존, 이산화탄소, 아황산가스 : ppm<br>미세먼지, 초미세먼지 : (㎍/㎥)<br><br><br></html>");
			unit.setHorizontalAlignment(JLabel.CENTER);
			unit.setBackground(Color.white);
			unit.setFont(small);
			
			//버튼
			showGraph = new JButton("그래프 보기");
			showGraph.setFont(middle);
			showGraph.setBackground(new Color(242,242,242));
			
			//리스너 호출
			RightListener Listener = new RightListener();
			showGraph.addActionListener(Listener);
			
			North.add(label2);
			Center.add(scrollpane);
			South.add(showGraph);
			right.add(North);
			right.add(Center);
			right.add(unit);
			right.add(South);
			
			add(right);
	}
	
	private class LeftListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == showResult) { //결과보기 버튼을 눌렀을 경우
				//아무 계절도 선택하지 않은 경우 경고문과 함께 return시키는 예외 추가하기
				System.out.println("결과보기 버튼 클릭");
				String temp = "<html><br><br><br>다음은 ";
				boolean first = false;
				ArrayList<String> selectSeason = new ArrayList<String>(); //몇월을 선택했는지 알아야한다. ->arrayList
				
				for(int i = 0; i < 4; i++) {
					if(season[i].isSelected()) {
						if(!first) { //첫번째 선택 인 경우
							temp += season[i].getText();
							selectSeason.add(season[i].getText()); //계절추가
							first = true;
						}else {
							temp += ", "+season[i].getText();
							selectSeason.add(season[i].getText()); 
						}
					}else {
						continue;
					}
				}
				temp+="의 평균 농도입니다.<br><br></html>";
					
				//아무 계절도 선택하지 않은 경우
				if(selectSeason.size() == 0) {
					JOptionPane.showMessageDialog(null,"계절을 선택하지 않았습니다!");
					return;
				}
					
				label2.setText(temp);
					
				double[] gasAverage = new double[6];
				/* 
				 * 이산화질소 0
				 * 오존 1
				 * 이산화탄소 2
				 * 아황산가스 3
				 * 미세먼지 4
				 * 초미세먼지 5
				 */
				//초기화
				for(int i = 0; i < 6; i++) {
					gasAverage[i] = 0;
				}
				int total = 0;
				
				//선택한 계절의 월별 각 기체 평균을 구한다.
				for(String s: selectSeason) {
					System.out.println(s+"선택");
					switch(s) {
					case "봄":
						//각 기체의 3,4,5월 평균을 가져온다
						for(int m = 2; m < 5; m++) {
							System.out.println((m+1)+"월 평균");
							gasAverage[0] += getAverage(m,"nitrogen"); //인수: 가져올 월
							gasAverage[1] += getAverage(m,"ozone");
							gasAverage[2] += getAverage(m,"carbon");
							gasAverage[3] += getAverage(m,"sulfur");
							gasAverage[4] += getAverage(m,"fine_dust");
							gasAverage[5] += getAverage(m,"ultrafine_dust");
						}
						break;
					case "여름":
						//6,7,8월 평균을 가져온다
						for(int m = 5; m < 8; m++) {
							System.out.println((m+1)+"월 평균");
							gasAverage[0] += getAverage(m,"nitrogen"); //인수: 가져올 월
							gasAverage[1] += getAverage(m,"ozone");
							gasAverage[2] += getAverage(m,"carbon");
							gasAverage[3] += getAverage(m,"sulfur");
							gasAverage[4] += getAverage(m,"fine_dust");
							gasAverage[5] += getAverage(m,"ultrafine_dust");
						}
						break;
					case "가을":
						//9,10,11월 평균을 가져온다
						for(int m = 8; m < 11; m++) {
							System.out.println((m+1)+"월 평균");
							gasAverage[0] += getAverage(m,"nitrogen"); //인수: 가져올 월
							gasAverage[1] += getAverage(m,"ozone");
							gasAverage[2] += getAverage(m,"carbon");
							gasAverage[3] += getAverage(m,"sulfur");
							gasAverage[4] += getAverage(m,"fine_dust");
							gasAverage[5] += getAverage(m,"ultrafine_dust");
						}
						break;
					case "겨울":
						//12,1,2월 평균을 가져온다
						for(int m = 11; m < 14; m++) {
							if(m == 11) {
								//m이 11인 경우 (12월)
								System.out.println((m+1)+"월 평균");
								gasAverage[0] += getAverage(m,"nitrogen"); //인수: 가져올 월
								gasAverage[1] += getAverage(m,"ozone");
								gasAverage[2] += getAverage(m,"carbon");
								gasAverage[3] += getAverage(m,"sulfur");
								gasAverage[4] += getAverage(m,"fine_dust");
								gasAverage[5] += getAverage(m,"ultrafine_dust");
							}else {
								//m이 12,13인 경우 (1월 2월)
								System.out.println((m-11)+"월 평균");
								gasAverage[0] += getAverage(m-12,"nitrogen"); //인수: 가져올 월
								gasAverage[1] += getAverage(m-12,"ozone");
								gasAverage[2] += getAverage(m-12,"carbon");
								gasAverage[3] += getAverage(m-12,"sulfur");
								gasAverage[4] += getAverage(m-12,"fine_dust");
								gasAverage[5] += getAverage(m-12,"ultrafine_dust");
							}
						}
						break;
					default:
							
					}
					total += 3;
				}
				System.out.println("total: "+total);
				//평균 계산
				for(int i = 0; i < 6; i++) {
					gasAverage[i] = Math.round((gasAverage[i]/total)*1000)/1000.0;
				}
				//테이블 값 변경
				for(int i = 0; i < 6; i++) {
					table.setValueAt(Double.toString(gasAverage[i]), i, 1);
				}
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




















