package pollutionproject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.util.ArrayList;

public class Month extends Average {

	private JPanel left, right; // 왼쪽 : 선택, 오른쪽 : 결과

	// 왼쪽 패널 구성 요소
	private JPanel p1, p2, p3, p4;
	private JLabel label1, label2;// 달을 선택하라는 안내 라벨
	private JCheckBox[] months = new JCheckBox[12];// 체크박스들
	private JComboBox<String> gasCombo;
	private String Gas[] = { "이산화질소", "오존", "일산화탄소", "아황산가스", "미세먼지", "초미세먼지" };
	private JButton result;// 결과 보기 버튼

	// 오른쪽 패널 구성 요소
	private JPanel North, Center, South;
	private JLabel label3;// 어떤 달을 선택했는지 알려주는 라벨
	private static JTable table;
	private String title[] = { "월", "농도" };
	private DefaultTableModel model;
	private JScrollPane scrollpane;

	private JLabel unit;
	private JButton showBarGraph, showLineGraph;// 그래프로 보기 버튼
	private JFrame monthGraph;

	// 폰트
	private Font Big = new Font("맑은 고딕", Font.BOLD, 23);
	private Font middle = new Font("맑은 고딕", Font.PLAIN, 17);
	private Font small = new Font("맑은 고딕", Font.BOLD, 13);

	// 리스너
	Listener Listener = new Listener();

	private static int rowSize;
	private static String myGas;
	private static String gasKor;

	public Month() {
		this.setLayout(new GridLayout(1, 2));
		setLeft();
		setRight();
	}

