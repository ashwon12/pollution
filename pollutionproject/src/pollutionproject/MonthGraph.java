package pollutionproject;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.applet.Applet;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

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
				if (!myGas.equals("carbon")) {
					g.drawLine(100, 400, 90 * (x.length + 1), 400); // x��
				}
			} else {
				if (!myGas.equals("carbon")) {
					g.drawLine(100, 400, 940, 400); // x��
				}
			}

			if (!myGas.equals("ultrafine_dust")) {
				g.drawLine(100, 50, 100, 400); // y��
			} else {
				g.drawLine(100, 20, 100, 400); // y��
			}

			/*
			 * myGas =>데이터베이스 열 이름과 같게 설정 이산화질소 : nitrogen 일산화탄소 : carbon 오존 : ozone 아황산가스 :
			 * sulfur 미세먼지 : fine_dust 초미세먼지 : ultrafine_dust
			 */

			if (!myGas.equals("carbon")) {
				if (x.length > 10) {
					MonthGraph.this.setSize(1100, 600);
					MonthGraph.this.setLocation(500, 200);
				} else {
					MonthGraph.this.setSize(120 + 90 * (x.length + 1), 600);
					MonthGraph.this.setLocation(500, 200);
				}
			}

			int[] r = new int[data.length];
			int[] s = new int[data.length];

			switch (myGas) {
			case "nitrogen":
				// 이산화질소 배경
				int a = 0;

				for (int cnt = 64; cnt < 400; cnt = cnt + 16) {
					g.setColor(new Color(189, 189, 189));
					if (x.length < 10) {
						g.drawLine(100, cnt, 90 * (x.length + 1), cnt); // x��
					} else {
						g.drawLine(100, cnt, 940, cnt); // x��
					}
					g.setColor(new Color(0, 0, 0));

					Double temp = Math.round((0.042 - (a * 0.001)) * 1000) / 1000.0; // y�� ��
					String str1 = Double.toString(temp);

					g.drawString(str1, 60, cnt + 5);
					// System.out.println(str1);
					a++;
				}

				// 그래프
				for (int i = 0; i < data.length; i++) {
					if (design.equals("Bar")) {
						double temp = Double.parseDouble(data[i]);
						temp -= 0.021;
						temp = Math.round(temp / .001) * .001;
						int blank = (int) (temp / 0.001);
						g.setColor(new Color(248, 224, 230));
						g.fillRect(130 + 60 * i, 400 - (blank * 16), 30, blank * 16);
					} else {

						double temp = Double.parseDouble(data[i]);
						temp -= 0.021;
						temp = Math.round(temp / .001) * .001;
						int blank = (int) (temp / 0.001);
						r[i] = 130 + 60 * i + 14;
						s[i] = 400 - (blank * 16);
					}
				}
				g2.setStroke(new BasicStroke(3));
				g2.setColor(Color.black);
				g2.drawPolyline(r, s, data.length);
				g2.setStroke(new BasicStroke(1));
				setX(g);

				break;

			case "ozone":
				// 오존 배경
				int b = 0;
				for (int cnt = 64; cnt < 400; cnt = cnt + 16) {
					g.setColor(new Color(189, 189, 189));
					if (x.length < 10) {
						g.drawLine(100, cnt, 90 * (x.length + 1), cnt); // x��
					} else {
						g.drawLine(100, cnt, 940, cnt); // x��
					}
					g.setColor(new Color(0, 0, 0));

					Double temp = Math.round((0.031 - (b * 0.001)) * 1000) / 1000.0; // y�� ��
					String str1 = Double.toString(temp);

					g.drawString(str1, 60, cnt + 5);
					// System.out.println(str1);
					b++;
				}

				// 그래프
				for (int i = 0; i < data.length; i++) {
					if (design.equals("Bar")) {
						double temp = Double.parseDouble(data[i]);
						temp -= 0.010;
						temp = Math.round(temp / .001) * .001;
						int blank = (int) (temp / 0.001);
						g.setColor(new Color(248, 224, 230));
						g.fillRect(130 + 60 * i, 400 - (blank * 16), 30, blank * 16);
					} else {
						double temp = Double.parseDouble(data[i]);
						temp -= 0.010;
						temp = Math.round(temp / .001) * .001;
						int blank = (int) (temp / 0.001);
						r[i] = 130 + 60 * i + 14;
						s[i] = 400 - (blank * 16);
					}
				}
				setX(g);
				g2.setStroke(new BasicStroke(3));
				g2.setColor(Color.black);
				g2.drawPolyline(r, s, data.length);
				g2.setStroke(new BasicStroke(1));
				break;

			case "carbon":
				// 일산화탄소 배경

				if (x.length >= 10) {
					MonthGraph.this.setSize(1200, 900);
					MonthGraph.this.setLocation(300, 80);
				} else {
					MonthGraph.this.setSize(120 + 90 * (x.length + 1), 850);
					MonthGraph.this.setLocation(300, 80);
				}

				if (x.length < 10) {
					g.drawLine(100, 642, 90 * (x.length + 1), 642); // x��
				} else {
					g.drawLine(100, 642, 940, 642); // x��
				}

				g.drawLine(100, 50, 100, 642); // y��

				int c = 0;
				for (int cnt = 65; cnt < 650; cnt = cnt + 16) {
					g.setColor(new Color(189, 189, 189));
					if (x.length < 10) {
						g.drawLine(100, cnt, 90 * (x.length + 1), cnt); // 그래프 내부 라인
					} else {
						g.drawLine(100, cnt, 940, cnt);
					}
					g.setColor(new Color(0, 0, 0));

					Double temp = Math.round((0.73 - (c * 0.01)) * 100) / 100.0; // y축 값
					String str1 = Double.toString(temp);

					g.drawString(str1, 60, cnt + 5);
					// System.out.println(str1);
					c++;
				}

				// 그래프
				for (int i = 0; i < data.length; i++) {
					double temp = Double.parseDouble(data[i]);
					temp -= 0.37;
					temp = Math.round(temp / .001) * .001;
					int blank = (int) (temp / 0.01);

					while (temp > 0) {
						temp -= 0.01;
					}
					temp += 0.01;
					temp *= 1000;
					temp = Math.round(temp / .1) * .1;
					if (design.equals("Bar")) {
						g.setColor(new Color(248, 224, 230));
						if (temp >= 8) {
							g.fillRect(130 + 60 * i, 628 - (blank * 16), 30, 14 + blank * 16);
						} else if (temp >= 4) {
							g.fillRect(130 + 60 * i, 633 - (blank * 16), 30, 9 + blank * 16);
						} else {
							g.fillRect(130 + 60 * i, 638 - (blank * 16), 30, 4 + blank * 16);
						}
					} else {
						if (temp >= 8) {
							r[i] = 130 + 60 * i + 14;
							s[i] = 628 - (blank * 16);
						} else if (temp >= 4) {
							r[i] = 130 + 60 * i + 14;
							s[i] = 633 - (blank * 16);
						} else {
							r[i] = 130 + 60 * i + 14;
							s[i] = 638 - (blank * 16);
						}
					}

				}
				g.setColor(new Color(0, 0, 0));
				for (int i = 0; i < x.length; i++) {
					g.setFont(small);
					g.drawString(x[i], 130 + 60 * i, 660);
				}
				g2.setStroke(new BasicStroke(3));
				g2.setColor(Color.black);
				g2.drawPolyline(r, s, data.length);
				g2.setStroke(new BasicStroke(1));
				break;

			case "sulfur":
				// 아황산가스 배경
				int d = 0;
				for (int cnt = 64; cnt < 400; cnt = cnt + 16) {
					g.setColor(new Color(189, 189, 189));
					if (x.length < 10) {
						g.drawLine(100, cnt, 90 * (x.length + 1), cnt); // x��
					} else {
						g.drawLine(100, cnt, 940, cnt); // x��
					}
					g.setColor(new Color(0, 0, 0));

					Double temp = Math.round((0.021 - (d * 0.001)) * 1000) / 1000.0; // y�� ��
					String str1 = Double.toString(temp);

					g.drawString(str1, 60, cnt + 5);
					// System.out.println(str1);
					d++;
				}
				setX(g);

				// 그래프
				for (int i = 0; i < data.length; i++) {
					if (design.equals("Bar")) {
						double temp = Double.parseDouble(data[i]);
						temp -= 0.0;
						temp = Math.round(temp / .001) * .001;
						int blank = (int) (temp / 0.001);
						g.setColor(new Color(248, 224, 230));
						g.fillRect(130 + 60 * i, 400 - (blank * 16), 30, blank * 16);
					} else {
						double temp = Double.parseDouble(data[i]);
						temp -= 0.0;
						temp = Math.round(temp / .001) * .001;
						int blank = (int) (temp / 0.001);
						g.setColor(new Color(248, 224, 230));
						r[i] = 130 + 60 * i + 14;
						s[i] = 400 - (blank * 16);
					}
				}
				g2.setStroke(new BasicStroke(3));
				g2.setColor(Color.black);
				g2.drawPolyline(r, s, data.length);
				g2.setStroke(new BasicStroke(1));
				break;

			case "fine_dust":
				// 미세먼지 배경
				int f = 0;
				for (int cnt = 65; cnt < 400; cnt = cnt + 16) {
					g.setColor(new Color(189, 189, 189));
					if (x.length < 10) {
						g.drawLine(100, cnt, 90 * (x.length + 1), cnt); // x��
					} else {
						g.drawLine(100, cnt, 940, cnt); // x��
					}
					g.setColor(new Color(0, 0, 0));

					Double temp = Math.round((56 - (f * 2)) * 1) / 1.0; // y�� ��
					String str1 = Double.toString(temp);

					g.drawString(str1, 60, cnt + 5);
					// System.out.println(str1);
					f++;
				}
				setX(g);

				// 그래프
				for (int i = 0; i < data.length; i++) {
					double temp = Double.parseDouble(data[i]);
					temp -= 14;
					temp = Math.round(temp / .001) * .001;

					int blank = (int) (temp / 2);
					int rest = (int) temp % 2;
					if (design.equals("Bar")) {
						g.setColor(new Color(248, 224, 230));
						if (rest == 1) {
							g.fillRect(130 + 60 * i, 392 - (blank * 16), 30, 8 + blank * 16);
						} else {
							g.fillRect(130 + 60 * i, 400 - (blank * 16), 30, blank * 16);
						}
					} else {
						if (rest == 1) {
							r[i] = 130 + 60 * i + 14;
							s[i] = 392 - (blank * 16);
						} else {
							r[i] = 130 + 60 * i + 14;
							s[i] = 400 - (blank * 16);
						}
					}
				}
				g2.setStroke(new BasicStroke(3));
				g2.setColor(Color.black);
				g2.drawPolyline(r, s, data.length);
				g2.setStroke(new BasicStroke(1));
				break;

			case "ultrafine_dust":
				// 초미세먼지 배경
				int e = 0;
				for (int cnt = 32; cnt < 400; cnt = cnt + 16) {
					g.setColor(new Color(189, 189, 189));
					if (x.length < 10) {
						g.drawLine(100, cnt, 90 * (x.length + 1), cnt); // x��
					} else {
						g.drawLine(100, cnt, 940, cnt); // x��
					}
					g.setColor(new Color(0, 0, 0));

					Double temp = Math.round((33 - (e * 1)) * 1) / 1.0; // y�� ��
					String str1 = Double.toString(temp);

					g.drawString(str1, 60, cnt + 5);
					// System.out.println(str1);
					e++;
				}
				setX(g);

				// 그래프
				for (int i = 0; i < data.length; i++) {
					double temp = Double.parseDouble(data[i]);
					temp -= 10;
					temp = Math.round(temp / .001) * .001;
					int blank = (int) (temp / 1);

					while (temp > 0) {
						temp -= 1;
					}
					temp += 1;
					temp *= 10;
					if (design.equals("Bar")) {
						g.setColor(new Color(248, 224, 230));
						if (temp >= 8) {
							g.fillRect(130 + 60 * i, 388 - (blank * 16), 30, 12 + blank * 16);
						} else if (temp >= 4) {
							g.fillRect(130 + 60 * i, 393 - (blank * 16), 30, 7 + blank * 16);
						} else {
							g.fillRect(130 + 60 * i, 397 - (blank * 16), 30, 3 + blank * 16);
						}
					} else {
						if (temp >= 8) {
							r[i] = 130 + 60 * i + 14;
							s[i] = 388 - (blank * 16);
						} else if (temp >= 4) {
							r[i] = 130 + 60 * i + 14;
							s[i] = 393 - (blank * 16);
						} else {
							r[i] = 130 + 60 * i + 14;
							s[i] = 397 - (blank * 16);
						}
					}
				}
				g2.setStroke(new BasicStroke(3));
				g2.setColor(Color.black);
				g2.drawPolyline(r, s, data.length);
				g2.setStroke(new BasicStroke(1));

				break;

			default:
				break;
			}
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