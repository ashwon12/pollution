package pollutionproject;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class MonthGraph extends JFrame {

	// 생성자
	Container contentpane;
	private JPanel South;
	private JButton close;
	private String[] x = new String[12];
	private String[] data = new String[12];
	private String myGas;
	private String design;

	// 폰트
	private Font small = new Font("", Font.PLAIN, 15);
	private Font middle = new Font("맑은 고딕", Font.BOLD, 17);

	public MonthGraph(String[] x, String[] data, String myGas, String design) {

		this.x = x;
		this.data = data;
		this.myGas = myGas;
		this.design = design;

		this.setTitle("월별 농도 조회 그래프");
		this.setSize(1200, 600);
		this.setLocation(500, 200);

		// 패널 및 클래스
		contentpane = getContentPane();
		contentpane.setLayout(new BorderLayout());
		South = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 30));

		// 버튼
		close = new JButton("close");
		close.setFont(middle);

		// 색상 지정
		close.setBackground(new Color(242, 242, 242));
		contentpane.setBackground(Color.white);
		South.setBackground(Color.white);

		// 리스너 호출
		Listener listener = new Listener();
		close.addActionListener(listener);

		// 추가
		South.add(close);
		contentpane.add(new drawGraph(), BorderLayout.CENTER);
		contentpane.add(South, BorderLayout.SOUTH);

		this.setVisible(true);
	}

	class drawGraph extends JPanel {
		@Override
		public void paint(Graphics g) {
			super.paint(g);
			Graphics2D g2 = (Graphics2D) g;

			g.clearRect(0, 0, getWidth(), getHeight());

			if (x.length < 10) {
				// if (!myGas.equals("carbon")) {
				g.drawLine(100, 400, 90 * (x.length + 1), 400); // x��
				MonthGraph.this.setSize(120 + 90 * (x.length + 1), 600);
				MonthGraph.this.setLocation(500, 200);
				// }
			} else {
				// if (!myGas.equals("carbon")) {
				g.drawLine(100, 400, 940, 400); // x��
				MonthGraph.this.setSize(1100, 600);
				MonthGraph.this.setLocation(500, 200);
				// }
			}

			// if (!myGas.equals("ultrafine_dust")) {
			// g.drawLine(100, 50, 100, 400); // y��
			// } else {
			g.drawLine(100, 20, 100, 400); // y��
			// }

			/*
			 * myGas =>데이터베이스 열 이름과 같게 설정 이산화질소 : nitrogen 일산화탄소 : carbon 오존 : ozone 아황산가스 :
			 * sulfur 미세먼지 : fine_dust 초미세먼지 : ultrafine_dust
			 */

			int[] r = new int[data.length];
			int[] s = new int[data.length];

			double max = Double.parseDouble(data[0]);
			double min = Double.parseDouble(data[0]);

			int a = 0;

			// 최대값
			for (int i = 0; i < data.length; i++) {
				max = max > Double.parseDouble(data[i]) ? max : Double.parseDouble(data[i]);
			}

			// 최솟값
			for (int i = 0; i < data.length; i++) {
				min = min < Double.parseDouble(data[i]) ? min : Double.parseDouble(data[i]);
			}

			double val = Math.round(((max - min) / 20) * 10000) / 10000.0;
			double graphMin = 0;
			for (int cnt = 64; cnt < 400; cnt = cnt + 16) {
				g.setColor(new Color(189, 189, 189));

				if (x.length < 10) {
					g.drawLine(100, cnt, 90 * (x.length + 1), cnt); // x��
				} else {
					g.drawLine(100, cnt, 940, cnt); // x��
				}

				g.setColor(new Color(0, 0, 0));

				double temp;
				if (max == min) {
					temp = Math.round((max - (a * 0.0001)) * 10000) / 10000.0;
				} else {
					temp = Math.round((max - (a * val)) * 10000) / 10000.0; // y�� ��
				}

				String str1 = Double.toString(temp);
				g.drawString(str1, 55, cnt + 5);
				a++;
				graphMin = temp;
			}
			graphMin -= val;

			// 그래프
			for (int i = 0; i < data.length; i++) {

				if (design.equals("Bar")) {
					double temp = Double.parseDouble(data[i]);
					int blank;
					if (data.length == 2) {
						blank = 21;
					} else {
						temp -= graphMin;
						System.out.println("temp" + temp);
						temp = Math.round(temp / val) * val;
						blank = (int) (temp / val);
					}
					g.setColor(new Color(248, 224, 230));
					g.fillRect(130 + 60 * i, 400 - (blank * 16), 30, blank * 16);

				} else {
					double temp = Double.parseDouble(data[i]);
					int blank;
					if (data.length == 2) {
						blank = 21;
					} else {
						temp -= graphMin;
						temp = Math.round(temp / val) * val;
						blank = (int) (temp / val);
					}
					r[i] = 130 + 60 * i + 14;
					s[i] = 400 - (blank * 16);
				}
			}
			System.out.println("val" + val);
			System.out.println("min" + min);
			System.out.println("max" + max);
			g2.setStroke(new BasicStroke(3));
			g2.setColor(Color.black);
			g2.drawPolyline(r, s, data.length);
			g2.setStroke(new BasicStroke(1));
			setX(g);
		}

		public void setX(Graphics g) {
			g.setColor(new Color(0, 0, 0));
			for (int i = 0; i < x.length; i++) {
				g.setFont(small);
				g.drawString(x[i], 130 + 60 * i, 425);
			}
		}
	}

	private class Listener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == close) { // 결과보기 버튼을 눌렀을 경우
				setVisible(false);
			}
		}
	}
}