	private void setLeft() {

		// 레이아웃 나누기
		left = new JPanel(new GridLayout(4, 1));
		p1 = new JPanel();
		p2 = new JPanel(new GridLayout(2, 6));
		p3 = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 60));
		p4 = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 50));

		// 배경색 설정
		p1.setBackground(new Color(250, 250, 250));
		p2.setBackground(new Color(250, 250, 250));
		p3.setBackground(new Color(250, 250, 250));
		p4.setBackground(new Color(250, 250, 250));

		// 레이블 작성
		label1 = new JLabel("<html><br><br><center>선택한 월에 대한 <br> 선택한 오염물질 농도 평균 조회입니다");
		label2 = new JLabel("오염물질 선택 :");
		label1.setFont(Big);
		label2.setFont(middle);

		// 체크박스 설정
		for (int i = 0; i < 12; i++) {
			months[i] = new JCheckBox((i + 1) + "월");
			months[i].setBackground(new Color(250, 250, 250));
			months[i].setFont(middle);
			months[i].setHorizontalAlignment(JCheckBox.CENTER);
			p2.add(months[i]);
		}

		// 콤보박스
		gasCombo = new JComboBox<String>(Gas);
		gasCombo.setBackground(Color.white);

		// 버튼
		result = new JButton("결과보기");
		result.setFont(middle);
		result.setBackground(new Color(242, 242, 242));

		// 리스너 달기
		result.addActionListener(Listener);

		// 패널에 추가
		p1.add(label1);
		p3.add(label2);
		p3.add(gasCombo);
		p4.add(result);

		left.add(p1);
		left.add(p2);
		left.add(p3);
		left.add(p4);

		add(left);
	}

	private void setRight() {

		// 레이아웃 설정
		right = new JPanel(new GridLayout(4, 1));
		right.setBackground(Color.white);
		North = new JPanel();
		Center = new JPanel();
		South = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
		North.setBackground(Color.white);
		Center.setBackground(Color.white);
		South.setBackground(Color.white);

		// 라벨은 액션리스너에서 바꿔준다.
		label3 = new JLabel("<html><br><br><br>선택한 결과 입니다.");
		label3.setFont(middle);

		// 테이블
		model = new DefaultTableModel(title, 0);
		table = new JTable(model);
		for (int i = 0; i < 7; i++) {
			model = (DefaultTableModel) table.getModel();
			model.addRow(new String[] { "", "" });
		}
		scrollpane = new JScrollPane(table);
		scrollpane.setPreferredSize(new Dimension(400, 135));

		// 단위
		unit = new JLabel(
				"<html><center>-단위-</center><br>이산화질소, 오존, 일산화탄소, 아황산가스 : ppm<br>미세먼지, 초미세먼지 : (㎍/㎥)<br><br><br></html>");
		unit.setHorizontalAlignment(JLabel.CENTER);
		unit.setBackground(Color.white);
		unit.setFont(small);

		// 버튼
		showBarGraph = new JButton("막대 그래프보기");
		showBarGraph.setFont(middle);
		showBarGraph.setBackground(new Color(242, 242, 242));

		showLineGraph = new JButton("꺾은선 그래프보기");
		showLineGraph.setFont(middle);
		showLineGraph.setBackground(new Color(242, 242, 242));

		// 리스너 달기
		showBarGraph.addActionListener(Listener);
		showLineGraph.addActionListener(Listener);

		North.add(label3);
		Center.add(scrollpane);
		South.add(showBarGraph);
		South.add(showLineGraph);

		right.add(North);
		right.add(Center);
		right.add(unit);
		right.add(South);

		add(right);
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

	private void setTable(ArrayList<Integer> selectMonth, double[] getMonthAverage) {
		int size = selectMonth.size();
		Object[] temp = new Object[2];
		int count = 0;

		// 테이블 초기화
		model = (DefaultTableModel) table.getModel(); // 테이블 설정 전에 초기화시키기
		model.setNumRows(0);

		// 테이블 값 변경
		for (int i = 0; i < size + 1; i++) {
			if (i == size) {
				temp[0] = "월 전체 평균";
			} else {
				temp[0] = (selectMonth.get(i) + 1) + "월";
			}

			temp[1] = getMonthAverage[i];

			model = (DefaultTableModel) table.getModel();
			model.addRow(temp);
			count++;
		}

		// 선택한 달이 6개 미만인 경우 밑의 테이블에 빈 값을 넣어준다.
		if (count < 7) {
			for (int i = 0; i < 7 - count; i++) {
				temp[0] = "";
				temp[1] = "";
				model = (DefaultTableModel) table.getModel();
				model.addRow(temp);
			}
		}
	}

	private class Listener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == result) { // 결과보기 버튼을 눌렀을 경우

				System.out.println("결과보기 버튼 클릭");

				String temp = "<html><br><br>다음은<br> ";
				boolean first = false;
				ArrayList<Integer> selectMonth = new ArrayList<Integer>(); // 몇월을 선택했는지 알아야한다. ->arrayList
				String selectGas = ""; // 선택한 기체

				for (int i = 0; i < 12; i++) {
					if (months[i].isSelected()) {
						if (!first) { // 첫번째 선택 인 경우
							temp += months[i].getText();
							selectMonth.add(i); // "i+1"월 추가
							first = true;
						} else {
							temp += ", " + months[i].getText();
							selectMonth.add(i);
						}
					} else {
						continue;
					}
				}
				// 내가 선택한 오염물질 종류를 알아야한다.
				gasKor = (String) gasCombo.getSelectedItem();
				selectGas = changeEnglish((String) gasCombo.getSelectedItem());
				temp += "<br>" + (String) gasCombo.getSelectedItem();
				temp += "의 평균 농도입니다.<br><br></html>";

				// 아무 월도 선택하지 않은 경우
				if (selectMonth.size() == 0) {
					JOptionPane.showMessageDialog(null, "월을 선택하지 않았습니다!");
					return;
				}

				label3.setText(temp);

				// 가져와야 하는 것 : 선택한 기체에 대한 각 월 평균 =>여길 통해서 전체 월 평균도 구해준다.
				int size = selectMonth.size();
				double[] getMonthAverage = new double[size + 1]; // 마지막에는 월 총 평균을 넣어준다.

				int count = 0;
				for (int m : selectMonth) {
					getMonthAverage[count] = getAverage(m, selectGas); // 인수: 가져올 월
					count++;
				}

				// 월 총 평균을 넣어준다
				double total = 0;
				for (int i = 0; i < size; i++) {
					total += getMonthAverage[i];
				}
				// System.out.println("total : "+total+" size:"+size);
				getMonthAverage[size] = Math.round((total / size) * 1000) / 1000.0;

				// 테이블값 설정
				setTable(selectMonth, getMonthAverage);
				// 행 크기 설정
				rowSize = size + 1;
				// 오염물질 종류 설정
				myGas = selectGas;

			} else if (e.getSource() == showBarGraph || e.getSource() == showLineGraph) {
				System.out.println("막대그래프로 보기 클릭");
				if (table.getValueAt(0, 1) == "") {
					JOptionPane.showMessageDialog(null, "조회 할 데이터가 없습니다!");
					return;
				} else {
					// 월별 그래프 x축 : 내가 선택한 각 월 배열 (배열 마지막에는 "총 평균"이라는 스트링이 들어감)
					// 데이터 : 내가 선택한 기체의 x축에 맞는 값
					String[] x = new String[rowSize]; // x축
					String[] data = new String[rowSize]; // 데이터

					// x축 값 가져오기
					for (int i = 0; i < rowSize; i++) {
						x[i] = (String) table.getValueAt(i, 0);
					}
					// 데이터 값 가져오기
					for (int i = 0; i < rowSize; i++) {
						data[i] = Double.toString((double) table.getValueAt(i, 1));
					}

					/*
					 * myGas =>데이터베이서 열 이름과 같게 설정 이산화질소 : nitrogen 일산화탄소 : carbon 오존 : ozone 아황산가스 :
					 * sulfur 미세먼지 : fine_dust 초미세먼지 : ultrafine_dust
					 */
					if (e.getSource() == showBarGraph) {
						monthGraph = new MonthGraph(x, data, myGas, "Bar");
						monthGraph.setVisible(true);
					} else if (e.getSource() == showLineGraph) {
						monthGraph = new MonthGraph(x, data, myGas, "Line");
						monthGraph.setVisible(true);
					}
				}
			}
		}
	}

	// 저장버튼 리스너 만들기
	public static ActionListener monthSave = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			System.out.println("월별의 저장");
			if (table.getValueAt(0, 1) == "") {
				JOptionPane.showMessageDialog(null, "저장 할 데이터가 없습니다!");
				return;
			}

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
				System.out.println(savepathname);
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
			ArrayList<String> list = new ArrayList<String>();
			// 내용 저장

			// 데이터 값 가져오기
			for (int i = 0; i < rowSize; i++) {
				list.add((String) table.getValueAt(i, 0));
				list.add(Double.toString((double) table.getValueAt(i, 1)));
			}

			// 파일 작성
			try {

				BufferedWriter fw = new BufferedWriter(
						new OutputStreamWriter(new FileOutputStream(savepathname), "EUC_KR"));
				// 첫줄 작성
				fw.write("월" + ",");
				fw.write(addUnit(gasKor));
				fw.newLine();
				int count = 0;
				for (String dom : list) {
					if (count == 1) {
						// 1열이 채워지면 2열이 채워질거임 그냥 넣기 쉼표없이
						fw.write(dom);
						// 이제 다 채워졌으니 새로운 열로 넘어가야함
						fw.newLine();
						// 이제 count를 초기화 시켜줌으로써 여기 이프문에 안들어오고 밑에껄로 들어가게함
						count = 0;
					} else {
						fw.write(dom + ","); // 첫 열 추가
						count++; // 두번째 열로 가야지 그리고 count는 1이야 지금
					}
				}

				fw.flush();
				fw.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};
}
