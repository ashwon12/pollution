package pollutionproject;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random; 

public class Quiz extends JPanel{
	
	//private JRadioButton but1, but2;  //X 버튼  O 버튼
	private JButton button_O,button_X;
	private JLabel quizlabel; //반환된 퀴즈문제 받음
	private JPanel Center;
	private JPanel panel_OX, panel_QUIZ; // 버튼그룹패널, 퀴즈문제 패널
	private Random rand;
	private int index;
	private Font f1;
	private QuizItem[] quizitem = new QuizItem[15];
	
	//생성자
	public Quiz() {
		//퀴즈 문제
		quizitem[0] = new QuizItem("1952년 런던에서 발명하여 많은 사람들을 사망하게 한 것은 황사이다.", "X");
		quizitem[1] = new QuizItem("환경을 오염시키는 행동을 전화로 신고할 수 있다.<br>이 환경신문고의 전화번호는 128이다.", "O");
		quizitem[2] = new QuizItem("지구온난화의 주범으로 알려진 온실가스들은 모두 사람들에 의해서 만들어진 것들이다.", "X");
		quizitem[3] = new QuizItem("태양이 쨍쨍 내리쬐는 한 여름에 나무 그늘을 찾아 들어가면 상쾌한 이유는 <br>나무가 뿜어내는 수증기 때문이다.", "X");
		quizitem[4] = new QuizItem("신재생에너지는 신에너지와 재생에너지를 합쳐 부르는 말이다.", "O");
		quizitem[5] = new QuizItem("세계 지구의 날은 매년 4월 22일이다. <br>우리나라는 이날을 전후하여 기후변화 주간으로 정하여 다양한 행사를 진행하고 있다.", "O");
		quizitem[6] = new QuizItem("하루 중 전력 소비량이 최대가 되는 여름철 전력피크 시간대는 오후 2시부터 5시이다.", "O");
		quizitem[7] = new QuizItem("하루 중 전력 소비량이 최대가 되는 겨울철 전력피크 시간대는 오후 2시부터 5시이다.", "X");
		quizitem[8] = new QuizItem("기상청은 100년 뒤와 같은 먼 미래 기후변화를 예측하여 발표한다.", "O");
		quizitem[9] = new QuizItem("지구온난화에 따른 기후변화에 적극 대처하기 위하여 <br>1988년 UN총회 결의 에 따라 세계기상기구와 유엔환경계획에 설치한 위원회가 IPCC이다.", "O");
		quizitem[10] = new QuizItem("지구 온난화를 유발시키는 온실가스와 반대되는 것으로 <br>지구를 냉각화시키는 역할을 하는 고체 또는 액체의 미립자가 에어러졸이다.", "O");
		quizitem[11] = new QuizItem("대기오염물질 중 인체에 미치는 직접적 영향이 강해서 <br>오염경보제를 운영하고 있는 물질은 오존이다.", "O");
		quizitem[12] = new QuizItem("지난 여름은 다른 해 보다도 무척 더웠다. 이것은 지구가 온난화되고 있다는 증거이다.", "X");
		quizitem[13] = new QuizItem("지구온난화에 따라 동물들의 몸집이 작아지고 있다.", "O");
		quizitem[14] = new QuizItem("온실가스는 태양복사에너지는 그대로 통과시키지만 <br>지구복사에너지는 흡수하는 특징이 있다.", "O");
	    
		setLayout(new BorderLayout());
	    setBackground(Color.white);
	     
	    Center = new JPanel(new GridLayout(2,1));
	    Center.setBackground(Color.white);
	    
	    //랜덤 변수 생성
	    rand = new Random();
	    //0부터 14까지의 임의의 정수
	    index = rand.nextInt(15);
	 
	    quizlabel = new JLabel("<html><br><br><br><br><hr><b>"+quizitem[index].getQuiz()+"</b><hr></html>",JLabel.CENTER); //퀴즈 문제 label객체 생성
	    f1 = new Font("돋움", Font.PLAIN, 20);
	    quizlabel.setFont(f1);
	    panel_QUIZ = new JPanel(); //패널객체생성
	    panel_QUIZ.add(quizlabel,BorderLayout.NORTH); //페널에 퀴즈 문제 추가
	    panel_QUIZ.setBackground(Color.white);
	   
	    //O, X 버튼
	    button_O = new JButton(new ImageIcon("./images/true.png")); //O 버튼 생성
	    button_X = new JButton(new ImageIcon("./images/false.png")); //X 버튼 생성
	    
	    button_O.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));
	    button_X.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));
	    button_O.setBackground(Color.white);
	    button_X.setBackground(Color.white); 
	    
	    panel_OX = new JPanel(); //패널 객체 생성
	    panel_OX.setLayout(new GridLayout(1,2)); //5행 4열
	    panel_OX.add(button_O); //패널에 버튼 추가
	    panel_OX.add(button_X); //패널에 버튼 추가
	    
	    //버튼 사건 리스너 객체를 생성한다.
	    ButtonListener listener = new ButtonListener();  
	    button_O.addActionListener(listener); //저장버튼을 추가한다
	    button_X.addActionListener(listener); //저장버튼을 추가한다
	    
	    Center.add(panel_QUIZ);
	    Center.add(panel_OX);
	   
	    add(Center, BorderLayout.CENTER);
	   
	}

	private class ButtonListener implements ActionListener{
		
		String result = "";
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == button_O) { //정답을 O로 입력한 경우
				result = quizitem[index].getT("O");
				JOptionPane.showMessageDialog(null,result); //메시지박스로 정답 여부 나타내기
			}else if(e.getSource() == button_X){ //정답을 X로 입력한 경우
				result = quizitem[index].getF("X");
				JOptionPane.showMessageDialog(null,result); //메시지박스로 정답 여부 나타내기
			}
		}
	}
}
