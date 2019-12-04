package pollutionproject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import java.awt.*;

public class AreaGraph extends JFrame {

	// 패널
	Container contentpane;
	private JPanel South;
	private JButton close;

	// 변수
	private double max; // 최댓값
	private double min; // 최소값
	private Double[] range = new Double[16];
	private String yIndex;// y축 입력값

	// 가져온 값
	private String[] date = new String[7];
	private String[] data = new String[7];
	private String myGas;
	private String design;

	// 폰트
	private Font small = new Font("", Font.PLAIN, 15);
	private Font middle = new Font("맑은 고딕", Font.BOLD, 17);

	public AreaGraph(String[] date, String[] data, String myGas, String design) {

		this.date = date;
		this.data = data;
		this.myGas = myGas;
		this.design = design;

		this.setTitle("지역별" + "" + this.myGas + "" + "평균 조회 그래프");
		this.setSize(900, 600);
		this.setLocation(500, 200);

		// 패널 및 클래스
		contentpane = getContentPane();
		contentpane.setLayout(new BorderLayout());
		South = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 30));

		// 버튼
		close = new JButton("close");
		close.setFont(middle);

		// 색상 지정
		contentpane.setBackground(Color.white);
		close.setBackground(new Color(242, 242, 242));
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
		public void paint(Graphics g) {

			super.paint(g);
			Graphics2D g2 = (Graphics2D) g;
			g.clearRect(0, 0, 900, 500);
			g.drawLine(100, 400, 800, 400); // x축
			g.drawLine(100, 60, 100, 400); // y축

			// 데이터 값들을 더블에 저장.
			double[] numberData = new double[data.length];

			for (int i = 0; i < data.length; i++) {
				try {
					numberData[i] = Double.parseDouble(data[i]);
				} catch (NumberFormatException e) {
					numberData[i] = 0;
				}
			}

			// 최대값
			max = numberData[0];
			for (int i = 0; i < data.length; i++) {
				max = max > numberData[i] ? max : numberData[i];
			}

			// 최솟값
			for (int i = 0; i < data.length; i++) {
				min = numberData[i];
				if (min != 0) {
					break;
				}
			}

			for (int i = 0; i < data.length; i++) {
				if (numberData[i] == 0)
					continue;
				min = min < numberData[i] ? min : numberData[i];
			}
			// System.out.println("min: "+min);

			// 간격
			double val = Math.round(((max - min) / 15) * 10000) / 10000.0;

			// y축
			int a = 0;
			for (int cnt = 80; cnt < 400; cnt = cnt + 20) {
				g.setColor(new Color(189, 189, 189));
				g.drawLine(100, cnt, 800, cnt);
				g.setColor(new Color(0, 0, 0));

				Double temp;

				if (max == min) {
					temp = Math.round((max - (a * 0.0001)) * 10000) / 10000.0;
				} else {
					temp = Math.round((max - (a * val)) * 10000) / 10000.0;
				}
				range[a] = temp;
				// System.out.println("y축은 : "+range[a]);
				yIndex = Double.toString(temp);
				g.drawString(yIndex, 50, cnt + 5);
				a++;
			}

			// x축
			int i = 0;
			g.setFont(small);
			for (int cnt = 125; cnt < 900 && i < 7; cnt = cnt + 100) {
				g.drawString(date[i], cnt, 425);
				i++;
			}
			// 막대그리기
			int[] r = new int[7];
			int[] s = new int[7];
			for (i = 0; i < 7; i++) {
				double mg = numberData[i];
				System.out.println(mg);

				if (mg == 0) {
					if (!design.equals("Bar")) {
						r[i] = 145 + i * 100 + 14;
						s[i] = 400;
					}
					continue;
				}

				for (int j = 0; j < 7; j++) {

					if (design.equals("Bar")) {
						g.setColor(new Color(248, 224, 230));
						if (mg == range[j]) {
							g.fillRect(145 + i * 100, 80 + (j * 20), 30, 320 - j * 20);
						} else if (mg < range[j] && mg > range[j + 1]) {
							g.fillRect(145 + i * 100, 80 + (j * 20 + 10), 30, 320 - (j * 20 + 10));
						}
						if (mg < range[7]) {
							g.fillRect(145 + i * 100, 80 + (15 * 20 + 10), 30, 320 - (15 * 20 + 10));
						}
					} else {

						if (mg == range[j]) {
							r[i] = 145 + i * 100 + 14;
							s[i] = 80 + (j * 20);
						} else if (mg < range[j] && mg > range[j + 1]) {
							r[i] = 145 + i * 100 + 14;
							s[i] = 80 + (j * 20 + 10);
						}
						if (mg < range[7]) {
							r[i] = 145 + i * 100 + 14;
							s[i] = 80 + (15 * 20 + 10);
						}
					}
				}
			}
			g2.setStroke(new BasicStroke(3));
			g2.setColor(Color.black);
			g2.drawPolyline(r, s, 7);
			g2.setStroke(new BasicStroke(1));

		}

	}

	private class Listener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			;
			if (e.getSource() == close) { // 결과보기 버튼을 눌렀을 경우
				// System.out.println("닫기 버튼 클릭");
				setVisible(false);
			}
		}
	}

}
