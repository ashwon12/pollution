package pollutionproject;

import javax.swing.*;
import java.awt.*;

public class Home extends JPanel {

	private JPanel main;
	private JLabel welcome; // 웰컴!!
	private JLabel stuNum; // 학번

	// 폰트
	private Font Big = new Font("맑은 고딕", Font.BOLD, 23);
	private Font middle = new Font("맑은 고딕", Font.PLAIN, 17);
	private Font small = new Font("맑은 고딕", Font.BOLD, 13);

	public Home() {

		setLayout(new BorderLayout());
		setSize(1000, 600);

		main = new JPanel();
		main.setLayout(new BorderLayout());

		welcome = new JLabel("🌸Welcome🌸");
		main.setBackground(Color.white);
		welcome.setFont(welcome.getFont().deriveFont(70.0f)); // 폰트 크기 변경
		welcome.setBackground(Color.white);
		welcome.setHorizontalAlignment(JLabel.CENTER);

		stuNum = new JLabel("<html>60182208 장세영<br>60182155 김재원<br>60182153 김은주 <br> 60182174 서예지<br><br><br></html>");
		stuNum.setFont(welcome.getFont().deriveFont(20.0f));
		stuNum.setBackground(Color.white);
		stuNum.setHorizontalAlignment(JLabel.CENTER);

		main.add(welcome, BorderLayout.CENTER);
		main.add(stuNum, BorderLayout.SOUTH);

		add(main);
	}
}
