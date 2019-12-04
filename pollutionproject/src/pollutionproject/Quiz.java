package pollutionproject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Quiz extends JPanel {

	Container contentpane;
	private JPanel Center;// 퀴즈 아이템
	private JPanel[] p1 = new JPanel[5];
	private JPanel South; // 버튼

	// 퀴즈 아이템
	private QuizItem[] quizitem = new QuizItem[15];
	private JLabel[] quizlabel = new JLabel[5];
	private Random random;
	private Integer index;

	private JLabel label;
	private Set<Integer> set = new HashSet<>();

	// 라디오버튼
	private ButtonGroup[] group = new ButtonGroup[5];
	private JRadioButton[] radio = new JRadioButton[10];

	// 버튼
	private JButton showResult;

	// 폰트
	private Font Big = new Font("맑은 고딕", Font.BOLD, 23);
	private Font middle = new Font("맑은 고딕", Font.PLAIN, 17);

	public Quiz() {
		// 퀴즈 문제
		quizitem[0] = new QuizItem("1952년 런던에서 발명하여 많은 사람들을 사망하게 한 것은 황사이다.", false);
		quizitem[1] = new QuizItem("<html><center>환경을 오염시키는 행동을 전화로 신고할 수 있다.<br>이 환경신문고의 전화번호는 128이다.</html>", true);
		quizitem[2] = new QuizItem("지구온난화의 주범으로 알려진 온실가스들은 모두 사람들에 의해서 만들어진 것들이다.", false);
		quizitem[3] = new QuizItem(
				"<html><center>태양이 쨍쨍 내리쬐는 한 여름에 나무 그늘을 찾아 들어가면 상쾌한 이유는 <br>나무가 뿜어내는 수증기 때문이다.</html>", false);
		quizitem[4] = new QuizItem("신재생에너지는 신에너지와 재생에너지를 합쳐 부르는 말이다.", true);
		quizitem[5] = new QuizItem(
				"<html><center>세계 지구의 날은 매년 4월 22일이다. <br>우리나라는 이날을 전후하여 기후변화 주간으로 정하여 다양한 행사를 진행하고 있다.</html>", true);
		quizitem[6] = new QuizItem("하루 중 전력 소비량이 최대가 되는 여름철 전력피크 시간대는 오후 2시부터 5시이다.", true);
		quizitem[7] = new QuizItem("하루 중 전력 소비량이 최대가 되는 겨울철 전력피크 시간대는 오후 2시부터 5시이다.", false);
		quizitem[8] = new QuizItem("기상청은 100년 뒤와 같은 먼 미래 기후변화를 예측하여 발표한다.", true);
		quizitem[9] = new QuizItem(
				"<html><center>지구온난화에 따른 기후변화에 적극 대처하기 위하여 <br>1988년 UN총회 결의 에 따라 세계기상기구와 유엔환경계획에 설치한 위원회가 IPCC이다.</html>",
				true);
		quizitem[10] = new QuizItem(
				"<html><center>지구 온난화를 유발시키는 온실가스와 반대되는 것으로 <br>지구를 냉각화시키는 역할을 하는 고체 또는 액체의 미립자가 에어러졸이다.</html>", true);
		quizitem[11] = new QuizItem("<html><center>대기오염물질 중 인체에 미치는 직접적 영향이 강해서 <br>오염경보제를 운영하고 있는 물질은 오존이다.</html>",
				true);
		quizitem[12] = new QuizItem("지난 여름은 다른 해 보다도 무척 더웠다. 이것은 지구가 온난화되고 있다는 증거이다.", false);
		quizitem[13] = new QuizItem("지구온난화에 따라 동물들의 몸집이 작아지고 있다.", true);
		quizitem[14] = new QuizItem("<html><center>온실가스는 태양복사에너지는 그대로 통과시키지만 <br>지구복사에너지는 흡수하는 특징이 있다.</html>", true);

		// 패널 추가
		this.setLayout(new BorderLayout());
		this.setBackground(Color.white);

		// 퀴즈 들어갈 패널
		Center = new JPanel(new GridLayout(5, 1));
		for (int k = 0; k < 5; k++) {
			p1[k] = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 30));
			p1[k].setBackground(Color.white);
		}
		// 버튼 들어갈 패널
		South = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 30));
		South.setBackground(Color.white);

		// 라디오버튼
		for (int j = 0, k = 1; j < 10; j += 2, k += 2) {
			// O버튼 라디오들
			radio[j] = new JRadioButton("O");
			radio[j].setFont(Big);
			radio[j].setBackground(Color.white);
			// System.out.println("O가 나와야 함"+radio[j]);
			// X버튼 라디오들
			radio[k] = new JRadioButton("X");
			radio[k].setFont(Big);
			radio[k].setBackground(Color.white);
			// System.out.println("X가 나와야 함"+radio[k]);
		}

		// 라디오 버튼 두개씩 그룹화
		group[0] = new ButtonGroup();
		group[1] = new ButtonGroup();
		group[2] = new ButtonGroup();
		group[3] = new ButtonGroup();
		group[4] = new ButtonGroup();

		for (int j = 0, k = 0; k < 10; j++, k += 2) {
			group[j].add(radio[k]);
			group[j].add(radio[k + 1]);
		}

		// 랜덤 퀴즈 말
		random = new Random();

		while (set.size() < 5) {
			index = random.nextInt(15);
			set.add(index);
		}

		// 퀴즈 라벨 넣기
		int count = 0;
		for (int index : set) {
			if (count == 5) {
				break;
			}
			quizlabel[count] = new JLabel(quizitem[index].getQuiz(), JLabel.CENTER);// 퀴즈 문제 label객체 생성
			quizlabel[count].setFont(middle);
			p1[count].add(quizlabel[count]);
			count++;

		}

		for (int j = 0, k = 0; j < 5; j++, k += 2) {
			p1[j].add(radio[k]);
			p1[j].add(radio[k + 1]);
			Center.add(p1[j]);
		}

		// 버튼
		showResult = new JButton(" 점수 보기 ");
		showResult.setFont(middle);
		showResult.setBackground(new Color(242, 242, 242));

		//
		Listener listener = new Listener();
		showResult.addActionListener(listener);

		// 추가
		South.add(showResult);
		this.add(Center, BorderLayout.CENTER);
		this.add(South, BorderLayout.SOUTH);

	}

	private class Listener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == showResult) { // 정답보기 버튼 눌렀을 경우
				System.out.println("점수보기 버튼 클릭");
				int score = 0;

				boolean[] myanswer = new boolean[5];

				int count = 0;
				for (int k = 0; k < 10; k++) {
					if (count == 5)
						break;
					if (radio[k].isSelected()) {
						if (radio[k].getText() == "O") {
							myanswer[count] = true;
						} else {
							myanswer[count] = false;
						}
						count++;
					}
				}
				if (count < 5) {
					JOptionPane.showMessageDialog(null, "모든 문제를 풀어주세요!");
					return;
				}

				int i = 0;
				for (int index : set) {

					if (i == 5)
						break;

					if (quizitem[index].isRight(myanswer[i])) {
						score = score + 20;
						i++;
					} else {
						i++;
						continue;
					}

				}

				JOptionPane.showMessageDialog(null, "당신의 점수는 " + score + "점 입니다!");

			}

		}
	}
}
